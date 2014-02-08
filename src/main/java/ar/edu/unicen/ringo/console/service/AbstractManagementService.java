package ar.edu.unicen.ringo.console.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.stream.BytesStreamOutput;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unicen.ringo.console.model.Identificable;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract management service, that should encapsulate most of the common
 * behaviour.
 * @author psaavedra
 */
public class AbstractManagementService<T extends Identificable> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Client client;

    private ObjectMapper mapper = new ObjectMapper();

    private final String index;

    private final String entity;

    private final Class<T> clazz;

    public AbstractManagementService(String index, String entity, Class<T> clazz) {
        this.index = index;
        this.entity = entity;
        this.clazz = clazz;
    }

    /**
     * Saves an entity into elastic search.
     *
     * @param object
     *            The entity to save.
     */
    public void save(T object) {
        try {
        	IndexRequestBuilder indexRequestBuilder = object.getId() == null ?  client.prepareIndex(index, entity) : client.prepareIndex(index, entity, object.getId()); 
            IndexResponse response = indexRequestBuilder
                    .setSource(mapper.writeValueAsString(object))
                    .execute()
                    .actionGet();
            //Force refresh.
            object.setId(response.getId());
            client.admin().indices().prepareRefresh(index).execute().actionGet();
            if (logger.isDebugEnabled()) {
                BytesStreamOutput out = new BytesStreamOutput(1024 * 1024);
                response.writeTo(out);
                logger.debug("Obtained response: {}", new String(out.bytes()
                        .array()));
            }
        } catch (ElasticSearchException | IOException e) {
            logger.error("Error while saving entity", e);
        }
    }

    public T get(String id) {
        GetResponse result = client.prepareGet(index, entity, id).execute()
                .actionGet();
        String source = result.getSourceAsString();
        System.out.println("Loaded SLA: " + source);
        try {
            T entity = mapper.readValue(source, clazz);
            entity.setId(result.getId());
            logger.info("Successfully obtained entity with id [{}]", id);
            return entity;
        } catch (IOException e) {
            logger.error("Error retrieving entity with id " + id, e);
            return null;
        }
    }

    public List<T> list() {
    	List<T> results = new ArrayList<>(0);
    	try {
    		SearchResponse response = client.prepareSearch(index).setTypes(entity)
                .execute().actionGet();
    		SearchHits hits = response.getHits();
	        results = new ArrayList<>(hits.getHits().length);
	        for (SearchHit hit : hits) {
	            String source = hit.getSourceAsString();
	            if (source == null) {
	                continue;
	            }
	            try {
	                T result = mapper.readValue(source, clazz);
	                result.setId(hit.getId());
	                results.add(result);
	            } catch (IOException e) {
	                logger.error("Error listing entities of type " + this.entity, e);
	            }
	        }
    	} catch (IndexMissingException e){
    		logger.error("Error listing entities of type " + this.entity, e);
        }
        return results;
    }

    public void delete(String id) {
        logger.info("Deleting entity with id {}", id);
        client.prepareDelete(index, entity, id).execute().actionGet();
        client.admin().indices().prepareRefresh(index).execute().actionGet();
        logger.info("Entity with id {} deleted successfully", id);
    }
}

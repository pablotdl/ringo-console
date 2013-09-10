package ar.edu.unicen.ringo.console.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unicen.ringo.console.model.Identificable;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract management service, that should encapsulate most of the common
 * behaviour.
 * @author psaavedra
 */
public class AbstractManagementService<T extends Identificable> {
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

    public void save(T object) {
        try {
            IndexResponse response = client.prepareIndex(index, entity)
                    .setSource(mapper.writeValueAsString(object))
                    .execute()
                    .actionGet();
            //Force refresh.
            object.setId(response.getId());
            client.admin().indices().prepareRefresh(index).execute().actionGet();
            response.writeTo(new OutputStreamStreamOutput(System.out));
        } catch (ElasticSearchException | IOException e) {
            e.printStackTrace();
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
            System.out.println("Created entity");
            return entity;
        } catch (IOException e) {
            System.out.println("Error creating entity");
            e.printStackTrace();
            return null;
        }
    }

    public List<T> list() {
        SearchResponse response = client.prepareSearch(index).setTypes(entity)
                .execute().actionGet();
        SearchHits hits = response.getHits();
        List<T> results = new ArrayList<>(hits.getHits().length);
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return results;
    }

    public void delete(String id) {
        client.prepareDelete(index, entity, id).execute().actionGet();
        client.admin().indices().prepareRefresh(index).execute().actionGet();
    }
}

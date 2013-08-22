package ar.edu.unicen.ringo.console.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unicen.ringo.console.model.Sla;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service class that provides methods for adding, removing or modifying SLAs
 * and nodes.
 * @author psaavedra
 */
@Service
public class SlaManagementService {

    @Autowired
    private Client client;

    private ObjectMapper mapper = new ObjectMapper();

    public void save(Sla sla) {
        try {
            IndexResponse response = client.prepareIndex("agent", "sla")
                    .setSource(mapper.writeValueAsString(sla))
                    .execute()
                    .actionGet();
            //Force refresh.
            sla.setId(response.getId());
            client.admin().indices().prepareRefresh("agent").execute().actionGet();
            response.writeTo(new OutputStreamStreamOutput(System.out));
        } catch (ElasticSearchException | IOException e) {
            e.printStackTrace();
        }
    }

    public Sla getSla(String id) {
        GetResponse result = client.prepareGet("agent", "sla", id).execute()
                .actionGet();
        String source = result.getSourceAsString();
        System.out.println("Loaded SLA: " + source);
        try {
            Sla sla = mapper.readValue(source, Sla.class);
            sla.setId(result.getId());
            System.out.println("Created SLA");
            return sla;
        } catch (IOException e) {
            System.out.println("Error creating sla");
            e.printStackTrace();
            return null;
        }
    }

    public List<Sla> listSlas() {
        SearchResponse response = client.prepareSearch("agent").setTypes("sla")
                .execute().actionGet();
        SearchHits hits = response.getHits();
        List<Sla> results = new ArrayList<>(hits.getHits().length);
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            if (source == null) {
                continue;
            }
            try {
                Sla sla = mapper.readValue(source, Sla.class);
                sla.setId(hit.getId());
                results.add(sla);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return results;
    }

    public void delete(String id) {
        DeleteResponse result = client.prepareDelete("agent", "sla", id)
                .execute().actionGet();
    }
}

package ar.edu.unicen.ringo.console.ui;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

public class ElasticSearchClientTest {

	@Test
	public void test() {
		// on startup
		Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", true).build();
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		
		try {
			SearchResponse searchresponse = client.prepareSearch("agent").setSize(100).execute().actionGet();
			
			System.out.println("total: " + searchresponse.getHits().getTotalHits());
			for(SearchHit hit : searchresponse.getHits()) {
				System.out.println(hit.getSourceAsString());
			} 
		}catch (Exception e) {
			System.out.println("error");
		}
		// on shutdown
		client.close();
	}

}

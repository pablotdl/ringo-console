package ar.edu.unicen.ringo.console.ui;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.junit.Ignore;
import org.junit.Test;

public class ElasticSearchCleanTest {
    
	@Test
	public void test() {
		
		// on startup
		Node node = nodeBuilder().node();
		Client client = node.client();
		
		SearchResponse searchresponse = client.prepareSearch().setSize(100).execute().actionGet();
		assertThat(searchresponse.status(), is(RestStatus.OK));
		
		System.out.println("getTotalHits: " + searchresponse.getHits().getTotalHits());
		if ( searchresponse.getHits().getTotalHits() > 0 ) {
			BulkRequestBuilder bulkRequest = node.client().prepareBulk();
			
			for(SearchHit hit : searchresponse.getHits()) {
				System.out.println("getIndex" + hit.getIndex());
				System.out.println("getType: " + hit.getType());
				System.out.println("getId: " + hit.getId());
				
				bulkRequest.add(client.prepareDelete(hit.getIndex(), hit.getType(), hit.getId()));
			} 		
			
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			System.out.println("hasFailures: " + bulkResponse.hasFailures());
			assertThat(bulkResponse.hasFailures(), is(false));
		}

		// on shutdown
		node.close();
		
	}

}

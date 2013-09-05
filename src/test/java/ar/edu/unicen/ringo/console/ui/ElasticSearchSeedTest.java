package ar.edu.unicen.ringo.console.ui;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.junit.Test;

public class ElasticSearchSeedTest {

	@Test
	public void test() throws IOException {
		
		String[] keys = {"SA_L9I6KSnaK5p_DZDGswA","r9iFXyW4Q4OHMsLyYYaJOQ"};
		
		Map<String, String[]> slas = new Hashtable<String, String[]>();
		
		String[] sla1 = {"node1","node2","node3"};
		slas.put("SA_L9I6KSnaK5p_DZDGswA", sla1);
		String[] sla2 = {"node4","node5","node6"};
		slas.put("r9iFXyW4Q4OHMsLyYYaJOQ", sla2);

		
		String[] methods = {"ME","TO","DO"}; 
		
		// on startup
		Client client = nodeBuilder().node().client();
		
		BulkRequestBuilder bulkRequest = client.prepareBulk();

		Random randomGenerator = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		for(int i=0; i<100; i++) {
			String sla = keys[randomGenerator.nextInt(2)];
			String node = slas.get(sla)[randomGenerator.nextInt(3)];
			String method = methods[randomGenerator.nextInt(3)];
			
			bulkRequest.add(client.prepareIndex("agent", "invocation")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("sla", sla)
			                        .field("node", node)
			                        .field("method", method)
			                        .field("execution_time", randomGenerator.nextInt(1000))
			                        .field("timestamp",  sdf.format(System.currentTimeMillis() - 3600000) )
			                    .endObject()
			                  )
			        );
		}
		
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		assertThat(bulkResponse.hasFailures(), is(false));
		
		// on shutdown
		nodeBuilder().node().close();		
	}

}

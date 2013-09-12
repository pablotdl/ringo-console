package ar.edu.unicen.ringo.console.ui;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacetBuilder;
import org.junit.Test;

public class ElasticSearchHistogramFacetTest {

	@Test
	public void test() {
		Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		
		DateHistogramFacetBuilder facet = FacetBuilders.dateHistogramFacet("histogram")
						.keyField("timestamp")
						.valueField("execution_time")
						.interval("hour");
			
		// Query Filters: SLA & Range by Period 
		QueryBuilder query = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("sla", "kpEFXSbtSymPMTSdSfHMhA"));
					//.must(QueryBuilders.rangeQuery("timestamp").from(period_from).to(period_to));
			
		// Client Search
		SearchResponse searchresponse = client.prepareSearch("agent").setTypes("invocation")
					.setQuery(query)
					.addFacet(facet)
					.execute().actionGet();
			
			// Get Histogram for SLA
		DateHistogramFacet histogram = (DateHistogramFacet) searchresponse.getFacets().facetsAsMap().get("histogram");
		System.out.println("total: " + histogram.getEntries().get(0).getTotal());
		
		// on shutdown
		client.close();
	}

}

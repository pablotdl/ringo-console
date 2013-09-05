package ar.edu.unicen.ringo.console.ui;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacetBuilder;
import org.elasticsearch.search.facet.histogram.HistogramFacet;
import org.junit.Test;

public class ElasticSearchHistogramFacetTest {

	@Test
	public void test() {
		// on startup
		Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));		
		
		//TermsFacetBuilder termsFacetBuilder = FacetBuilders.termsFacet("termsFacet").field("node").size(10);
		DateHistogramFacetBuilder dateHistogramFacetBuilder = FacetBuilders.dateHistogramFacet("dateHistogramFacet").field("timestamp").interval("year");
		//HistogramFacetBuilder histogramFacetBuilder = FacetBuilders.histogramFacet("histogramFacet").field("execution_time").interval(100);
		QueryBuilder query = QueryBuilders.boolQuery()
				.must(QueryBuilders.matchQuery("sla","r9iFXyW4Q4OHMsLyYYaJOQ"));
		
		SearchResponse searchresponse = client.prepareSearch()
				.setQuery(query)
				.addFacet(dateHistogramFacetBuilder)
				//.addFacet(histogramFacetBuilder)
				.execute().actionGet();
		// assertThat(searchresponse.status(), is(RestStatus.OK));
		
		SearchHits hits = searchresponse.getHits();
		for (SearchHit hit: hits) {
			System.out.println(hit.field("sla"));
		}
		// TERM
		DateHistogramFacet termsFacet = (DateHistogramFacet) searchresponse.getFacets().facetsAsMap().get("dateHistogramFacet");
		
		System.out.println("name: " + termsFacet.getName());
		System.out.println("type: " + termsFacet.getType());
		
		System.out.println(": " + termsFacet.getEntries().get(0).getCount());
		for (DateHistogramFacet.Entry entry : termsFacet) {
		    System.out.println(": " + entry.getCount());
		}
		
		
		// HISTOGRAM
		HistogramFacet histogramFacet = (HistogramFacet) searchresponse.getFacets().facetsAsMap().get("histogramFacet");

		// For each entry
		for (HistogramFacet.Entry entry : histogramFacet) {
			System.out.println(entry.getKey() + ": " + entry.getCount());
		}		
		
		// on shutdown
		client.close();
	}

}

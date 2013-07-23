package ar.edu.unicen.ringo.console.ui;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.histogram.HistogramFacet;
import org.elasticsearch.search.facet.histogram.HistogramFacetBuilder;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;
import org.junit.Test;

public class ElasticSearchHistogramFacetTest {

	@Test
	public void test() {
		// on startup
		Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));		
		
		TermsFacetBuilder termsFacetBuilder = FacetBuilders.termsFacet("termsFacet").field("node").size(10);
		//DateHistogramFacetBuilder dateHistogramFacetBuilder = FacetBuilders.dateHistogramFacet("dateHistogramFacet").field("timestamp").interval("year");
		HistogramFacetBuilder histogramFacetBuilder = FacetBuilders.histogramFacet("histogramFacet").field("execution_time").interval(100);
		
		SearchResponse searchresponse = client.prepareSearch()
				.addFacet(termsFacetBuilder)
				.addFacet(histogramFacetBuilder)
				.execute().actionGet();
		// assertThat(searchresponse.status(), is(RestStatus.OK));
		
		
		// TERM
		TermsFacet termsFacet = (TermsFacet) searchresponse.getFacets().facetsAsMap().get("termsFacet");
		
		System.out.println("total: " + termsFacet.getTotalCount());
		System.out.println("other: " + termsFacet.getOtherCount());
		System.out.println("missing: " + termsFacet.getMissingCount());
		
		for (TermsFacet.Entry entry : termsFacet) {
		    System.out.println(entry.getTerm() + ": " + entry.getCount());
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

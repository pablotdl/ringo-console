package ar.edu.unicen.ringo.console.ui;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacetBuilder;
import org.elasticsearch.search.facet.termsstats.TermsStatsFacet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {
	
	@RequestMapping("/")
	public String index(ModelMap model, @RequestParam(value="period", defaultValue="hour") String period) {
		System.out.println("period: " + period);
		
		
		Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		
		DateHistogramFacetBuilder builder = FacetBuilders.dateHistogramFacet("histogram")
				.keyField("timestamp")
				.valueField("execution_time")
				.interval(period);
		//RangeQueryBuilder query = QueryBuilders.rangeQuery("timestamp").from("2010-03-01").to("2014-04-01");
		
		SearchResponse searchresponse = client.prepareSearch()
				//.setQuery(query)
				.addFacet(builder)
				.execute().actionGet();
		
		DateHistogramFacet histogram = (DateHistogramFacet) searchresponse.getFacets().facetsAsMap().get("histogram");
		
		model.addAttribute("facets", histogram);
		model.addAttribute("period", period);
		model.addAttribute("message", "Spring 3 MVC Hello World");
		return "index";
 
	}

}

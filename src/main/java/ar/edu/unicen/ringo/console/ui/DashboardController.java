package ar.edu.unicen.ringo.console.ui;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet.Entry;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacetBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.SlaManagementService;

@Controller
public class DashboardController {

	@Autowired
	private SlaManagementService service;
	
	@Autowired
	private Client client;	
	
	@RequestMapping("/")
	public String index(ModelMap model, @RequestParam(value="period", defaultValue="hour") String period) {
		
		List<Sla> slas = service.list();
		
		long to = System.currentTimeMillis();
		long from = to - millisecondsForPeriod(period,7);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String period_from = sdf.format(from);
		String period_to   = sdf.format(to);
		
		HashMap<String, DateHistogramFacet> histograms = new HashMap<String, DateHistogramFacet>();
		for(Sla sla : slas) {
			System.out.println("sla: " + sla.getId());
			// Facet by Period 
			DateHistogramFacetBuilder facet = FacetBuilders.dateHistogramFacet("histogram")
						.keyField("timestamp")
						.valueField("execution_time")
						.interval(period);
			
			// Query Filters: SLA & Range by Period 
			QueryBuilder query = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("sla", sla.getId()));
					//.must(QueryBuilders.rangeQuery("timestamp").from(period_from).to(period_to));
			
			// Client Search
			SearchResponse searchresponse = client.prepareSearch("agent").setTypes("invocation")
					.setQuery(query)
					.addFacet(facet)
					.execute().actionGet();
			
			// Get Histogram for SLA
			DateHistogramFacet histogram = (DateHistogramFacet) searchresponse.getFacets().facetsAsMap().get("histogram");
			histograms.put(sla.getId(), histogram);
		}
				
		model.addAttribute("period", period);
		model.addAttribute("slas", slas);
		model.addAttribute("histograms", histograms);
		return "index";
 
	}
	
	private long millisecondsForPeriod(String period, long range) {
		long base = 3600 * 1000; // 1 hour
		
		long factor;
		if (period.equals("hour")) {
			factor = 1; // 1 hour
		}else if (period.equals("day")) {
			factor = 24; // 1 day
		} else {
			factor = 7 * 24; // 1 week
		}
		
		return range * factor * base;
	}

}

package ar.edu.unicen.ringo.console.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacetBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.SlaManagementService;
import ar.edu.unicen.ringo.console.ui.dto.FlotData;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory
            .getLogger(DashboardController.class);

	@Autowired
	private SlaManagementService service;
	
	@Autowired
	private Client client;	
	
	@RequestMapping("/")
	public String index(ModelMap model, @RequestParam(value="period", defaultValue="hour") String period) {
		
		List<Sla> slas = service.list();
		
		long to = System.currentTimeMillis();
		long from = to - millisecondsForPeriod(period,27);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String period_from = String.valueOf(from); //sdf.format(from);
		String period_to   = String.valueOf(to); //sdf.format(to);
		
		HashMap<String, DateHistogramFacet> histograms = new HashMap<String, DateHistogramFacet>();
		for(Sla sla : slas) {
			logger.debug("Sla: {}", sla.getId());
			// Facet by Period 
			DateHistogramFacetBuilder facet = FacetBuilders.dateHistogramFacet("histogram")
						.keyField("timestamp")
						.valueField("execution_time")
						.interval(period);
			
			// Query Filters: SLA & Range by Period 
			QueryBuilder query = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("sla", sla.getId()))
					.must(QueryBuilders.rangeQuery("timestamp").from(period_from).to(period_to));
			
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
	
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)	
    public List<FlotData> data(@RequestParam(value="period", defaultValue="hour") String period) {
    	List<FlotData> data = new ArrayList<FlotData>();
    	FlotData fd_1 = new FlotData();
    	List<int[]> fd_1_d = new ArrayList<int[]>();
    	int[] list = {1,2};
    	
    	fd_1.setLabel("jeronimo");
    	fd_1.setData(fd_1_d);
    	fd_1_d.add(list);

    	
    	FlotData fd_2 = new FlotData();
    	fd_2.setLabel("andres");
    	
    	data.add(fd_1);
    	data.add(fd_2);
    	return data;
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

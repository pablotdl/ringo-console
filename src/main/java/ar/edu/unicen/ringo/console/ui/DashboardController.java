package ar.edu.unicen.ringo.console.ui;

import java.util.ArrayList;
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
		model.addAttribute("period", period);
		return "index"; 
	}
	
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)	
    public List<FlotData> data(@RequestParam(value="period", defaultValue="hour") String period) {
    	List<Sla> slas = service.list();
    	
    	HashMap<String, DateHistogramFacet> data = generateData(period);
    	
    	List<FlotData> response = new ArrayList<FlotData>();
    	for(Sla sla: slas){
    		FlotData flotdata = new FlotData();
    		
    		List<double[]> datalist = new ArrayList<double[]>();
    		for(Entry entry : data.get(sla.getId()).getEntries()){
    			double[] list = {entry.getTime(), entry.getMean()};
    			datalist.add(list);
    		}
    		
    		flotdata.setLabel(sla.getName());
    		flotdata.setColor(sla.getColor());
    		flotdata.setData(datalist);
    		
    		response.add(flotdata);
    	}

    	return response;
    }
	
    private HashMap<String, DateHistogramFacet> generateData(String period) {
		List<Sla> slas = service.list();
		
		long to = System.currentTimeMillis();
		long from = to - millisecondsForPeriod(period);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String period_from = String.valueOf(from); //sdf.format(from);
		String period_to   = String.valueOf(to); //sdf.format(to);
		
		HashMap<String, DateHistogramFacet> histograms = new HashMap<String, DateHistogramFacet>();
		for(Sla sla : slas) {
			logger.debug("Sla: {}", sla.getId());
			// Facet by Period 
			DateHistogramFacetBuilder facet = FacetBuilders.dateHistogramFacet("histogram")
						.keyField("timestamp")
						.valueField("execution_time")
						.interval("1s");
			
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
				    	
		return histograms;
    }
     
	private long millisecondsForPeriod(String period) {
		long base = 1000; // 1 hour
		
		long factor;
		if (period.equals("minute")) {
			factor = 60; // 1 min
		} else if (period.equals("hour")) {
			factor = 3600; // 1 hour
		} else if (period.equals("day")) {
			factor = 24 * 3600; // 1 day
		} else {
			factor = 7 * 24 * 3600; // 1 week
		}
		
		return factor * base;
	}

}

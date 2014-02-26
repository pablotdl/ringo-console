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

import ar.edu.unicen.ringo.console.model.Node;
import ar.edu.unicen.ringo.console.model.Sla;
import ar.edu.unicen.ringo.console.service.NodeManagementService;
import ar.edu.unicen.ringo.console.service.SlaManagementService;
import ar.edu.unicen.ringo.console.ui.dto.FlotData;

@Controller
public class DashboardController {

    private static final Logger logger = LoggerFactory
            .getLogger(DashboardController.class);

	@Autowired
	private SlaManagementService service;
	
	@Autowired
	private NodeManagementService service_node;	
	
	@Autowired
	private Client client;	
	
	@RequestMapping("/")
	public String index(ModelMap model, @RequestParam(value="period", defaultValue="hour") String period) {
		model.addAttribute("period", period);
		return "index"; 
	}
	
    @ResponseBody
    @RequestMapping(value = "/data", method = RequestMethod.GET)	
    public HashMap<String, List<FlotData>> data(@RequestParam(value="period", defaultValue="hour") String period) {
    	List<Sla> slas = service.list();
    	
    	HashMap<String, DateHistogramFacet> data = generateData(period);
    	HashMap<String, List<FlotData>> response = new HashMap<String, List<FlotData>>();
    	
    	List<FlotData> mean = new ArrayList<FlotData>();
    	List<FlotData> total = new ArrayList<FlotData>();
    	List<FlotData> count = new ArrayList<FlotData>();
    	
    	List<FlotData> usage = new ArrayList<FlotData>();
    	List<FlotData> invocations = new ArrayList<FlotData>();
    	
    	HashMap<String, Double> sla_nodes = new HashMap<String, Double>();;
    	
    	for(Sla sla: slas){
    		sla_nodes.put(sla.getId(), new Double(0));
    		
    		FlotData<List<double[]>> meanflotdata = new FlotData<List<double[]>>();
    		FlotData<List<double[]>> totalflotdata = new FlotData<List<double[]>>();
    		FlotData<List<double[]>> countflotdata = new FlotData<List<double[]>>();
    		
    		FlotData<Double> usageflotdata = new FlotData<Double>();
    		Double usagedata = new Double(0);
    		
    		FlotData<Double> invocationsflotdata = new FlotData<Double>();
    		Double invocationsdata = new Double(0);    		
    		
    		List<double[]> meandatalist = new ArrayList<double[]>();
    		List<double[]> totaldatalist = new ArrayList<double[]>();
    		List<double[]> countdatalist = new ArrayList<double[]>();
    		for(Entry entry : data.get(sla.getId()).getEntries()){
    			double[] meanlist = {entry.getTime(), entry.getMean()};
    			meandatalist.add(meanlist);
    			
    			double[] totallist = {entry.getTime(), entry.getTotal()};
    			totaldatalist.add(totallist);
    			usagedata += entry.getTotal();
    			invocationsdata += entry.getCount();
    			
    			double[] countlist = {entry.getTime(), entry.getCount()};
    			countdatalist.add(countlist);    			
    		}
    		
    		meanflotdata.setLabel(sla.getName());
    		meanflotdata.setColor(sla.getColor());
    		meanflotdata.setData(meandatalist);

    		totalflotdata.setLabel(sla.getName());
    		totalflotdata.setColor(sla.getColor());
    		totalflotdata.setData(totaldatalist);
    		
    		countflotdata.setLabel(sla.getName());
    		countflotdata.setColor(sla.getColor());
    		countflotdata.setData(countdatalist);   
    		
    		usageflotdata.setLabel(sla.getName());
    		usageflotdata.setColor(sla.getColor());
    		usageflotdata.setData(usagedata);
    		
    		invocationsflotdata.setLabel(sla.getName());
    		invocationsflotdata.setColor(sla.getColor());
    		invocationsflotdata.setData(invocationsdata);    
    		
    		mean.add(meanflotdata);
    		total.add(totalflotdata);
    		count.add(countflotdata);
    		usage.add(usageflotdata);
    		invocations.add(invocationsflotdata);
    	}

    	List<Node> nodes = service_node.list();
    	for(Node node: nodes){
    		Double total_nodes = sla_nodes.get(node.getSla());
    		sla_nodes.put(node.getSla(), total_nodes + 1);
    	}    	
    	
    	List<FlotData> total_nodes = new ArrayList<FlotData>();
    	
    	for(Sla sla: slas) {
    		FlotData<Double> nodesflotdata = new FlotData<Double>();
    		Double nodesdata = sla_nodes.get(sla.getId());		
    		
    		nodesflotdata.setLabel(sla.getName());
    		nodesflotdata.setColor(sla.getColor());
    		nodesflotdata.setData(nodesdata);  
    		
    		total_nodes.add(nodesflotdata);
    	}
    	
    	
    	response.put("mean", mean);
    	response.put("total", total);
    	response.put("count", count);
    	response.put("usage", usage);
    	response.put("invocations", invocations);
    	response.put("nodes", total_nodes);
    	
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

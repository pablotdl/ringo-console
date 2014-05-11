package ar.edu.unicen.ringo.console.ui;

import java.awt.Color;
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
import org.springframework.web.bind.annotation.PathVariable;
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
public class SlaDashboardController {
    private static final Logger logger = LoggerFactory.getLogger(SlaDashboardController.class);

	@Autowired
	private SlaManagementService service;
	
	@Autowired
	private NodeManagementService service_node;	
	
	@Autowired
	private Client client;	
	
	@RequestMapping(value = "/dashboard/sla/{id}", method = RequestMethod.GET)
	public String index(ModelMap model, @PathVariable("id") String id, @RequestParam(value="period", defaultValue="hour") String period) {
		model.addAttribute("sla", service.get(id));
		model.addAttribute("nodes", service_node.list(QueryBuilders.termQuery("sla", id)));
		model.addAttribute("period", period);
		return "dashboard.sla"; 
	}
	
    @ResponseBody
    @RequestMapping(value = "/dashboard/sla/{id}/data", method = RequestMethod.GET)	
    public HashMap<String, List<FlotData>> data(@PathVariable("id") String id, @RequestParam(value="period", defaultValue="hour") String period) {
    	Sla sla = service.get(id); 
    	List<Node> nodes = service_node.list(QueryBuilders.matchQuery("sla", id));
    	
    	HashMap<String, DateHistogramFacet> data = generateData(nodes, period);
    	HashMap<String, List<FlotData>> response = new HashMap<String, List<FlotData>>();
    	
    	List<FlotData> mean = new ArrayList<FlotData>();
    	List<FlotData> total = new ArrayList<FlotData>();
    	List<FlotData> count = new ArrayList<FlotData>();
    	
    	List<FlotData> usage = new ArrayList<FlotData>();
    	List<FlotData> invocations = new ArrayList<FlotData>();
    	
    	List<FlotData> total_nodes = new ArrayList<FlotData>();
    	
    	String color = sla.getColor().substring(1);
    	int colorValue = Integer.parseInt( color,16 );
    	Color baseColor = new Color( colorValue ); 
    	Color aColor = baseColor.darker();
    	int colours = nodes.size();
    	int i = colours / 2;
    	
    	for(Node node: nodes){
    		int rgb = aColor.getRGB();
    		color = "#" + Integer.toHexString(rgb).substring(2);
    		i++;
    		if (i == colours) { 
    			aColor = baseColor;
    		} else if (i < colours) {
    			aColor = aColor.darker();
    		} else {
    			aColor = aColor.brighter();
    		}
    		
    		
    		
    		FlotData<List<double[]>> meanflotdata = new FlotData<List<double[]>>();
    		FlotData<List<double[]>> totalflotdata = new FlotData<List<double[]>>();
    		FlotData<List<double[]>> countflotdata = new FlotData<List<double[]>>();
    		
    		FlotData<Double> usageflotdata = new FlotData<Double>();
    		Double usagedata = new Double(0);
    		
    		FlotData<Double> invocationsflotdata = new FlotData<Double>();
    		Double invocationsdata = new Double(0);    	
    		
    		FlotData<Double> nodesflotdata = new FlotData<Double>();
    		nodesflotdata.setLabel(node.getName());
    		nodesflotdata.setColor(color);
    		nodesflotdata.setData(1.0);    		
    		
    		List<double[]> meandatalist = new ArrayList<double[]>();
    		List<double[]> totaldatalist = new ArrayList<double[]>();
    		List<double[]> countdatalist = new ArrayList<double[]>();
    		
    		for(Entry entry : data.get(node.getId()).getEntries()){
    			double[] meanlist = {entry.getTime(), entry.getMean()};
    			meandatalist.add(meanlist);
    			
    			double[] totallist = {entry.getTime(), entry.getTotal()};
    			totaldatalist.add(totallist);
    			usagedata += entry.getTotal();
    			invocationsdata += entry.getCount();
    			
    			double[] countlist = {entry.getTime(), entry.getCount()};
    			countdatalist.add(countlist);    			
    		}
    		
    		meanflotdata.setLabel(node.getName());
    		meanflotdata.setColor(color);
    		meanflotdata.setData(meandatalist);

    		totalflotdata.setLabel(node.getName());
    		totalflotdata.setColor(color);
    		totalflotdata.setData(totaldatalist);
    		
    		countflotdata.setLabel(node.getName());
    		countflotdata.setColor(color);
    		countflotdata.setData(countdatalist);   
    		
    		usageflotdata.setLabel(node.getName());
    		usageflotdata.setColor(color);
    		usageflotdata.setData(usagedata);
    		
    		invocationsflotdata.setLabel(node.getName());
    		invocationsflotdata.setColor(color);
    		invocationsflotdata.setData(invocationsdata);    
    		
    		mean.add(meanflotdata);
    		total.add(totalflotdata);
    		count.add(countflotdata);
    		usage.add(usageflotdata);
    		invocations.add(invocationsflotdata);
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
	
    private HashMap<String, DateHistogramFacet> generateData(List<Node> nodes, String period) {
		
		long to = System.currentTimeMillis();
		long from = to - millisecondsForPeriod(period);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		String period_from = String.valueOf(from); //sdf.format(from);
		String period_to   = String.valueOf(to); //sdf.format(to);
		
		HashMap<String, DateHistogramFacet> histograms = new HashMap<String, DateHistogramFacet>();
		for(Node node : nodes) {
			logger.debug("Node: {}", node.getId());
			// Facet by Period 
			DateHistogramFacetBuilder facet = FacetBuilders.dateHistogramFacet("histogram")
						.keyField("timestamp")
						.valueField("execution_time")
						.interval("1s");
			
			// Query Filters: SLA & Range by Period 
			QueryBuilder query = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery("sla", node.getSla()))
					.must(QueryBuilders.matchQuery("node", node.getId()))
					.must(QueryBuilders.rangeQuery("timestamp").from(period_from).to(period_to));
			
			// Client Search
			SearchResponse searchresponse = client.prepareSearch("agent").setTypes("invocation")
					.setQuery(query)
					.addFacet(facet)
					.execute().actionGet();
			
			// Get Histogram for SLA
			DateHistogramFacet histogram = (DateHistogramFacet) searchresponse.getFacets().facetsAsMap().get("histogram");
			histograms.put(node.getId(), histogram);
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

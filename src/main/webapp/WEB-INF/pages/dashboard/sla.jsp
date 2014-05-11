<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <!--page title-->
        <div class="pagetitle">
           <h1><a href="/console-ui/?period=${period}" >Dashboard</a> / ${sla.name}</h1> 
           <div class="btn-group">
           	 <a href="?period=minute" class="btn <c:if test="${period=='minute'}">active</c:if>">Minute</a>
             <a href="?period=hour" class="btn <c:if test="${period=='hour'}">active</c:if>">Hour</a>
             <a href="?period=day" class="btn <c:if test="${period=='day'}">active</c:if>">Day</a>
             <a href="?period=week" class="btn <c:if test="${period=='week'}">active</c:if>">Week</a>
           </div>
           <div class="clearfix"></div>
        </div>
		<!--page title end-->
		
        <!--Informatin BOX 3-->
        <div class="information-box-3">
              <div class="item" style="height:300px">
              	<div class="box-figures">Usage</div>             
                <div id="usage-stat" style="height:300px"></div>
              </div>
              <div class="item" style="height:300px">
              	<div class="box-figures">Invocations</div>             
                <div id="invocations-stat" style="height:300px"></div>
              </div>
              <div class="item" style="height:300px">
              	<div class="box-figures">Nodes</div>             
                <div id="nodes-stat" style="height:300px"></div>
              </div>
        </div>
        <!--Informatin BOX 3 END-->		
		
		<!--Collapsible Group Start-->
		<div class="grid-transparent">
			<div class="accordion" id="accordion-mean">
				<div class="accordion-group">
					<div class="accordion-titleing grid-title">
	                   <div class="pull-left">
	                      <div class="icon-title"><i class="icon-bar-chart"></i></div>
	                      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-mean" href="#collapse-mean" style="width: 300px" >Average usage</a> 
	                      <div class="clearfix"></div>
	                   </div>					
					</div>
					<div id="collapse-mean" class="accordion-body collapse in">
						<div class="accordion-inner">
							<div class="wrap-chart" style="height:300px">
								<div id="mean-stat" class="sla-stat chart" style="height:300px"></div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="accordion" id="accordion-total" style="margin-top: 10px">
				<div class="accordion-group">
					<div class="accordion-titleing grid-title">
	                   <div class="pull-left">
	                      <div class="icon-title"><i class="icon-bar-chart"></i></div>
	                      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-total" href="#collapse-total" style="width: 300px" >Total Usage</a> 
	                      <div class="clearfix"></div>
	                   </div>					
					</div>
					<div id="collapse-total" class="accordion-body collapse in">
						<div class="accordion-inner">
							<div class="wrap-chart" style="height:300px">
								<div id="total-stat" class="sla-stat chart" style="height:300px"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="accordion" id="accordion-count" style="margin-top: 10px">
				<div class="accordion-group">
					<div class="accordion-titleing grid-title">
	                   <div class="pull-left">
	                      <div class="icon-title"><i class="icon-bar-chart"></i></div>
	                      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-count" href="#collapse-count" style="width: 300px" >Invocations</a> 
	                      <div class="clearfix"></div>
	                   </div>					
					</div>
					<div id="collapse-count" class="accordion-body collapse in">
						<div class="accordion-inner">
							<div class="wrap-chart" style="height:300px">
								<div id="count-stat" class="sla-stat chart" style="height:300px"></div>
							</div>
						</div>
					</div>
				</div>
			</div>			
		</div>
		<!--Collapsible Group END-->
		

		<script type="text/javascript">
			
			$( document ).ready(function() {
				setInterval(pollData, 1000);
			});
			

			
			function pollData(){
				$.ajax({
					url: "${sla.id}/data?period=${period}",
					type: "GET",
					dataType: "json",
					success: onDataReceived
				});				
			}
			
			function onDataReceived(data) {

				options_lines = {
						series: {
							shadowSize: 0,
							stack: true,
							lines: {
								show: true,
								fill: true,
								steps: false
							}
						},
				        grid: {
				            backgroundColor: '#FFFFFF',
				            borderWidth: 0,
				            borderColor: '#CDCDCD',
				            hoverable: true
				        },	
				        xaxis: {
				            show: false
				        }        
					}
			
				options_pie = {
					    series: {
					        pie: {
					        	innerRadius: 0.5,
					            show: true,
					            label: {
					                show: true,
					                radius: 1,
					                background: {
					                    opacity: 0.8,
					                    color: '#fff'
					                }
					            }
					        }
					    },
					    grid: {
					        hoverable: true,
					        clickable: true
					    },
					    legend: {
					        show: false
					    }
					}
				
				$.plot("#mean-stat", data.mean, options_lines);
				$.plot("#total-stat", data.total, options_lines);				
				$.plot("#count-stat", data.count, options_lines);
				
				
				$.plot('#usage-stat', data.usage, options_pie);
				$.plot('#invocations-stat', data.invocations, options_pie);
				$.plot('#nodes-stat', data.nodes, options_pie);
			
			}
			
		 </script>  		

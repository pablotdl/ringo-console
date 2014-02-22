<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <!--page title-->
        <div class="pagetitle">
           <h1>Dashboard</h1> 
           <div class="btn-group">
           	 <a href="?period=minute" class="btn <c:if test="${period=='minute'}">active</c:if>">Minute</a>
             <a href="?period=hour" class="btn <c:if test="${period=='hour'}">active</c:if>">Hour</a>
             <a href="?period=day" class="btn <c:if test="${period=='day'}">active</c:if>">Day</a>
             <a href="?period=week" class="btn <c:if test="${period=='week'}">active</c:if>">Week</a>
           </div>
           <div class="clearfix"></div>
        </div>
        <!--page title end-->   
		 
         <!-- info-box -->
         <div class="info-box">
           <div class="row-fluid dashboard-box">
              <div class="span12">
                <div class="wrap-chart" style="height:300px"><div id="visitor-stat" class="sla-stat chart" style="height:300px"></div></div>
              </div>
            </div> 
           
			<div class="information-data">
				<div class="data">
	                 <p class="date-figures"></p>
	                 <p class="date-title">SLA</p>
	             </div>
	             <div class="data">
	                 <p class="date-figures"></p>
	                 <p class="date-title">Min</p>
	             </div>
	             <div class="data">
	                 <p class="date-figures"></p>
	                 <p class="date-title">Max</p>
	             </div>
	             <div class="data data-last">
	                 <p class="date-figures"></p>
	                 <p class="date-title">Updates</p>
	             </div>
            </div>
         </div>
         <!-- info-box end -->
             
		<script type="text/javascript">
			$( document ).ready(function() {
				setInterval(pollData, 1000);
			});
			
			function pollData(){
				$.ajax({
					url: "data?period=${period}",
					type: "GET",
					dataType: "json",
					success: onDataReceived
				});				
			}
			
			function onDataReceived(data) {

				$.plot("#visitor-stat", data, {
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
				});
				
			}
			
		 </script>  		

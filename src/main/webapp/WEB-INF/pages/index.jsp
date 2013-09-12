<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <!--page title-->
        <div class="pagetitle">
           <h1>Dashboard</h1> 
           <div class="btn-group">
             <a href="?period=hour" class="btn <c:if test="${period=='hour'}">active</c:if>">Hour</a>
             <a href="?period=day" class="btn <c:if test="${period=='day'}">active</c:if>">Day</a>
             <a href="?period=week" class="btn <c:if test="${period=='week'}">active</c:if>">Week</a>
           </div>
           <div class="clearfix"></div>
        </div>
        <!--page title end-->

		<c:forEach items="${slas}" var="sla">
		
             <c:set var="entries" value="${histograms.get(sla.getId()).getEntries()}"/> 
             
             <!-- info-box -->
             <div class="info-box">
               <div class="row-fluid stats-box">
                  <div class="span4">
                    <div class="stats-box-title">Usage</div>
                    <div class="stats-box-all-info"><img src="images/icon/icon_vizitors_stats.png" alt=""> 
                    	<c:if test="${entries.size() > 0}">
                    		<fmt:formatNumber type="number" maxFractionDigits="2" value="${entries.get(entries.size()-1).getTotal()}" />
                    		
                    		<script type="text/javascript">
                    			var data_visitor_${sla.getId()} = new Array();
                    			<c:forEach var="i" begin="0" end="${entries.size()-1}">
                    				data_visitor_${sla.getId()}[${i}] = ["${entries.get(i).getTime()}", ${entries.get(i).getTotal()}];
                    			</c:forEach>
                    			
                    			$(document).ready(function() {
                    				loadStats($("#visitor-stat-${sla.getId()}"), data_visitor_${sla.getId()}, "#1aae1a");
                    			});
                    		</script>
                    	</c:if>
                    </div>
                    <div class="wrap-chart"><div id="visitor-stat-${sla.getId()}" class="sla-stat chart"></div></div>
                  </div>
                  
                  <div class="span4">
                    <div class="stats-box-title">Events</div>
                    <div class="stats-box-all-info"><img src="images/icon/icon_like_stats.png" alt="">
						<c:if test="${entries.size() > 0}">
							${entries.get(entries.size()-1).getCount()}
							
                   			<script type="text/javascript">
                    			var data_order_${sla.getId()} = new Array();
                    			<c:forEach var="i" begin="0" end="${entries.size()-1}">
                    				data_order_${sla.getId()}[${i}] = ["${entries.get(i).getTime()}", ${entries.get(i).getCount()}];
                    			</c:forEach>
                    			
                    			$(document).ready(function() {
                    				loadStats($("#order-stat-${sla.getId()}"), data_order_${sla.getId()}, "#3f94ed");
                    			});
                    		</script>							
                    	</c:if> 
                    </div>
                    <div class="wrap-chart"><div id="order-stat-${sla.getId()}" class="chart"></div></div>
                  </div>
                  
                  <div class="span4">
                    <div class="stats-box-title">Response Time</div>
                    <div class="stats-box-all-info"><img src="images/icon/icon_orders_stats.png" alt="">
                    	<c:if test="${entries.size() > 0}">
                    		<fmt:formatNumber type="number" maxFractionDigits="2" value="${entries.get(entries.size()-1).getMean()}" />
                    		
                   			<script type="text/javascript">
                    			var data_user_${sla.getId()} = new Array();
                    			<c:forEach var="i" begin="0" end="${entries.size()-1}">
                    				data_user_${sla.getId()}[${i}] = ["${entries.get(i).getTime()}", ${entries.get(i).getMean()}];
                    			</c:forEach>
                    			
                    			$(document).ready(function() {
                    				loadStats($("#user-stat-${sla.getId()}"), data_user_${sla.getId()}, "#ff6868");
                    			});
                    		</script>		                    		
                    	</c:if>                    
					</div>
                    <div class="wrap-chart"><div id="user-stat-${sla.getId()}" class="chart"></div></div>
                  </div>
               </div>
               
				<div class="information-data">
					<div class="data">
	                    <p class="date-figures">${sla.getName()}</p>
	                    <p class="date-title">SLA</p>
	                </div>
	                <div class="data">
	                    <p class="date-figures"><c:if test="${entries.size() > 0}">${entries.get(entries.size()-1).getMin()}</c:if></p>
	                    <p class="date-title">Min</p>
	                </div>
	                <div class="data">
	                    <p class="date-figures"><c:if test="${entries.size() > 0}">${entries.get(entries.size()-1).getMax()}</c:if></p>
	                    <p class="date-title">Max</p>
	                </div>
	                <div class="data data-last">
	                    <p class="date-figures">95%</p>
	                    <p class="date-title">Updates</p>
	                </div>
                </div>
             </div>
             <!-- info-box end -->
             
		</c:forEach>                          
             
              

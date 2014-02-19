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
			var data = new Array();
			var data_sla;
			<c:forEach var="i" begin="0" end="${slas.size()-1}">
				<c:set var="sla" value="${slas.get(i)}"/>
				<c:set var="entries" value="${histograms.get(sla.getId()).getEntries()}"/>
				
				<c:if test="${entries.size() > 0}">
					data_sla = new Array();
					<c:forEach var="j" begin="0" end="${entries.size()-1}">
						data_sla[${j}] = ["${entries.get(j).getTime()}", ${entries.get(j).getTotal()}];
    				</c:forEach>
    				data[${i}] = {label: "${sla.getName()}", data: data_sla};
				</c:if>	
				
			</c:forEach> 
			
			$(document).ready(function() {
				loadStats($("#visitor-stat"), data, "#1aae1a");
			});
		 </script>  		



		                       
             
              

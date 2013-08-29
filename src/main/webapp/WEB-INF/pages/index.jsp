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

		<c:forEach items="${facets.iterator()}" var="entry">

             
             <!-- info-box -->
             <div class="info-box">
               <div class="row-fluid stats-box">
                  <div class="span4">
                    <div class="stats-box-title">Usage</div>
                    <div class="stats-box-all-info"><img src="images/icon/icon_vizitors_stats.png" alt=""> <fmt:formatNumber type="number" 
            maxFractionDigits="2" value="${entry.getTotal()}" /></div>
                    <div class="wrap-chart"><div id="visitor-stat" class="chart"></div></div>
                  </div>
                  
                  <div class="span4">
                    <div class="stats-box-title">Events</div>
                    <div class="stats-box-all-info"><img src="images/icon/icon_like_stats.png" alt=""> ${entry.getCount()}</div>
                    <div class="wrap-chart"><div id="order-stat" class="chart"></div></div>
                  </div>
                  
                  <div class="span4">
                    <div class="stats-box-title">Response Time</div>
                    <div class="stats-box-all-info"><img src="images/icon/icon_orders_stats.png" alt=""> <fmt:formatNumber type="number" 
            maxFractionDigits="2" value="${entry.getMean()}" /> </div>
                    <div class="wrap-chart"><div id="user-stat" class="chart"></div></div>
                  </div>
               </div>
               
				<div class="information-data">
					<div class="data">
	                    <p class="date-figures">SLA</p>
	                    <p class="date-title">SLA</p>
	                </div>
	                <div class="data">
	                    <p class="date-figures">${entry.getMin()}</p>
	                    <p class="date-title">Min</p>
	                </div>
	                <div class="data">
	                    <p class="date-figures">${entry.getMax()}</p>
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
             
             
              

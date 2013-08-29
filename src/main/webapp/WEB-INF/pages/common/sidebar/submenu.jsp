<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="tab-content">
<div id="dashboard" class="tab-pane active">
<ul class="additional-menu" style="margin-left:0px">
	<li class="active"><a href="<c:url value="/" />"><i
			class="icon-home"></i> Dashboard</a></li>
	<li><a href="<c:url value="/" />admin/sla"><i
			class="icon-picture"></i> SLAs</a></li>
	<li><a href="icons.html"><i class="icon-star"></i> Icons</a></li>
	<li><a href="calendar.html"><i class="icon-calendar"></i>
			Calendar</a></li>
</ul>
</div>
<div id="admin" class="tab-pane">
<ul class="additional-menu" style="margin-left:0px">
    <li class="active"><a href="<c:url value="/" />"><i
            class="icon-home"></i> Sla</a></li>
    <li><a href="<c:url value="/" />admin/sla"><i
            class="icon-picture"></i> Node</a></li>
    <li><a href="icons.html"><i class="icon-star"></i> Icons</a></li>
    <li><a href="calendar.html"><i class="icon-calendar"></i>
            Calendar</a></li>
</ul>
</div>
</div>
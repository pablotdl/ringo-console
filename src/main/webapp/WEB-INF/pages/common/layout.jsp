<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>Ringo: Monitoring Console</title>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<tiles:insertAttribute name="cssload" />

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="<c:url value="/favicon.ico" />">
<script src="<c:url value="/js/jquery.min.js" />"></script>
<script src="<c:url value="/js/jquery.dataTables.min.js" />"></script>

<tiles:insertAttribute name="jsload" />

</head>

<body>
	<!--BEGIN HEADER-->
	<div id="header" role="banner">
		
		<a id="menu-link" class="head-button-link menu-hide" href="#menu"><span>Menu</span></a>
		<!--Logo-->
		<a href="dashboard.html" class="logo"><h1>Ringo</h1></a>
		<!--Logo END-->

		<tiles:insertAttribute name="search" />
		
		<div class="right">
			<tiles:insertAttribute name="notification" />
			<tiles:insertAttribute name="message" />
			<tiles:insertAttribute name="config" />
			<tiles:insertAttribute name="profile" />
		</div>
		
	</div>
	<!--END HEADER-->

	<div id="wrap">

		<!--BEGIN SIDEBAR-->
		<div id="menu" role="navigation">
			<tiles:insertAttribute name="menu" />
			<tiles:insertAttribute name="submenu" />
			<div class="clearfix"></div>
		</div>
		<!--SIDEBAR END-->


		<!--BEGIN MAIN CONTENT-->
		<div id="main" role="main">
			<div class="block">
				<div class="clearfix"></div>

				<tiles:insertAttribute name="body" />

				<tiles:insertAttribute name="copyright" />

				<div class="clearfix"></div>
			</div>
			<!--end .block-->
		</div>
		<!--MAIN CONTENT END-->

	</div>
	<!--/#wrapper-->
	
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!--page title-->
<div class="pagetitle">
	<h1><tiles:insertAttribute name="entity" ignore="true" /></h1>
	<div class="btn-group">
		<button class="btn btn-primary metro" onclick="location.href = './<tiles:insertAttribute name="uri" />/new'">New</button>
	</div>
	<div class="clearfix"></div>
</div>
<!--page title end-->

<div class="grid">
	<div class="grid-title">
		<div class="pull-left">
			<div class="icon-title"><i class="icon-cog"></i></div>
			<span>List</span>
			<div class="clearfix"></div>
		</div>
		<div class="pull-right"> 
			<div class="icon-title"><a href="./<tiles:insertAttribute name="uri" />/new"><i class="icon-plus"></i></a></div>
		</div>
		<div class="clearfix"></div>
	</div>

	<div class="grid-content overflow">

		<table class="table table-bordered table-mod-2" id="sla-table">
			<thead>
				<tr>
					<th>Id</th>
					<th>Color</th>
					<th>Name</th>
					<th class="hidden-mobile">Description</th>
					<th class="hidden-mobile">Enabled</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>


		<div class="clearfix"></div>
	</div>

</div>





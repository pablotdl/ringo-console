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


<script type="text/javascript">

    var oTable
	$(document).ready(function() {
		oTable = $('#sla-table').dataTable({
			"iDisplayLength": 10,
			"bProcessing" : true,
			"bDeferRender": true,
			"bAutoWidth": true,
			"sPaginationType": "full_numbers",
			"sAjaxSource" : "./<tiles:insertAttribute name="uri" />/list",
			"aoColumns" : [{
				"mDataProp": "id"
            }, {
				"mDataProp" : "color",
				"sWidth": "30px",
				"sClass": "colorbox",
                "fnRender": function ( object ) {
                	return "<div style=\"background-color: " + object.aData.color + ";\"></div>"
				}
			}, {
				"mDataProp" : "name"
			}, {
				"sClass": "hidden-mobile",
				"mDataProp" : "description"
			}, {
				"sClass": "hidden-mobile",
				"mDataProp" : "enabled",
                "fnRender": function ( object ) {
                	if ( object.aData.enabled ) return  "<i class='icon-ok'></i>";
                	return "";
				}
			}, {
				"mDataProp" : "id",
				"sName": "Actions",
				"sClass": "action-table",
                "bSearchable": false,
                "bSortable": false,
                "bUseRendered" : false,
                "sWidth": "30px",
                "fnRender": function ( object ) {
                	var edit = "<a href='./<tiles:insertAttribute name="uri" />/" + object.aData.id + "'><img src='<c:url value="/images/icon/table_edit.png" />' alt=''></a> ";
                	var remove = "<a href='#' onclick='return doDelete(this, \"" + object.aData.id + "\")'><img src='<c:url value="/images/icon/table_del.png" />' alt=''></a>";
					return edit + remove;
				}
			} ]
		});
	});
    function doDelete(elem, id) {
    	var row = elem.parentElement.parentElement;
        $.ajax({
            url: "./<tiles:insertAttribute name="uri" />/" + id,
            type: 'DELETE',
            success: function (result) {
            	oTable.fnDeleteRow(row);
            }
        });
        return false;
    }
</script>




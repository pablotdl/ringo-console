<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="btn-group">
                  <button class="btn" onclick="location.href = './<tiles:insertAttribute name="uri" />/new'">New <tiles:insertAttribute name="entity" ignore="true"/></button>
                </div>              

<div class="grid">
              <div class="grid-title">
               <div class="pull-left">
               <div class="icon-title"></div>
                  <span><tiles:insertAttribute name="entity" ignore="true"/> listing</span> 
                  <div class="clearfix"></div>
               </div>
               <div class="pull-right"> 
               </div>
              <div class="clearfix"></div>   
              </div>
            
              <div class="grid-content overflow">
                
            <table class="table table-bordered table-mod-2" id="sla-table">
            <thead>
              <tr>
                <th class="t_center"><input type="checkbox" id="c1" name="cc" /><label for="c1"><span></span></label></th>
                <th>Name</th>
                <th class="hidden-mobile">Description</th>
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
			"bProcessing" : true,
			"bDeferRender": true,
			"bAutoWidth": true,
			"sAjaxSource" : "./<tiles:insertAttribute name="uri" />/list",
			"aoColumns" : [{"mData": null,
                "sName": "Select",
                "bSearchable": false,
                "bSortable": false,
                "sClass": "t_center",
                "mRender": function ( data, type, full ) {
                	return "<input type=\"checkbox\" name=\"mark\" id=\"mark\" /><label for=\"mark\"><span></span></label>";
                }
            }, {
				"mData" : "name"
			}, {
				"sClass": "hidden-mobile",
				"mData" : "description"
			}, {
				"mData" : null,
				"sName": "Actions",
				"sClass": "action-table",
                "bSearchable": false,
                "bSortable": false,
                "mRender": function ( data, type, full ) {
					return "<a href='#'><img src='<c:url value="/images/icon/table_view.png" />' alt=''></a> <a href='./<tiles:insertAttribute name="uri" />/" + full.id + "'><img src='<c:url value="/images/icon/table_edit.png" />' alt=''></a> <a href='#' onclick='return doDelete(\"" + full.id + "\")'><img src='<c:url value="/images/icon/table_del.png" />' alt=''></a>";
				}
			} ]
		});
	});
    function doDelete(id) {
        $.ajax({
            url: "./<tiles:insertAttribute name="uri" />/" + id,
            type: 'DELETE',
            success: function (result) {
            	oTable.fnDraw();
            }
        });
        return false;
    }
</script>




<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="btn-group">
                  <button class="btn" onclick="location.href = './sla/new'">New SLA</button>
                </div>              

<div class="grid">
              <div class="grid-title">
               <div class="pull-left">
               <div class="icon-title"></div>
                  <span>SLA listing</span> 
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
              <tr>
                <td class="t_center"><input type="checkbox" id="c2" name="cc" /><label for="c2"><span></span></label></td>
                <td>sla 1</td>
                <td class="hidden-mobile">An SLA</td>
                <td class="action-table"><a href="#"><img src="images/icon/table_view.png" alt=""></a> <a href="#"><img src="images/icon/table_edit.png" alt=""></a> <a href="#"><img src="images/icon/table_del.png" alt=""></a></td>
              </tr>
              <tr>
                <td class="t_center"><input type="checkbox" id="c3" name="cc" /><label for="c3"><span></span></label></td>
                <td>sla 2</td>
                <td class="hidden-mobile">Another SLA</td>
                <td class="action-table"><a href="#"><img src="images/icon/table_view.png" alt=""></a> <a href="#"><img src="images/icon/table_edit.png" alt=""></a> <a href="#"><img src="images/icon/table_del.png" alt=""></a></td>
              </tr>
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
			"sAjaxSource" : "./sla/list",
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
					return "<a href='#'><img src='<c:url value="/images/icon/table_view.png" />' alt=''></a> <a href='./sla/" + full.id + "'><img src='<c:url value="/images/icon/table_edit.png" />' alt=''></a> <a href='#' onclick='return doDelete(\"" + full.id + "\")'><img src='<c:url value="/images/icon/table_del.png" />' alt=''></a>";
				}
			} ]
		});
	});
    function doDelete(id) {
        $.ajax({
            url: "./sla/" + id,
            type: 'DELETE',
            success: function (result) {
            	oTable.fnDraw();
            }
        });
        return false;
    }
</script>




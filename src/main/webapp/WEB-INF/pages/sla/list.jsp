
<div class="container-fluid">
    <div class="row"></div>
    <div class="row-fluid">
        <div class="span12 offset2">New SLA</div>
    </div>
    <div class="row-fluid">
		<div class="span12 offset1">
			<table id="sla-table-deprecated" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered">
    <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>etc</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Row 1 Data 1</td>
            <td>Row 1 Data 2</td>
            <td>etc</td>
        </tr>
        <tr>
            <td>Row 2 Data 1</td>
            <td>Row 2 Data 2</td>
            <td>etc</td>
        </tr>
    </tbody>
</table>
		</div>
	</div>
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

	$(document).ready(function() {
		var oTable = $('#sla-table').dataTable({
			"bProcessing" : true,
			"bAutoWidth": true,
			"sAjaxSource" : "./sla/list",
			
			"aoColumns" : [{"mData": null,
                "sName": "Actions",
                "bSearchable": false,
                "bSortable": false,
                "mRender": function ( data, type, full ) {
                    return "checks";
                }
            }, {
				"mData" : "name"
			}, {
				"mData" : "description"
			}, {
				"mData" : null,
				"sName": "Actions",
                "bSearchable": false,
                "bSortable": false,
                "mRender": function ( data, type, full ) {
					return "actions";
				}
			} ]
		});
	});
</script>




<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="container-fluid">
    <div class="row"></div>
    <div class="row-fluid">
        <div class="span12 offset2">New Node</div>
    </div>
    <div class="row-fluid">
		<div class="span12 offset1">
			<form class="form-horizontal" method="post">
				<div class="control-group">
					<label class="control-label" for="name">Node name</label>
					<div class="controls">
					<input type="text" id="name" name="name" placeholder="Name..." value="${node.name}" />
					</div>
				</div>
				<div class="control-group">
				  <label class="control-label" for="sla">Sla:</label>
                        <div class="controls">
                         <select id="sla" name="sla" class="chzn-select chosen_select"  >
                            <option value=""></option>
                            <c:forEach items="${slas}" var="sla" >
                                <option 
                                <c:if test="${sla.id == node.sla}">
                                 selected
                                </c:if>
                                value="${sla.id}">${sla.name}</option>
                            </c:forEach>
                          </select>  
                        </div>
                </div>
				<div class="control-group">
                    <div class="controls">
				    	<button type="submit" class="btn">Save</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

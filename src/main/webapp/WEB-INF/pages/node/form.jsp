<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--page title-->
<div class="pagetitle">
	<c:set var="id" value="New" />
	<c:if test="${node.id != null}">
		<c:set var="id" value="${node.id}" />
	</c:if>
	<h1>
		<tiles:insertAttribute name="entity" ignore="true" />
		: ${id}
	</h1>
	<div class="clearfix"></div>
</div>
<!--page title end-->

<!--Input fields Start-->
<div class="grid">
	<div class="grid-title">
		<div class="pull-left">
			<div class="icon-title">
				<i class="icon-bookmark"></i>
			</div>
			<span>Inputs</span>
			<div class="clearfix"></div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="grid-content">
		<form method="post">
			<div class="formRow">
				<label>Name: </label>
				<div class="formRight">
					<input type="text" id="name" name="name" class="span input" placeholder="Name..." value="${node.name}" />
				</div>
			</div>
			<div class="formRow">
				<label>Select:</label>
				<div class="formRight">
					<select  id="sla" name="sla" class="chzn-select chosen_select width-100">
						<option value=""></option>
						<c:forEach items="${slas}" var="sla">
							<option
								<c:if test="${sla.id == node.sla}">selected</c:if>
								value="${sla.id}">${sla.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="formRow">
				<div class="formRight">
					<button type="submit" class="btn btn-primary metro">Save</button>
					<button type="button" class="btn metro"
						onclick="location.href = '<c:url value="/admin/" /><tiles:insertAttribute name="uri" />'">Cancel</button>
				</div>
			</div>
			<div class="clearfix"></div>
		</form>
	</div>
</div>



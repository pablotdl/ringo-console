<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!--page title-->
<div class="pagetitle">
	<c:set var="id" value="New" />
	<c:if test="${invocation.id != null}">
		<c:set var="id" value="${invocation.id}" />
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
		<form:form method="post" commandName="invocation">
			<div class="formRow">
				<label>Method: </label>
				<div class="formRight">
					<form:input path="method"  class="span input" />
				</div>
			</div>
			<div class="formRow">
				<label>Execution time: </label>
				<div class="formRight">
					<form:input path="execution_time"  class="span input" />
				</div>
			</div>			
			<div class="formRow">
				<label>Sla:</label>
				<div class="formRight">
					<form:select path="sla" class="chzn-select chosen_select width-100">
						<form:option value="" label="--- Select ---"/>
   						<form:options items="${slas}" itemValue="id" itemLabel="name"/>
   					</form:select>
				</div>
			</div>
			<div class="formRow">
				<label>Node:</label>
				<div class="formRight">
					<form:select path="node" class="chzn-select chosen_select width-100">
						<form:option value="" label="--- Select ---"/>
   						<form:options items="${nodes}" itemValue="id" itemLabel="name"/>
   					</form:select>
				</div>
			</div>			
			<div class="formRow">
				<label>Timestamp: </label>
				<div class="formRight">
					<form:input path="timestamp" class="span input" />
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
		</form:form>
	</div>
</div>



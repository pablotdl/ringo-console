<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!--page title-->
<div class="pagetitle">
	<c:set var="id" value="New" />
	<c:if test="${sla.id != null}">
		<c:set var="id" value="${sla.id}" />
	</c:if> 
	<h1><tiles:insertAttribute name="entity" ignore="true" />: ${id}</h1>
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
		<form:form method="post" commandName="sla">
			<div class="formRow">
				<label>Name: </label>
				<div class="formRight">
					<form:input path="name"  class="span input" />			
				</div>
			</div>
	        <div class="formRow">
	               <label>Description: </label>
	               <div class="formRight">
	               		<form:textarea path="description" rows="5" class="span input same-height-1" />
	               </div>
	        </div>
			<div class="formRow">
				<div class="formRight">
					<p>
						<input type="checkbox" id="enabled" name="enabled"  
							<c:if test="${sla.enabled}">checked</c:if> 
						/> 
						<label for="enabled"><span> </span> Enabled</label>
					</p>
				</div>
			</div>
			<div class="formRow">	
				<div class="formRight">	
					<button type="submit" class="btn btn-primary metro">Save</button>
					<button type="button" class="btn metro" onclick="location.href = '<c:url value="/admin/" /><tiles:insertAttribute name="uri" />'" >Cancel</button>
				</div>
			</div>
			<div class="clearfix"></div>   
		</form:form>
	</div>
</div>

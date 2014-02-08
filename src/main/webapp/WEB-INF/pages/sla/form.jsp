<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<form method="post">
			<div class="formRow">
				<label>Name: </label>
				<div class="formRight">
					<input type="text" id="name" name="name" class="span input" placeholder="Name..." value="${sla.name}" />			
				</div>
			</div>
	        <div class="formRow">
	               <label>Description: </label>
	               <div class="formRight">
	                   <textarea  class="span input same-height-1" id="description" name="description" rows="3">${sla.description}</textarea>
	               </div>
	        </div>
			<div class="formRow">
				<div class="formRight">
					<p>
						<c:if test="${sla.enabled}">
							<c:set var="enabled" value="checked" />
						</c:if> 
						<input type="checkbox" id="enabled" name="enabled" ${enabled} /> 
						<label for="enabled"><span> </span> Enabled</label>
					</p>
				</div>
			</div>
			<div class="formRow">	
				<div class="formRight">	
					<button type="submit" class="btn btn-primary metro">Save</button>
					<button type="button" class="btn metro" onclick="location.href = '<c:url value="/admin/sla" />'" >Cancel</button>
				</div>
			</div>
			<div class="clearfix"></div>   
		</form>
	</div>
</div>

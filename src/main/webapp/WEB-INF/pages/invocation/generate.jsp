<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!--page title-->
<div class="pagetitle">
	<h1>Generate Invocations</h1>
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
		<form:form method="post" commandName="generate">
			<div class="formRow">
				<label>Invocations: </label>
				<div class="formRight">
					<form:input path="invocations"  class="span input" />
				</div>
			</div>		
			<div class="formRow">
				<label>Method: </label>
				<div class="formRight">
					<form:input path="method"  class="span input" />
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
					<button type="submit" class="btn btn-primary metro">Generate</button>
					<button type="button" class="btn metro"
						onclick="location.href = '<c:url value="/admin/" /><tiles:insertAttribute name="uri" />'">Cancel</button>
				</div>
			</div>
			<div class="clearfix"></div>
		</form:form>
	</div>
</div>



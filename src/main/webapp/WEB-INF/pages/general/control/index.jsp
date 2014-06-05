<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!--page title-->
<div class="pagetitle">
	<h1>Control</h1>
	<div class="clearfix"></div>
</div>
<!--page title end-->

<!--Collapsible Group Start-->
<div class="grid-transparent">
	<c:forEach items="${slas}" var="sla">
		<div class="accordion" id="accordion-accordion-sla-${sla.id}" style="margin-top: 10px">
			<div class="accordion-group">
				<div class="accordion-titleing grid-title">
					<div class="pull-left">
						<div class="icon-title">
							<i class="icon-bar-chart"></i>
						</div>
						<a class="accordion-toggle" data-toggle="collapse"
							data-parent="#accordion-sla-${sla.id}" href="#collapse-sla-${sla.id}"
							style="width: 300px">${sla.name}</a>
						<div class="clearfix"></div>
					</div>
				</div>
				<div id="collapse-sla-${sla.id}" class="accordion-body collapse in">
					<div class="accordion-inner">
						<div class="friend sla" ondrop="drop(event)" ondragover="allowDrop(event)" data-color="${sla.color}">
							<c:forEach items="${sla_nodes.get(sla.id)}" var="node">
								<div id="node_${node.id}" class="f-avatar" style="border-color: ${sla.color}" draggable="true" ondragstart="drag(event)" >
									<div style="background-color: ${sla.color}"></div>
								</div>
							</c:forEach>												
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<script>
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("Text", ev.target.id);
}

function drop(ev) {
	ev.preventDefault();
	var sla = $(ev.target), 
		clearfix = sla.find(".clearfix"),
    	node = $("#" + ev.dataTransfer.getData("Text"));
    
	node.insertBefore(clearfix);
	node.css("border-color", sla.attr("data-color"));
	node.find("div").css("background-color", sla.attr("data-color"));
}
</script>

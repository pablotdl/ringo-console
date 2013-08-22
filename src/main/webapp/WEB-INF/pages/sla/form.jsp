
<div class="container-fluid">
    <div class="row"></div>
    <div class="row-fluid">
        <div class="span12 offset2">New SLA</div>
    </div>
    <div class="row-fluid">
		<div class="span12 offset1">
			<form class="form-horizontal" method="post">
				<div class="control-group">
					<label class="control-label" for="name">SLA name</label>
					<div class="controls">
					<input type="text" id="name" name="name" placeholder="Name..." value="${sla.name}" />
					</div>
				</div>
				<div class="control-group">
				    <label class="control-label" for="description">Description</label>
				    <div class="controls"><textarea id="description" name="description">${sla.description}</textarea></div>
				</div>
				<div class="control-group">
                    <div class="controls">
                        <label class="checkbox">
                            <input type="checkbox"> Enabled
                        </label>
				    	<button type="submit" class="btn">Save</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>

// INFO BOX
// tootips chart
function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css( {
        position: 'absolute',
        display: 'none',
        top: y + 5,
        left: x + 5,
        border: '0px',
        padding: '2px 10px 2px 10px',
        opacity: 0.8,
    }).appendTo("body").fadeIn(200);
}
 
function loadStats(element, data, color) {

    //d1 = [ ['jan', 100], ['feb', 132], ['mar', 92], ['apr', 152], ['maj', 116], ['jun', 264], ['jul', 104] ];
    //d2 = [ ['jan', 100], ['feb', 132], ['mar', 92], ['apr', 152], ['maj', 116], ['jun', 264], ['jul', 104] ];
    //d3 = [ ['jan', 100], ['feb', 132], ['mar', 92], ['apr', 152], ['maj', 116], ['jun', 264], ['jul', 104] ];
    
    //var visitor = $("#visitor-stat"),
    //order = $("#order-stat"),
    //user = $("#user-stat"),
    
	/*
    data_visitor = [{
            data: d1,
            color: '#1aae1a'
        }],
    data_order = [{
            data: d2,
            color: '#3f94ed'
        }],
    data_user = [{
            data: d3,
            color: '#ff6868'
        }],
     */
    var data_conf = [{
        data: data,
        color: color
    }],
	
    stack = true,
	lines = true,
	steps = false;
    
    options_lines = {
        series: {
        	stack: stack,
        	shadowSize: 0,	
			lines: {
				show: lines,
				fill: true,
				steps: steps
			},
        },
        grid: {
            backgroundColor: '#FFFFFF',
            borderWidth: 0,
            borderColor: '#CDCDCD',
            hoverable: true
        },
        xaxis: {
            show: false
        }

    };
    
    // render stat flot
    //$.plot(visitor, data_visitor, options_lines);
    //$.plot(order, data_order, options_lines);
    //$.plot(user, data_user, options_lines);
    $.plot(element, data, {
		series: {
			shadowSize: 0,
			stack: stack,
			lines: {
				show: lines,
				fill: true,
				steps: steps
			}
		},
        grid: {
            backgroundColor: '#FFFFFF',
            borderWidth: 0,
            borderColor: '#CDCDCD',
            hoverable: true
        },	
        xaxis: {
            show: false
        }        
	});


    var previousPoint = null;
    //$('#visitor-stat, #order-stat, #user-stat').bind("plothover", function (event, pos, item) {
    element.bind("plothover", function (event, pos, item) {    
        if (item) {
            if (previousPoint != item.dataIndex) {
                previousPoint = item.dataIndex;

                $("#tooltip").remove();
                var x = item.datapoint[0].toFixed(2),
                y = item.datapoint[1].toFixed(2);
                label = item.series.xaxis.ticks[item.datapoint[0]].label;
                
                showTooltip(item.pageX, item.pageY,
                label + " = " + y);
            }
        }
        else {
            $("#tooltip").remove();
            previousPoint = null;            
        }
        
    });
} 
// end
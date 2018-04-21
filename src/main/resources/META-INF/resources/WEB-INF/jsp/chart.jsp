<div class="col-xs-12 col-sm-12 col-md-4" id="highchart1" style="min-width: 310px; height: 400px; margin: 0 auto 5% auto;"></div>
	<script type="text/javascript">
	Highcharts.chart('highchart1', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'Number of ideas by each Department statistics. April, 2018'
	    },
	    subtitle: {
	        text: 'Click the columns to view versions.'
	    },
	    xAxis: {
	        type: 'category'
	    },
	    yAxis: {
	        title: {
	            text: 'Total number ideas'
	        }

	    },
	    legend: {
	        enabled: false
	    },
	    plotOptions: {
	        series: {
	            borderWidth: 0,
	            dataLabels: {
	                enabled: true
	                /* format: '{point.y:.1f}%' */
	            }
	        }
	    },

	    tooltip: {
	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.f}</b> ideas<br/>'
	    },

	    "series": [
	        {
	            "name": "Ideas",
	            "colorByPoint": true,
	            
	            "data": ${numberOfIdeas}
	        }
	    ]
	});
	</script>
	
	<div class="col-xs-12 col-sm-12 col-md-4" id="piechart" style="min-width: 310px; height: 400px; margin: 0 auto 5% auto;"></div>
	<script type="text/javascript">
	Highcharts.chart('piechart', {
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie'
	    },
	    title: {
	        text: 'Percentage of ideas by each Department statistics. April, 2018'
	    },
	    tooltip: {
	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	    },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
	            dataLabels: {
	                enabled: false
	            },
	            showInLegend: true
	        }
	    },
	    series: [{
	        name: 'Percentage of ideas',
	        colorByPoint: true,
	        data: ${percentageOfIdeas}
	    }]
	});
	</script>
	
	<div class="col-xs-12 col-sm-12 col-md-4" id="highchart2" style="min-width: 310px; height: 400px; margin: 0 auto 5% auto;"></div>
	<script type="text/javascript">
	Highcharts.chart('highchart2', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'Number of contributors within each Department statistics. April, 2018'
	    },
	    subtitle: {
	        text: 'Click the columns to view versions.'
	    },
	    xAxis: {
	        type: 'category'
	    },
	    yAxis: {
	        title: {
	            text: 'Total number contributors'
	        }

	    },
	    legend: {
	        enabled: false
	    },
	    plotOptions: {
	        series: {
	            borderWidth: 0,
	            dataLabels: {
	                enabled: true
	                /* format: '{point.y:.1f}%' */
	            }
	        }
	    },

	    tooltip: {
	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.f}</b> contributors<br/>'
	    },

	    "series": [
	        {
	            "name": "Contributors",
	            "colorByPoint": true,
	            "data": ${numberOfContributor}
	        }
	    ]
	});
	</script>

<div class="col-xs-12 col-sm-12 col-md-4" id="highchart3" style="min-width: 310px; height: 400px; margin: 0 auto 5% auto;"></div>
	<script type="text/javascript">
	Highcharts.chart('highchart3', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'Ideas without a comment statistics. April, 2018'
	    },
	    subtitle: {
	        text: 'Click the columns to view versions.'
	    },
	    xAxis: {
	        type: 'category'
	    },
	    yAxis: {
	        title: {
	            text: 'Total number ideas'
	        }

	    },
	    legend: {
	        enabled: false
	    },
	    plotOptions: {
	        series: {
	            borderWidth: 0,
	            dataLabels: {
	                enabled: true
	                /* format: '{point.y:.1f}%' */
	            }
	        }
	    },

	    tooltip: {
	        headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
	        pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.f}</b> ideas<br/>'
	    },

	    "series": [
	        {
	            "name": "Ideas",
	            "colorByPoint": true,
	            "data": ${ideasWithoutComment}
	        }
	    ]
	});
	</script>
	
	<div class="col-xs-12 col-sm-12 col-md-4" id="highchart4" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	<script type="text/javascript">
	Highcharts.chart('highchart4', {
	    chart: {
	        type: 'column'
	    },
	    title: {
	        text: 'Anonymous ideas and comments'
	    },
	    xAxis: {
	        categories: ['Ideas', 'Comments']
	    },
	    credits: {
	        enabled: false
	    },
	    series: [{
	        name: 'Computing',
	        data: [5, 3]
	    }, {
	        name: 'Business',
	        data: [2, 2]
	    }, {
	        name: 'Graphic Design',
	        data: [3, 4]
	    }, {
	        name: 'Language',
	        data: [1, 3]
	    }, {
	        name: 'Financial',
	        data: [1, 5]
	    }]
	});
	</script>
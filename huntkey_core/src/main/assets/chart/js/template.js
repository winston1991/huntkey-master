function loadChartView(id, option) {
    var myChart = echarts.init(document.getElementById(id));
    myChart.setOption(option);
}

function initChart() {

    loadChartView('chart', JSON.parse(window.Android.makeBarChartOptions()))
    loadChartView('chart2',  JSON.parse(window.Android.makePieChartOptions()))
    loadChartView('chart3', JSON.parse(window.Android.makeLineChartOptions()))
    loadChartView('chart4', JSON.parse(window.Android.makeGaugeChartOptions()))
}

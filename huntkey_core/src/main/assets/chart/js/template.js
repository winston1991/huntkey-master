function loadChartView(id, option) {
    var myChart = echarts.init(document.getElementById(id), "shine");
    myChart.setOption(option);
    window.addEventListener("resize", function(){
        myChart.resize();
    });
    window.onresize = function(){
    myChart.resize();
   }
  return "ok";
}

function clearChart()
{
   echarts.getInstanceByDom(document.getElementById("chart1")).dispose ();
   echarts.getInstanceByDom(document.getElementById("chart2")).dispose ();
   echarts.getInstanceByDom(document.getElementById("chart3")).dispose ();
   echarts.getInstanceByDom(document.getElementById("chart4")).dispose ();

}

function hideDiv()
{
   document.getElementById("chart4").style.display="none";//隐藏
}

function showDiv()
{
   document.getElementById("chart4").style.display="";//显示
}

function initChart() {

//    loadChartView('chart', JSON.parse(window.Android.makeBarChartOptions()))
//    loadChartView('chart2',  JSON.parse(window.Android.makePieChartOptions()))
//    loadChartView('chart3', JSON.parse(window.Android.makeLineChartOptions()))
//    loadChartView('chart4', JSON.parse(window.Android.makeGaugeChartOptions()))
      loadChartView('chart5',getChart() )

}


function getChart()
{
    var option = {
                      xAxis: {
                          type: 'category',
                          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                      },
                      yAxis: {
                          type: 'value'
                      },
                       dataZoom: [{
                          type: 'inside',
                          start: 0,
                          end: 10
                      }, {
                          start: 0,
                          end: 10,
                          handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                          handleSize: '80%',

                      }],
                      series: [{
                          data: [820, 932, 901, 934, 1290, 1330, 1320],
                          type: 'line'
                      }]
                  };
    return option;
}
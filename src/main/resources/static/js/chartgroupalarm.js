



/**
 * Making diagram on the alarms groups
 */

var ret = 0;//Для присвоения id <canvas id='myChart'+idx>
var i = 0;
var arrChartMain = [];
var arrChart = [];
var chartObj = [];

/**
* Удаление all myCharts
*/
function destroyChart(){
	var j = chartObj.length;
	var i;
	for(i = 0; i < j; i++){
		chartObj[i].destroy();
	}
	chartObj.lenght = 0;
	arrChartMain.length = 0;
	arrChart.length = 0;
}

/**
 * Making Diagram
 * 
 */
function chartDiagramGroup(){
	
 arrChartMain = new Array();
 arrChart = new Array();
	
 /**
 * Заполнение данными массивов для построения диаграммы
 */
i = 0;
_.each(listFlow, function(list) {
	  if(list.length != 0){
	    arrChartMain.push(list.countRow === undefined ? 0 : list.countRow);
		arrChart.push(list.nameGroup);
	  }
	  i++;
});	

  ret = makeCharts(ret, arrChart, arrChartMain, 'My label', 'rgba(1, 1, 255, 0.4)', 'rgba(1, 1, 255, 1)');
	
}


/**
 * Creating My Chart
 *
 */
function makeCharts(idx, arrChart, arrChartMain, label, backColor, borderColor){
	addCanvas(idx);
	chartObj[idx] = addChart(idx, arrChart, arrChartMain, label, backColor, borderColor);
	return (++idx);
}

/**
 * This is creating Canvas that, will be drawing diagram
 * 
 */
function addCanvas(idx){
	var div = document.getElementById("main_div");
	var canvas = document.createElement('canvas');
	canvas.setAttribute('id', 'myChart'+idx);
	canvas.setAttribute('width', '400');
	canvas.setAttribute('height', '100');
	div.appendChild(canvas);
}


function addChart(idx, arrayLabel, arrayData, label, backColor, borderColor){
var ctx = null;
var myChart = null;

ctx = document.getElementById('myChart'+idx).getContext('2d');
myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: arrayLabel,
        datasets: [{
	        minBarLength: 2,
            label: label,
            data: arrayData,
            backgroundColor: [
                backColor
            ],
            borderColor: [
                borderColor
            ],
            borderWidth: 1
        }]
    },
    options: {
		
		responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Распределение аварий по группам'
      },
      colors:{
		  enabled:false
	  }
    },
	    
       scales: {
        	x: {
              },
            y: {
                beginAtZero: true,
                
            }
        }
    }
});
 return myChart;
}



 
 /**
 * Сброс фильтрации
 */
$(function(){
   $('#btn-reset').click(function(){
	  location.reload();
	  //dateFrom = myDateNow();
	  //$('#datefrom').val(dateFrom);
	  
   });
});

/**
 * Вывод текущей даты в формате YYYY-MM-DD 
 */
   function myDateNow(){
 	  var mlsk = Date.now();
 	  var nowDate = new Date(mlsk);
 	  var year = nowDate.getFullYear().toString();
 	  var month = (nowDate.getMonth() + 1).toString().length > 1 ? (nowDate.getMonth() + 1).toString() : '0'+(nowDate.getMonth() + 1).toString();
 	  var day = nowDate.getDate().toString().length > 1 ? nowDate.getDate().toString() : '0'+nowDate.getDate().toString();  
 	  var dat = year + '-' + month + '-' + day;
 	  return dat;
   }
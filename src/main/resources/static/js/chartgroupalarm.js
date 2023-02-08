

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

  ret = makeCharts(ret, arrChart, arrChartMain, 'Аварии в группах');
	
}


/**
 * Creating My Chart
 *
 */
function makeCharts(idx, arrChart, arrChartMain, label){
	addCanvas(idx);
	chartObj[idx] = addChart(idx, arrChart, arrChartMain, label);
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


function addChart(idx, arrayLabel, arrayData, label){
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
               'rgba(255, 99, 132, 0.5)',
                'rgba(54, 162, 235, 0.5)',
                'rgba(255, 206, 86, 0.5)',
                'rgba(75, 192, 192, 0.5)',
                'rgba(153, 102, 255, 0.5)',
                'rgba(255, 159, 164, 0.5)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 0.8)',
                'rgba(54, 162, 235, 0.8)',
                'rgba(255, 206, 86, 0.8)',
                'rgba(75, 192, 192, 0.8)',
                'rgba(153, 102, 255, 0.8)',
                'rgba(255, 159, 64, 0.8)'
            ],
            borderWidth: 1
        }]
    },
    options: {
	   responsive: true,
       
       plugins: {
         colors: {
          forceOverride: true
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


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
function chartDiagramGroup(nameLabel, type){
  
 arrChartMain = new Array();
 arrChart = new Array();
 
	
 /**
 * Заполнение данными массивов для построения диаграммы
 */
i = 0;
_.each(listFlow, function(list) {
	  if(list.length != 0){
	    arrChartMain.push(list.countRow === undefined ? 0 : list.countRow);
	    if(type == 1){ //for groups
		arrChart.push(list.nameGroup);
		}
		
	    if(type == 2){//for alarms from groups
		arrChart.push(list.nameAlarm);
		}
	  }
	  i++;
});	

  ret = makeCharts(ret, arrChart, arrChartMain, nameLabel);
	
}


/**
 * Creating My Chart
 *
 */
function makeCharts(idx, arrChart, arrChartMain, label){
    addCanvas(idx);
    
    var sel = $('#listTypeDiagram').val();
    if(sel == 1){
	   chartObj[idx] = addChart(idx, arrChart, arrChartMain, label);
	}else if(sel == 2){
       chartObj[idx] = addPie(idx, arrChart, arrChartMain, label);
    }
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
	//canvas.setAttribute('width', '500');
	canvas.setAttribute('height', '500');
	div.appendChild(canvas);
}

/**
 * Make diagram type at Pie
 */
function addPie(idx, arrayLabel, arrayData, label){
var ctx = null;
var myChart = null;
ctx = document.getElementById('myChart'+idx).getContext('2d');

myChart = new Chart(ctx, {
	
    type: 'pie',
    
    data: {
        labels: arrayLabel,
        datasets: [{
	        minBarLength: 2,
            label: label,
            data: arrayData,
            backgroundColor: [
                '#FF00FF80',
                '#80008080',
                '#FF000080',
                '#80000080',
                '#FFFF0080',
                '#80800080',
                '#00FF0080',
                '#00800080',
                '#00FFFF80',
                '#00808080',
                '#0000FF80',
                '#00008080'
            ],
            borderColor: [
                '#FF00FF90',
                '#80008090',
                '#FF000090',
                '#80000090',
                '#FFFF0090',
                '#80800090',
                '#00FF0090',
                '#00800090',
                '#00FFFF90',
                '#00808090',
                '#0000FF90',
                '#00008090'
            ],
           hoverOffset: 4
        }]
    },
    options: {
	   responsive: true,
	   //radius: '100%',
	   maintainAspectRatio: false,
	   	   	          
       plugins: {
         colors: {
          forceOverride: true
         }
       }
     }
});
 return myChart;
}

	
	
	
/**
 * Make diagram type at Bars
 */

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
                '#FF00FF80',
                '#80008080',
                '#FF000080',
                '#80000080',
                '#FFFF0080',
                '#80800080',
                '#00FF0080',
                '#00800080',
                '#00FFFF80',
                '#00808080',
                '#0000FF80',
                '#00008080'
            ],
            borderColor: [
                '#FF00FF90',
                '#80008090',
                '#FF000090',
                '#80000090',
                '#FFFF0090',
                '#80800090',
                '#00FF0090',
                '#00800090',
                '#00FFFF90',
                '#00808090',
                '#0000FF90',
                '#00008090'
            ],
            borderWidth: 1
        }]
    },
    options: {
	   responsive: true,
	   maintainAspectRatio: false,
       
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
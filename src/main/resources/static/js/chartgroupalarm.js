/**
 * Making diagram on the alarms groups
 */

var ret = 0;//Для присвоения id <canvas id='myChart'+idx>
var i = 0;
var arrChartMain = [ [ [] ] ];
var arrChart = [ [ [] ] ];
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
	arrChartMain[0].length = 0;
	arrChart[0].length = 0;
}

/**
 * Making Diagram
 * 
 */
function chartDiagramGroup(){
	
 arrChartMain[0][ret] = new Array();
 arrChart[0][ret] = new Array();
	
 /**
 * Заполнение данными массивов для построения диаграммы
 */
i = 0;
_.each(listFlow, function(list) {
	    arrChartMain[0][ret][i] = list.countRow == undefined ? 0 : list.countRow;
		arrChart[0][ret][i] = list.nameGroup;//str;
		
		i++;
});	
	
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
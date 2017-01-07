$(function(){
		initTable();
		
		$('#find').click(function(){
			$table.bootstrapTable('refresh');
		});
		
		/*$('#start,#end').datetimepicker({
	 		format: 'yyyy-mm-dd',
	 		language: 'zh-CN',
	 		pickTime: false,
	 		minView: "month"
	 	});*/
		
		
		$('#start').datetimepicker({
		    format:'yyyy-mm-dd',
		    language:  'zh-CN',
		    //weekStart: 1,
		    //todayBtn:  1,
		    autoclose: 1,
		    todayHighlight: 1,
		    startView: 2,
		    minView: "month",
		    forceParse: 0,
		    //showMeridian: 1
		}).on("changeDate",function(ev){
		    var transferdate=transferDate($("#start").val());//转时间日期
		    $('#end').datetimepicker('remove');
		    $('#end').datetimepicker({
		        format:'yyyy-mm-dd',
		        language:  'zh-CN',
		        minView: "month",
		        autoclose: 1,
		        'startDate':transferdate
		    }).on("changeDate",function(ev){
		        var enddate=$("#end").val();
		        setEndTime(enddate);
		    });
		});
		$('#end').datetimepicker({
		    format:'yyyy-mm-dd',
		    language:  'zh-CN',
		    minView:2,
		    autoclose: 1
		}).on("changeDate",function(ev){
		    var enddate=$("#end").val();
		    setEndTime(enddate);
		});
			
});	
		
function initTable() {
	$table=$('#photoList').bootstrapTable({
		url: 'getphotoList.do',
		method: 'post',
		contentType: "application/x-www-form-urlencoded",
		//toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		pagination: true,                   //是否显示分页（*）
        //sortable: true, 
        showColumns: false,
        minimumCountColumns: 2,
        //sortOrder: "asc",                   //排序方式
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        sidePagination : "server", // 服务端处理分页
        //clickToSelect :true,
        queryParams: queryParams,
		columns: [{field: 'state',checkbox:true, align : 'center'},
			      {field: 'id',visible:false, align : 'center'},
		          {field: 'userNo',title: '户号', align : 'center'}, 
		          {field: 'userName',title: '户名', align : 'center'},
			      {field: 'name',title: '上传用户', align : 'center'},
			      {field: 'time',title: '上传时间', align : 'center',formatter: operateTime},
		          {field: 'operate',title: '操作', align : 'center',formatter: operateFormatter, events: operatevent}
				  ],
        responseHandler : function(res) {  
            return { 
                total:res.total,  
                rows:res.records  
            };  
        }
	});
}


function operateTime(value,row,index){
	var date = new Date(value);
	var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var hour = date.getHours();
    var min = date.getMinutes();
    return year+"-"+month+"-"+day+" "+hour+":"+min;
}

function operateFormatter(value, row, index){
	return '<a href="getphoto.do?userNo='+row.userNo +'"><i class=\'glyphicon glyphicon-th-list\'></i> 查看</a>'+
	        '&nbsp&nbsp'+
	        '<a href="download.do?userNo='+row.userNo +'"><i class=\'glyphicon glyphicon-download-alt\'></i> 下载</a>'+
	        '&nbsp&nbsp'+
	        '<a onclick=deletePicture(\''+row.userNo+'\')><i class=\'glyphicon glyphicon-remove\'></i> 删除</a>';
}


function operatevent(){}



function queryParams(params){
	console.log(params);
	return {
		    pageSize: params.limit,   //页面大小
	        pageNumber: params.offset,  //页码
	        sortOrder: params.order,//排位命令
	        name: $('#name').val(),
			userName:$('#userName').val(),
			userNo:$('#userNo').val(),
			start:$('#start').val(),
			end:$('#end').val()
	};
}


function deletePicture(userNo){
	$.ajax({
			type:"post",  //提交方式  
            dataType:"json", //数据类型  
            data: {userNo:userNo},
            url:"delete.do", //请求url  
            success:function(data){ //提交成功的回调函数  
                $.each(data, function(k, v) {
                   alert(v);
            });
            $('#photoList').bootstrapTable('refresh', {url: "getphotoList.do"});
            }
	 });
}



function setEndTime(enddate){
    $('#start').datetimepicker('remove');
        $('#start').datetimepicker({
            format:'yyyy-mm-dd',
            language:  'zh-CN',
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            'endDate':transferDate(enddate)
    });
}
//将时间字符串转为date
function transferDate(data){
    var start_time=data;
    var newTime= start_time.replace(/-/g,"-");
    var transferdate = new Date(newTime);
    return transferdate;
}
function transferTime(str){
    var newstr=str.replace(/-/g,'-');
    var newdate=new Date(newstr);
    var time=newdate.getTime();
    return time;
}







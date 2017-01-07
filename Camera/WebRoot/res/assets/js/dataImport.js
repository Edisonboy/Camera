(function ($) {
	$(document).ready(function () {
		$('#Table').bootstrapTable({
			url: 'getDataList.do',
			method: 'get',
			toolbar: '#toolbar',                //工具按钮用哪个容器
			striped: true,                      //是否显示行间隔色
			pagination: true,                   //是否显示分页（*）
	        sortable: true, 
	        search: true,
	        //height: '100%',//是否启用排序
	        showColumns: false,
	        minimumCountColumns: 2,
	        sortOrder: "asc",                   //排序方式
	        pageNumber:1,                       //初始化加载第一页，默认第一页
	        pageSize: 10,                       //每页的记录行数（*）
	        //pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
	        idField: 'id',
	        sidePagination : "server", // 服务端处理分页
	        clickToSelect :true,
	        uniqueId : "id",
	        queryParamsType : "undefined",
	        columns: [{field: 'state',checkbox:true, align : 'center'},
			      {field: 'id',visible:false, align : 'center'},
			      {field: 'worksheetNo',title: '工作单编号', align : 'center',sortable: true,width:700},
		          {field: 'operate',title: '操作', align : 'center',formatter: operateFormatter, events: operatevent}
				  ],
	        responseHandler : function(res) {  
	            return { 
	                total:res.total,  
	                rows:res.records  
	            };  
	        }
		});
		
		function operateFormatter(value, row, index){
			return '<a href="getData.do?worksheetNo='+row.worksheetNo +'"><i class=\'glyphicon glyphicon-search\'></i> 查看</a>'+
			       '&nbsp&nbsp'+
			       '<a onclick=deleteData(\''+row.worksheetNo+'\')><i class=\'glyphicon glyphicon-remove\'></i> 删除</a>';
		}
		
		function operatevent(){}
		
		$('#add').click(function(){
			var flag = $('#fileToUpload').val();
			if(flag!=""){
	            $('#loading').show();
	            $.ajaxFileUpload({
	                url : 'dataTobase.do',
	                secureuri : false,
	                fileElementId : 'fileToUpload',
	                dataType : 'json',//此时指定的是后台需要返回json字符串,前端自己解析,可以注释掉.后台直接返回map
	                success : function(data, status) {
	                	$('#loading').hide();
	                	$.each(data, function(k, v) {
		                       alert(v);
		                });
	                	$('#Table').bootstrapTable('refresh', {url: "getDataList.do"});
	                },
	                error : function(data, status, e) {
	                    $('#loading').hide();
	                    alert("上传发生异常");
	                }
	            });
			}else{
				alert("请选择文件！");
			}
		});
		
	});
})(jQuery);


	var deleteData = function(worksheetNo){
		alert(worksheetNo);
		$.ajax({
			type:"post",  //提交方式  
	        dataType:"json", //数据类型  
	        data: {worksheetNo:worksheetNo},
	        url:"deleteData.do", //请求url  
	        success:function(data){ //提交成功的回调函数  
	            $.each(data, function(k, v) {
	                   alert(v);
	            });
	        $('#Table').bootstrapTable('refresh', {url: "getDataList.do"});
	        }
 		});
	 }
$(function(){
	$('#Table').bootstrapTable({
		url: 'getData.do',
		method: 'get',
		toolbar: '#toolbar',                //工具按钮用哪个容器
		striped: true,                      //是否显示行间隔色
		pagination: true,                   //是否显示分页（*）
        sortable: true, 
        //height: '100%',//是否启用排序
        showColumns: false,
        minimumCountColumns: 2,
        sortOrder: "asc",                   //排序方式
        pageNumber:1,                       //初始化加载第一页，默认第一页
        pageSize: 10,                       //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        idField: 'recordNo',
        sidePagination : "server", // 服务端处理分页
        clickToSelect :true,
        uniqueId : "recordNo",
        queryParamsType : "undefined",
		columns: [{field: 'state',checkbox:true, align : 'center'},
			      {field: 'recordNo',visible:false, align : 'center'},
		          {field: 'userNo',title: '户号', align : 'center'}, 
		          {field: 'userName',title: '姓名', align : 'center'},
		          {field: 'address',title: '地址', align : 'center' ,width: 100,},
		          {field: 'cflag',title: '变更标志', align : 'center'},
		          {field: 'meterNo',title: '抄表序号', align : 'center'},
		          {field: 'type',title: '型号/规格', align : 'center'},
		          {field: 'tableNo',title: '表号', align : 'center'},
		          {field: 'pedestal',title: '底度', align : 'center'},
		          {field: 'sealposition',title: '封印位置', align : 'center'},
		          {field: 'sealNo',title: '封印号', align : 'center'},
		          {field: 'caseNo',title: '箱(柜)号', align : 'center'},
		          {field: 'staff',title: '工作人员', align : 'center'},
		          {field: 'date',title: '装/拆日期', align : 'center'},
		          {field: 'signature',title: '客户签名', align : 'center'}
				  ],
        responseHandler : function(res) {  
            return { 
                total:res.total,  
                rows:res.records  
            };  
        }
	});
	
	
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
                	$('#Table').bootstrapTable('refresh', {url: "getData.do"});
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


var goback = function(){
	window.location.href="getDataList.do";
}

var getTableForm = function() {
	return document.getElementById('tableForm');
}
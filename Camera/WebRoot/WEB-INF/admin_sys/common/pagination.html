<#macro pagination value >
<#if !value?is_sequence>
<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td align="center" class="pn-sp">
	共 ${value.totalCount} 条&nbsp;
	每页<input type="text" value="${value.pageSize}" style="width:30px" onfocus="this.select();" onblur="$.cookie('_cookie_page_size',this.value,{expires:3650});" onkeypress="if(event.keyCode==13){$(this).blur();return false;}"/>条&nbsp;
	<input class="first-page" type="button" value="首 页" onclick="_gotoPage('1');"<#if value.firstPage> disabled="disabled"</#if>/>
	<input class="pre-page" type="button" value="上一页" onclick="_gotoPage('${value.prePage}');"<#if value.firstPage> disabled="disabled"</#if>/>
	<input class="next-page" type="button" value="下一页" onclick="_gotoPage('${value.nextPage}');"<#if value.lastPage> disabled="disabled"</#if>/>
	<input class="last-page" type="button" value="尾 页" onclick="_gotoPage('${value.totalPage}');"<#if value.lastPage> disabled="disabled"</#if>/>&nbsp;
	当前 ${value.pageNo}/${value.totalPage} 页 &nbsp;转到第<input type="text" id="_goPs" style="width:50px" onfocus="this.select();" onkeypress="if(event.keyCode==13){$('#_goPage').click();return false;}"/>页
	<input class="go" id="_goPage" type="button" value="转" onclick="_gotoPage($('#_goPs').val());"<#if value.totalPage==1> disabled="disabled"</#if>/>
</td></tr></table>
<script type="text/javascript">
function _gotoPage(pageNo) {
	try{
		var tableForm = getTableForm();
		$("input[name=pageNo]").val(pageNo);
		tableForm.action="${listAction}";
		tableForm.onsubmit=null;
		tableForm.submit();
	} catch(e) {
		alert('_gotoPage(pageNo)方法出错');
	}
}
</script>
</#if>
</#macro>
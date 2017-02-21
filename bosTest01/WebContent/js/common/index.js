/**
 * 
 */
	// 初始化ztree菜单
	$(function() {
		var setting = {
			data : {
				simpleData : { // 简单数据 
					enable : true
				}
			},
			callback : {
				onClick : onClick
			}
		};
		
		// 基本功能菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/json/menu.json',
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				//var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#treeMenu"), setting, data);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 系统管理菜单加载
		$.ajax({
			url : '${pageContext.request.contextPath}/json/admin.json',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#adminMenu"), setting, zNodes);
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		});
		
		// 页面加载后 右下角 弹出窗口
		/**************/
		window.setTimeout(function(){
			$.messager.show({
				title:"消息提示",
				msg:'欢迎登录，超级管理员！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
				timeout:5000
			});
		},3000);
		/*************/
		
		$("#btnCancel").click(function(){
			$('#editPwdWindow').window('close');
		});
	});

	function onClick(event, treeId, treeNode, clickFlag) {
		// 判断树菜单节点是否含有 page属性
		if (treeNode.page!=undefined && treeNode.page!= "") {
			if ($("#tabs").tabs('exists', treeNode.name)) {// 判断tab是否存在
				$('#tabs').tabs('select', treeNode.name); // 切换tab
			} else {
				// 开启一个新的tab页面
				var content = '<div style="width:100%;height:100%;overflow:hidden;">'
						+ '<iframe src="'
						+ treeNode.page
						+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

				$('#tabs').tabs('add', {
					title : treeNode.name,
					content : content,
					closable : true
				});
			}
		}
	}

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'
				+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		$.messager
		.confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
			if (isConfirm) {
				location.href = '${pageContext.request.contextPath }/userAction_logout.action';
			}
		});
	}
	
	
	// 修改密码
	function editPassword() {
		$('#editPwdWindow').window('open');
	}
	// 版权信息
	function showAbout(){
		$.messager.alert("宅急送 v1.0","管理员邮箱: 372070693@qq.com");
	}
	
	
	
	
	  //为“确定”按钮绑定事件
	$("#btnEp").click(function(){
		//进行表单校验
		var v = $("#editPasswordForm").form("validate");//对应表单中的所有输入框进行校验
		if(v){//表单校验通过
			//判断两次输入是否一致
			var v1 = $("#txtNewPass").val();
			var v2 = $("#txtRePass").val();
			if(v1 == v2){
				//输入一致，发送ajax请求，修改当前用户的密码
				var url = "${pageContext.request.contextPath}/userAction_editPassword.action";
				$.post(url,{"password":v1},function(data){
					if(data == '1'){
						//修改密码成功
						$.messager.alert("提示信息","密码修改成功！","info");
					}else{
						//修改失败
						$.messager.alert("提示信息","密码修改失败！","warning");
					}
					//关闭修改密码的窗口 
					$("#editPwdWindow").window("close");
				});
			}else{
				//输入不一致，提示用户输入不一致
				$.messager.alert("提示信息","两次输入密码不一致！","warning");
			}
		}
	});

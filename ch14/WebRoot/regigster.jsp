<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>注册界面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>
<body class="reg">
<%
	String str="";
	String msg_name = (String)request.getSession().getAttribute("msg_sign_name");
	if(msg_name!=null){
		str=msg_name;
	}
 %>
 <%
	String str1="";
	String msg_error = (String)request.getSession().getAttribute("msg_sign_error");
	if(msg_error!=null){
		str1=msg_error;
	}
 %>
	<div style="align:center">

		<div class="row">
			<div class="col-md-4">
				<!-- Start Sign In Form -->
				<form action="SignUpServlet" class="fh5co-form animate-box"
					data-animate-effect="fadeInLeft" method="post">
					<label style="color: red;font-size: 20px;"><%=str1 %></label>
					<h2>注册界面</h2>
					
					
					<div>
						<label for="name" class="sr-only">名字</label> 
						<label style="color: red;font-size: 10px;"><%=str %></label>
						<input type="text" class="form-control" id="name" 
						placeholder="请输入名字" autocomplete="off" name="sign_name">
					</div>
					<br>
					<div class="form-group">
						<label for="password" class="sr-only">密码</label> 
						<input type="password" class="form-control" id="password"
							placeholder="请输入密码" autocomplete="off" name="sign_pwd">
					</div>
					<br>
					<div class="form-group">
						<label for="password" class="sr-only">确认密码</label> 
						<input type="password" class="form-control" id="password"
							placeholder="请再次输入密码" autocomplete="off" name="sign_pwd">
					</div>
					<br>
					<div class="form-group1">
						<label for="remember"> 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						<input type="radio" id="remember" name="sign_sex">男
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
						<input type="radio" id="remember" name="sign_sex">女
						</label>
					</div>
					<br>
					<div class="form-group1">
						<label for="email" class="sr-only">电话</label> 
						<input type="text" class="form-control" id="email" 
						placeholder="请输入电话" autocomplete="off" name="sign_phone">
					</div>
					<br>
					<div class="form-group">
						<label for="email" class="sr-only">家庭地址</label> 
						<input type="text" class="form-control" id="email" 
						placeholder="请输入家庭地址" autocomplete="off" name="sign_address">
					</div>
					<br>
					<div class="form-group">
						<p>
							已经注册? 点击<a href="index.jsp">登录</a>
						</p>
					</div>
					<div class="form-group">
						<input type="submit" value="注册" class="btn btn-primary">
					</div>
				</form>
			


			</div>
		</div>

	</div>



</body>
</html>

<%@page pageEncoding="GB2312"%>
<%@page contentType="text/html; charset=GB2312"%>
<%request.setCharacterEncoding("GB2312");%>

<%@ page import="java.util.*,database.*" %>
<jsp:useBean id="bookDB" class="database.BookDBAO" scope="session" /> 

<html>

<head>	
		
		<title>Kyle�����</title>
		<link href="book.css" rel="stylesheet" type="text/css">
		<link href="second.css" rel="stylesheet" type="text/css">
</head>
<body style="background-color:#fafafa">		


 <div class="ddnewhead_wrap">
    <div class="ddnewhead_content">
        <div class="ddnewhead_operate" id="__ddnav_menu">
          <ul class="ddnewhead_operate_nav">
        
            <li class="ddnewhead_separate"></li>
            <li></li>
          </ul>
      </div>
    </div>
    <div class="ddnewhead_bottom">
    <div class="ddnewhead_search_panel">
      <div class="ddnewhead_search">
        <div class="ddnewhead_adsearch"></div>
  </div>          <div class="clear"></div>
         </div>
    </div>
</div>
<script type="text/javascript">initHeaderOperate();
Suggest_Initialize("key_S",255,0,30);</script>
<div id="suggest_key" class="suggest_key" style="position: absolute; 
left: 472px; top: 138px; z-index: 10000; background-color: White; display: none;"></div>

		<!--ͨ�����-->
				<div class="book_banner"><h1 style="font-size:35px">��ӭ����Kyle�����</h1></div>

		<div class="book_wrap">
			<!--����-->
			<div class="book_sub">

<div class="sub_box">
    <h2 class="book_author">��������</h2>
    <div class="sub_box_author">
        <ul>
                        <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
						 <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
						 <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
						 <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
						      <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
						 <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
						 <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
						 <li>
                        	<a href="https://zhidao.baidu.com/" >Kyle</a>
							<a href="https://zhidao.baidu.com" >Mushan</a>
						</li>
       </ul>
    </div>
</div>
</div>


			<!--����-->
			<div class="book_main">

		<div class="book_channel"></div>				


		<div id="main_focus" ></div>


				
<div class="main_box">
	<div class="main_box_t">
		<h2>���ͼ��</h2>
	</div>
	<div class="main_box_c">
		<ul class="book_special">
			<%
			for(ListIterator iter=bookDB.getBooks().listIterator();iter.hasNext();){
				BookDetails book=(BookDetails)iter.next();
			%>
    <li ><span class="book_special_t">        
        	<a href="bookdetails.jsp?bookId=<%=book.getBookId()%>">
        		<b><%=book.getTitle()%>&nbsp;</b>
        	</a>
       </span>
    	<span class="book_special_price"> 
    		<%=book.getPrice()%>Ԫ 
    	</span> 
    	&nbsp;&nbsp;&nbsp;&nbsp;
    	<span style="background:#CFC" class="book_special_t2"> 
    		<a href="bookcatalog?Add=<%=book.getBookId()%>">
    			<b>���빺�ﳵ</b>
    		</a>
    	</span>
    	&nbsp;&nbsp;&nbsp;&nbsp;
         <span style="background-color:#FFC" class="book_special_price"> 
         ����: <em><%=book.getName()%>&nbsp;</em>
    </span>
    </li> 
    <br/>
    <%}%>
		</ul>
		<div class="clear"></div>
	</div>
</div>
<!--�ؼ������-->

			</div>


			<!--����-->
		  <div class="book_third">

				<!--��¼����-->

<div class="book_login">
    <h2>��¼����</h2>
    <div class="book_login_c">
    <br>
    <form name="from" method="post" action="">
    <p align="center">�û�����<input type="text" name="username"></p>
    <br>
    <p align="center">�� &nbsp;&nbsp;�� : &nbsp;<input type="password" name="password"></p>
    <br>
    <div class="book_login_l">
    <p align="center"><input type="submit" name="submit" value="��¼"></p>
    <br>
    </div>
    <p align="center"><a href=regigster.jsp >��ûע�ᣬ��������</a></p>
    </form>
</div>
</div>
	 

	

			<div class="foot">
		
   <span><p align="center">Kyle����� | ��Ȩ���� | ��������</p></span>
   <p align="center">��ϵ���ǣ�333-333</p>
   </div>
</body>
</html>
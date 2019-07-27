<%@ page contentType="text/html; charset=gb2312" language="java"  %>
<%request.setCharacterEncoding("gb2312");%>

<%@ page import="java.util.*,database.*" %>

<jsp:useBean id="bookDB" class="database.BookDBAO"  scope="session" /> 
<%
	String bookId=request.getParameter("bookId");	
%>


<center>
<table style="width:960px;padding:5px;margin-top:10px">
  <tr>
    <td>
	    <p>
	    <%if (bookId!=null){%>      
	      	<%BookDetails book=bookDB.getBookDetails(bookId);%>
	    </p>
      	<h2 style="color:#1D82FE"><%=book.getTitle()%></h2>
      	<h4>作者：<em><%=book.getName()%> (<%=book.getYear()%>)<br /></em></h4>
      	<h4>书评</h4>
      	<blockquote><%=book.getDescription()%></blockquote>
      	<h4>价格 <%=book.getPrice()%>元</h4>
      	<a style="display:block;width:30%;background:#85DA46;float:left;
      		   border:1px solid;height:40px;margin-right:10px;line-height:40px;text-align:center;
      		   color:#fff;text-decoration: none;" href="bookcatalog?Add=<%=book.getBookId()%>">购买</a>
      	<%}%>
      	<a style="display:block;width:30%;background:#F6A51B;float:left;
      		   border:1px solid;height:40px;margin-right:10px;line-height:40px;text-align:center;
      		   color:#fff;text-decoration: none;" href="bookcatalog">继续购物</a>
    </td>
      
    <td style="width:50%">
	    <div class="book_third">
	      <div class="book_news">
	        <h2>书讯快递</h2>
	        <div class="book_news_c">
	          <ul>
	            <li name="____ShuXunKD-w">·<a href="#" target="_blank" title="2011光华版公务员考试最后冲刺！">2011光华版公务员考试最后冲<span class="dot">...</span></a></li>
	            <li name="____ShuXunKD-w"><a href="#" target="_blank" title="2010软考大纲及指定教材80折！">&middot;2010软考大纲及指定教材80折</a></li>
	            <li name="____ShuXunKD-w">·<a href="#" target="_blank" title="抢购！2011新大纲公务员教材半价抢！">抢购！2011新大纲公务员教材<span class="dot">...</span></a></li>
	            <li name="____ShuXunKD-w">·<a href="#" target="_blank" title="抢购！2011考研全面冲刺，辅导书50折起！">抢购！2011考研全面冲刺，辅<span class="dot">...</span></a></li>
	            <li name="____ShuXunKD-w">·<a href="#" target="_blank" title="2011最新版计算机等级考试教材上市">2011最新版计算机等级考试教<span class="dot">..</span></a><a href="http://static.dangdang.com/book/topic/2018_178120.shtml" target="_blank" title="2010成人高考教材现货80折"></a></li>
	          </ul>
	        </div>
	      </div>
	      <div class="book_bang_5star" name="__5xingbang"></div>
	    </div>
    </td>
  </tr>
</table>

</center>
<p>&nbsp;</p>
<div class="footer_copyright">
	<span><p align="center">Kyle的书店 | 版权声明 | 加入我们</p></span>
</div>
</body>
</html>



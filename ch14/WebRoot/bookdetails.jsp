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
      	<h4>���ߣ�<em><%=book.getName()%> (<%=book.getYear()%>)<br /></em></h4>
      	<h4>����</h4>
      	<blockquote><%=book.getDescription()%></blockquote>
      	<h4>�۸� <%=book.getPrice()%>Ԫ</h4>
      	<a style="display:block;width:30%;background:#85DA46;float:left;
      		   border:1px solid;height:40px;margin-right:10px;line-height:40px;text-align:center;
      		   color:#fff;text-decoration: none;" href="bookcatalog?Add=<%=book.getBookId()%>">����</a>
      	<%}%>
      	<a style="display:block;width:30%;background:#F6A51B;float:left;
      		   border:1px solid;height:40px;margin-right:10px;line-height:40px;text-align:center;
      		   color:#fff;text-decoration: none;" href="bookcatalog">��������</a>
    </td>
      
    <td style="width:50%">
	    <div class="book_third">
	      <div class="book_news">
	        <h2>��Ѷ���</h2>
	        <div class="book_news_c">
	          <ul>
	            <li name="____ShuXunKD-w">��<a href="#" target="_blank" title="2011�⻪�湫��Ա��������̣�">2011�⻪�湫��Ա��������<span class="dot">...</span></a></li>
	            <li name="____ShuXunKD-w"><a href="#" target="_blank" title="2010����ټ�ָ���̲�80�ۣ�">&middot;2010����ټ�ָ���̲�80��</a></li>
	            <li name="____ShuXunKD-w">��<a href="#" target="_blank" title="������2011�´�ٹ���Ա�̲İ������">������2011�´�ٹ���Ա�̲�<span class="dot">...</span></a></li>
	            <li name="____ShuXunKD-w">��<a href="#" target="_blank" title="������2011����ȫ���̣�������50����">������2011����ȫ���̣���<span class="dot">...</span></a></li>
	            <li name="____ShuXunKD-w">��<a href="#" target="_blank" title="2011���°������ȼ����Խ̲�����">2011���°������ȼ����Խ�<span class="dot">..</span></a><a href="http://static.dangdang.com/book/topic/2018_178120.shtml" target="_blank" title="2010���˸߿��̲��ֻ�80��"></a></li>
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
	<span><p align="center">Kyle����� | ��Ȩ���� | ��������</p></span>
</div>
</body>
</html>



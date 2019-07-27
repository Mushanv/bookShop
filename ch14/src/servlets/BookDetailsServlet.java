package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import database.*;
import util.Currency;
import exception.*;


public class BookDetailsServlet extends HttpServlet {
    private BookDBAO bookDB;

    public void init() throws ServletException {
        bookDB = (BookDBAO) getServletContext()
                                .getAttribute("bookDB");

        if (bookDB == null) {
            throw new UnavailableException("Couldn't get database.");
        }
    }

    public void destroy() {
        bookDB = null;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        
        // 设置 content-type
        response.setContentType("text/html;charset=gb2312");
        response.setBufferSize(8192);

        PrintWriter out = response.getWriter();

        //  把数据写入响应流
        out.println("<html>" + "<head><title>BookStore</title></head>");

        
        RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/head");

        if (dispatcher != null) {
            dispatcher.include(request, response);
        }

        //取得dispatcher对象，将页首放在本页面
        String bookId = request.getParameter("bookId");

        if (bookId != null) {
            // 书目的详细信息
            try {
                BookDetails bd = bookDB.getBookDetails(bookId);
                Currency c = (Currency) session.getAttribute("currency");

                if (c == null) {
                    c = new Currency();
                    c.setLocale(request.getLocale());
                    session.setAttribute("currency", c);
                }

                c.setAmount(bd.getPrice());
                
                out.println("<center><h2>" + bd.getTitle() + "</h2>" + "&nbsp;" +
                    "作者：" + " <em>" + bd.getName() +
                    "</em> &nbsp; &nbsp; " + "(" +
                    bd.getYear() + ")<br> &nbsp; <br>" + "<h4>" +
                    "书评：" + "</h4>"  +
                    bd.getDescription() + "</blockquote>" + "<h4>" +
                    "价格：" + c.getFormat() + "</h4>" +
                    "<p><strong><a href=\"" +
                    response.encodeURL(request.getContextPath() +
                        "/bookcatalog?bookId=" + bookId) + "\">" +
                    "加入购物车" + "</a>&nbsp;&nbsp;&nbsp;" +
                    "<a href=\"" +
                    response.encodeURL(request.getContextPath() +
                        "/bookcatalog") + "\">" +
                    "继续购物" +
                    "</a></p></strong></center>");
            } catch (BookNotFoundException ex) {
                response.resetBuffer();
                throw new ServletException(ex);
            }
        }
        out.println("<hr>");
        out.println("<div class=\"footer\"><span>Kyle的书店 | 版权声明 | 加入我们" +
        		"<span>联系我们：333-333</span></div>");
        out.println("</body></html>");
        out.close();
    }

    public String getServletInfo() {
        return "The BookDetail servlet returns information about" +
        "any book that is available from the bookstore.";
    }
}

package servlets;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import database.*;
import cart.*;
import util.Currency;
import exception.*;



public class CatalogServlet extends HttpServlet {
    private BookDBAO bookDB;

    public void init() throws ServletException {
        bookDB = (BookDBAO) getServletContext().getAttribute("bookDB");
        if (bookDB == null) {
            throw new UnavailableException("Couldn't get database.");
        }
    }

    public void destroy() {
        bookDB = null;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // 获取用户 session 和 购物车对象
        HttpSession session = request.getSession(true);        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        // 如果没有购物车，创建新的购物车
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        // 设置 content-type 
        response.setContentType("text/html;charset=gb2312");
        response.setBufferSize(8192);

        PrintWriter out = response.getWriter();

        // 把数据写入响应流
        out.println("<html>" + "<head><title>BookStore</title></head><body style=\"background-color:#fff\">");

        //  取得dispatcher对象，将页首放在本页面
        RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/head");

        if (dispatcher != null) {
            dispatcher.include(request, response);
        }

        //加入购物车内的书目信息
        String bookId = request.getParameter("bookId");

        if (bookId != null) {
            try {
                BookDetails book = bookDB.getBookDetails(bookId);

                cart.add(bookId, book);
                out.println("<p><center><h3>" + "您把<font color=\"#ff0000\">" +
                    "<i>" + book.getTitle() +
                    "</i> " + "</font>加入了购物车" +
                    "</h3></center>");
            } catch (BookNotFoundException ex) {
                response.reset();
                throw new ServletException(ex);
            }
        }

        //检查购物车是否为空，如果不为空，显示结帐和查看购物车连接
        if (cart.getNumberOfItems() > 0) {
            out.println("<center><strong><a href=\"" +
                response.encodeURL(request.getContextPath() + "/bookshowcart") +
                "\">" + "查看购物车" +
                "</a>&nbsp;&nbsp;&nbsp;" + "<a href=\"" +
                response.encodeURL(request.getContextPath() + "/bookcashier") +
                "\">" + "结账" + "</a>" + "<br></strong></center>");
        }

        
        out.println("<br> &nbsp;" + "<center> <h3>" + "请选购" +
            "</h3>" + "<table summary=\"layout\">");
        
        //迭代显示书目信息
        try {
            Collection coll = bookDB.getBooks();

            Iterator i = coll.iterator();
            Currency c = (Currency) session.getAttribute("currency");
         
            if (c == null) {
                c = new Currency();
                c.setLocale(request.getLocale());
                session.setAttribute("currency", c);
            }
            out.println("<table style=\"width:960px;border-bottom:1px dashed;border-left:1px dashed;\">");
            while (i.hasNext()) {
                BookDetails book = (BookDetails) i.next();
                bookId = book.getBookId();
                c.setAmount(book.getPrice());
                
                out.println("<tr style=\"height:40px\">" + "<td style=\"width:35%;border-top:1px dashed;border-right:1px dashed;\">" + "<a style=\"color:#000;\" href=\"" +
                    response.encodeURL(request.getContextPath() +
                        "/bookdetails?bookId=" + bookId) + "\"> <strong>" +
                    book.getTitle() + "</strong></a></td>" + "<td style=\"border-top:1px dashed;border-right:1px dashed;\">" + "&nbsp; &nbsp;" +
                    "作者："  + book.getName() +"</td>"+
                    "<td  style=\"border-top:1px dashed;border-right:1px dashed;color:#1D82FE\">" + c.getFormat() +
                    "&nbsp; </td>" + "<td style=\"border-top:1px dashed;border-right:1px dashed;\">" +
                    "<a style=\"display:block;width:100%;background:#EE5858;float:left;border:1px solid;height:40px;"
				+ "margin-right:10px;line-height:40px;text-align:center;color:#fff;text-decoration: none;\" href=\"" +
                    response.encodeURL(request.getContextPath() +
                        "/bookcatalog?bookId=" + bookId) + "\"> &nbsp;" +
                    "加入购物车" + "&nbsp;</a></td></tr>");
            }
            out.println("</table></center></body></html>");
            out.close();
        } catch (BooksNotFoundException ex) {
            response.reset();
            throw new ServletException(ex);
        }
    }

    public String getServletInfo() {
        return "出错了";
    }
}

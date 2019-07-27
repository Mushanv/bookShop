package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import cart.ShoppingCart;
import database.*;
import exception.*;

public class ReceiptServlet extends HttpServlet {
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        boolean orderCompleted = true;

  
        HttpSession session = request.getSession(true);
        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

       
        try {
            bookDB.buyBooks(cart);
        } catch (OrderException e) {
            System.err.println(e.getMessage());
            orderCompleted = false;
        }

        session.invalidate();

       
        response.setContentType("text/html;charset=gb2312");
        response.setBufferSize(8192);

        PrintWriter out = response.getWriter();

       
        out.println("<html>" + "<head><title>BookStore</title></head>");

      
        RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/head");

        if (dispatcher != null) {
            dispatcher.include(request, response);
        }

        if (orderCompleted) {
            out.println("<center><h3>" + "谢谢光临，欢迎下次再来 " +
                request.getParameter("cardname") + ".");
        } else {
            out.println("<h3>" + "对不起，暂时缺货。");
        }

        out.println("<p> &nbsp; <p><strong><a href=\"" +
            response.encodeURL(request.getContextPath()) + "/bookcatalog\">" +
            "继续购物" +
            "</a> &nbsp; &nbsp; &nbsp;" + "</center>"+"</body>"+"</html>");
        out.close();
    }

    public String getServletInfo() {
        return "出错了";
    }
}

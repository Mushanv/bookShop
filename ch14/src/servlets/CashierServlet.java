package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import cart.*;
import util.Currency;


public class CashierServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        //  ��ȡ�û� session �� ���ﳵ����
        HttpSession session = request.getSession();
        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        // ���� content-type ��ҳ�����
        response.setContentType("text/html;charset=gb2312");
        
        PrintWriter out = response.getWriter();
        Currency c = (Currency) session.getAttribute("currency");

        if (c == null) {
            c = new Currency();
            c.setLocale(request.getLocale());
            session.setAttribute("currency", c);
        }

        c.setAmount(cart.getTotal());

        // ������д����Ӧ��
        out.println("<html>" + "<head><title>BookStore</title></head><body style=\"background:#fafafa\">");
        
        RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/head");

        if (dispatcher != null) {
            dispatcher.include(request, response);
        }
        
        // ��ʾ�����ܼۺͿͻ����ÿ�֧����
        out.println("<div style=\"width:950px;margin:0px auto;border:1px solid #d0d0d0;padding:5px;background:#fff;\">"
        		+ "<p>" + "�����ܼۣ�" + "<strong style=\"color:#FF2827\">" +
            c.getFormat() + "</strong>" + "</p>" +
            "����֧����֧����" + "<form action=\"" +
            response.encodeURL(request.getContextPath() + "/bookreceipt") +
            "\" method=\"post\">" 
            + "<table>" + "<tr>" +
            	"<td><strong>" + "����" + "</strong></td>" +
            	"<td><input style=\"width:300px;height:30px;\" type=\"text\" name=\"cardname\"" +
            	"value=\"Kyle\" size=\"19\"></td>" 
            + "</tr>" 
            + "<tr>" +
            	"<td><strong>" + "�˺�" + "</strong></td>" +
            	"<td>" + "<input style=\"width:300px;height:30px;\" type=\"text\" name=\"cardnum\" " +
            	"value=\"333 3333 3333\" size=\"19\"></td>" 
            + "</tr>"
            + "<tr>" 
            	+ "<td></td>" 
            	+ "<td><input style=\"width:300px;height:34px;border:0;background:#1D82FE;color:#fff;border-radius:10px\" type=\"submit\"" + "value=\"" +
            	"�ύ" + "\"></td>" 
            + "</tr>" 
            + "</table>" +
            "</form>" 
            + "</div></body>" + "</html>");
        out.close();
    }

    public String getServletInfo() {
        return "������ ";
    }
}

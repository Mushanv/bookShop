package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import cart.*;
import util.Currency;


public class CashierServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        //  获取用户 session 和 购物车对象
        HttpSession session = request.getSession();
        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        // 设置 content-type 和页面编码
        response.setContentType("text/html;charset=gb2312");
        
        PrintWriter out = response.getWriter();
        Currency c = (Currency) session.getAttribute("currency");

        if (c == null) {
            c = new Currency();
            c.setLocale(request.getLocale());
            session.setAttribute("currency", c);
        }

        c.setAmount(cart.getTotal());

        // 把数据写入响应流
        out.println("<html>" + "<head><title>BookStore</title></head><body style=\"background:#fafafa\">");
        
        RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/head");

        if (dispatcher != null) {
            dispatcher.include(request, response);
        }
        
        // 显示购物总价和客户信用卡支付表单
        out.println("<div style=\"width:950px;margin:0px auto;border:1px solid #d0d0d0;padding:5px;background:#fff;\">"
        		+ "<p>" + "购物总价：" + "<strong style=\"color:#FF2827\">" +
            c.getFormat() + "</strong>" + "</p>" +
            "请用支付宝支付：" + "<form action=\"" +
            response.encodeURL(request.getContextPath() + "/bookreceipt") +
            "\" method=\"post\">" 
            + "<table>" + "<tr>" +
            	"<td><strong>" + "姓名" + "</strong></td>" +
            	"<td><input style=\"width:300px;height:30px;\" type=\"text\" name=\"cardname\"" +
            	"value=\"Kyle\" size=\"19\"></td>" 
            + "</tr>" 
            + "<tr>" +
            	"<td><strong>" + "账号" + "</strong></td>" +
            	"<td>" + "<input style=\"width:300px;height:30px;\" type=\"text\" name=\"cardnum\" " +
            	"value=\"333 3333 3333\" size=\"19\"></td>" 
            + "</tr>"
            + "<tr>" 
            	+ "<td></td>" 
            	+ "<td><input style=\"width:300px;height:34px;border:0;background:#1D82FE;color:#fff;border-radius:10px\" type=\"submit\"" + "value=\"" +
            	"提交" + "\"></td>" 
            + "</tr>" 
            + "</table>" +
            "</form>" 
            + "</div></body>" + "</html>");
        out.close();
    }

    public String getServletInfo() {
        return "出错了 ";
    }
}

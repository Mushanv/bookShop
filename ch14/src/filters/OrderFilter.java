package filters;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import database.*;
import cart.*;
import util.Currency;
import exception.*;
import java.sql.*;

public class OrderFilter implements Filter {
    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException { 
    	
    	if(filterConfig == null)
    	return;
    

        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        HttpServletRequest hsr=(HttpServletRequest)request;
        HttpSession session=hsr.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Currency c = (Currency) session.getAttribute("currency");
        c.setAmount(cart.getTotal());
        writer.println();
        writer.println(
            "=======================================================");
        //Timestamp d = new Timestamp(System.currentTimeMillis());   
        writer.println("订单生成时间: " + new Timestamp(System.currentTimeMillis()));
        writer.println(
            "=======================================================");
        writer.println();
        writer.println(
            "客户姓名"+request.getParameter("cardname"));
        writer.println();
        writer.println(
            "总价"+c.getFormat());
        writer.println();
        
        int num=cart.getNumberOfItems();
        if(num>0){
        	Iterator i=cart.getItems().iterator();
            while (i.hasNext()) {
                ShoppingCartItem item = (ShoppingCartItem) i.next();
                BookDetails bookDetails=(BookDetails)item.getItem();
                writer.print("ISBN:"+bookDetails.getBookId());
                writer.print("   书名"+bookDetails.getTitle());
                writer.print("   数量"+item.getQuantity());
                writer.println();
                writer.println(
                "=======================================================");
        }
        // 将计数器写入Log 
        writer.flush();
        System.out.println(sw.getBuffer().toString());
        response.setCharacterEncoding("gb2312");
        
        chain.doFilter(request, response);        
        }   
        //向返回客户端的输出流中插入计数器信息
       
        
    }

    public String toString() {
        if (filterConfig == null) {
            return ("HitCounterFilter()");
        }

        StringBuffer sb = new StringBuffer("OderFilter(");
        sb.append(filterConfig);
        sb.append(")");

        return (sb.toString());
    }
}
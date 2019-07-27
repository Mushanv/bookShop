package servlets;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import database.*;
import cart.*;
import util.Currency;
import exception.*;



public class ShowCartServlet extends HttpServlet {
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
        // ��ȡ�û� session �� ���ﳵ����
        HttpSession session = request.getSession(true);
        
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        // ���û�й��ﳵ�������µĹ��ﳵ
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        //���� content-type 
        response.setContentType("text/html;charset=gb2312");
        response.setBufferSize(8192);

        PrintWriter out = response.getWriter();

       
        out.println("<html>" + "<head><title>BookStore</title></head><body style=\"background:#fafafa\">");

        // ������д����Ӧ��
        RequestDispatcher dispatcher =
            getServletContext()
                .getRequestDispatcher("/head");

        if (dispatcher != null) {
            dispatcher.include(request, response);
        }

        /* �ӹ��ﳵ��ɾ��ѡ�е���Ʒ */
        String bookId = request.getParameter("Remove");
        BookDetails bd;
        if (bookId != null) {
            try {
                bd = bookDB.getBookDetails(bookId);
                cart.remove(bookId);
                out.println("<center>ɾ��<font color=\"#ff00000\" size=\"+2\">" +
                    "<strong>" +
                    bd.getTitle() + "</strong> <br> &nbsp; <br>" + "</font></center>");
            } catch (BookNotFoundException ex) {
                response.reset();
                throw new ServletException(ex);
            }
        } else if (request.getParameter("Clear") != null) {
            cart.clear();
            out.println("<center><font color=\"#ff0000\" size=\"+2\"><strong>" +
                "��չ��ﳵ" +
                "</strong> <br>&nbsp; <br> </font></center>");
        }

        // ��ӡ���ﳵ�ڵ���Ʒ����
        int num = cart.getNumberOfItems();

        if (num > 0) {
            out.println("<center><font size=\"+2\">" +
                "�����嵥 ��" + num +"��</font><br>&nbsp;");

            // ��ӡ�����嵥 
            out.println("<table style=\"width:960px;\">" + "<tr style=\"background:#f0f0f0;height:40px;\">" +
                "<th align=center>" + "����" +
                "</th>" + "<th align=center>" + "����" +
                "</th>" + "<th align=center>" + "����" +
                "</th>" + "</tr>");

            Iterator i = cart.getItems()
                             .iterator();
            Currency c = (Currency) session.getAttribute("currency");

            if (c == null) {
                c = new Currency();
                c.setLocale(request.getLocale());
                session.setAttribute("currency", c);
            }

            while (i.hasNext()) {
                ShoppingCartItem item = (ShoppingCartItem) i.next();
                bd = (BookDetails) item.getItem();
                c.setAmount(bd.getPrice());

                out.println("<tr style=\"height:40px;\">" +
                    "<td align=\"center\" style=\"background:#fcfcfc;border-bottom:1px dashed;border-left:1px dashed;\">" +
                    item.getQuantity() + "</td>" + "<td bgcolor=\"#fcfcfc\" style=\"border-bottom:1px dashed;border-left:1px dashed;\">" +
                    "<strong><a style=\"color:#000;margin-left:20px\" href=\"" +
                    response.encodeURL(request.getContextPath() +
                        "/bookdetails?bookId=" + bd.getBookId()) + "\">" +
                    bd.getTitle() + "</a></strong>" + "</td>" +
                    "<td bgcolor=\"#fcfcfc\" align=\"center\" style=\"border-bottom:1px dashed;border-left:1px dashed;\">" + c.getFormat() +
                    "</td>" + "<td bgcolor=\"#EC181A\">" + "<strong>" +
                    "<a style=\"color:#fff;text-align:center;display:block;text-decoration: none;\" href=\"" +
                    response.encodeURL(request.getContextPath() +
                        "/bookshowcart?Remove=" + bd.getBookId()) + "\">" +
                    "ɾ��" + "</a></strong>" +
                    "</td></tr>");
            }

            c.setAmount(cart.getTotal());

            // ��ӡ�ܼ�
            out.println("<tr>" +
                "<td colspan=\"2\" align=\"right\"" + "bgcolor=\"#ffffff\">" +
				 
				"<p><a style=\"display:block;width:30%;background:#F6A51B;float:left;border:1px solid;height:40px;"
				+ "margin-right:10px;line-height:40px;text-align:center;color:#fff;text-decoration: none;\" href=\"" +
				response.encodeURL(request.getContextPath() + "/bookcatalog") +
				"\">" + "��������" +
				"</a>" + "<a style=\"display:block;width:30%;background:#85DA46;float:left;border:1px solid;height:40px;"
				+ "margin-right:10px;line-height:40px;text-align:center;color:#fff;text-decoration: none;\" href=\"" +
				response.encodeURL(request.getContextPath() + "/bookcashier") +
				"\">" + "����" +
				"</a>" + "<a style=\"display:block;width:30%;background:#FF2827;float:left;border:1px solid;height:40px;"
				+ "margin-right:10px;line-height:40px;text-align:center;color:#fff;text-decoration: none;\" href=\"" +
				response.encodeURL(request.getContextPath() +
				    "/bookshowcart?Clear=clear") + "\">" +
				"��չ��ﳵ" + "</a>" 
                
                + "</td>" +
                "<td align=\"right\" style=\"height:40px;background:#1D82FE;text-align:center;color:#fff\">�ܼƣ�" + c.getFormat() +
                "</td>" + "</td><td></td></tr></table>");

           
        } else {
            // ���ﳵΪ����ʾ��������
            out.println("<center><font size=\"+2\">" + "���ﳵ��û����Ʒ" +
                "</font>" + "<br> &nbsp; <br>" + "<a href=\"" +
                response.encodeURL(request.getContextPath() + "/bookcatalog") +
                "\">" + "������Ʒ�б�" + "</a> </center>");
        }

        out.println("</body> </html>");
        out.close();
    }

    public String getServletInfo() {
        return "������";
    }
}

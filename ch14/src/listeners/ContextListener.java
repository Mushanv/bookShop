package listeners;

import database.BookDBAO;
import javax.servlet.*;
import util.Counter;


public final class ContextListener implements ServletContextListener {
    private ServletContext context = null;

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();

        /*
         * ʵ����BookDBAO������BookDBAO�������Servlet������
         */
        try {
            BookDBAO bookDB = new BookDBAO();
            context.setAttribute("bookDB", bookDB);
        } catch (Exception ex) {
            System.out.println("Couldn't create bookstore database bean: " +
                ex.getMessage());
        }

        Counter counter = new Counter();
        context.setAttribute("hitCounter", counter);
        counter = new Counter();
        context.setAttribute("orderCounter", counter);
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();
        /*
         * �������н�������Servlet��������ɾ��BookDBAO���󣬹ر����ݿ�����
         */
        BookDBAO bookDB = (BookDBAO) context.getAttribute("bookDB");

        if (bookDB != null) {
            bookDB.remove();
        }

        context.removeAttribute("bookDB");
        context.removeAttribute("hitCounter");
        context.removeAttribute("orderCounter");
    }
}

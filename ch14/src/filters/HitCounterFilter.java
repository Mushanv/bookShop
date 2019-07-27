package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import util.Counter;


public final class HitCounterFilter implements Filter {
    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException { 

        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);

        Counter counter =
            (Counter) filterConfig.getServletContext()
                                  .getAttribute("hitCounter");
        writer.println();
        writer.println(
            "=======================================================");
        writer.println("The number of hits is: " + counter.incCounter());
        writer.println(
            "=======================================================");

        // ��������д��Log 
        writer.flush();
        System.out.println(sw.getBuffer().toString());
        response.setCharacterEncoding("gb2312");
        PrintWriter out = response.getWriter();
        
        CharResponseWrapper wrapper =
           new CharResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, wrapper);        
        
        //�򷵻ؿͻ��˵�������в����������Ϣ
        CharArrayWriter caw = new CharArrayWriter();
        caw.write(wrapper.toString().substring(0,
        		wrapper.toString().indexOf("</body>") - 1));
        caw.write("<p>\n<center>" + "���ǵ�: " +
            "<font color='red'>" + counter.getCounter() + "</font>������ͻ���</center>");
        caw.write("\n</body></html>");
        response.setContentLength(caw.toString()
                                     .getBytes().length);
        out.write(caw.toString());
        out.close();
        
    }

    public String toString() {
        if (filterConfig == null) {
            return ("HitCounterFilter()");
        }

        StringBuffer sb = new StringBuffer("HitCounterFilter(");
        sb.append(filterConfig);
        sb.append(")");

        return (sb.toString());
    }
}

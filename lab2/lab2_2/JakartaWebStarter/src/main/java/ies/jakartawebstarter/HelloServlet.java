package ies.jakartawebstarter;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String msg = request.getParameter("msg");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if (msg != null)
            out.println("<h1>" + msg + "</h1>");
        else
            out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}
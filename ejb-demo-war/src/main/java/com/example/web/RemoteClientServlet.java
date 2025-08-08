package com.example.web;

import com.example.api.MySessionBeanRemote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoteClientServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        out.println("<html>");
        out.println("<head><title>Remote EJB Client Demo</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println("form { margin: 20px 0; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }");
        out.println("input[type='text'] { padding: 8px; margin: 5px; }");
        out.println("button { padding: 10px 15px; background-color: #007bff; color: white; border: none; border-radius: 3px; cursor: pointer; }");
        out.println("button:hover { background-color: #0056b3; }");
        out.println(".result { margin: 20px 0; padding: 20px; background-color: #f8f9fa; border-radius: 5px; }");
        out.println(".error { color: red; }");
        out.println(".success { color: green; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Remote EJB Client Demo</h1>");
        out.println("<p>This demonstrates calling a Remote Session Bean using JNDI lookup.</p>");
        
        out.println("<form method='post'>");
        out.println("<label>Your name:</label>");
        out.println("<input type='text' name='name' placeholder='Enter your name' required/>");
        out.println("<button type='submit'>Call Remote EJB</button>");
        out.println("</form>");
        
        out.println("<a href='/ejb-demo/app'>← Back to Main Demo</a>");
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        String name = req.getParameter("name");
        String greeting = null;
        String errorMessage = null;
        
        try {
            // Perform JNDI lookup for Remote EJB
            Context ctx = new InitialContext();
            
            // Try different JNDI names based on actual GlassFish deployment
            String[] possibleJndiNames = {
                // Portable Global JNDI name (preferred)
                "java:global/ejb-demo-ear-1.0-SNAPSHOT/com.example-ejb-demo-ejb-1.0-SNAPSHOT/MySessionBean!com.example.api.MySessionBeanRemote",
                // Legacy JNDI names that GlassFish creates automatically
                "com.example.api.MySessionBeanRemote",
                "com.example.api.MySessionBeanRemote#com.example.api.MySessionBeanRemote",
                // Additional backup names
                "java:global/ejb-demo-ear-1.0-SNAPSHOT/ejb-demo-ejb-1.0-SNAPSHOT/MySessionBean!com.example.api.MySessionBeanRemote",
                "java:global/ejb-demo/ejb-demo-ejb/MySessionBean!com.example.api.MySessionBeanRemote",
                "ejb/MySessionBeanRemote"
            };
            
            MySessionBeanRemote remoteBean = null;
            String usedJndiName = null;
            
            for (String jndiName : possibleJndiNames) {
                try {
                    remoteBean = (MySessionBeanRemote) ctx.lookup(jndiName);
                    usedJndiName = jndiName;
                    break;
                } catch (NamingException e) {
                    // Continue trying other JNDI names
                }
            }
            
            if (remoteBean != null) {
                greeting = remoteBean.getGreeting(name);
                
                out.println("<html>");
                out.println("<head><title>Remote EJB Result</title>");
                out.println("<style>");
                out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
                out.println(".result { margin: 20px 0; padding: 20px; background-color: #d4edda; border-radius: 5px; border: 1px solid #c3e6cb; }");
                out.println(".info { margin: 20px 0; padding: 20px; background-color: #f8f9fa; border-radius: 5px; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Remote EJB Call Successful!</h1>");
                out.println("<div class='result'>");
                out.println("<h3>Response from Remote EJB:</h3>");
                out.println("<p><strong>" + greeting + "</strong></p>");
                out.println("</div>");
                out.println("<div class='info'>");
                out.println("<p><strong>JNDI Name Used:</strong> " + usedJndiName + "</p>");
                out.println("<p><strong>Input:</strong> " + name + "</p>");
                out.println("</div>");
                out.println("<a href='/ejb-demo/remote-client'>← Try Again</a> | ");
                out.println("<a href='/ejb-demo/app'>← Back to Main Demo</a>");
                out.println("</body>");
                out.println("</html>");
                
            } else {
                errorMessage = "Could not locate Remote EJB using any of the attempted JNDI names.";
            }
            
        } catch (Exception e) {
            errorMessage = "Error calling Remote EJB: " + e.getMessage();
            e.printStackTrace();
        }
        
        if (errorMessage != null) {
            out.println("<html>");
            out.println("<head><title>Remote EJB Error</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
            out.println(".error { margin: 20px 0; padding: 20px; background-color: #f8d7da; border-radius: 5px; border: 1px solid #f5c6cb; color: #721c24; }");
            out.println(".info { margin: 20px 0; padding: 20px; background-color: #f8f9fa; border-radius: 5px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Remote EJB Call Failed</h1>");
            out.println("<div class='error'>");
            out.println("<p>" + errorMessage + "</p>");
            out.println("</div>");
            out.println("<div class='info'>");
            out.println("<h3>Debugging Information:</h3>");
            out.println("<p>Make sure the EJB is properly deployed and the JNDI name is correct.</p>");
            out.println("<p>Check GlassFish admin console or server logs for the actual JNDI names.</p>");
            out.println("</div>");
            out.println("<a href='/ejb-demo/remote-client'>← Try Again</a> | ");
            out.println("<a href='/ejb-demo/app'>← Back to Main Demo</a>");
            out.println("</body>");
            out.println("</html>");
        }
        
        out.close();
    }
}

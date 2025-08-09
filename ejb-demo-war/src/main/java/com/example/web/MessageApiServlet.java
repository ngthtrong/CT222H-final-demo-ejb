package com.example.web;

import com.example.api.MySessionBeanLocal;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/messages")
public class MessageApiServlet extends HttpServlet {

    @EJB
    private MySessionBeanLocal mySessionBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        PrintWriter out = resp.getWriter();
        
        // Simple JSON response
        out.println("{");
        out.println("  \"messageCount\": " + mySessionBean.getMessageCount() + ",");
        out.println("  \"latestMessage\": \"" + escapeJson(mySessionBean.getLatestMessage()) + "\",");
        out.println("  \"messages\": [");
        
        List<String> messages = mySessionBean.getAllMessages();
        for (int i = 0; i < messages.size(); i++) {
            out.print("    \"" + escapeJson(messages.get(i)) + "\"");
            if (i < messages.size() - 1) {
                out.print(",");
            }
            out.println();
        }
        
        out.println("  ]");
        out.println("}");
    }
    
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}

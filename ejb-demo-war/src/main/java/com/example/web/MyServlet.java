package com.example.web;

import com.example.api.MySessionBeanLocal;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.jms.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {

    // Inject Local EJB
    @EJB
    private MySessionBeanLocal mySessionBean;

    // JMS Resources
    @Resource(lookup = "jms/myConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/myQueue")
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", mySessionBean.getAllUserNames());
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        if ("addUser".equals(action)) {
            String name = req.getParameter("name");
            mySessionBean.addUser(name);
        } else if ("sendMessage".equals(action)) {
            String message = req.getParameter("message");
            sendMessageToMDB(message);
        }

        resp.sendRedirect(req.getContextPath() + "/app");
    }

    private void sendMessageToMDB(String messageText) {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            
            MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage(messageText);
            producer.send(message);
            
            System.out.println("Message sent to MDB: " + messageText);
            
        } catch (JMSException e) {
            System.err.println("Error sending message to MDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

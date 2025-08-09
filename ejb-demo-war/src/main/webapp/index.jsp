<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>EJB Message Processing Demo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { margin: 20px 0; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        input[type="text"] { padding: 8px; margin: 5px; width: 300px; }
        button { padding: 10px 15px; background-color: #007bff; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        ul { list-style-type: disc; padding-left: 20px; }
        li { margin: 5px 0; padding: 5px; background-color: #f8f9fa; border-radius: 3px; }
        hr { margin: 30px 0; }
        .message-stats { 
            background-color: #e9ecef; 
            padding: 15px; 
            border-radius: 5px; 
            margin: 20px 0; 
        }
        .latest-message {
            background-color: #d4edda;
            padding: 10px;
            border-radius: 5px;
            margin: 10px 0;
            border-left: 4px solid #28a745;
        }
    </style>
</head>
<body>
    <h1>EJB Message Processing Demo</h1>
    <p><strong>Architecture:</strong> User sends message → MDB receives → Session Bean Local processes → Message stored & displayed</p>

    <hr/>
    <h2>Send Message to Queue</h2>
    <form action="app" method="post">
        <input type="hidden" name="action" value="sendMessage"/>
        <label>Message to send:</label><br>
        <input type="text" name="message" placeholder="Enter your message here..." required/>
        <button type="submit">Send Message</button>
    </form>
    <p><i>Your message will be sent to JMS Queue → MDB will receive → Session Bean Local will process</i></p>

    <hr/>
    <h2>Message Processing Status</h2>
    <div class="message-stats">
        <p><strong>Total Messages Processed:</strong> ${messageCount}</p>
        <div class="latest-message">
            <strong>Latest Message:</strong> ${latestMessage}
        </div>
    </div>

    <h3>All Processed Messages:</h3>
    <c:choose>
        <c:when test="${empty messages}">
            <p><em>No messages processed yet. Send a message above to see it processed!</em></p>
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach var="message" items="${messages}" varStatus="status">
                    <li><c:out value="${message}"/></li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</body>
</html>

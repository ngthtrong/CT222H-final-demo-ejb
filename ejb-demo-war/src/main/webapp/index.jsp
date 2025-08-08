<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>EJB Demo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { margin: 20px 0; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        input[type="text"] { padding: 8px; margin: 5px; }
        button { padding: 10px 15px; background-color: #007bff; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background-color: #0056b3; }
        ul { list-style-type: disc; padding-left: 20px; }
        hr { margin: 30px 0; }
    </style>
</head>
<body>
    <h1>EJB Demo with GlassFish and MySQL</h1>

    <hr/>
    <h2>1. Session Bean (Local) & JDBC</h2>
    <form action="app" method="post">
        <input type="hidden" name="action" value="addUser"/>
        <label>User Name:</label>
        <input type="text" name="name" required/>
        <button type="submit">Add User</button>
    </form>
    <h3>Current Users in Database:</h3>
    <ul>
        <c:forEach var="user" items="${users}">
            <li><c:out value="${user}"/></li>
        </c:forEach>
    </ul>

    <hr/>
    <h2>2. Message-Driven Bean (MDB) & JMS</h2>
    <form action="app" method="post">
        <input type="hidden" name="action" value="sendMessage"/>
        <label>Message to send:</label>
        <input type="text" name="message" required/>
        <button type="submit">Send Message to MDB</button>
    </form>
    <p><i>Check GlassFish server log for MDB output.</i></p>
    
    <hr/>
    <h2>3. Session Bean (Remote) & JNDI Lookup</h2>
    <form action="remote-client" method="get">
        <button type="submit">Test Remote EJB Client</button>
    </form>
    <p><i>This demonstrates calling a Remote Session Bean using JNDI lookup.</i></p>

    <hr/>
    <h2>EJB Demo Status</h2>
    <p>✅ Session Bean (Local) working with MySQL database</p>
    <p>🔥 Message-Driven Bean enabled with JMS</p>
    <p>🌐 Session Bean (Remote) with JNDI lookup</p>
</body>
</html>

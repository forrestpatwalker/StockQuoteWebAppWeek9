<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
This example JSP uses JTSL rather than scriplet to access data.
In the StockSearchServlet servlet, an instance of a Mood is placed in the http session.
The code on this page gets that instance of out the session and uses'
it values to determine what to display to the user.
--%>

<%-- get the stockquote instance out of the session context --%>
<jsp:useBean id="stockquote" class="com.origamisoftware.teach.advanced.model.StockQuote" scope="session">
    <c:set target='${stockquote}'  value='${sessionScope.get("stockquote")}'/>
</jsp:useBean>

<html>
<head>
    <title>Requested StockQuote</title>
</head>
<body>

<%--
Now use the stockquote values to see what message to send to the user
Note the use of c:if which is a JSTL tag the makes it easy to do if statements in
JSP code. There JSTL tags for all common  operations
--%>

Here is the result of call stockquote.toString() but using JSTL: <br>
<c:out value="${stockquote}"/>  <%-- out is like using System.out.println - the output will go the page --%>

Please click <A href="stockquote.jsp">here</A> to start a new search.

</body>
</html>
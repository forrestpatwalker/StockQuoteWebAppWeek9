<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stocks finder</title>
</head>
<body>

<h2>Stocks finder</h2>

<form name="stockSearchForm" action="servlets/StocksFinder/" method="post">

    Stock Symbol : <input type="text" name="stockSymbol"><br>
    Last Name : <input type="text" name="fromDate"><br>
    Last Name : <input type="text" name="untilDate"><br>

    <br>
    <input type="SUBMIT" value="OK">
    <input type="HIDDEN" name="submit" value="true">
</form>

</body>
</html>
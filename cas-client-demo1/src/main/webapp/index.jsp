<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>
<h1>品优购</h1>
<%=request.getRemoteUser()%>
<a href="http://192.168.182.132:8082/cas/logout?service=http://www.baidu.com">退出登录</a>
  </body>
</html>

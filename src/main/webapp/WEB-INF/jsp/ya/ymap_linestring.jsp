<%--
  Created by IntelliJ IDEA.
  User: sss
  Date: 01.01.16
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Дорога.</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- Если вы используете API локально, то в URL ресурса необходимо указывать протокол в стандартном виде (http://...)-->
    <script src="//api-maps.yandex.ru/2.1/?lang=ru-RU" type="text/javascript"></script>
    <script src="/resources/js/ya/ymap_linestring.js" type="text/javascript"></script>
    <style>
        html, body, #map {
            width: 100%;
            height: 100%;
            padding: 0;
            margin: 0;
        }
    </style>
</head>
<body>
<div id="map"></div>
</body>
</html>

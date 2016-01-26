<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:url value="" var="homeUrl" htmlEscape="true"/>
<spring:url value="/about" var="aboutUrl" htmlEscape="true"/>
<spring:url var="logoutUrl" value="logout"/>

<div class="menu navbar navbar-default" role="navigation">
    <!--
     <a href="${homeUrl}">В начало</a>
     <a href="${aboutUrl}">About</a>
     <span align="right">
     <sec:authorize access="isAuthenticated()">
          <a href="${logoutUrl}">Выход</a>
     </sec:authorize>
    -->
    <!-- Static navbar -->
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <!--  <span class="sr-only">Toggle navigation</span> -->
                <span>Навигация</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="t1">Главная</a>
        </div>
        <div class="navbar-collapse collapse">

            <ul class="nav navbar-nav">

                <li class="active"><a href="${homeUrl}"> <span class="glyphicon glyphicon-home"></span></a></li>
                <li><a href="#" onclick="addRecord()">Добавить запись</a></li>
                <li><a href="roads/reportall">Печать списка автодорог</a></li>
                <!--
                                <li><a href="#">Заочное</a></li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Режимы<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Действие 1</a></li>
                                        <li><a href="#">Действие 2</a></li>
                                        <li><a href="#">Действие 3</a></li>
                                        <li class="divider"></li>
                                        <li class="dropdown-header">Nav header</li>
                                        <li><a href="#">Separated link</a></li>
                                        <li><a href="#">One more separated link</a></li>
                                    </ul>
                                </li>
                -->
            </ul>

            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="${logoutUrl}"><span class="glyphicon glyphicon-log-out"></span>
                        Выход</a></li>
                </ul>
            </sec:authorize>
        </div>
        <!--/.nav-collapse -->
    </div>
    <!--/.container-fluid -->

    <!--
     <sec:authorize access="isAnonymous()">
          Войдите в систему
     </sec:authorize>
     -->


</div>



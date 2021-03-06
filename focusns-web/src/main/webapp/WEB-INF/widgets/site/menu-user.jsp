<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/widgets/widget.jsp" %>

<ui:widget styleId="menu-user">
    <ui:widget-body>
        <div class="navbar-text pull-right dropdown">
            <a class="navbar-link dropdown-toggle" data-toggle="dropdown" href="#">
                <tool:img-avatar styleClass="avatar" projectUserId="${sessionScope.projectUser.id}" width="24" height="24" />
                ${sessionScope.projectUser.nickname}
            </a>
            <a class="navbar-link" href="<c:url value="/signout" />">
                退出
            </a>
            <ul class="dropdown-menu">
                <c:forEach items="${features}" var="feature">
                    <li>
                        <a href='<c:url value="/${feature.code};p=${projectUser.project.code}" />'>${feature.label}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </ui:widget-body>
</ui:widget>

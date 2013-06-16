<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/widgets/widget.jsp" %>

<ui:widget>
    <ui:widget-body>
        <div class="navbar-text pull-right dropdown">
            <a class="navbar-link dropdown-toggle" data-toggle="dropdown" href="#">
                <tool:img-avatar styleClass="avatar" dimension="24" projectId="${sessionScope.projectUser.projectId}" projectUserId="${sessionScope.projectUser.id}" />
                ${sessionScope.projectUser.username}
            </a>
            <a class="navbar-link" href="<widget:actionUrl value="/site/logout" />">
                退出
            </a>
            <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                <c:forEach items="${features}" var="feature">
                    <li>
                        <a href='<c:url value="/${feature.code};p=${projectUser.project.code}" />'>${feature.label}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </ui:widget-body>
</ui:widget>

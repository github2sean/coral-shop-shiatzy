<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>
   .first-title .second-title{display: none}
   .first-title.active .second-title {display: block}
   .first-title.active .second-title li .answer{display: none}
   .first-title.active .second-title li.active .answer{display: block}
</style>
<div class="dx-CommonProblems">
    <div class="dx-title"><spring:message code="commonQuestion"/> <a href="javascript:history.go(-1)"><spring:message code="goBack"/></a></div>
    <div class="content">
        <ul>
            <c:forEach var="itemDomain" items="${domainList}">
                <li class="first-title j_collapse">
                    <a href="#">${web:selectLanguage()=='en_US'?itemDomain.en_title:itemDomain.title}</a>
                    <ul class="second-title">
                        <c:forEach var="domainlist" items="${itemDomain.contentItemDomainList}">
                            <li class="j_collapse">
                                <a href="#">${web:selectLanguage()=='en_US'?domainlist.en_title:domainlist.title}</a>
                                <p class="answer">${web:selectLanguage()=='en_US'?domainlist.en_content:domainlist.content}</p>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </div>

    <jsp:include page="/WEB-INF/views/include/footer.jsp">
        <jsp:param name="nav" value="首页"/>
    </jsp:include>
    <script>
        $(function () {
            //常见问题页面JS
            //下拉菜单展开收起
            $(".j_collapse>a").on("click", function () {
                $(this).parent().siblings().removeClass("active");
                $(this).parent().toggleClass("active");
            });
        });
    </script>


<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<div class="dx-login">
    <div class="dx-title clearfix">
        <div class="member">
            <span> <img src="${ctx}/static/images/icon-member.png" alt=""></span>
            <spring:message code="vip"/>
            </div>
        <a href="" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
    <form class="j_ajaxForm" method="post" action="/passport/login" data-next="${ctx}/home/index">
        <div class="content">
            <div class="login"><spring:message code="login"/></div>
            <div class="title"><spring:message code="login.tips"/></div>
            <div class="form-item">
                <input type="email" placeholder='<spring:message code="login.holderAccount"/>' name="userName"
                       id="userName"
                       onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="login.holderAccount"/>'"
                       data-rule="电子邮箱:required;email">
            </div>
            <div class="form-item">
                <input type="password" placeholder='<spring:message code="login.holderPassword"/>' name="password"
                       id="userPwd" onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="login.holderPassword"/>'"
                       data-rule="密码:required;password">
            </div>
            <div class="remind"><a href="${ctx}/passport/toForget"><spring:message code="login.forgotPassword"/>？</a>
            </div>
            <div class="remind loginInfo"></div>
            <!--添加的a标签用来演示-->
            <button type="submit" class="btn btn-default"><spring:message code="login"/></button>
            <div class="register"><spring:message code="login.noneAccount"/>？ <a
                    href="${ctx}/passport/toRegister"><spring:message code="register"/></a></div>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        var backUrl = document.referrer;
        console.log(backUrl);
        if(backUrl.indexOf("toLogin")>0 || backUrl.indexOf("toRegister")>0){
            $(".j_ajaxForm").attr("data-next","${ctx}/u/account/index");
        }else{
            $(".j_ajaxForm").attr("data-next",backUrl);
        }
    });
</script>
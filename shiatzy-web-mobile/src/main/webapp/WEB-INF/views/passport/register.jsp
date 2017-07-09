<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-registered">
    <div class="dx-title"><spring:message code="register"/> <a href="/passport/toLogin"><spring:message
            code="goBack"/></a></div>
    <form class="j_ajaxForm" action="/passport/register.do" method="post" data-next="${ctx}/passport/toLogin">
        <div class="dx-form">
            <div class="form-item text-center"><spring:message code="register.titleInfo"/></div>
            <div class="form-item">
                <input type="email" placeholder='<spring:message code="register.holderEmail"/>' name="email"
                       id="userName"
                       onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="register.holderEmail"/>'"
                       data-rule="${web:t("电子邮箱","Email")}:required;email">
            </div>
            <div class="form-item">
                <input type="password" placeholder='<spring:message code="register.holderPass"/>' name="password"
                       id="password" onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="register.holderPass"/>'"
                       data-rule="${web:t("密码","Password")}:required;password">
            </div>
            <div class="form-item">
                <input type="password" placeholder='<spring:message code="register.holderRePass"/>'
                       name="confirm_password"
                       id="confirm_password" onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="register.holderRePass"/>'"
                       data-rule="${web:t("确认密码","Confirm password")}:required;password;match(password)">
            </div>
            <div class="form-item">
                <div class="dx-verify clearfix">
                    <input type="text" class="verify" placeholder='<spring:message code="register.holderValidCode"/>'
                           name="validCode" id="validCode" onfocus="this.placeholder=''"
                           onblur="this.placeholder='<spring:message code="register.holderValidCode"/>'"
                           data-rule="${web:t("验证码","ValidCode")}:required;">
                    <div class="dx-verify-pic"><img src="/captcha" alt="" id="codeImg" class="j_captcha"></div>
                </div>
            </div>
            <div class="form-item button">
                <button type="submit" class="btn btn-default"><spring:message code="register"/></button>
            </div>
            <div class="form-item text-center"><spring:message code="register.endInfo"/></div>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    $(function () {
        $('.j_captcha').click(function () {
            var $this = $(this);
            $this.attr('src', '/captcha');
        });
    });
</script>

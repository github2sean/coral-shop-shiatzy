<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    .content input {
        font-size: 1.1rem;
        line-height:1;
        padding:0.4rem 0.6rem 0;
        width: 100%;
        height: 2.8rem;
        border: 2px solid #b2b2b2;
    }
</style>
<div class="order">
    <p style="float: left"><spring:message code="account.personal.updatePassword"/></p>
    <a style="float: right;" href="/u/account/details">< <spring:message code="goBack"/></a>
</div>

<div class="mail-change" style="border-bottom: none">

        <h4><spring:message code="account.personal.update.password"/></h4>
        <h4>* <spring:message code="account.personal.updateEmailOrPass.must"/></h4>

</div>
<div class="password-change">
    <form class="j_ajaxForm" action="/u/account/updatePassword" method="post" data-next="${ctx}/u/account/details">
    <div class="register-mail content">
        <div class="input form-item">
            <i>*</i>
            <input class="oldPassword" name="oldPassword" type="password"
                   placeholder="<spring:message code="account.personal.updateEmailOrPass.holderPassword"/>"
                   onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                    code="account.personal.updateEmailOrPass.holderPassword"/>'"
                   data-rule="<spring:message code="oldPassword"/>:required;password"/>
        </div>
        <div class="input form-item">
            <i>*</i>
            <input id="newPassword" name="newPassword" type="password"
                   placeholder="<spring:message code="account.personal.updateEmailOrPass.holderNewPassword"/>"
                   onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                    code="account.personal.updateEmailOrPass.holderNewPassword"/>'"
                   data-rule="<spring:message code="newPassword"/>:required;password"/>
        </div>
        <div class="input form-item">
            <i>*</i>
            <input id="confirmPassword" name="confirmPassword" type="password"
                   placeholder="<spring:message code="account.personal.updateEmailOrPass.holderReNewPassword"/>"
                   onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                    code="account.personal.updateEmailOrPass.holderReNewPassword"/>'"
                   data-rule="<spring:message code="newPassword"/>:required;password;match(newPassword)">
        </div>
        <div class="emailInfo2"></div>
        <button type="submit" class="btn-default">${web:t("保存","SAVE")}</button>
    </div>
    </form>

</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {

        $(".top-right-nav").find("li:eq(2)").addClass("active");
    });
</script>


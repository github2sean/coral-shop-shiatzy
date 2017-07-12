<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-login dx-ForgotPassword">
    <div class="dx-title clearfix">
        <div class="member"><spring:message code="forgetPassword"/></div>
        <a href="/passport/toLogin" class="icon iconfont" type="button">&#xe67d;</a>
    </div>

    <form class="j_ajaxForm" action="/passport/forgetPassword" method="post" data-next="${ctx}/passport/toLogin">
        <div class="dx-form">
            <div class="tips" style="line-height: 2.0rem"><spring:message code="forgotTitle"/></div>
            <div class="form-item">
                <input type="email" placeholder="<spring:message code="login.holderAccount"/>" class="userName"
                       name="userName" id="userName" onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="login.holderAccount"/>'"
                       data-rule="<spring:message code="login.holderAccount"/>:required;email">
            </div>
            <div class="form-item">
                <div class="dx-verify clearfix" style="width: 100%">
                    <input type="text" class="verify"
                           placeholder='<spring:message code="register.holderValidCode"/>'
                           name="validCode" id="validCode" onfocus="this.placeholder=''"
                           onblur="this.placeholder='<spring:message code="register.holderValidCode"/>'"
                           data-rule="<spring:message code="register.holderValidCode"/>:required;" style="float: left;width: 60%">
                    <div class="dx-verify-pic"><img src="/captcha" alt="" id="codeImg" class="j_captcha"></div>
                </div>
            </div>
            <div class="remind"></div>
            <button type="submit" class="btn btn-default"><spring:message code="send"/></button>
            <div class="register"><spring:message code="login.noneAccount"/>？ <a
                    href="${ctx}/passport/toRegister"><spring:message code="register"/></a></div>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    //邮箱验证
    function userName() {
        var userName = $("#userName").val();
        var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        if (userName == '') {
            $(".remind").show().css("color", "red").text("<spring:message code="register.validNull"/>");
            return false;
        } else if (!reg.test(userName)) {
            $(".remind").show().css("color", "red").text("<spring:message code="register.validNull"/>");
            return false;
        }
        $(".remind").hide();

        return true;

    };
    //验证码验证
    function validCode() {
        var validCode = $('.verify').val();
        if (validCode == '') {
            $(".remind").show().css("color", "red").text("<spring:message code="register.validNull"/>");
            return false;
        }
        $(".remind").hide();

        return true;
    }

    $(function () {
        $('.j_captcha').click(function () {
            var $this = $(this);
            $this.attr('src', '/captcha');
        });
    });
   /* $(function () {
        $('#userName').focus(function () {
            $(".remind").show().css("color", "red").text("<spring:message code="register.validEmail"/>");
        }).blur(userName);
        $('.verify').focus(function () {
            $(".remind").show().css("color", "red").text("<spring:message code="register.holderValidCode"/>");
        }).blur(validCode);

        $(".sendBtn").click(function () {
            var userName = $(".userName").val();
            var validCode = $(".verify").val();
            var data = {"userName": userName, "validCode": validCode};
            $.post("/passport/sendPassword.do", data, function (data) {
                alert(data.message);
            })
        });
    });*/
</script>


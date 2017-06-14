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
    <form class="j_ajaxForm" action="/passport/register.do" method="post" data-next="${ctx}/u/account/index">
        <div class="dx-form">
            <div class="form-item text-center"><spring:message code="register.titleInfo"/></div>
            <div class="form-item">
                <input type="email" placeholder='<spring:message code="register.holderEmail"/>' name="email"
                       id="userName"
                       onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="register.holderEmail"/>'"
                       data-rule="电子邮箱:required;email">
            </div>
            <div class="form-item">
                <input type="password" placeholder='<spring:message code="register.holderPass"/>' name="password"
                       id="password" onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="register.holderPass"/>'"
                       data-rule="密码:required;password">
            </div>
            <div class="form-item">
                <input type="password" placeholder='<spring:message code="register.holderRePass"/>'
                       name="confirm_password"
                       id="confirm_password" onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="register.holderRePass"/>'"
                       data-rule="确认密码:required;password;match(password)">
            </div>
            <div class="form-item">
                <div class="dx-verify clearfix">
                    <input type="text" class="verify" placeholder='<spring:message code="register.holderValidCode"/>'
                           name="validCode" id="validCode" onfocus="this.placeholder=''"
                           onblur="this.placeholder='<spring:message code="register.holderValidCode"/>'"
                           data-rule="验证码:required;">
                    <div class="dx-verify-pic"><img src="/captcha" alt="" id="codeImg"></div>
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
    });

    /*//邮箱验证
    function userName() {
        var userName = $("#userName").val();
        var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        if (userName == '') {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validNull"/>');
            return false;
        } else if (!reg.test(userName)) {
            $(".remind").show().css("color", "red").text("<spring:message code="register.validEmail"/>");
            return false;
        }
        $(".remind").hide();

        return true;

    };
    //密码验证
    function userPwd() {
        var userPwd = $('#userPwd').val();
        var reg = /^[a-zA-Z]\w{5,17}$/;
        if (userPwd == '') {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validNull"/>');
            return false;
        } else if (!reg.test(userPwd)) {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validPassStyle"/>');
            return false;
        }
        $(".remind").hide();

        return true;
    };
    //再次输入密码验证
    function confirm_password() {
        var userPwd = $('#userPwd').val();
        var confirm_password = $('#confirm_password').val();
        if (confirm_password == '') {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validNull"/>');
            return false;
        } else if (confirm_password != userPwd) {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validPass"/>');
            return false;
        }
        $(".remind").hide();

        return true;
    }
    //验证码验证
    function validCode() {
        var validCode = $('#validCode').val();
        if (validCode == '') {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validNull"/>');
            return false;
        }
        $(".remind").hide();

        return true;
    }
    $(function () {
        $('#userName').focus(function () {
            $(".remind").show().css("color", "red").text("<spring:message code="register.validEmail"/>");
        }).blur(userName);
        $('#userPwd').focus(function () {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validPassStyle"/>');
        }).blur(userPwd);
        $('#confirm_password').focus(function () {
            $(".remind").show().css("color", "red").text('<spring:message code="register.validPass"/>');
        }).blur(confirm_password);
        $('#validCode').focus(function () {
            $(".remind").show().css("color", "red").text('<spring:message code="register.holderValidCode"/>');
        }).blur(validCode);

        $(".registerBtn").click(function () {
            var $form = $(".registerForm");
            var data = $form.serializeArray();
            $.post("/passport/register.do", data, function (data) {
                // alert(data.message);

                if (data.code == 200) {
                    location.href = "${ctx}/passport/toLogin";
                } else {
                    $(".remind").text(data.message);
                }

            })
        });
    });*/
</script>


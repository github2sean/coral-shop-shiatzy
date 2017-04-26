<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-registered">
    <div class="dx-title">注册 <a href="注册登录.登录.html">返回上页</a></div>
    <form class="registerForm" action="/passport/register.do" method="post">
    <div class="content">
        <div class="title">快速注册，享更多便捷体验</div>
        <input type="email" placeholder="电子邮箱" name="email">
        <input type="password" placeholder="创建密码" name="password">
        <input type="password" placeholder="再次输入密码" name="confirm_password">
        <div class="dx-verify clearfix">
            <input type="text" class="verify" placeholder="输入右方验证码" name="validCode">
            <div class="dx-verify-pic"><img src="images/verify_03.jpg" alt=""></div>
        </div>
        <div class="remind">验证码输入错误，请重新输入</div>
        <button type="button" class="registerBtn">注 册</button>
        <div class="notice">完成注册，表示您已同意接受Shop.shiatzychen.com 的隐私政策及相关线上条例。</div>
    </div>
    </form>
    <div class="thirdparty">
        <div class="title">使用第三方帐号快速登入</div>
        <div class="thirdparty-login"><a href=""></a><a href=""></a><a href=""></a><a href=""></a></div>
        <div class="dx-instructions"><a href="#">使用说明</a></div>
        <div class="dx-privacy"><a href="#">隐私权政策</a></div>
    </div>
    <script>
        $(function () {
            $(".registerBtn").click(function () {
                var $form = $(".registerForm");
                var data  = $form.serializeArray();
                $.post("/passport/register.do",data,function (data) {
                    alert(data.message);
                })
            });
        });
    </script>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>



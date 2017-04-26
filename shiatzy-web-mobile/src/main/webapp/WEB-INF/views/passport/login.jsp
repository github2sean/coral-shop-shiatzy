<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-login">
    <div class="dx-title clearfix">
        <div class="member"><span><img src="images/icon-member.png" alt=""></span>会员</div>
        <a href="" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
    <form class="loginForm" method="post" action="/passport/login"></form>
    <div class="content">
        <div class="login">登入</div>
        <div class="title">请于下方输入您的电子邮箱与密码登入</div>
        <input type="email" placeholder="输入您的电子邮箱" name="loginForm.email">
        <input type="password" placeholder="输入您的密码" name="loginForm.password">
        <div class="remind"><a href="注册登录.登录.忘记密码.html">忘记密码？</a></div>
        <!--添加的a标签用来演示-->
        <a href="我的账户.首页.html"><button type="button" class="loginBtn">登入</button></a>
        <div class="register">尚未拥有帐号？ <a href="${ctx}/passport/register">注册</a></div>
    </div>
    <div class="thirdparty">
        <div class="title">使用第三方帐号快速登入</div>
        <div class="thirdparty-login"><a href=""></a><a href=""></a><a href=""></a><a href=""></a></div>
    </div>

    <script>
        $(function () {
            $(".loginBtn").click(function () {
                $(".loginForm").submit();
            });
        });
    </script>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


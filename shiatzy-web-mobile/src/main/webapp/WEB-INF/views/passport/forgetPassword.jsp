<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-login dx-ForgotPassword">
    <div class="dx-title clearfix">
        <div class="member">忘记密码</div>
        <a href="#" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
    <div class="content">
        <div class="title">请于下方输入您的电子邮箱，以获取您的登录密码</div>
        <input type="email" placeholder="输入您的电子邮箱" class="userName" name="userName">
        <div class="dx-verify clearfix">
            <input type="text" class="verify" placeholder="输入右方验证码" name="validCode">
            <div class="dx-verify-pic"><img src="images/verify_03.jpg" alt=""></div>
        </div>
        <div class="remind">验证码输入错误，请重新输入</div>
        <button type="button" class="sendBtn">发送</button>
        <div class="register">尚未拥有帐号？ <a href="${ctx}/passport/toRegister">注册</a></div>
    </div>

    <script>
        $(function () {
            $(".sendBtn").click(function () {
                var userName = $(".userName").val();
                var validCode = $(".verify").val();
                var data  = {"userName":userName,"validCode":validCode };
                $.post("/passport/sendPassword.do",data,function (data) {
                alert(data.message);
                })
            });
        });
    </script>

</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


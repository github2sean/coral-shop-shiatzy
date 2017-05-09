<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-login">
    <div class="dx-title clearfix">
        <div class="member"><span><img src="${ctx}/static/images/icon-member.png" alt=""></span>会员</div>
        <a href="" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
    <form class="loginForm" method="post" action="/passport/login">
    <div class="content">
        <div class="login">登入</div>
        <div class="title">请于下方输入您的电子邮箱与密码登入</div>
        <input type="email" placeholder="输入您的电子邮箱" name="userName">
        <input type="password" placeholder="输入您的密码" name="password">
        <div class="remind"><a href="${ctx}/passport/toForget">忘记密码？</a></div>
        <div class="remind loginInfo"></div>
        <!--添加的a标签用来演示-->
        <button type="button" class="loginBtn">登入</button>
        <div class="register">尚未拥有帐号？ <a href="${ctx}/passport/toRegister">注册</a></div>
    </div>
    </form>
    <div class="thirdparty">
        <div class="title">使用第三方帐号快速登入</div>
        <div class="thirdparty-login"><a href=""></a><a href=""></a><a href=""></a><a href=""></a></div>
    </div>


</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {
        $(".loginInfo").hide();
        $(".loginBtn").click(function () {
            var $form = $(".loginForm");
            var data  = $form.serializeArray();
            $.post("/passport/login.do",data,function (data) {
                var code = data.code;
                if(code == '200'){
                    location.href = "${ctx}/home/index";
                }else{
                    $(".loginInfo").show().css("color","red").text(data.message);
                }
            });
        });
    });
</script>

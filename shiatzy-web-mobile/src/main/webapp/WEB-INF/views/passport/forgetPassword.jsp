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
        <a href="javascript:history.go(-1)" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
    <div class="content">
        <div class="title">请于下方输入您的电子邮箱，以获取您的登录密码</div>
        <input type="email" placeholder="输入您的电子邮箱" class="userName" name="userName" id="userName"onfocus="this.placeholder=''" onblur="this.placeholder='输入您的电子邮箱'">
        <div class="dx-verify clearfix">
            <input type="text" class="verify" placeholder="输入右方验证码" name="validCode">
            <div class="dx-verify-pic"><img src="/captcha" alt=""></div>
        </div>
        <div class="remind"></div>
        <button type="button" class="sendBtn">发送</button>
        <div class="register">尚未拥有帐号？ <a href="${ctx}/passport/toRegister">注册</a></div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    //邮箱验证
    function userName(){
        var userName =$("#userName").val();
        var reg =/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
        if(userName=='') {
            $(".remind").show().css("color","red").text("邮箱不能为空！");
            return false;
        }else if(!reg.test(userName))
        {
            $(".remind").show().css("color","red").text("邮箱格式不对，请重新输入！");
            return false;
        }
        $(".remind").hide();

        return true;

    };
    //验证码验证
    function validCode()
    {
        var validCode = $('.verify').val();
        if(validCode =='') {
            $(".remind").show().css("color","red").text("验证码不能为空！");
            return false;
        }
        $(".remind").hide();

        return true;
    }
    $(function () {
        $('#userName').focus(function(){
            $(".remind").show().css("color","red").text("邮箱的格式为：xxx@xxx.com或cn或cnt");
        }).blur(userName);
        $('.verify').focus(function(){
            $(".remind").show().css("color","red").text("请输入验证码");
        }).blur(validCode);

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


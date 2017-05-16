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
        <input type="email" placeholder="输入您的电子邮箱" name="userName" id="userName"onfocus="this.placeholder=''" onblur="this.placeholder='输入您的电子邮箱'">
        <input type="password" placeholder="输入您的密码" name="password" id="userPwd"onfocus="this.placeholder=''" onblur="this.placeholder='输入您的密码'">
        <div class="remind"><a href="${ctx}/passport/toForget">忘记密码？</a></div>
        <div class="remind loginInfo"></div>
        <!--添加的a标签用来演示-->
        <button type="button" class="btn loginBtn">登入</button>
        <div class="register">尚未拥有帐号？ <a href="${ctx}/passport/toRegister">注册</a></div>
    </div>
    </form>
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
            $(".loginInfo").show().css("color","red").text("邮箱不能为空！");
            return false;
        }else if(!reg.test(userName))
        {
            $(".loginInfo").show().css("color","red").text("邮箱格式不对，请重新输入！");
            return false;
        }
        $(".loginInfo").hide();

        return true;

    };
    $(function () {
        $('#userName').focus(function(){
            $(".loginInfo").hide();
        }).blur(userName);


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

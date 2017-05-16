<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="dx-registered">
    <div class="dx-title">注册 <a href="/passport/toLogin">返回上页</a></div>
    <form class="registerForm" action="/passport/register.do" method="post">
    <div class="content">
        <div class="title">快速注册，享更多便捷体验</div>
        <input type="email" placeholder="电子邮箱" name="email"  id="userName"onfocus="this.placeholder=''" onblur="this.placeholder='电子邮箱'">
        <input type="password" placeholder="创建密码" name="password" id="userPwd"onfocus="this.placeholder=''" onblur="this.placeholder='创建密码'">
        <input type="password" placeholder="再次输入密码" name="confirm_password" id="confirm_password" onfocus="this.placeholder=''" onblur="this.placeholder='再次输入密码'">
        <div class="dx-verify clearfix">
            <input type="text" class="verify" placeholder="输入右方验证码" name="validCode" id="validCode" onfocus="this.placeholder=''" onblur="this.placeholder='输入右方验证码'">
            <div class="dx-verify-pic"><img src="/captcha" alt="" id="codeImg"></div>
        </div>
        <div class="remind"></div>
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
    //密码验证
    function userPwd()
    {
        var userPwd=$('#userPwd').val();
        var reg=/^[a-zA-Z]\w{5,17}$/;
        if(userPwd=='') {
            $(".remind").show().css("color","red").text("密码不能为空！");
            return false;
        }else if(!reg.test(userPwd))
        {
            $(".remind").show().css("color","red").text("密码格式不对，请重新输入！");
            return false;
        }
        $(".remind").hide();

        return true;
    };
    //再次输入密码验证
    function confirm_password()
    {
        var userPwd = $('#userPwd').val();
        var confirm_password = $('#confirm_password').val();
        if(confirm_password =='') {
            $(".remind").show().css("color","red").text("密码不能为空！");
            return false;
        }else if(confirm_password !=userPwd)
        {
            $(".remind").show().css("color","red").text("两次密码不一样，请重新输入！");
            return false;
        }
        $(".remind").hide();

        return true;
    }
    //验证码验证
    function validCode()
    {
        var validCode = $('#validCode').val();
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
        $('#userPwd').focus(function(){
            $(".remind").show().css("color","red").text("以字母开头，长度在6~18之间，只能包含字符、数字和下划线");
        }).blur(userPwd);
        $('#confirm_password').focus(function(){
            $(".remind").show().css("color","red").text("两次密码必须相同");
        }).blur(confirm_password);
        $('#validCode').focus(function(){
            $(".remind").show().css("color","red").text("请输入验证码");
        }).blur(validCode);

        $(".registerBtn").click(function () {
            var $form = $(".registerForm");
            var data  = $form.serializeArray();
            $.post("/passport/register.do",data,function (data) {
               // alert(data.message);

                if (data.code==200){
                    location.href = "${ctx}/passport/toLogin";
                }else{
                    $(".remind").text(data.message);
                }

            })
        });
    });
</script>


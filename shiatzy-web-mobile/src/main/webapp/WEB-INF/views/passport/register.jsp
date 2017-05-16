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
        <input type="email" placeholder="电子邮箱" name="email">
        <input type="password" placeholder="创建密码" name="password">
        <input type="password" placeholder="再次输入密码" name="confirm_password">
        <div class="dx-verify clearfix">
            <input type="text" class="verify" placeholder="输入右方验证码" name="validCode">
            <div class="dx-verify-pic"><img src="/captcha" alt=""></div>
        </div>
        <div class="remind"></div>
        <button type="button" class="registerBtn">注 册</button>
        <div class="notice">完成注册，表示您已同意接受Shop.shiatzychen.com 的隐私政策及相关线上条例。</div>
    </div>
    </form>

</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {
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


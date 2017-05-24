<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<div class="dx-login">
    <div class="dx-title clearfix">
        <div class="member"><span><img src="${ctx}/static/images/icon-member.png" alt=""></span><spring:message code="vip"/></div>
        <a href="" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
    <form class="loginForm" method="post" action="/passport/login">
    <div class="content">
        <div class="login"><spring:message code="login"/></div>
        <div class="title"><spring:message code="login.tips"/></div>
        <input type="email" placeholder='<spring:message code="login.holderAccount"/>' name="userName" id="userName"onfocus="this.placeholder=''"    onblur="this.placeholder='<spring:message code="login.holderAccount"/>'">
        <input type="password" placeholder='<spring:message code="login.holderPassword"/>' name="password" id="userPwd"onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message code="login.holderPassword"/>'">
        <div class="remind"><a href="${ctx}/passport/toForget"><spring:message code="login.forgotPassword"/>？</a></div>
        <div class="remind loginInfo"></div>
        <!--添加的a标签用来演示-->
        <button type="button" class="btn loginBtn"><spring:message code="login"/></button>
        <div class="register"><spring:message code="login.noneAccount"/>？ <a href="${ctx}/passport/toRegister"><spring:message code="register"/></a></div>
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
            $(".loginInfo").show().css("color","red").text("<spring:message code="login.validAccount"/>！");
            return false;
        }else if(!reg.test(userName))
        {
            $(".loginInfo").show().css("color","red").text("<spring:message code="login.validAccountStyle"/>！");
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

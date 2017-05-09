<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left">邮箱及密码修改</p>
    <a style="float: right;" href=”#” onClick="javascript :history.back(-1);">< 回上页</a>
</div>
<form class="updateForm" method="post" action="u/account/updateEmailOrPassword">
<div class="mail-change">
    <h4>您可在此处修改您的电子邮箱或密码。</h4>
    <h4>* 必填栏位</h4>
    <div class="register-mail">
        <p>当前邮箱：</p>
        <p style="margin-bottom: 2rem;">${accountDomain.email}</p>
        <p>修改邮箱：</p>
        <div class="input">
            <i>*</i>
            <input id="newEmail" name="newEmail" type="text" placeholder="请输入您的新登录邮箱地址"/>
        </div>
        <div class="input">
            <i>*</i>
            <input id="newEmail2" type="text" placeholder="请再次输入您的新登录邮箱地址" />
        </div>
        <div class="erroInfo" style='color: red;'></div>
        <a  type="button" class="saveBtn">
            <span><</span>
            保存
        </a>
    </div>
</div>
<div class="password-change">
    <div class="register-mail">
        <p style="margin-bottom: 2rem;">修改密码</p>
        <div class="input">
            <i>*</i>
            <input class="oldPassword" name="password" type="password" placeholder="请输入您原始登录密码"/>
        </div>
        <div class="input">
            <i>*</i>
            <input id="newPassword" name="newPassword" type="password" placeholder="请输入新密码"/>
        </div>
        <div class="input">
            <i>*</i>
            <input id="newPassword2" type="password" placeholder="请再次输入新密码" />
        </div>
        <div class="erroInfo2"></div>
        <a type="button" class="updateBtn">
            <span><</span>
            确认
        </a>
    </div>


</div>
</form>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>

    $(function () {

        $(".saveBtn").click(function () {

            var email = $("#newEmail").val();
            var email2 = $("#newEmail2").val();
            var erroinfo = $(".erroInfo").text();
            console.log(email+"  "+email2+" "+erroinfo);
            if(email=='' && email2==''){
                $(".erroInfo").show();
                $(".erroInfo").text("邮箱不能为空");
                erroinfo='';
            }else if (email!=email2){
                $(".erroInfo").show();
                $(".erroInfo").text("两次输入的邮箱不一致");
            }else if( email!='' && email2!='' && email==email2){
                $.post("/u/account/validUserName",{"userName":email},function (data) {
                    if(data.code==200){
                        $(".erroInfo").text("邮箱可用");
                    }else{
                        $(".erroInfo").text(data.message);
                    }
                });
            }
        });

        $(".updateBtn").click(function () {
            var password = $("#newPassword").val();
            var password2 = $("#newPassword2").val();
            var oldpassword = $(".oldpassword").val();
            var erroinfo2 = $(".erroInfo2").text();
            if (password!="" && password2!="" && password!=password2 && erroinfo2==""){
                $(".erroInfo2").show();
                $(".erroInfo2").append("<span style='color: red'>"+"两次输入的密码不一致"+"</span>");
            }else if(password==password2){
                $(".erroInfo2").hide();
                $(".erroInfo2").text("");
            }
            if(password!="" && password2!="" && oldpassword!=""){
                var data  = $(".updateForm").serializeArray();
                $.post("updateEmailOrPassword",data,function (data) {
                    console.log(data);
                    console.log(data.message);
                    if(data.code!=200){
                        $(".erroInfo2").show();
                        $(".erroInfo2").css("color","red").text(data.message);
                    }

                });
            }

        });

    });
</script>

<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    i {
        line-height: 5.942rem;
        left: -10px;
    }
</style>

<div class="order">
    <p style="float: left"><spring:message code="account.personal.updatePassword"/></p>
    <a style="float: right;" href="/u/account/details">< <spring:message code="goBack"/></a>
</div>

<div class="mail-change" style="border-bottom: none">

        <h4><spring:message code="account.personal.update.password"/></h4>
        <h4>* <spring:message code="account.personal.updateEmailOrPass.must"/></h4>

</div>
<div class="password-change">
    <form class="j_ajaxForm" action="/u/account/updatePassword" method="post" data-next="${ctx}/u/account/details">
    <div class="register-mail">
        <div class="input form-item">
            <i>*</i>
            <input class="oldPassword" name="oldPassword" type="password"
                   placeholder="<spring:message code="account.personal.updateEmailOrPass.holderPassword"/>"
                   onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                    code="account.personal.updateEmailOrPass.holderPassword"/>'"
                   data-rule="旧密码:required;password"/>
        </div>
        <div class="input form-item">
            <i>*</i>
            <input id="newPassword" name="newPassword" type="password"
                   placeholder="<spring:message code="account.personal.updateEmailOrPass.holderNewPassword"/>"
                   onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                    code="account.personal.updateEmailOrPass.holderNewPassword"/>'"
                   data-rule="新密码:required;password"/>
        </div>
        <div class="input form-item">
            <i>*</i>
            <input id="confirmPassword" name="confirmPassword" type="password"
                   placeholder="<spring:message code="account.personal.updateEmailOrPass.holderReNewPassword"/>"
                   onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                    code="account.personal.updateEmailOrPass.holderReNewPassword"/>'"
                   data-rule="确认密码:required;password;match(newPassword)">
        </div>
        <div class="emailInfo2"></div>
        <button type="submit" class="btn-default"><spring:message
                code="account.personal.updateEmailOrPass.validEmail"/></button>

    </div>
    </form>

</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<%--  <script>
      //邮箱验证
      function newEmail() {
          var newEmail = $("#newEmail").val();
          var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
          if (newEmail == '') {
              $(".emailInfo").show().css("color", "red").text("邮箱不能为空！");
              return false;
          } else if (!reg.test(newEmail)) {
              $(".emailInfo").show().css("color", "red").text("邮箱格式不对，请重新输入！");
              return false;
          }
          $(".emailInfo").hide();

          return true;

      };
      function newEmail2() {
          var newEmail2 = $("#newEmail2").val();
          var newEmail = $("#newEmail").val();
          if (newEmail2 == '') {
              $(".emailInfo").show().css("color", "red").text("邮箱不能为空！");
              return false;
          } else if (newEmail2 != newEmail) {
              $(".emailInfo").show().css("color", "red").text("两次邮箱输入不一样，请重新输入！");
              return false;
          }
          $(".emailInfo").hide();

          return true;

      };
      //密码验证
      function oldPassword() {
          var oldPassword = $('.oldPassword').val();
          var reg = /^[a-zA-Z]\w{5,17}$/;
          if (oldPassword == '') {
              $(".emailInfo2").show().css("color", "red").text("密码不能为空！");
              return false;
          } else if (!reg.test(oldPassword)) {
              $(".emailInfo2").show().css("color", "red").text("密码格式不对，请重新输入！");
              return false;
          }
          $(".emailInfo2").hide();

          return true;
      };
      function newPassword() {
          var newPassword = $('#newPassword').val();
          var reg = /^[a-zA-Z]\w{5,17}$/;
          if (newPassword == '') {
              $(".emailInfo2").show().css("color", "red").text("密码不能为空！");
              return false;
          } else if (!reg.test(newPassword)) {
              $(".emailInfo2").show().css("color", "red").text("密码格式不对，请重新输入！");
              return false;
          }
          $(".emailInfo2").hide();

          return true;
      };
      function newPassword2() {
          var newPassword2 = $('#newPassword2').val();
          var newPassword = $('#newPassword').val();
          if (newPassword2 == '') {
              $(".emailInfo2").show().css("color", "red").text("密码不能为空！");
              return false;
          } else if (newPassword2 != newPassword) {
              $(".emailInfo2").show().css("color", "red").text("两次密码不一样，请重新输入！");
              return false;
          }
          $(".emailInfo2").hide();

          return true;
      };
      $(function () {
          $('#newEmail').focus(function () {
              $(".emailInfo").show().css("color", "red").text("请输入新的登陆邮箱！");
          }).blur(newEmail);
          $('#newEmail2').focus(function () {
              $(".emailInfo").show().css("color", "red").text("请再次输入新的登陆邮箱！");
          }).blur(newEmail2);
          $('.oldPassword').focus(function () {
              $(".emailInfo2").show().css("color", "red").text("请输入您原始登录密码！");
          }).blur(oldPassword);
          $('#newPassword').focus(function () {
              $(".emailInfo2").show().css("color", "red").text("请输入新的登录密码！");
          }).blur(newPassword);
          $('#newPassword2').focus(function () {
              $(".emailInfo2").show().css("color", "red").text("请再次输入新的登录密码！");
          }).blur(newPassword2);

          $(".saveBtn").click(function () {

              var email = $("#newEmail").val();
              var email2 = $("#newEmail2").val();
              var erroinfo = $(".erroInfo").text();
              console.log(email + "  " + email2 + " " + erroinfo);
              if (email == '' && email2 == '') {
                  $(".erroInfo").show();
                  $(".erroInfo").text("邮箱不能为空");
                  erroinfo = '';
              } else if (email != email2) {
                  $(".erroInfo").show();
                  $(".erroInfo").text("两次输入的邮箱不一致");
              } else if (email != '' && email2 != '' && email == email2) {
                  $.post("/u/account/validUserName", {"userName": email}, function (data) {
                      if (data.code == 200) {
                          $(".erroInfo").text("邮箱可用");
                      } else {
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
              if (password != "" && password2 != "" && password != password2 && erroinfo2 == "") {
                  $(".erroInfo2").show();
                  $(".erroInfo2").append("<span style='color: red'>" + "两次输入的密码不一致" + "</span>");
              } else if (password == password2) {
                  $(".erroInfo2").hide();
                  $(".erroInfo2").text("");
              }
              if (password != "" && password2 != "" && oldpassword != "") {
                  var data = $(".updateForm").serializeArray();
                  $.post("updateEmailOrPassword", data, function (data) {
                      console.log(data);
                      console.log(data.message);
                      if (data.code != 200) {
                          $(".erroInfo2").show();
                          $(".erroInfo2").css("color", "red").text(data.message);
                      }

                  });
              }

          });

      });
  </script>--%>

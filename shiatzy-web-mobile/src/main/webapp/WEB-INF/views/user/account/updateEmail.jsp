<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    .content input {
        font-size: 1.1rem;
        line-height:1;
        padding:0.4rem 0.6rem 0;
        width: 100%;
        height: 2.8rem;
        border: 2px solid #b2b2b2;
    }
</style>

<div class="order">
    <p style="float: left" ><spring:message code="account.personal.updateEmail"/></p>
    <a style="float: right;" href="/u/account/details">< <spring:message code="goBack"/></a>
</div>

<div class="mail-change" style="padding-bottom: 15px;border-bottom: none">
    <form class="j_ajaxForm" action="/u/account/updateEmail" method="post" data-next="${ctx}/u/account/details">
        <h4><spring:message code="account.personal.update.email"/></h4>
        <h4>* <spring:message code="account.personal.updateEmailOrPass.must"/></h4>
        <div class="register-mail content">
            <p><spring:message code="account.personal.updateEmailOrPass.nowEmail"/>：</p>
            <p style="margin-bottom: 2rem;">${accountDomain.email}</p>
            <p><spring:message code="account.personal.updateEmailOrPass.email"/>：</p>

            <div class="input form-item">
                <i>*</i>
                <input id="email" name="email" type="text"
                       placeholder="<spring:message code="account.personal.updateEmailOrPass.holderNewEmail"/>"
                       onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                        code="account.personal.updateEmailOrPass.holderNewEmail"/>'"
                       data-rule="邮箱:required;email"/>
            </div>
            <div class="input form-item">
                <i>*</i>
                <input id="confirmEmail" name="confirmEmail" type="text"
                       placeholder="<spring:message code="account.personal.updateEmailOrPass.holderReNewEmail"/>"
                       onfocus="this.placeholder=''" onblur="this.placeholder='<spring:message
                        code="account.personal.updateEmailOrPass.holderReNewEmail"/>'"
                       data-rule="确认邮箱:required;email;match(email)"/>
            </div>
            <div class="emailInfo"></div>
            <button type="submit" class="btn-default"><spring:message
                    code="account.personal.updateEmailOrPass.validEmail"/></button>

        </div>
    </form>
</div>



<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>

    $(function () {

        $(".top-right-nav").find("li:eq(2)").addClass("active");
    });
</script>

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

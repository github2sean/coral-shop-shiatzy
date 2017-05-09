<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="back-up clearfix">
    <a href="我的账户.个人信息.html">
        <a style="float: right;" href=”#” onClick="javascript :history.back(-1);"><h3>< 回上页</h3></a>
    </a>
</div>
<div class="club">
    <h3>ART CLUB会员认证 </h3>
    <p>请输入您加入ART CLUB时留存的手机号码
        <input type="text" class="phoneNum">
    </p>
    <div id="showInfo" style="display: none;color: red;text-align: center;width: 100%"></div>
    <a href="#" class="validBtn">认证</a>
    <div class="club-bottom">
        <p>加入夏姿陈Art Club会员，可</p>
        <p>尊享xxxxxxxxxxxxxxxxxxxxxxx.</p>
    </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {


        $(".validBtn").click(function () {
            var  phoneNum = $(".phoneNum").val();

            $.post("/u/account/validVip",{"phoneNumber":phoneNum},function (data) {
                if(data.code==200){
                    location.href = "/u/account/vipDetail";
                }else{
                    console.log(data.message);
                    $("#showInfo").show().text(data.message);
                }
            });
        });

    });

</script>

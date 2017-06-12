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
        width: 100%;
        height: 2.8rem;
        border: none;
        border: 2px solid #b2b2b2;
        padding-left: .6rem;
    }
</style>
<div class="back-up clearfix">
    <a style="float: right;" href=”#” onClick="javascript :history.go(-1);"><h3>< 返回上页</h3></a>
</div>
<div class="club">
    <form action="/u/account/validVip" class="j_ajaxForm" method="post" data-next="/u/account/vipDetail">
        <div class="content">
        <h3>ART CLUB会员认证 </h3>
        <p>请输入您加入ART CLUB时留存的手机号码

        </p>
        <div class="form-item">
            <input type="text" class="phoneNum" name="phoneNumber"
                   data-rule="手机号码:required;mobile">
        </div>
        <div id="showInfo" style="display: none;color: red;text-align: center;width: 100%"></div>
        <button type="submit" class="btn btn-default">认证</button>
        <div class="club-bottom" style="margin-top: 30px;">
            <p>ART CLUB会员，线上同尊享线下购物礼遇。</p>
        </div>
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
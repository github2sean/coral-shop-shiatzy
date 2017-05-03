<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="back-up clearfix">
    <a href="我的账户.个人信息.html">
        <h3>回上一步</h3>
    </a>
</div>
<div class="club-card">
    <img src="images/card.png" alt="">
    <p class="card-title">夏姿门市 ART CLUB 會員</p>
    <p class="card-name">墨竹卡会员</p>
    <p class="card-num">会员卡号：＃＃＃＃＃＃＃＃</p>
</div>
<div class="anew">
    <a href="我的账户.个人信息.art_club认证.html">
        <p>重新认证</p>
    </a>
</div>
<div class="club-bottom">
    <p>加入夏姿陈Art Club会员，可</p>
    <p>尊享xxxxxxxxxxxxxxxxxxxxxxx.</p>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {




    });

</script>

<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
div class="main-content">
<div class="index-w text-center">
    <a href="" class="do-block img-cover"><img src="${ctx}/static/images/index-banner.jpg" alt=""></a>
    <div class="index-se font-16">
        <a href="产品列表.html">
            <p class="text-bold">冬日禮讚</p>
            <div><p class="bd-btm">WINTER SALES</p></div>
            <p class="para-discount">結算頁面輸入代碼<br/>享冬季首單九折</p>
            <div><p class="bd-ard">代碼：FRIST90</p></div>
        </a>
    </div>
    <div class="col-2-w clearfix">
        <a href="产品列表.html">
            <p class="guide-link-before">女裝</p>
            <div><img src="${ctx}/static/images/index-women.jpg" alt=""></div>
        </a>
        <a href="产品列表.html">
            <p class="guide-link-before">男裝</p>
            <div><img src="${ctx}/static/images/index-men.jpg" alt=""></div>
        </a>
    </div>
    <a href="产品列表.html" class="do-block img-cover index-cat">
        <p class="guide-link-before">包袋</p>
        <img src="${ctx}/static/images/index-list1.jpg" alt="">
    </a>
    <a href="产品列表.html" class="do-block img-cover index-cat">
        <p class="guide-link-before">鞋履</p>
        <img src="${ctx}/static/images/index-list2.jpg" alt="">
    </a>
    <a href="产品列表.html" class="do-block img-cover index-cat">
        <p class="guide-link-before">配飾</p>
        <img src="${ctx}/static/images/index-list3.jpg" alt="">
    </a>
    <a href="产品列表.html" class="do-block index-show">
        <p class="guide-link-before">2017春夏巴黎时装秀</p>
        <img src="${ctx}/static/images/index-show.jpg" alt="">
    </a>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


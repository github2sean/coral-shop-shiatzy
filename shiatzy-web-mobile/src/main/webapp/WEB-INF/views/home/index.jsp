<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="index-w text-center">
    <a href="" class="do-block img-cover"><img src="${ctx}/static/images/index-banner.jpg" alt=""></a>
    <div class="index-se font-16">
        <a href="/home/index">
            <c:if test="${ not empty coupon}">
                <p class="text-bold">${coupon.title}</p>
                <div><p class="bd-btm">${coupon.enTitle}</p></div>
                <p class="para-discount">${coupon.name}<br/></p>
                <div><p class="bd-ard">代碼：${coupon.code}</p></div>
            </c:if>
            <c:if test="${empty coupon}">
                <p class="text-bold" style="font-size: 20px">暂无优惠活动</p>
                <div><p class="bd-btm" style="font-size: 10px">敬请期待...</p></div>
            </c:if>
        </a>
    </div>
    <div class="col-2-w clearfix">
        <a href="/goods/list?categoryId=16">
            <p class="guide-link-before"><spring:message code="womenClothing"/></p>
            <div><img src="${ctx}/static/images/index-women.jpg" alt=""></div>
        </a>
        <a href="/goods/list?categoryId=26">
            <p class="guide-link-before"><spring:message code="menClothing"/></p>
            <div><img src="${ctx}/static/images/index-men.jpg" alt=""></div>
        </a>
    </div>
    <a href="/goods/list?categoryId=21" class="do-block img-cover index-cat">
        <p class="guide-link-before"><spring:message code="bags"/></p>
        <img src="${ctx}/static/images/index-list1.jpg" alt="">
    </a>
    <a href="/goods/list?categoryId=13"class="do-block img-cover index-cat">
        <p class="guide-link-before"><spring:message code="shoes"/></p>
        <img src="${ctx}/static/images/index-list2.jpg" alt="">
    </a>
    <a href="/goods/list?categoryId=12" class="do-block img-cover index-cat">
        <p class="guide-link-before"><spring:message code="accessories"/></p>
        <img src="${ctx}/static/images/index-list3.jpg" alt="">
    </a>
    <a href="/goods/list?categoryId=14" class="do-block index-show">
        <p class="guide-link-before">2017<spring:message code="fashion"/></p>
        <img src="${ctx}/static/images/index-show.jpg" alt="">
    </a>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


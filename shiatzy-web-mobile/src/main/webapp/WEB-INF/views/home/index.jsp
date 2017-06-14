<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="index-w text-center">
    <a href="#" class="do-block img-cover"><img src="${ctx}/static/images/Banner-1.jpg" alt=""></a>
    <div class="do-title text-right">
        <div class="title-main">大标题</div>
        <div class="title-sub">小标题</div>
    </div>
    <a href="#" class="do-block img-cover"><img src="${ctx}/static/images/Banner-2.jpg" alt=""></a>
    <div class="do-title text-center">
        <div class="title-main">大标题</div>
        <div class="title-sub">小标题</div>
    </div>
    <a href="#" class="do-block img-cover">
        <img src="${ctx}/static/images/Banner-3.jpg" alt="">
        <span class="do-cover-title do-cover-start">
            <span class="cover-title-main">标题标题</span>
        </span>
    </a>
    <a href="#" class="do-block img-cover">
        <img src="${ctx}/static/images/Banner-4.jpg" alt="">
        <span class="do-cover-title do-cover-center">
            <span class="cover-title-main">标题标题</span>
        </span>
    </a>
    <a href="#" class="do-block img-cover">
        <img src="${ctx}/static/images/Banner-4.jpg" alt="">
        <span class="do-cover-title do-cover-end">
            <span class="cover-title-main">标题标题</span>
        </span>
    </a>
    <div class="index-se font-16 hide">
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
    <div class="col-2-w clearfix" style="margin-top: 2rem;">
        <a href="/goods/list?categoryId=16" style="padding-right: 5px">
            <p class="guide-link-before"><spring:message code="womenClothing"/></p>
            <div><img src="${ctx}/static/images/Banner-5.jpg" alt=""></div>
        </a>
        <a href="/goods/list?categoryId=26" style="padding-left: 5px">
            <p class="guide-link-before"><spring:message code="menClothing"/></p>
            <div><img src="${ctx}/static/images/Banner-7.jpg" alt=""></div>
        </a>
    </div>

    <a href="#" class="do-block img-cover">
        <p class="guide-link-before hide"><spring:message code="menClothing"/></p>
        <img src="${ctx}/static/images/Banner-8.jpg" alt="">
    </a>

    <a href="#" class="do-block img-cover" >
        <p class="guide-link-before hide"><spring:message code="menClothing"/></p>
        <img src="${ctx}/static/images/Banner-9.jpg" alt="">
    </a>
    <%--<a href="/goods/list?categoryId=21" class="do-block img-cover index-cat">
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
    </a>--%>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


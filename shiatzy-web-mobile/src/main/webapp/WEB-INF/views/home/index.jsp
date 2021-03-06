<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>


<div class="index-w text-center" style="margin-top: -1.7rem;">

  <%--  <div class="theme-default">
        <div>
            <ul class="slider">
                <li><img src="${ctx}/static/images/Banner-1.jpg" alt="" title="" /></li>
                <li><img src="${ctx}/static/images/Banner-1.jpg" alt="" title="" /></li>
                <li><img src="${ctx}/static/images/Banner-1.jpg" alt="" title="" /></li>
                <li><img src="${ctx}/static/images/Banner-1.jpg" alt="" title="" /></li>
            </ul>
        </div>
    </div>--%>

    <c:forEach items="${groupList}" var="row">
        <c:if test="${row.type==1 && not empty row.indexBlockDomainList}">
            <c:if test="${row.indexBlockDomainList[0].isCover==0}">
                <a href="${row.indexBlockDomainList[0].link}" class="do-block img-cover"><img src="${ImageModel.toFirst(row.indexBlockDomainList[0].image).file}" alt=""></a>
                <div class="do-title ${row.indexBlockDomainList[0].position}">
                    <div class="title-main">${web:selectLanguage()=='en_US'?row.indexBlockDomainList[0].enTitle:row.indexBlockDomainList[0].title}</div>
                    <div class="title-sub">${web:selectLanguage()=='en_US'?row.indexBlockDomainList[0].enSubTitle:row.indexBlockDomainList[0].subTitle}</div>
                </div>
            </c:if>
            <c:if test="${row.indexBlockDomainList[0].isCover==1}">
                <a href="${row.indexBlockDomainList[0].link}" class="do-block img-cover">
                    <span class="do-cover-title ${row.indexBlockDomainList[0].position}" >
                        <span class="cover-title-main">${web:selectLanguage()=='en_US'?row.indexBlockDomainList[0].enTitle:row.indexBlockDomainList[0].title}</span>
                    </span>
                    <img src="${ImageModel.toFirst(row.indexBlockDomainList[0].image).file}" alt="">
                </a>
            </c:if>

        </c:if>
        <c:if test="${row.type==2 && row.indexBlockDomainList.size()>1}">
            <div class="col-2-w clearfix" style="margin-top: 2rem;">
                <a href="${row.indexBlockDomainList[0].link}" style="padding-right: 5px">
                    <c:if test="${not empty row.indexBlockDomainList[0].title}"><p class="guide-link-before">${web:selectLanguage()=='en_US'?row.indexBlockDomainList[0].enTitle:row.indexBlockDomainList[0].title}</p></c:if>
                    <div><img src="${ImageModel.toFirst(row.indexBlockDomainList[0].image).file}" alt=""></div>
                </a>
                <a href="${row.indexBlockDomainList[1].link}" style="padding-left: 5px">
                    <c:if test="${not empty row.indexBlockDomainList[1].title}"><p class="guide-link-before">${web:selectLanguage()=='en_US'?row.indexBlockDomainList[1].enTitle:row.indexBlockDomainList[1].title}</p></c:if>
                    <div><img src="${ImageModel.toFirst(row.indexBlockDomainList[1].image).file}" alt=""></div>
                </a>
            </div>
        </c:if>
    </c:forEach>

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
    <div class="col-2-w clearfix hide" style="margin-top: 2rem;">
        <a href="/goods/list?categoryId=16" style="padding-right: 5px">
            <p class="guide-link-before"><spring:message code="womenClothing"/></p>
            <div><img src="${ctx}/static/images/Banner-5.jpg" alt=""></div>
        </a>
        <a href="/goods/list?categoryId=26" style="padding-left: 5px">
            <p class="guide-link-before"><spring:message code="menClothing"/></p>
            <div><img src="${ctx}/static/images/Banner-7.jpg" alt=""></div>
        </a>
    </div>
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


<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<div class="dx-login dx-shopping">
    <div class="dx-title clearfix" style="background-color: #666">
        <div class="member"><span><img src="/static/images/icon-member.png" alt=""></span> <spring:message code="vip"/></div>
        <a onclick="history.go(-1)" class="icon iconfont" type="button">&#xe67d;</a>
    </div>

    <form class="j_ajaxForm" method="post" action="/passport/login" data-next="${ctx}/home/index">
        <div class="dx-form">
            <h3 class="title"><spring:message code="login"/></h3>
            <div class="tips"><spring:message code="login.tips"/></div>
            <div class="form-item">
                <input type="email" placeholder='<spring:message code="login.holderAccount"/>' name="userName"
                       id="userName"
                       onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="login.holderAccount"/>'"
                       data-rule="电子邮箱:required;email">
            </div>
            <div class="form-item">
                <input type="password" placeholder='<spring:message code="login.holderPassword"/>' name="password"
                       id="userPwd" onfocus="this.placeholder=''"
                       onblur="this.placeholder='<spring:message code="login.holderPassword"/>'"
                       data-rule="密码:required;password">
            </div>
            <div class="form-item text-center"><a href="${ctx}/passport/toForget"><spring:message code="login.forgotPassword"/>？</a>
            </div>

            <div class="form-item button">
            <button type="submit" class="btn btn-default"><spring:message code="login"/></button>
            </div>
            <div class="form-item text-center"><spring:message code="login.noneAccount"/>？ <a
                    href="${ctx}/passport/toRegister"><spring:message code="register"/></a></div>
        </div>
    </form>

<c:if test="${ not empty skuList}">
    <div class="dx-commodity">
    <div class="maybeLike clearfix" style="border-top: 2px solid #cccccc">
    <div class="title" style="margin:auto;margin-top: 1rem;border-bottom: 2px solid #cccccc;width: 80%">心愿单</div>
    <c:forEach var="row" items="${skuList}" >
        <a href="/goods/details/${row.goodsItem.id}" style="width: 50%;">
            <div class="left">
                <div class="pic" style="height: 100px">
                    <img style="width:80px;height: 100px;" src="${ImageModel.toFirst(row.goodsItem.thumb).file}" alt="">
                </div>
                <div class="name" style="text-align: center">${row.goods.name}</div>
                <div class="price">${row.goodsItem.price}</div>
                <ul class="color clearfix">
                        <li style="background: ${row.goodsItem.colorValue}"></li>
                </ul>
            </div>
        </a>
    </c:forEach>
    </div>
    </div>
</c:if>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        var backUrl = document.referrer;
        console.log(backUrl);
        if(backUrl.indexOf("toLogin")>0 || backUrl.indexOf("toRegister")>0){
            $(".j_ajaxForm").attr("data-next","${ctx}/u/account/index");
        }else{
            $(".j_ajaxForm").attr("data-next",backUrl);
        }
    });
</script>
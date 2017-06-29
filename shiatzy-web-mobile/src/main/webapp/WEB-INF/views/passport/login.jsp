<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>
<style>
    .price{
        display: inline;
        margin-left: 1rem;
    }
</style>
<div class="dx-login dx-shopping">
    <div class="dx-title clearfix" style="background-color: #555">
        <div class="member"><span><svg><use xlink:href="#account"></use></svg></span> <spring:message code="vip"/></div>
        <a onclick="history.go(-1)" class="icon iconfont" type="button">&#xe67d;</a>
    </div>

    <form class="j_ajaxForm"  data-next="${ctx}/home/index">
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
            <button type="button"  class="btn btn-default login"><spring:message code="login"/></button>
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
                <div class="price do-pro-price ${not empty row.goods.disPrice?'xzc-price':''}" data-value="${row.goodsItem.price}"></div>
                <c:if test="${not empty row.goods.disPrice}">
                <div class="price do-pro-price xzc-dis-price" data-value="${row.goods.disPrice}"></div>
                </c:if>
                <br/>
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

        setPrice();

        $(".top-right-nav").find("li:eq(2)").addClass("active");
        var backUrl = document.referrer;
        var ctx = '<%=request.getServerName()%>';
        console.log("backUrl:"+backUrl+" ctx:"+ctx);
        var dataNext = '';
        if(backUrl.indexOf("toLogin")>-1 || backUrl.indexOf("toRegister")>-1 ||backUrl.indexOf("toSetNewPassword")>-1
            ||backUrl.indexOf("activeEmail")>-1||(ctx!=''&& backUrl.indexOf(ctx)<0 )
        ){
            $(".j_ajaxForm").attr("data-next","${ctx}/u/account/index");
            dataNext = "${ctx}/u/account/index";
        }else{
            $(".j_ajaxForm").attr("data-next",backUrl);
            dataNext = backUrl;
        }
        console.log("dataNext:"+dataNext);
        $(".login").click(function () {
            var data = $(".j_ajaxForm").serializeArray();
            $.post("/passport/login",data,function(data){
               if(data.code==200){
                   console.log("dataNext："+dataNext);
                   location.href = dataNext;
               }else {
                   layer.msg(data.message);
               }
            });
        });
    });
</script>
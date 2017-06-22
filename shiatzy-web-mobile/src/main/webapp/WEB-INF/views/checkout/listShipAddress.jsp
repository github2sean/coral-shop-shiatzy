<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>
    .dx-shipping .btn .accounts-btn{font-size: 1.2rem;}
</style>
<div class="dx-shipping">
    <div class="dx-title"><spring:message code="delivery.address.set"/> <a href="#" class="goBack"><spring:message code="goBack"/></a></div>
    <div class="content">
        <a href="/checkout/createShipAddress" class="new-address"><spring:message code="delivery.address.add"/> +</a>
        <c:forEach var="row" items="${addressList}" varStatus="num">
            <div class="address addressDiv" >
                <div class="title"><spring:message code="delivery.address"/>&nbsp;${num.count} <span data-value="${row.id}" class="icon iconfont" style="display: none;background-color: inherit;border:1px solid #333">&#xe618;</span></div>
                <div class="text clearfix">
                    <div class="text-left">
                        <div class="postcode">${row.title}</div>
                        <div class="street">${row.address}</div>
                        <div class="tel"><spring:message code="delivery.address.phone"/>：${row.phone}</div>
                    </div>
                    <div class="text-right">
                        <div class="compile"><a href="/checkout/updateShipAddress?addressId=${row.id}" class="updateBtn" data-value="${row.id}"><spring:message code="delivery.address.update"/> ></a></div>
                        <div class="del"><a href="#" class="delBtn" data-value="${row.id}"><spring:message code="delivery.address.delete"/> -</a></div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>
    <div class="btn">
        <a href="/checkout/confirm" type="button" class="accounts-btn">&lt; <spring:message code="delivery.address.enter"/></a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){

        var way = '${way}';
        var backUrl;

        if(way=='index'){
            backUrl = "/u/account/index";
            $(".top-right-nav").find("li:eq(2)").addClass("active");
        }else{
            backUrl = '/checkout/confirm?page=/checkout/orderInfo';
            $(".addressDiv").find(".iconfont").show();
        }
        $(".goBack").attr("href",backUrl);

        $(".iconfont").click(function () {
            $(this).css("background-color","#333");
            $(this).parents(".addressDiv").siblings().find(".iconfont").css({"background-color":"white","border":"1px solid black"});
            var id = $(this).attr("data-value");
            $.post("/checkout/setAddress",{"addressId":id},function (data) {
                console.log(data);
                if(data.code==200){
                }
            });
        });

        $(".delBtn").click(function () {
            var $now = $(this);
            var id = $now.attr("data-value");
            console.log(id);
            layer.confirm('<spring:message code="delivery.address.isdelete"/>?',{
            },function () {
                $.post("/checkout/removeAddress",{"addressId":id},function (data) {
                    console.log(data);
                    if(data.code==200){
                       location.reload();
                    }
                });
            })

        });

    });
</script>

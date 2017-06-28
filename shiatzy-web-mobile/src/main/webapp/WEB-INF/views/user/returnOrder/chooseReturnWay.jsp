<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left"><spring:message code="consignee.selectBackWay"/></p>
    <a style="float: right;" href="/returnOrder/returnOrderConsigneeInfo">< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3><spring:message code="consignee.selectBackWay"/></h3>
        <p><a href="#"><img src="images/questionMark.png" alt=""><spring:message code="returnOrderInfo.returnInfo"/></a></p>
    </div>
    <p><spring:message code="order.details.no"/>：<span>${order.orderNo}</span></p>
    <p><spring:message code="order.details.time"/>：<span><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></p>
    <div class="order-finish">
        <div class="delivery">
            <%--<div class="delivery-message" style="position: relative;margin-top: 2px">
                <span>快递取件</span>
                <div style="position: absolute;top: 1.5rem;right: 0">
                </div>
                <p>联系人：</p>
                <p style="display: initial;">地址：</p>
                <a href="/returnOrder/listAddress">编辑 ></a>
            </div>
            <div class="drugstore">
                <p>退回门店</p>
                <a href="/returnOrder/listStoreCountry">编辑 ></a>
            </div>--%>
                <c:if test="${backWay==2}">
                    <div class="delivery-message" style="background-color: inherit;border: 2px solid #999999;border-bottom: none;">
                        <span><spring:message code="consignee.shippingbackWay"/></span>
                        <p class="shipName"></p>
                        <p style="display: initial;" class="shipAddress"></p>
                        <a href="javascript:void(0)" class="fillAddress"><spring:message code="delivery.address.update"/> ></a>
                    </div>
                    <div class="delivery-message" style="height: 120px;background-color: #cccccc;">
                        <p><spring:message code="consignee.storebackWay"/></p>
                        <p>${web:selectLanguage()=='en_US'?return_order.storeDomain.enTitle:return_order.storeDomain.name}</p>
                        <p>${web:selectLanguage()=='en_US'?return_order.storeDomain.enAddress:return_order.storeDomain.address}</p>
                        <a href="/returnOrder/listStoreCountry"><spring:message code="orderinfo.confirm.select"/> ></a>
                    </div>
                </c:if>
                <c:if test="${backWay==1}">
                    <div class="delivery-message" style="margin-top: 2px">
                        <span><spring:message code="consignee.shippingbackWay"/></span>
                        <p class="shipName">${sessionScope.shipName}</p>
                        <p style="display: initial;" class="shipAddress">${sessionScope.returnAddress}</p>
                        <a href="javascript:void(0)" class="fillAddress"><spring:message code="delivery.address.update"/> ></a>
                    </div>
                    <div class="drugstore">
                        <p><spring:message code="consignee.storebackWay"/></p>
                        <a href="/returnOrder/listStoreCountry"><spring:message code="orderinfo.confirm.select"/> ></a>
                    </div>
                </c:if>
                <c:if test="${backWay!=1 &&backWay!=2}">
                    <div class="delivery-message" style="margin-top: 2px">
                        <span><spring:message code="consignee.shippingbackWay"/></span>
                        <p class="shipName"></p>
                        <p style="display: initial;" class="shipAddress"></p>
                        <a href="javascript:void(0)" class="fillAddress"><spring:message code="delivery.address.update"/> ></a>
                    </div>
                    <div class="drugstore">
                        <p><spring:message code="consignee.storebackWay"/></p>
                        <a href="/returnOrder/listStoreCountry"><spring:message code="orderinfo.confirm.select"/> ></a>
                    </div>
                </c:if>

        </div>
    </div>
    <div class="return-btn">
        <a href="#" class="sureBtn">< <spring:message code="orderinfo.enter"/></a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;"><spring:message code="order.details.7day"/></span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;margin-left: -10px">> </span>
            <span style="float: left;" class="privacyNotice"><spring:message code="privacyPolicy"/></span>
        </a>
    </div>
</div>




<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    $(function () {

        console.log('backWay '+'${backWay}');
        $(".sureBtn").click(function () {
            if('${backWay}'==''&&${return_order.storeDomain==null}){
             layer.msg("<spring:message code="consignee.selectBackWay"/>");
             return false;
             }
            location.href="/returnOrder/returnOrderConsigneeInfo";
        });

        $(".fillAddress").on("click",function () {
            var html = '<div style="overflow: hidden;text-align: center"><div style="width: 100%"><div style="width: 50%;text-align: left"><spring:message code="consignee.chooseway.consignee"/>：</div><input type="text" id="shipName" style="width: 70%"/></div><div style="width: 100%;overflow: hidden"> <div style="width: 50%;text-align: left"><spring:message code="consignee.chooseway.consigneeAddress"/>：</div><input id="shipAddress" type="text" style="width: 70%"/></div><div style="text-align: center;margin-top: 5px"><button onclick="hideLayer()" class="btn btn-default fillBtn" style="margin: auto;width: 40%;border-radius: 0"><spring:message code="orderinfo.enter"/></button></div></div>';
            //页面层
            layer.open({
                type: 1,
                title:"<spring:message code="consignee.chooseway.renturnInfo"/>",
                area: ['400px',' 30%'], //宽高
                content: html
            });
        });

    });

    function hideLayer() {

        var name = $("#shipName").val();
        var shipAddress = $("#shipAddress").val();
        if(name==''||shipAddress==''){
            layer.msg('<spring:message code="consignee.chooseway.mustrenturnInfo"/>');
            return false;
        }else {
            $.post("/returnOrder/fillReturnAddress",{"address":shipAddress,"name":name},function (data) {

                if(data.code==200){
                    $(".shipName").text(name);
                    $(".shipAddress").text(shipAddress);
                    window.location.reload();
                }
                console.log(data);
            });
        }

        layer.close(layer.index);
    };

</script>
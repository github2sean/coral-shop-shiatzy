<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="首页"/>
    <jsp:param name="pageTitle" value="首页"/>
</jsp:include>

<div class="order">
    <p style="float: left"><spring:message code="consignee.selectBackWay"/></p>
    <a style="float: right;" href="/u/returnOrder/returnOrderConsigneeInfo">< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3><spring:message code="consignee.selectBackWay"/></h3>
        <p><a href="#" class="returnOrchange"><img src="${ctx}/static/images/questionMark.png" alt=""><spring:message code="returnOrderInfo.returnInfo"/></a></p>
    </div>
    <p><spring:message code="order.details.no"/>：<span>${order.orderNo}</span></p>
    <p><spring:message code="order.details.time"/>：<span><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></span></p>
    <div class="order-finish">
        <div class="delivery">

                <c:if test="${backWay==2}">
                    <div id="back2store">
                    <div class="delivery-message" id="checkStore" style="background-color: inherit;border: 2px solid #999999;border-bottom: none;">
                        <span><spring:message code="consignee.shippingbackWay"/></span>
                        <p class="shipName"></p>
                        <p style="display: initial;" class="shipAddress"></p>
                        <a href="javascript:void(0)" class="fillAddress"><spring:message code="orderinfo.confirm.select"/> ></a>
                    </div>
                    <div class="delivery-message" style="height: 120px;background-color: #cccccc;">
                        <p><spring:message code="consignee.storebackWay"/></p>
                        <p>${web:selectLanguage()=='en_US'?return_order.storeDomain.enTitle:return_order.storeDomain.name}</p>
                        <p>${web:selectLanguage()=='en_US'?return_order.storeDomain.enAddress:return_order.storeDomain.address}</p>
                        <a href="/u/returnOrder/listStoreCountry"><spring:message code="orderinfo.confirm.select"/> ></a>
                    </div>
                    </div>
                </c:if>
                    <div class="${backWay==2?'hide':''}" id="back2express">
                    <div class="delivery-message" style="margin-top: 2px">
                        <span><spring:message code="consignee.shippingbackWay"/></span>
                        <p class="shipName">${web:t('电商客服','Shop.shiatzychen.com')}</p>
                        <p style="display: initial;" class="shipAddress">${web:t('闵行区春申路1929号','No.1929,Chunshen Road,Minhang District')}</p>
                        <p>${web:t('上海，200237','Shanghai，200237')}</p>
                        <p>${web:t('中国','CHINA')}</p>
                        <p>+86 21 64109988</p>
                        <a href="javascript:void(0)" class="fillAddress"><spring:message code="orderinfo.confirm.select"/> ></a>
                    </div>
                    <div class="drugstore">
                        <p><spring:message code="consignee.storebackWay"/></p>
                        <a href="/u/returnOrder/listStoreCountry"><spring:message code="orderinfo.confirm.select"/> ></a>
                    </div>
                    </div>
        </div>
    </div>
    <div class="return-btn">
        <a href="#" class="sureBtn btn-default">< <spring:message code="orderinfo.enter"/></a>
    </div>
    <div class="privacy">
        <a href="#" class="returnOrchange">
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
        if(${backWay!=2}){
        defaultBackWay();
        }
        $("#checkStore").click(function () {
            defaultBackWay();
            $('#back2store').addClass('hide');
            $('#back2express').removeClass('hide');
        });

        console.log('backWay '+'${backWay}');
        $(".sureBtn").click(function () {
            location.href="/u/returnOrder/returnOrderConsigneeInfo";
        });

       /* $(".fillAddress").on("click",function () {
            var html = '<div style="overflow: hidden;text-align: center"><div style="width: 100%"><div style="width: 50%;text-align: left"><spring:message code="consignee.chooseway.consignee"/>：</div><input type="text" id="shipName" style="width: 70%"/></div><div style="width: 100%;overflow: hidden"> <div style="width: 50%;text-align: left"><spring:message code="consignee.chooseway.consigneeAddress"/>：</div><input id="shipAddress" type="text" style="width: 70%"/></div><div style="text-align: center;margin-top: 5px"><button onclick="hideLayer()" class="btn btn-default fillBtn" style="margin: auto;width: 40%;border-radius: 0"><spring:message code="orderinfo.enter"/></button></div></div>';
            //页面层
            layer.open({
                type: 1,
                title:"<spring:message code="consignee.chooseway.renturnInfo"/>",
                area: ['400px',' 30%'], //宽高
                content: html
            });
        });*/
    });

    function defaultBackWay() {
        var name = $("#shipName").val();
        var shipAddress = $("#shipAddress").val();
        $.post("/u/returnOrder/fillReturnAddress",{"name":'${web:t('电商客服','Shop.shiatzychen.com')}',"address":'${web:t('闵行区春申路1929号','No.1929,Chunshen Road,Minhang District')}'},function (data) {
                if(data.code==200){
                    $(".shipName").text(name);
                    $(".shipAddress").text(shipAddress);
                }
                console.log(data);
        });
    };

</script>
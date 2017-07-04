<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left"><spring:message code="returnOrderInfo.return"/></p>
    <a style="float: right;" href="/order/details?orderId=${order.id}">< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3><spring:message code="returnOrderInfo.chooseReason"/></h3>
        <p><a href="#" class="returnOrchange"><img src="${ctx}/static/images/questionMark.png" alt=""><spring:message
                code="returnOrderInfo.returnInfo"/></a></p>
    </div>
    <p><spring:message code="order.details.no"/>：<span>${order.orderNo}</span></p>
    <p><spring:message code="order.details.time"/>：<span><fmt:formatDate value="${order.orderTime}"
                                                                         pattern="yyyy-MM-dd hh:mm:ss" type="date"
                                                                             dateStyle="long"/></span></p>
    <form method="post" class="goodsForm" action="/returnOrder/chooseGoodsAndReason">
        <!--商品列表-->
        <div class="goods-list return-goods clearfix">
            <c:forEach var="item" items="${cartList}" varStatus="num">
            <div class="goods-item clearfix ">
                <label class="radiobox" style="float: left">
                    <input type="checkbox" name="returnList[${num.count-1}].orderItemId" value="${item.id}">
                    <i class="i-radiobox iconfont icon-duigou" style="float: left;"></i>
                </label>
                <div class="thumb">
                    <img src="${ImageModel.toFirst(item.goodsItemDomain.thumb).file}" alt="">
                </div>
                <div class="goods-info">
                    <div class="name">${item.goodsName}&nbsp;&nbsp;&nbsp;&nbsp;</div>
                    <p class="code"><spring:message code="shoppingCart.no"/>：${item.goodsCode}</p>

                        <p style="float:left;margin-right: 3.0918rem;">${web:selectLanguage()=='en_US'?item.goodsItemDomain.enName:item.goodsItemDomain.name}</p>
                        <p><spring:message code="shoppingCart.size"/>
                            ：${web:selectLanguage()=='en_US'?item.sizeDomain.enName:item.sizeDomain.name}</p>

                    <p class=""><spring:message code="shoppingCart.number"/>：${item.num} &nbsp;</p>
                    <p class="${not empty item.goodsDisPrice?'xzc-price':''}" ><spring:message code="shoppingCart.unitPrice"/>：<font class="coinSymbol">
                        <c:choose>
                            <c:when test="${order.currentCode=='CNY'}">
                                &nbsp;<spring:message code="coin.ZH"/>
                            </c:when>
                            <c:when test="${order.currentCode=='USD'}">
                                &nbsp;<spring:message code="coin.USA"/>
                            </c:when>
                            <c:when test="${order.currentCode=='EUR'}">
                                &nbsp;<spring:message code="coin.EU"/>
                            </c:when>
                        </c:choose>
                    </font>&nbsp;<fmt:formatNumber value="${item.goodsPrice}" pattern="#,###"/></p>
                    <c:if test="${not empty item.goodsDisPrice}">
                        <p class="xzc-dis-price"><spring:message code="shoppingCart.disPrice"/>：<font class="coinSymbol">
                            <c:choose>
                                <c:when test="${order.currentCode=='CNY'}">
                                    &nbsp;<spring:message code="coin.ZH"/>
                                </c:when>
                                <c:when test="${order.currentCode=='USD'}">
                                    &nbsp;<spring:message code="coin.USA"/>
                                </c:when>
                                <c:when test="${order.currentCode=='EUR'}">
                                    &nbsp;<spring:message code="coin.EU"/>
                                </c:when>
                            </c:choose>
                        </font>&nbsp;<fmt:formatNumber value="${item.goodsDisPrice}" pattern="#,###"/></p>
                    </c:if>
                </div>

            </div>
            <div class="return-list clearfix" style="display: none">
                <h4 class="title" style=""><spring:message code="returnOrderInfo.selectTips"/></h4>

                    <%--服务--%>
                <ul>
                    <li><spring:message code="returnOrderInfo.service"/></li>
                    <li>
                        <div>
                            <input type="hidden" name="skuId" value="${item.skuId}">
                            <input type="hidden" name="orderId" value="${item.skuId}">
                            <span class="mr-2"><label class="radiobox">
                        <input type="hidden" value="服务" name="returnList[${num.count-1}].type1.name">
                        <input type="checkbox" name="returnList[${num.count-1}].type1.reason1" value="错误商品"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.service.erroGoods"/></label></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox"><input type="checkbox"
                                                                              name="returnList[${num.count-1}].type1.reason2"
                                                                              value="货运过长"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message code="returnOrderInfo.service.longDelivery"/></label></span>
                        </div>
                    </li>
                </ul>
                <%--品质--%>
                <ul>
                    <li><spring:message code="returnOrderInfo.quality"/></li>
                    <li>
                        <div>
                    <span class="mr-2"><label class="radiobox">
                        <input type="hidden" value="品质" name="returnList[${num.count-1}].type2.name">
                        <input type="checkbox" name="returnList[${num.count-1}].type2.reason1" value="瑕紙品"><i
                            class="i-radiobox iconfont icon-duigou"></i><spring:message
                            code="returnOrderInfo.quality.defectiveGoods"/></label></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox"><input type="checkbox"
                                                                              name="returnList[${num.count-1}].type2.reason2"
                                                                              value="与图片不符"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.quality.picErro"/></label></span>
                        </div>
                    </li>
                </ul>
                    <%--尺寸--%>
                <ul>
                    <li><spring:message code="shoppingCart.size"/></li>
                    <li>
                        <div>
                        <span class="mr-2">
                            <label class="radiobox">
                        <input type="hidden" value="尺寸" name="returnList[${num.count-1}].type4.name">
                        <input type="checkbox" name="returnList[${num.count-1}].type4.reason1" value="太大">
                            <i class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.size.toobig"/></label>
                        </span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox">
                                <input type="checkbox" name="returnList[${num.count-1}].type4.reason2" value="太小"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.size.toosmall"/></label></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox"><input type="checkbox"
                                                                              name="returnList[${num.count-1}].type4.reason3"
                                                                              value="太长"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message code="returnOrderInfo.size.toolong"/></label></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox"><input type="checkbox"
                                                                              name="returnList[${num.count-1}].type4.reason4"
                                                                              value="太短"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.size.tooshort"/></label></span>
                        </div>
                    </li>
                </ul>
                    <%--其他--%>
                <ul>
                    <li><spring:message code="returnOrderInfo.other"/></li>
                    <li>
                        <div>
                    <span class="mr-2"><label class="radiobox">
                        <input type="hidden" value="其它" name="returnList[${num.count-1}].type3.name">
                        <input type="checkbox" name="returnList[${num.count-1}].type3.reason1" value="色差"><i
                            class="i-radiobox iconfont icon-duigou"></i><spring:message
                            code="returnOrderInfo.other.colorErro"/></label></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox"><input type="checkbox"
                                                                              name="returnList[${num.count-1}].type3.reason2"
                                                                              value="面料"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.other.skin"/></label></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox"><input type="checkbox"
                                                                              name="returnList[${num.count-1}].type3.reason3"
                                                                              value="风格不合适"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.other.styleErro"/></label></span>
                        </div>
                    </li>
                    <li>
                        <div>
                            <span class="mr-2"><label class="radiobox"><input type="checkbox"
                                                                              name="returnList[${num.count-1}].type3.reason4"
                                                                              value="改变心意"><i
                                    class="i-radiobox iconfont icon-duigou"></i><spring:message
                                    code="returnOrderInfo.other.changeHeart"/></label></span>
                        </div>
                    </li>
                </ul>
            </div>
            </c:forEach>
        </div>
    </form>
    <%--<div style="padding-left: 4rem;" class="return-commodity clearfix">
        <img style="float: left;" src="images/goods-pic01.jpg" alt="">
        <div style="margin-top: 3.5rem;margin-left: 0.7rem;">
            <p>玉镯提包系列黑色刺绣托特包<span style="margin-left: 6rem;">v</span></p>
            <span>产品编号 1B1184 Z</span>
        </div>
    </div>--%>
    <div class="return-btn">
        <a href="#" class="submitBtn btn-default">< <spring:message code="orderinfo.next"/></a>
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
        $(".reasonShow").click(function () {
            //$(this).parents(".return-commodity").siblings(".return-list").slideToggle(300);
        });
        //$(".return-list:first").show();

        if ($('input[type=checkbox]').is(':checked')) {
            // do something
        }

        $(".goods-item ").find(".i-radiobox").click(function () {
            $(this).parents(".goods-item").next(".return-list").slideToggle(300);
        });

        $(".submitBtn").click(function () {

            var data = $(".goodsForm").serializeArray();
            $.post("/returnOrder/chooseGoodsAndReason", data, function (data) {
                if (data.code == 200) {
                    location.href = "/returnOrder/returnOrderConsigneeInfo?page=/returnOrder/initReturnOrder&orderId=${order.id}";
                } else {
                    layer.msg(data.message);
                }
            });
        });


    });
</script>

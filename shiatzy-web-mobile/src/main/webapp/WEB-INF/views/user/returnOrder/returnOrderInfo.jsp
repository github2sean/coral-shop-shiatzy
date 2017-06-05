<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left">退货</p>
    <a style="float: right;" href="/order/details?orderId=${order.id}">< <spring:message code="goBack"/></a>
</div>
<div class="verify-message">
    <div class="return-way clearfix">
        <h3>选择退货商品和理由</h3>
        <p><a href="#"><img src="images/questionMark.png" alt="">退货说明</a></p>
    </div>
    <p>订单编号：<span>${order.orderNo}</span></p>
    <p>订单日期：<span><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd hh:mm:ss" type="date" dateStyle="long" /></span></p>
    <form method="post" class="goodsForm" action="/returnOrder/chooseGoodsAndReason">
    <c:forEach var="row" items="${cartList}" varStatus="num">
    <div class="return-commodity clearfix">
        <div style="border-bottom: 2px #cccccc solid">
            <span class="mr-2">
                <label class="radiobox" style="float: left;width: 30%">
                    <input type="checkbox" name="returnList[${num.count-1}].orderItemId" value="${row.id}">
                    <i class="i-radiobox" style="float: left"></i>
                    <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="width: 80px;height: 100px;float: left">
                </label>
                <div class="verify-main" style="float: right;width: 70%;border-bottom: none">
                    <div class="img-message">
                        <h3>${row.goodsName}&nbsp;&nbsp;&nbsp;&nbsp;<span class="reasonShow">v</span></h3>
                        <h6>${row.goodsCode}</h6>
                        <div style="display: inline-block;" class="size">
                            <p style="float:left;margin-right: 3.0918rem;">${row.goodsItemDomain.name}</p>
                            <p>${JSONObject.fromObject(row.skuSpecifications).getString("size")}号</p>
                        </div>
                        <p>数量：${row.num}</p>
                        <p>单价　¥ ${row.goodsPrice}</p>
                    </div>
                </div>
            </span>

        </div>
    </div>
   <div class="return-list clearfix" style="display: none">
        <p>请勾选退货理由（可复选）</p>
        <ul>
            <li>尺寸</li>
            <li>
                <div>
                    <input type="hidden" name="skuId" value="${row.skuId}">
                    <input type="hidden" name="orderId" value="${row.skuId}">
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type1.reason1" value="太大"><i class="i-radiobox"></i>太大</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type1.reason2" value="太小"><i class="i-radiobox"></i>太小</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type1.reason3" value="太长"><i class="i-radiobox"></i>太长</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type1.reason4" value="太短"><i class="i-radiobox"></i>太短</label></span>
                </div>
            </li>
        </ul>
        <ul>
            <li>尺寸</li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type2.reason1" value="太大"><i class="i-radiobox"></i>太大</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type2.reason2" value="太小"><i class="i-radiobox"></i>太小</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type2.reason3" value="太长"><i class="i-radiobox"></i>太长</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="returnList[${num.count-1}].type2.reason4" value="太短"><i class="i-radiobox"></i>太短</label></span>
                </div>
            </li>
        </ul>
        <ul>
            <li>尺寸</li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="type3.reason1" value="太大"><i class="i-radiobox"></i>太大</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="type3.reason2" value="太小"><i class="i-radiobox"></i>太小</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="type3.reason3" value="太长"><i class="i-radiobox"></i>太长</label></span>
                </div>
            </li>
            <li>
                <div>
                    <span class="mr-2"><label class="radiobox"><input type="checkbox" name="type3.reason4" value="太短"><i class="i-radiobox"></i>太短</label></span>
                </div>
            </li>
        </ul>
    </div>
    </c:forEach>
    </form>
    <%--<div style="padding-left: 4rem;" class="return-commodity clearfix">
        <img style="float: left;" src="images/goods-pic01.jpg" alt="">
        <div style="margin-top: 3.5rem;margin-left: 0.7rem;">
            <p>玉镯提包系列黑色刺绣托特包<span style="margin-left: 6rem;">v</span></p>
            <span>产品编号 1B1184 Z</span>
        </div>
    </div>--%>
    <div class="return-btn">
        <a href="#" class="submitBtn">< 確認</a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">收到商品后7天内享有轻松退货政策</span>
        </a>
    </div>
    <div class="privacy">
        <a href="#">
            <span style="float:left;">> </span>
            <span style="float: left;">隐私权政策</span>
        </a>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){
        $(".reasonShow").click(function () {
           $(this).parents(".return-commodity").siblings(".return-list").slideToggle(300);
        });
        $(".return-list:first").show();
        $(".submitBtn").click(function () {
            $(this).css("color","#333");
            var data = $(".goodsForm").serializeArray();
            $.post("/returnOrder/chooseGoodsAndReason",data,function (data) {
               if(data.code==200){
                   location.href = "/returnOrder/returnOrderConsigneeInfo?page=/returnOrder/initReturnOrder&orderId=${order.id}";
               }else {
                   layer.msg(data.message);
               }
            });
        });



    });
</script>

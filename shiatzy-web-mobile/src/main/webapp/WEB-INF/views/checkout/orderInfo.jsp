<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-accounts">
    <div class="dx-title"><spring:message code="orderinfo.checkoutTitle"/> <a href="/cart/list"><spring:message code="goBack"/></a></div>
    <div class="content">
        <c:forEach var="row" items="${cartList}" varStatus="num">

        <div class="dx-GoodsDetails goodsDiv">
            <div class="goods clearfix">
                <div class="goods-left">
                    <div class="pic" ><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt=""></div>
                </div>
                <div class="goods-right">
                    <div class="name">${web:selectLanguage()=='en_US'?row.goodsEnName:row.goodsName}</div>
                    <div class="number">${row.goodsCode}</div>
                    <div class="color" >${web:selectLanguage()=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}<span >${web:selectLanguage()=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}</span></div>
                    <div class="quantity" data-value="${row.num}"><spring:message code="shoppingCart.number"/> : <span>${row.num}</span></div>
                    <div class="price ${not empty row.goodsDisPrice?'xzc-price':''}" data-value="${row.goodsPrice}"><spring:message code="shoppingCart.unitPrice"/>&nbsp;
                        <font class="coinSymbol ">
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
                       </font>&nbsp;<span class="${empty row.goodsDisPrice?'true-price':''}"><fmt:formatNumber value="${row.goodsPrice}" pattern="#,###"/></span></div>
                    <c:if test="${not empty row.goodsDisPrice}">
                        <div class="price xzc-dis-price" data-value="${row.goodsDisPrice}"><spring:message code="shoppingCart.disPrice"/>&nbsp;<font class="coinSymbol">
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
                        </font>&nbsp;<span class="true-price"><fmt:formatNumber value="${row.goodsDisPrice}" pattern="#,###"/></span></div>
                    </c:if>
                </div>


                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_appointment" data-value="${row.id}"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect" data-value="${row.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
                    <li><a href="javascript:;" class="deleteBtn" data-value="${row.id}"><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            <div class="alter j_alter"  data-value="${num.count-1}"><a href="javascript:;"><spring:message code="orderinfo.update"/></a></div>
        </div>
            <div class="alter-popup alter-popup${num.count-1}">
                <div class="dx-goods clearfix">
                    <div class="goods-pic">
                        <span></span><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="width: 100px;">
                    </div>
                    <div class="goods-details">
                        <div class="name">${web:selectLanguage()=='en_US'?row.goodsEnName:row.goodsName}</div>
                        <div class="number checkedNo"  data-value="${row.goodsCode}"><spring:message code="shoppingCart.no"/>&nbsp;${row.goodsCode}</div>
                        <div class="color">
                            <div class="title">${web:selectLanguage()=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}(<spring:message code="goods.detail.thereAre"/>&nbsp;${row.goodsDomain.goodsItemList.size()-1}&nbsp;<spring:message code="goods.detail.colors"/>)</div>
                            <ul>
                                <c:forEach var="colorRow" items="${row.goodsDomain.goodsItemList}" varStatus="status">
                                    <li data-id="${colorRow.id}" class=" <c:if test="${colorRow.id==row.goodsItemDomain.id}">active</c:if> "><a href="#">${web:selectLanguage()=='en_US'?colorRow.enName:colorRow.name}</a></li>
                                </c:forEach>
                                    <%--<li class="active"><a href="">紅色</a></li>
                                    <li><a href="">藍色</a></li>
                                    <li><a href="">桃色</a></li>--%>
                            </ul>
                        </div>
                        <div class="size">
                            <div class="title"><spring:message code="shoppingCart.size"/></div>
                            <ul>
                                <c:forEach var="sizeRow" items="${row.sizeDomins}"  varStatus="line">
                                    <c:set var="sizeQuantity" value="${row.goodsDomain.goodsItemList[line.count-1].quantity}"></c:set>
                                    <li class="<c:if test="${sizeRow.id == row.sizeDomain.id}">active</c:if> "  data-value="${sizeQuantity}" data-id="${sizeRow.id}">
                                        <a href="#">${sizeRow.name} <c:if test="${row.goodsDomain.goodsItemList[num.count-1].quantity<=0}"><span>(<spring:message code="sellout"/>)</span></c:if></a></li>
                                </c:forEach>
                                    <%--<li><a href="">S</a> <span></span></li>
                                    <li class="active"><a href="">M</a> <span></span></li>
                                    <li><a href="">L</a> <span>(已售完)</span></li>--%>
                            </ul>
                        </div>
                        <div class="quantity" data-value="${row.goodsDomain.goodsItemList[num.count-1].quantity}"><spring:message code="shoppingCart.number"/> <a href="javascript:;" class="minus">-</a> <input class="quantitys" type="text" value="1"> <a href="javascript:;" class="add">+</a></div>
                    </div>
                </div>
                <button type="button" class="btn j_x_close" data-value="${row.id}"><spring:message code="orderinfo.enter"/></button>
            </div>
        </c:forEach>
        <div class="privilege">
            <div class="title"><spring:message code="orderinfo.discountInput"/> <a href="javascript:;" class="icon iconfont j_alter2 ">&#xe77d;</a></div>
            <form action="" class="clearfix">
                <input type="text" style="width: 69%" placeholder="<spring:message code="orderinfo.pleaseInputCode"/>" class="text couponCode"><button type="button" class="btn couponBtn"><spring:message code="orderinfo.enter"/></button><button type="button" class="btn cancelCouponBtn hide"><spring:message code="orderinfo.cancleCoupon"/></button>
            </form>
        </div>
        <div class="showInfo" style="text-align: center;color: red"></div>
    </div>
    <div class="total">
        <div class="title"><spring:message code="orderinfo.checkout"/></div>
        <div class="wrap">
            <div class="subtotal"><spring:message code="orderinfo.subtotal"/> <span>
                <font class="coinSymbol">
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
            </font>&nbsp;<span id="subtotal" class="" data-value="${order.goodsTotal}"><fmt:formatNumber value="${order.goodsTotal}" pattern="#,###"/></span></span>
            </div>
            <div class="discount" style="color: red;display: none"><spring:message code="orderinfo.discount"/> <span><font class="coinSymbol">
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
            </font>&nbsp;<span id="discount" class="">0</span></span></div>

            <div class="memDiscount" style="${empty order.memberDiscount||0==order.memberDiscount?'display:none;':''}">ART CLUB 会员优惠<span><font class="coinSymbol">
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
            </font>&nbsp;<span id="memDiscount" class=""><fmt:formatNumber value="${order.memberDiscount}" pattern="#,###"/></span></span></div>

            <div class="express"><spring:message code="orderinfo.freight"/> <span><font class="coinSymbol">
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
            </font>&nbsp;<span id="express" class=""><fmt:formatNumber value="${order.shipFee==null?0:order.shipFee}" pattern="#,###"/></span></span> </div>
            <div class="predict"><spring:message code="orderinfo.preTotal"/><span><font class="coinSymbol" style="margin-right: 0">
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

            </font>&nbsp;<span id="ordertotal" class=""><fmt:formatNumber value="${order.orderTotal}" pattern="#,###"/></span></span></div>
        </div>
    </div>
    <a href="/checkout/confirm?page=/checkout/orderInfo" type="button" class="accounts-btn"><spring:message code="orderinfo.checkoutTitle"/></a>
</div>

<!-------修改弹窗开始------->

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>
    function clsTotal() {
        var total = 0;
        $(".goods").find(".goods-right").each(function () {
            var num =  ($(this).find(".quantity").attr("data-value"))*1;
            var price  = rmoney($(this).find(".true-price").text())*1;
            total += num * price;
            console.log("num:"+num+" pri:"+price+" total:"+total);
        });
        var fee = rmoney($("#express").text())*1;
        var dis = rmoney($("#discount").text())*1;
        var memdis = rmoney($("#memDiscount").text())*1;
        var final_amt = total+fee-dis-memdis;
        console.log("fee:"+fee+" dis "+ dis+" "+final_amt);
        $("#ordertotal").html("&nbsp;"+fmoney(final_amt.toFixed(0),0));

    }

    $(function(){
        commonApp.init();
        //setPrice();
        //clsTotal();
        //修改弹窗
        $(".j_alter").on("click",function () {

            var num = $(this).attr("data-value");
            var str = ".alter-popup"+num;
            layer.open({
                type:1,
                title: false,
                content:$(str),
                shade:0.8,
                area:['29rem','30rem']
            });
        });

        //点击问号弹窗
        $(".j_alter2").on("click",function () {
            /*layer.open({
                type:1,
                title: false,
                content:$(".question-popup"),
                shade:0.8,
                closeBtn:1,
                area:['25rem','44rem']
            });*/
            //自定页
            layer.open({
                title:'<spring:message code="orderinfo.coupon"/>',
                type: 1,
                skin: 'layui-layer-demo', //样式类名
                closeBtn:1,
                shadeClose: true, //开启遮罩关闭
                content: '<br/>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 14px"><spring:message code="orderinfo.coupon.content"/></span>',
                area:['25rem','12rem']
            });
        });

        //关闭弹窗
        /*$(".j_x_close").on("click",function () {
            layer.close(layer.index);
        });*/

        //下拉菜单展开收起
        $(".j_toggle").on("click",function () {
            $(this).next().toggleClass("hide");
        });

        $(".j_toggle2>li").on("click",function () {
            $(this).find(".answer").toggleClass("hide");
        });

        $(".alter-popup").find(".size").find("li").click(function () {
           $(this).addClass("active").siblings().removeClass("active");
        });
        $(".alter-popup").find(".color").find("li").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
        });

        //修改颜色与尺寸
        $(".j_x_close").click(function () {
          var  goodsItemId ;
          var  sizeId;
            var $checked = $(this).siblings(".dx-goods").find(".size").find("li").each(function () {
                if($(this).hasClass("active")){
                    sizeId = $(this).attr("data-id");
                }
            });
            var $checked = $(this).siblings(".dx-goods").find(".color").find("li").each(function () {
                if($(this).hasClass("active")){
                    goodsItemId = $(this).attr("data-id");
                }
            });
            var checkedNo = $(".alter-popup").find(".checkedNo").attr("data-value");
            //查询当前选择的商品的库存
            var $now = $(this);
            var data = {"goodsNo":checkedNo,"colorId":goodsItemId,"sizeId":sizeId};
            console.log("data:"+data);

            if(goodsItemId==''||sizeId==''){
                layer.msg('<spring:message code="orderinfo.mustcolorandsize"/>');
                return false;
            }

            $.post("/checkout/getStockBySizeAndColor",data,function (data) {
                if(data.code==200 && data.data>0){
                    var num = $now.siblings(".dx-goods").find(".quantitys").val();
                    var sendData = {"goodsItemId":goodsItemId,"sizeId":sizeId,"cartId":$now.attr("data-value"),"num":num};
                    console.log(sendData);
                    if(goodsItemId!=''&& sizeId!=''&& num!=null){
                        //移除原来商品，把新的商品加入购物车，更新下当前的session
                        $.post("/checkout/updateGoodsInCheck",sendData,function (data) {
                            console.log(data);
                            if(data.code==200){
                                location.href = "${ctx}/checkout/initOrder";
                            }
                        });
                    }
                }else{
                    layer.msg('<spring:message code="goods.detail.thisIsSellOut"/>');
                }
            });

        });


        //点击数量增加减少
        $(".add").on("click",function () {

            var colorId;
            var sizeId;
            var checkedNo = $(".alter-popup").find(".checkedNo").attr("data-value");
            $(this).parents(".quantity").siblings(".size").find("li").each(function () {
                if($(this).hasClass("active")){
                    sizeId = $(this).attr("data-id");
                }
            });
            $(this).parents(".quantity").siblings(".color").find("li").each(function () {
                if($(this).hasClass("active")){
                    colorId = $(this).attr("data-id");
                }
            });
            if(colorId==''||sizeId==''){
                layer.msg('<spring:message code="orderinfo.mustcolorandsize"/>');
                return false;
            }

            //查询当前选择的商品的库存
            var $now = $(this);
            var data = {"goodsNo":checkedNo,"colorId":colorId,"sizeId":sizeId};
            console.log("data:"+data);
            $.post("/checkout/getStockBySizeAndColor",data,function (data) {
               if(data.code==200){
                   var num  = data.data;
                   console.log("num:"+num);
                   num = num==''?0:num;
                   var t = $now.parent().find(".quantitys");
                   var addNum =  parseInt(t.val())+1;
                   if(addNum>num && num>0){
                       layer.msg('<spring:message code="orderinfo.lessthanstock"/>');
                   }else if(addNum>num && num==0){
                       layer.msg('<spring:message code="goods.detail.thisIsSellOut"/>');
                   }
                   t.val(addNum>num?num:addNum);
                   $(".minus").removeAttr("disabled");
               }else{

               }
            });
        });

        $(".minus").on("click",function () {
            var t = $(this).parent().find(".quantitys");
            if (parseInt(t.val())>1){
                t.val(parseInt(t.val())-1);
            }else {
                $("#min").attr("disabled","disabled");
            }
        })

        //
        $(".couponBtn").click(function () {

           var couponCode = $(".couponCode").val();
            if(couponCode==''){
                $('.showInfo').text('<spring:message code="orderinfo.pleaseInputCode"/>');
                return false;
            }
            $('.showInfo').text("");
            $.post("/checkout/useCoupon",{"couponCode":couponCode},function (data) {
                if(data.code==200){
                    layer.msg('<spring:message code="orderinfo.coupon.success"/>');
                    $(".discount").show();
                    $("#discount").text(fmoney((data.data).toFixed(0),0));
                    clsTotal();
                }else{
                    $('.showInfo').text(data.message);
                }
                $(".couponBtn").addClass("hide").siblings(".cancelCouponBtn").removeClass("hide");
            });
        });

        $(".cancelCouponBtn").click(function () {

            $.post("/checkout/cancelUseCoupon",function (data) {
                if(data.code==200){
                    $(".cancelCouponBtn").addClass("hide").siblings(".couponCode").val("").siblings(".couponBtn").removeClass("hide");
                    $("#discount").text(0);
                    clsTotal();
                    $('.showInfo').text("");
                    $(".discount").css("display","none");
                }else{
                    $('.showInfo').text(data.message);
                }
            });
        });


        $(".deleteBtn").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            $.post("/cart/removeFromCart",{"shoppingCartItemId":id},function (data) {
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    setCartNum();
                    if(typeof (isNull)=="undefined"){
                        window.location.href = "/cart/list";
                    }
                }
            });
        });
        $(".j_collect").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            var data = {"shoppingCartItemId":id};
            $.post("/cart/cartToWish",data,function (data) {
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg('<spring:message code="success.towish"/>');
                    setCartNum();
                    if(typeof (isNull)=="undefined"){
                        window.location.href = "/cart/list";
                    }
                }
            });
        });
        $(".j_appointment").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            var data = {"shoppingCartItemId":id};
            $.post("/cart/cartToBoutique",data,function (data) {
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg('<spring:message code="success.toboutique"/>');
                    setCartNum();
                    if(typeof (isNull)=="undefined"){
                        window.location.href = "/cart/list";
                    }
                }
            });
        });


    });
</script>
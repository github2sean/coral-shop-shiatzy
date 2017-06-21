<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-shopping">
    <div class="dx-title clearfix">
        <div class="member"><span><svg><use xlink:href="#cart-nav"></use></svg></span><spring:message
                code="shoppingCart"/></div>
        <a onclick="history.go(-1)" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
<c:if test="${cartList.size()>0}">
    <div class="content">
        <div class="dx-GoodsDetails">
            <c:forEach var="row" items="${cartList}">
            <div class="goods clearfix goodsDiv">
                <div class="goods-left">
                    <div class="pic" style="overflow: hidden"><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt=""></div>
                </div>
                <div class="goods-right" style="word-break: break-all">
                    <div class="name">${ sessionScope.language=='en_US'?row.goodsEnName:row.goodsName}</div>
                    <div class="number"><spring:message code="shoppingCart.no"/> ${row.goodsCode}</div>
                    <div class="color" >${sessionScope.language=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}<span ><spring:message code="shoppingCart.size"/>:${sessionScope.language=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}</span></div>
                    <div class="quantity"><spring:message code="shoppingCart.number"/>:
                        <c:if test="${row.stock>0}"><a href="#" class="minus" data-value="${row.id}">-</a><input class="quantitys" type="text" value="${row.num}"><a href="#" class="add" data-num="${row.stock}" data-value="${row.id}">+</a></c:if>
                        <c:if test="${row.stock<1}"><a href="#" class="minus hasOut" data-value="${row.id}">-</a><input class="quantitys" type="text" value="0"><a href="#" class="add" data-num="${row.stock}" data-value="${row.id}">+</a>(已售罄)</c:if>
                    </div>
                    <div class="price"><spring:message code="shoppingCart.unitPrice"/>&nbsp;<span class="coinSymbol"></span>&nbsp;<span class="js_price do-pro-price" data-value="${row.goodsPrice}" data-rate="1">&nbsp;</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_appointment" data-value="${row.id}" <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if> > <svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect" data-value="${row.id}" <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if> ><svg><use xlink:href="#heart"></use></svg></a></li>
                    <li><a href="javascript:;"  class="deleteBtn" data-value="${row.id}" <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if> ><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            </c:forEach>

            <div class="total"><spring:message code="shoppingCart.sub"/> <span class="coinSymbol"></span><span id="js_total" style="margin-left: 2px"> &nbsp;</span></div>

        </div>
    </div>
    <div class="shopping-start">
        <a data-href="${sessionScope.shippingCountryId==null?'/home/listShippingCountry':'/checkout/initOrder'}" class="shopping checkout"><spring:message code="shoppingCart.checkout"/></a>
        <div class="explain">
            <ul>
                <li><a href="#" class="returnAndExchange"><spring:message code="shoppingCart.returnAndExchange"/><span>></span></a></li>
                <li><a href="#" class="deliveryTime"><spring:message code="shoppingCart.deliveryTime"/><span>></span></a></li>
                <li class="last hide"><a href="#"><spring:message code="shoppingCart.rule"/><span>></span></a></li>
            </ul>
        </div>
    </div>
</c:if>
    <c:if test="${cartList.size()==0}">
        <div class="content dx-wish dx-shopping">
            <div id="toggleDiv2">
                <a href="/home/index"> <div class="message"><p><spring:message code="shoppingCart.bagNull"/>&nbsp;(0)</p></div></a>
            </div>
        </div>
        <div class="explain">

        <div class="choose-store">
            <a href="/home/index" class="shopping"><spring:message code="shoppingCart.selectGoods"/></a>
        </div>
            <ul>
                <li><a href="/goods/list?categoryId=1"><spring:message code="shoppingCart.selectWoman"/><span>></span></a></li>
                <li><a href="/goods/list?categoryId=8"><spring:message code="shoppingCart.selectMan"/><span>></span></a></li>
            </ul>
        </div>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    function clsTotal() {
        var total = 0;
        $(".goods").find(".goods-right").each(function () {
           var num =  ($(this).find(".quantitys").val())*1;
           var price  = ($(this).find(".js_price").text())*1;
            total +=num * price;
            $("#js_total").html(total.toFixed(2));
        })
    }
    function setPrice() {
        $.post("/home/queryCurrentRate",function (data) {
            var coinSymbol = '￥';
            var rate = 1;
            if (data.code==200){
                var obj = data.data;
                var type = obj.rateType;
                console.log("type2"+type);
                if(obj.rateType == 1){
                    coinSymbol = '<spring:message code="coin.USA"/>';
                }else if(obj.rateType == 2){
                    coinSymbol = '<spring:message code="coin.EU"/>';
                }
                rate = obj.rate;
                console.log("rate"+rate);
            }
            $(".do-pro-price").each(function () {
                var oldPri = $(this).attr("data-value")*1;
                $(this).text(((oldPri/rate).toFixed(2))).attr("data-rate",rate);
            });
            $(".coinSymbol").text(coinSymbol);
            clsTotal();
        });
    }
    $(function(){

        $(".top-right-nav").find("li:first").addClass("active");
        setPrice();
        var isLogin = '${isGuest}'=='onLine'?true:false;
        //

        $(".checkout").on("click",function(){
           if($(".minus").hasClass("hasOut")){
               layer.msg('<spring:message code="shoppingCart.deleteOut"/>');
               return false;
           }
            var href = !isLogin?"${ctx}/passport/toLogin":$(".checkout").attr("data-href");
            location.href = href;
            //$(".checkout").attr("href",href);
        });



        //初始化购物车数量
        var cartNum = '${cartList.size()}'*1;
        $(".cart_num").text(cartNum);
        clsTotal();
        //点击数量增加减少
        $(".add").on("click",function () {
            var t = $(this).parent().find(".quantitys");
            var quantity = $(this).attr("data-num");
            var num = parseInt(t.val())+1>quantity?quantity:parseInt(t.val())+1;
            var id = $(this).attr("data-value");
            t.val(num);
            var data = {"shoppingCartItemId":id,"num":num};
            $.post("/cart/updateCart",data,function () {
            });

            $(".minus").removeAttr("disabled");
            clsTotal();
        });
        $(".minus").on("click",function () {
            var t = $(this).parent().find(".quantitys");
            var id = $(this).attr("data-value");
            if (parseInt(t.val())>1){
                var num = parseInt(t.val())-1;
                t.val(num);
                var data = {"shoppingCartItemId":id,"num":num};
                $.post("/cart/updateCart",data,function () {
                });
            }else {
               /* $.post("/cart/removeFromCart",{"id":id},function () {
                });*/
                $("#min").attr("disabled","disabled");
            }
            clsTotal();
        });

        console.log('${isGuest} '+isLogin)
        $(".deleteBtn").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            if(!isLogin){
                id = $(this).attr("data-formid");
                $.post("/cart/removeFromSessionCart",{"formId":id},function (data) {
                    console.log(data);
                    if(data.code==200){
                        $self.parents(".goodsDiv").remove();
                        var  isNull= $(".goodsDiv").attr("class");
                        setCartNum();
                        if(typeof (isNull)=="undefined"){
                            window.location.reload();
                        }else{
                            clsTotal();
                        }
                    }
                });
            }else{
                $.post("/cart/removeFromCart",{"shoppingCartItemId":id},function (data) {
                    console.log(data);
                    if(data.code==200){
                        $self.parents(".goodsDiv").remove();
                        var  isNull= $(".goodsDiv").attr("class");
                        setCartNum();
                        if(typeof (isNull)=="undefined"){
                            window.location.reload();
                        }else{
                            clsTotal();
                        }
                    }
                });
            }
        });

        $(".j_collect").on("click",function () {
            var $self = $(this);
            var id = isLogin?$(this).attr("data-value"):$(this).attr("data-formid");
            var data = {"shoppingCartItemId":id};
            if(!isLogin){
                $.post("/cart/changeFromSessionCartType",{"formId":id,"goalType":2},function (data) {
                    console.log(data);
                    if(data.code==200){
                        $self.parents(".goodsDiv").remove();
                        var  isNull= $(".goodsDiv").attr("class");
                        layer.msg('<spring:message code="success.towish"/>');
                        setCartNum();
                        if(typeof (isNull)=="undefined"){
                            window.location.reload();
                        }else{
                            clsTotal();
                        }
                    }
                });
            }else{
                $.post("/cart/cartToWish",data,function (data) {
                    console.log(data);
                    if(data.code==200){
                        $self.parents(".goodsDiv").remove();
                        var  isNull= $(".goodsDiv").attr("class");
                        layer.msg('<spring:message code="success.towish"/>');
                        setCartNum();
                        if(typeof (isNull)=="undefined"){
                            window.location.reload();
                        }else{
                            clsTotal();
                        }
                    }
                });
            }
        });
        $(".j_appointment").on("click",function () {
            setCartNum('sub');
            var $self = $(this);
            var id = isLogin?$(this).attr("data-value"):$(this).attr("data-formid");
            var data = isLogin?{"shoppingCartItemId":id}:{"formId":id,"goalType":3};
            var url = isLogin?"/cart/cartToBoutique":"/cart/changeFromSessionCartType";
            $.post(url,data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg('<spring:message code="success.toboutique"/>');
                    setCartNum();
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }else{
                        clsTotal();
                    }
                }
            });
        });


        //
        //iframe窗
        $(".returnAndExchange").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="shoppingCart.returnAndExchange"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['100%', '80%'],
                content: ['${ctx}/content/returnOrchange'],//iframe的url，no代表不显示滚动条
                shadeClose: true
            });
        });
        $(".deliveryTime").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="shoppingCart.deliveryTime"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['100%', '80%'],
                content: ['${ctx}/content/deliveryTime'],//iframe的url，no代表不显示滚动条
                shadeClose: true
            });
        });

    });
</script>

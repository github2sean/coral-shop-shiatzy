<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-accounts">
    <div class="dx-title">结账 <a href="/cart/list">回上页</a></div>
    <div class="content">
        <c:forEach var="row" items="${cartList}" varStatus="num">
        <div class="dx-GoodsDetails goodsDiv">
            <div class="goods clearfix">
                <div class="goods-left">
                    <div class="pic" style="overflow: hidden"><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="width: 100px;"></div>
                </div>
                <div class="goods-right">
                    <div class="name">${row.goodsName}</div>
                    <div class="number">${row.goodsCode}</div>
                    <div class="color" >${row.goodsItemDomain.name}<span >${JSONObject.fromObject(row.skuSpecifications).getString("size")}号</span></div>
                    <div class="quantity" data-value="${row.num}">数量: <span>${row.num}</span></div>
                    <div class="price" data-value="${row.goodsPrice}">单价&nbsp; &yen; <span>${row.goodsPrice}</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_appointment" data-value="${row.id}"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect" data-value="${row.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
                    <li><a href="javascript:;" class="deleteBtn" data-value="${row.id}"><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            <div class="alter j_alter"  data-value="${num.count-1}"><a href="javascript:;">修改</a></div>
        </div>
            <div class="alter-popup alter-popup${num.count-1}">
                <div class="dx-goods clearfix">
                    <div class="goods-pic">
                        <span></span><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="width: 100px;">
                    </div>
                    <div class="goods-details">
                        <div class="name">${row.goodsName}</div>
                        <div class="number">${row.goodsCode}</div>
                        <div class="color">
                            <div class="title">${row.goodsItemDomain.name}(还有款颜色)</div>
                            <ul>
                                <c:forEach var="row" items="${cartList[0].goodsDomain.goodsItemList}" varStatus="status">
                                    <li class=" <c:if test="${status.first}">active</c:if> "><a href="">${row.name}</a></li>
                                </c:forEach>
                                    <%--<li class="active"><a href="">紅色</a></li>
                                    <li><a href="">藍色</a></li>
                                    <li><a href="">桃色</a></li>--%>
                            </ul>
                        </div>
                        <div class="size">
                            <div class="title">选择尺寸</div>
                            <ul>
                                <c:forEach var="row" items="${cartList[0].sizeDomins}" >
                                    <li class="active"><a href="">${row.name} <c:if test=""></c:if><span>(已售完)</span></a></li>
                                </c:forEach>
                                    <%--<li><a href="">S</a> <span></span></li>
                                    <li class="active"><a href="">M</a> <span></span></li>
                                    <li><a href="">L</a> <span>(已售完)</span></li>--%>
                            </ul>
                        </div>
                        <div class="quantity">数量 <a href="javascript:;" class="minus">-</a> <input class="quantitys" type="text" value="1"> <a href="javascript:;" class="add">+</a></div>
                    </div>
                </div>
                <button type="button" class="btn j_x_close">確認</button>
            </div>
        </c:forEach>
        <div class="privilege">
            <div class="title">优惠码输入 <a href="javascript:;" class="icon iconfont j_alter2 ">&#xe77d;</a></div>
            <form action="" class="clearfix">
                <input type="text" placeholder="请输入优惠代码" class="text couponCode"><button type="button" class="btn couponBtn">确定</button><button type="button" class="btn cancelCouponBtn hide">移除代码</button>
            </form>
        </div>
        <div class="showInfo" style="text-align: center;color: red"></div>
    </div>
    <div class="total">
        <div class="title">结算</div>
        <div class="wrap">
            <div class="subtotal">小计 <span>&yen;<span id="subtotal">${order.goodsTotal}</span></span> </div>
            <div class="discount" style="color: red">优惠 <span>&yen;<span id="discount"> 0</span></span></div>
            <div class="express">快递 <span>&yen;<span id="express" data-value="${order.shipFee==null?0:order.shipFee}">${order.shipFee==null?0:order.shipFee}</span></span> </div>
            <div class="predict">预计订单总额 <span>&yen;<span id="ordertotal"> ${order.orderTotal}</span></span></div>
        </div>
    </div>
    <a href="/checkout/confirm?page=/checkout/orderInfo" type="button" class="accounts-btn">结账</a>
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
            var price  = ($(this).find(".price").attr("data-value"))*1;
            total +=num * price;
            $("#js_total").html("&yen; &nbsp;"+total.toFixed(2));

        });
        var fee = ($("#express").attr("data-value"))*1;
        var dis = ($("#discount").text())*1;
        var final_amt = total+fee-dis;
        console.log(fee+" dis "+ dis+" "+final_amt);
        $("#ordertotal").html("&nbsp;"+final_amt.toFixed(2));

    }

    $(function(){
        commonApp.init();
        clsTotal();
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
                title:'优惠券',
                type: 1,
                skin: 'layui-layer-demo', //样式类名
                closeBtn:1,
                shadeClose: true, //开启遮罩关闭
                content: '<br/>&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 14px">输入相应的“优惠代码/优惠券号”，点击“应用”，即刻享有相应优惠礼遇。请注意使用时间和相应条款。</span>',
                area:['25rem','12rem']
            });
        });

        //关闭弹窗
        $(".j_x_close").on("click",function () {
            layer.close(layer.index);
        });

        //下拉菜单展开收起
        $(".j_toggle").on("click",function () {
            $(this).next().toggleClass("hide");
        });

        $(".j_toggle2>li").on("click",function () {
            $(this).find(".answer").toggleClass("hide");
        });


        //点击数量增加减少
        $(".add").on("click",function () {
            var t = $(this).parent().find(".quantitys");
            t.val(parseInt(t.val())+1);
            $(".minus").removeAttr("disabled");
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
                $('.showInfo').text("请先输入优惠券码");
                return false;
            }
            $('.showInfo').text("");
            $.post("/checkout/useCoupon",{"couponCode":couponCode},function (data) {
                if(data.code==200){
                    layer.msg('使用优惠码成功');
                    $("#discount").text(data.data);
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
                    layer.msg("加入心愿单成功");
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
                    layer.msg("加入精品店成功");
                    setCartNum();
                    if(typeof (isNull)=="undefined"){
                        window.location.href = "/cart/list";
                    }
                }
            });
        });


    });
</script>
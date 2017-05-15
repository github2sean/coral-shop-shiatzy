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
        <c:forEach var="row" items="${cartList}">
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
            <div class="alter j_alter"><a href="javascript:;">修改</a></div>
        </div>
        </c:forEach>
        <div class="privilege">
            <div class="title">优惠码输入 <a href="javascript:;" class="icon iconfont j_alter2 ">&#xe77d;</a></div>
            <form action="" class="clearfix">
                <input type="text" placeholder="请输入优惠代码" class="text couponCode"><button type="button" class="btn couponBtn">确定</button>
            </form>
        </div>
        <div class="showInfo" style="text-align: center;color: red"></div>
    </div>
    <div class="total">
        <div class="title">结算</div>
        <div class="wrap">
            <div class="subtotal">小计 <span>&yen;<span id="subtotal">${order.goodsTotal}</span></span> </div>
            <div class="discount" style="color: red">优惠 <span>&yen;<span id="discount"> 0</span></span></div>
            <div class="express">快递 <span>&yen;<span id="express" data-value="50">50</span></span> </div>
            <div class="predict">预计订单总额 <span>&yen;<span id="ordertotal"> ${order.orderTotal}</span></span></div>
        </div>
    </div>
    <a href="/checkout/confirm?page=/checkout/orderInfo" type="button" class="accounts-btn">结账</a>
</div>

<!-------修改弹窗开始------->
<div class="alter-popup">
    <div class="dx-goods clearfix">
        <div class="goods-pic">
            <span></span><img src="images/goods-pic01.jpg" alt="">
        </div>
        <div class="goods-details">
            <div class="name">玉镯提包系列黑色刺绣托特包</div>
            <div class="number">产品编号 1B1184 Z</div>
            <div class="color">
                <div class="title">黑色(还有3款颜色)</div>
                <ul>
                    <li class="active"><a href="">紅色</a></li>
                    <li><a href="">藍色</a></li>
                    <li><a href="">桃色</a></li>
                </ul>
            </div>
            <div class="size">
                <div class="title">选择尺寸</div>
                <ul>
                    <li><a href="">S</a> <span></span></li>
                    <li class="active"><a href="">M</a> <span></span></li>
                    <li><a href="">L</a> <span>(已售完)</span></li>
                </ul>
            </div>
            <div class="quantity">数量 <a href="javascript:;" class="minus">-</a> <input class="quantitys" type="text" value="1"> <a href="javascript:;" class="add">+</a></div>
        </div>
    </div>
    <button type="button" class="btn j_x_close">確認</button>
</div>


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
            layer.open({
                type:1,
                title: false,
                content:$(".alter-popup"),
                shade:0.8,
                area:['29rem','30rem']
            });
        });

        //点击问号弹窗
        $(".j_alter2").on("click",function () {
            layer.open({
                type:1,
                title: false,
                content:$(".question-popup"),
                shade:0.8,
                area:['25rem','44rem']
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
            });
        });



        $(".deleteBtn").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            $.post("/cart/removeFromCart",{"shoppingCartItemId":id},function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    if(typeof (isNull)=="undefined"){
                        window.location.href = "/cart/list";
                    }
                }
            });
        });
        $(".j_collect").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            var data = {"shoppingCartItemId":id};
            $.post("/cart/cartToWish",data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("加入心愿单成功");
                    if(typeof (isNull)=="undefined"){
                        window.location.href = "/cart/list";
                    }
                }
            });
        });
        $(".j_appointment").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            var data = {"shoppingCartItemId":id};
            $.post("/cart/cartToBoutique",data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("加入精品店成功");
                    if(typeof (isNull)=="undefined"){
                        window.location.href = "/cart/list";
                    }
                }
            });
        });


    });
</script>
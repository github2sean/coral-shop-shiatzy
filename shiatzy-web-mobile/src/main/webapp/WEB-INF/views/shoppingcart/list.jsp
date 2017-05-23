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
        <div class="member"><span><svg><use xlink:href="#cart-nav"></use></svg></span>购物车</div>
        <a href="/home/index" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
<c:if test="${cartList.size()>0}">
    <div class="content">
        <div class="dx-GoodsDetails">
            <c:forEach var="row" items="${cartList}">
            <div class="goods clearfix goodsDiv">
                <div class="goods-left">
                    <div class="pic" style="overflow: hidden"><img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="width: 100px;"></div>
                </div>
                <div class="goods-right">
                    <div class="name">${row.goodsName}</div>
                    <div class="number">产品编号 ${row.goodsCode}</div>
                    <div class="color" >${row.goodsItemDomain.name}<span >${JSONObject.fromObject(row.skuSpecifications).getString("size")}号</span></div>
                    <div class="quantity">数量: <a href="#" class="minus" data-value="${row.id}">-</a><input class="quantitys" type="text" value="${row.num}"><a href="#" class="add" data-value="${row.id}">+</a></div>
                    <div class="price">单价&nbsp; &yen; <span class="js_price">${row.goodsPrice}</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_appointment" data-value="${row.id}"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect" data-value="${row.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
                    <li><a href="javascript:;"  class="deleteBtn" data-value="${row.id}"><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            </c:forEach>

            <div class="total">小计 <span id="js_total">&yen; &nbsp;</span></div>

        </div>
    </div>
    <div class="shopping-start">
        <a href="${sessionScope.shippingCountryId==null?'/home/listShippingCountry':'/checkout/initOrder'}" class="shopping">查看购物袋 / 结帐</a>
        <div class="dx-clause">
            <ul>
                <li><a href="#">购物说明</a></li>
                <li><a href="#">使用条款</a></li>
                <li class="last"><a href="#">配送规则</a></li>
            </ul>
        </div>
    </div>
</c:if>
    <c:if test="${cartList.size()==0}">
        <div class="content">
           <p>您的购物车目前是空的</p>
        </div>
        <div class="shopping-start">
            <a href="/home/index" class="shopping">开始购物</a>
            <div class="dx-clause">
                <ul>
                    <li><a href="#">选购女士</a></li>
                    <li><a href="#">选购男士</a></li>
                </ul>
            </div>
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
            $("#js_total").html("&yen; &nbsp;"+total.toFixed(2));
        })
    }
    $(function(){

        clsTotal();
        //点击数量增加减少
        $(".add").on("click",function () {
            var t = $(this).parent().find(".quantitys");
            var num = parseInt(t.val())+1;
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
                        window.location.reload();
                    }else{
                        clsTotal();
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
                        window.location.reload();
                    }else{
                        clsTotal();
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
                        window.location.reload();
                    }else{
                        clsTotal();
                    }
                }
            });
        });



    });
</script>

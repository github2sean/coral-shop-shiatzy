<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left">门市预约</p>
    <a style="float: right;" href="/u/account/index">X</a>
</div>

<c:if test="${empty preOderItemList}">
    <div class="content dx-wish dx-shopping">
        <div id="toggleDiv2">
          <a href="/home/index"> <div class=" content"><p>预约单（0）</p></div></a>
        </div>
    </div>
</c:if>


<c:forEach var="row" items="${preOderItemList}">
<div class="order-main clearfix">
    <div class="order-main-left goodsDiv">
        <p class="product-num">产品编号 ${row.leftItem.goodsCode}</p>
        <img src="${ImageModel.toFirst(row.leftItem.goodsItemDomain.thumb).file}" alt="" style="height: 120px;width: 100px;">
        <p class="product-name">${row.leftItem.goodsName}</p>
        <div class="color-size">
            <p>${row.leftItem.goodsItemDomain.name}</p>
            <p>${JSONObject.fromObject(row.leftItem.skuSpecifications).getString("size")}号</p>
        </div>
        <p class="price">单价　¥ ${row.leftItem.goodsPrice}</p>
        <ul class="do-list-icon">
            <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.leftItem.id}"><svg><use xlink:href="#bag"></use></svg></a></li>
            <li><a href="javascript:;" class="j_collect" data-value="${row.leftItem.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
            <li><a href="" class="deleteBtn" data-value="${row.leftItem.id}"><svg><use xlink:href="#close"></use></svg></a></li>
        </ul>
    </div>
    <c:if test="${not empty row.rightItem}">
    <div class="order-main-right goodsDiv">
        <p class="product-num">产品编号 ${row.rightItem.goodsCode}</p>
        <img src="${ImageModel.toFirst(row.rightItem.goodsItemDomain.thumb).file}" alt="" style="height: 120px;width: 100px;">
        <p class="product-name">${row.rightItem.goodsName}</p>
        <div class="color-size">
            <p>${row.rightItem.goodsItemDomain.name}</p>
            <p>${JSONObject.fromObject(row.rightItem.skuSpecifications).getString("size")}号</p>
        </div>
        <p class="price">单价　¥ ${row.rightItem.goodsPrice}</p>
        <ul class="do-list-icon">
            <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.rightItem.id}"><svg><use xlink:href="#bag"></use></svg></a></li>
            <li><a href="javascript:;" class="j_collect" data-value="${row.rightItem.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
            <li><a href="" class="deleteBtn" data-value="${row.rightItem.id}"><svg><use xlink:href="#close"></use></svg></a></li>
        </ul>
    </div>
    </c:if>
</div>
</c:forEach>
<div class="explain">

    <c:if test="${empty preOderItemList}">
        <div class="choose-store">
            <a href="/home/index">选择商品</a>
        </div>
    </c:if>
    <c:if test="${not empty preOderItemList}">
    <div class="choose-store">
        <a href="/reservation/initChoose">选择预约门市</a>
    </div>
    </c:if>
    <ul>
        <li><a href="#">购物说明<span>></span></a></li>
        <li><a href="#">使用条款<span>></span></a></li>
        <li><a href="#">配送规则<span>></span></a></li>
    </ul>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        //console.log('${goodsList}');



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
                    }
                }
            });
        });
        $(".j_collect").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            var data = {"shoppingCartItemId":id};
            $.post("/cart/boutiqueToWish",data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("加入心愿单成功");
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }
                }
            });
        });
        $(".j_bag").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            var data = {"shoppingCartItemId":id};
            $.post("/cart/boutiqueToCart",data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("加入购物车成功");
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }
                }
            });
        });
    });
</script>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>

    .categoryList{margin-top: 1rem;margin-left: 2rem;margin-right: 2rem;}
    .categoryList .title{    border-bottom: 1px solid #000;display: inline-block;width: 100%;}
    .categoryList .title:after{
        content: ">";
        float: right;
        margin-right: .7rem;
        -webkit-transform: rotate(0deg);
        transform: rotate(0deg);
        -webkit-transition: -webkit-transform .5s;
        transition: -webkit-transform .5s;
        transition: transform .5s;
        transition: transform .5s, -webkit-transform .5s;}
    .categoryList.active .title:after{    -webkit-transform: rotate(90deg);  transform: rotate(90deg);}
    .categoryList .body{margin-top: 1rem;line-height: 2rem;display: none;}
    .categoryList.active .body{display: block;}
    .categoryList .body .category-item{margin: 0 1rem;}
    .categoryList .body .category-item:hover,.categoryList .body .category-item.active{background-color: #cccccc;}
    .j_collapse{}
</style>
<div class="dx-shopping clearfix <c:if test='${empty wishList}'>dx-commodity</c:if>">
    <div class="dx-title" style="background-color: #999999"><spring:message code="wish"/><a href="/u/account/index"><spring:message code="goBack"/></a></div>
    <div class="categoryList j_collapse">
        <a href="javascript:void(0)" class="title"><spring:message code="wish.save"/></a>
        <c:if test="${not empty wishList}">
            <ul class="body">
                <c:forEach var="row" items="${categoryList}">
                    <li class="category-item <c:if test='${categoryDomain.id==row.id}'>active</c:if>" >
                        <a href="/cart/wishlist?categoryId=${row.id}">${row.name}</a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>

        <div class="dx-GoodsDetails" style="display: block">
            <c:forEach var="row" items="${wishList}">
            <div class="goods clearfix goodsDiv">
                <div class="goods-left">
                    <div class="pic"> <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="height: 120px;width: 100px;"></div>
                </div>
                <div class="goods-right" style="word-break: break-all">
                    <div class="name" style="margin: 0;width: 100%">${sessionScope.language=='en_US'?row.goodsEnName:row.goodsName}</div>
                    <div class="number"><spring:message code="shoppingCart.no"/>${row.goodsCode}</div>
                    <div class="goods_color" data-value=${row.skuSpecifications}>${ sessionScope.language=='en_US'? row.goodsItemDomain.enName:row.goodsItemDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;<span>
                       <spring:message code="shoppingCart.size"/>: ${sessionScope.language=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}
                    </span></div>
                    <div class="preferential-price"><spring:message code="shoppingCart.unitPrice"/> &nbsp;<span class="do-pro-price" data-value="${row.goodsPrice}">&nbsp;</span></div>
                    <div class="price hide"><spring:message code="wish.discountPrice"/>&nbsp; <span class="do-pro-price" data-value="${row.goodsPrice}">0</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.id}"><svg><use xlink:href="#bag"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_appointment toBoutique" data-value="${row.id}"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="" class="deleteBtn" data-value="${row.id}"><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            </c:forEach>

        </div>
    <c:if test="${empty wishList}">
    <div class="maybeLike clearfix" style="border-top: 2px solid #cccccc">
        <div class="title" style="margin:auto;margin-top: 1rem;border-bottom: 2px solid #cccccc;width: 80%">推荐商品</div>
        <c:forEach var="goods" items="${historyList}" begin="0" end="1">
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
                <div class="left">
                    <a href="/goods/details/${firstItem.id}">
                    <div class="pic" style="height: 100px">
                        <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                    </div>
                    <div class="name">${goods.name}</div>
                    <div class="price do-pro-price" data-value="${goods.price}">&nbsp;</div>
                    <ul class="color clearfix">
                        <c:forEach var="goodsItem" items="${goods.goodsItemList}">
                            <li style="background: ${goodsItem.colorValue}"></li>
                        </c:forEach>
                    </ul>
                     </a>
                </div>
        </c:forEach>
    </div>
    </c:if>
        <div class="explain">
            <ul>
                <li><a href="/goods/list?categoryId=1"><spring:message code="shoppingCart.selectWoman"/><span>&gt;</span></a></li>
                <li><a href="/goods/list?categoryId=8"><spring:message code="shoppingCart.selectMan"/><span>&gt;</span></a></li>
                <li class="last whatWish"><a href="#"><spring:message code="wish.whatWish"/>?<span>&gt;</span></a></li>
            </ul>
        </div>
</div>



<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {

        setPrice();

        $(".j_collapse>a").on("click", function (e) {
            e.preventDefault();
            $(this).parent().toggleClass("active");
            return false;
        });
        $(".categoryList").click(function () {
            location.href = "${ctx}/cart/wishlist"
        });
        $(".childDiv li").click(function () {
            $(this).addClass("active").siblings().removeClass("active");
        });

        if(${empty wishList}){
            $("#toggleDiv2").show();
        }else{
            $("#toggleDiv2").hide();
        }

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
        $(".j_appointment").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            console.log(id);
            var data = {"shoppingCartItemId":id};
            $.post("/cart/wishToBoutique",data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("加入精品店成功");
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }
                }
            });
        });
        $(".j_bag").on("click",function () {
            var $self = $(this);
            var id = $(this).attr("data-value");
            var data = {"shoppingCartItemId":id};
            $.post("/cart/boutiqueToCart",data,function (data) {
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("加入购物车成功");
                    setCartNum();
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }
                }
            });
        });


        $(".whatWish").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="wish.whatWish"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['100%', '50%'],
                content: ['${ctx}/content/whatWish'],//iframe的url，no代表不显示滚动条
                shadeClose: true
            });
        });


    });
</script>
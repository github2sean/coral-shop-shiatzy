<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>
    .childlist>a>li{
        width:90%;
        height: 2.5rem;
        margin: 0 1.7rem;
        font-size: 1.1rem;
        line-height: 2.2rem;
        margin-top: 1px;
        margin-bottom: 1px;
    }
    .childlist .active{
        background-color: #cccccc;
    }
</style>
<div class="dx-wish clearfix <c:if test='${empty wishList}'>dx-commodity</c:if>">
    <div class="dx-title" style="background-color: #999999"><spring:message code="wish"/><a href="/u/account/index"><spring:message code="goBack"/></a></div>
    <div class="content">
        <c:if test="${not empty wishList}">
        <div id="toggleDiv">
            <div class="dx-total" style="margin-bottom: 2px">
            <span class="categoryList"><div class="title" style="border-bottom:.1rem solid #333"><spring:message code="wish.save"/>&nbsp;></div></span>
            </div>
        </div>

        </c:if>
        <c:if test="${empty wishList}">
            <div id="toggleDiv2" style="text-align: center">
            <div class="" style="padding-top: 50px;text-align: center;border-bottom: 2px solid #cccccc;width: 90%;margin: auto"><p><spring:message code="wish"/>(0)</p></div>
            </div>
        </c:if>
    </div>
    <c:if test="${not empty wishList}">
    <div class="childDiv" style="overflow: hidden;width: 100%;margin-top: 0">
        <ul class="childlist">
            <c:forEach var="row" items="${categoryList}">
            <a href="/cart/wishlist?categoryId=${row.id}"><li data-value="${row.id}" class="<c:if test='${categoryDomain.id==row.id}'>active</c:if>" >${row.name}</li></a>
            </c:forEach>
        </ul>
    </div>
    </c:if>
        <div class="dx-GoodsDetails" style="display: block">
            <c:forEach var="row" items="${wishList}">
            <div class="goods clearfix goodsDiv">
                <div class="goods-left">
                    <div class="pic"> <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="" style="height: 120px;width: 100px;"></div>
                </div>
                <div class="goods-right" style="width: 200px;word-break: break-all">
                    <div class="name" style="margin: 0;width: 100%">${sessionScope.language=='en_US'?row.goodsEnName:row.goodsName}</div>
                    <div class="number"><spring:message code="shoppingCart.no"/>${row.goodsCode}</div>
                    <div class="goods_color" data-value=${row.skuSpecifications}>${ sessionScope.language=='en_US'? row.goodsItemDomain.enName:row.goodsItemDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;<span>
                       <spring:message code="shoppingCart.size"/>: ${JSONObject.fromObject(row.skuSpecifications).getString("size")}
                    </span></div>
                    <div class="preferential-price"><spring:message code="shoppingCart.unitPrice"/>&nbsp; &yen; <span>${row.goodsPrice}</span></div>
                    <div class="price"><spring:message code="wish.discountPrice"/>&nbsp; &yen; <span>0</span></div>
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
            <a href="/goods/details/${firstItem.id}">
                <div class="left">
                    <div class="pic">
                        <img src="${ImageModel.toFirst(goods.thumb).file}" alt="" style="height: 89px;width: 92px;">
                    </div>
                    <div class="name">${goods.name}</div>
                    <div class="price">${firstItem.price}</div>
                    <ul class="color clearfix">
                        <c:forEach var="goodsItem" items="${goods.goodsItemList}">
                            <li style="background: ${goodsItem.colorValue}"></li>
                        </c:forEach>
                    </ul>
                </div>
            </a>
        </c:forEach>
    </div>
    </c:if>
        <div class="dx-clause">
            <ul>
                <li><a href="#"><spring:message code="shoppingCart.selectWoman"/></a></li>
                <li><a href="#"><spring:message code="shoppingCart.selectMan"/></a></li>
                <li class="last"><a href="#"><spring:message code="wish.whatWish"/>?</a></li>
            </ul>
        </div>
</div>



<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {

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
        


    });
</script>
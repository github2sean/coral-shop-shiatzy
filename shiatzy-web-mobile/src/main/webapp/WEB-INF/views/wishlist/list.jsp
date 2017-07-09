<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<style>

    .categoryList{margin-top: 1rem;}
    .categoryList .title{    border-bottom: 1px solid #000;display: block;margin-left: 2rem;margin-right: 2rem;font-size: 1.4rem;}
    .categoryList .title:after{
        content: ">";
        float: right;

        -webkit-transform: rotate(0deg);
        transform: rotate(0deg);
        -webkit-transition: -webkit-transform .5s;
        transition: -webkit-transform .5s;
        transition: transform .5s;
        transition: transform .5s, -webkit-transform .5s;}
    .categoryList.active .title:after{    -webkit-transform: rotate(90deg);  transform: rotate(90deg);}
    .categoryList .body{margin-top: 1rem;line-height: 2rem;display: none;}
    .categoryList.active .body{display: block;}

    .categoryList .body .category-item{font-size:1.2rem;padding-left: 3rem;padding-top: 0.6rem;padding-bottom: 0.6rem;}
    .categoryList .body .category-item a{display:block;}
    .categoryList .body .category-item.active{background-color: #cccccc;}
    .j_collapse{}
    .price{
        display: inline;
    }
    .dx-shopping .dx-GoodsDetails .do-list-icon{
        margin-top: 1.3rem;
    }
</style>
<div class="dx-shopping clearfix <c:if test='${empty wishList}'>dx-commodity</c:if>">
    <div class="dx-title" style="background-color: #999999"><spring:message code="wish"/><a href="/u/account/index"><spring:message code="goBack"/></a></div>
<c:choose>
    <c:when test="${not empty wishList}">
        <div class="categoryList j_collapse">
            <a  class="title"><spring:message code="wish.save"/></a>

            <ul class="body">
                <c:forEach var="row" items="${categoryList}">
                    <li class="category-item <c:if test='${categoryDomain.id==row.id}'>active</c:if>" >
                        <a href="/cart/wishlist?categoryId=${row.id}">${web:selectLanguage()=='en_US'?row.enName:row.name}</a>
                    </li>
                </c:forEach>
            </ul>

        </div>
    </c:when>
   <c:otherwise>
       <div class="content dx-wish dx-shopping">
           <div id="toggleDiv2">
               <a href="/home/index"> <div class="message"><p>愿望清单&nbsp;(0)</p></div></a>
           </div>
       </div>
</c:otherwise>
</c:choose>

        <div class="dx-GoodsDetails" style="display: block">
            <c:forEach var="row" items="${wishList}">
            <div class="goods clearfix goodsDiv" style="padding: 2rem 0;">
                <div class="goods-left">
                    <div class="pic"> <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt=""></div>
                </div>
                <div class="goods-right" style="word-break: break-all">
                    <p class="name" style="margin: 0;width: 100%">${web:selectLanguage()=='en_US'?row.goodsEnName:row.goodsName}</p>
                    <p class="number"><spring:message code="shoppingCart.no"/>${row.goodsCode}</p>
                    <p class="goods_color" data-value=${row.skuSpecifications}>${ web:selectLanguage()=='en_US'? row.goodsItemDomain.enName:row.goodsItemDomain.name}&nbsp;&nbsp;&nbsp;&nbsp;<span>
                       <spring:message code="shoppingCart.size"/>: ${web:selectLanguage()=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}
                    </span>&nbsp;&nbsp;<c:if test="${row.stock<1}">（<spring:message code="sellout"/>）</c:if></p>
                    <p class="preferential-price ${not empty row.goodsDisPrice?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/> &nbsp;<span class="do-pro-price" data-value="${row.goodsPrice}">&nbsp;</span></p>
                    <c:if test="${not empty row.goodsDisPrice}">
                    <p class="price  xzc-dis-price"><spring:message code="wish.discountPrice"/>&nbsp; <span class="do-pro-price" data-value="${row.goodsDisPrice}">&nbsp;</span></p>
                    </c:if>

                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.id}"><svg style="margin-left: 3px"><use xlink:href="#bag"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_appointment toBoutique" data-value="${row.id}"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="" class="deleteBtn" data-value="${row.id}"><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            </c:forEach>

        </div>
    <c:if test="${empty wishList}">
        <!--也许喜欢-->
        <div class="maybeLike clearfix">
            <div class="title"><spring:message code="goods.detail.maybeLike"/></div>
            <div class="container">
                <ul class="do-pro-list">
                    <c:forEach var="goods" items="${likeGoodsList}" varStatus="num" begin="0" end="3">
                        <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
                        <li>
                            <a href="/goods/details/${firstItem.id}">
                                <div class="do-img">
                                    <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                                </div>
                                <p class="do-pro-t ellipsis-25"
                                   name="goodsName">${web:selectLanguage()=='en_US'?goods.enName:goods.name}</p>
                                <p class="do-pro-price <c:if test="${not empty  goods.disPrice}">xzc-price</c:if>"
                                   name="goodsPrice" data-value="${firstItem.price}">&nbsp;</p>
                                <c:if test="${not empty  goods.disPrice}">
                                    <p class="do-pro-price xzc-dis-price" name="goodsPrice"
                                       data-value="${goods.disPrice}">&nbsp;</p>
                                </c:if>
                                <ul class="do-list-color" name="skuId" data-value="">
                                    <c:forEach var="item" items="${goods.goodsItemList}">
                                        <li style="background: ${item.colorValue}"></li>
                                    </c:forEach>
                                </ul>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>
        <div class="explain">
            <ul>
                <li><a href="/goods/list?categoryId=17"><spring:message code="shoppingCart.selectWoman"/><span>&gt;</span></a></li>
                <li><a href="/goods/list?categoryId=18"><spring:message code="shoppingCart.selectMan"/><span>&gt;</span></a></li>
                <li class="last whatWish"><a href="#"><spring:message code="wish.whatWish"/>?<span>&gt;</span></a></li>
            </ul>
        </div>
</div>



<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        $(".top-right-nav").find("li:eq(2)").addClass("active");
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
                    layer.msg("<spring:message code="success.toboutique"/>");
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
                    layer.msg("<spring:message code="success.tocart"/>");
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
                content: ['${ctx}/content/whatWish?id=33'],//iframe的url，no代表不显示滚动条
                shadeClose: true
            });
        });


    });
</script>
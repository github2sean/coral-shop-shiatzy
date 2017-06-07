<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p style="float: left"><spring:message code="reservation"/></p>
    <a style="float: right;" onclick="history.go(-1)">X</a>
</div>

<c:if test="${empty preOderItemList}">
    <div class="content dx-wish dx-shopping">
        <div id="toggleDiv2">
          <a href="/home/index"> <div class=" content"><p><spring:message code="reservation.list"/>(0)</p></div></a>
        </div>
    </div>
</c:if>


<c:forEach var="row" items="${preOderItemList}">
<div class="order-main clearfix">
    <div class="order-main-left goodsDiv">
        <p class="product-num"><spring:message code="shoppingCart.no"/> ${row.leftItem.goodsCode}</p>
        <img src="${ImageModel.toFirst(row.leftItem.goodsItemDomain.thumb).file}" alt="" style="height: 120px;width: 100px;">
        <p class="product-name">${sessionScope.language=='en_US'?row.leftItem.goodsEnName:row.leftItem.goodsName}</p>
        <div class="color-size">
            <p>${sessionScope.language=='en_US'?row.leftItem.goodsItemDomain.enName:row.leftItem.goodsItemDomain.name}</p>
            <p><spring:message code="shoppingCart.size"/>:&nbsp;${JSONObject.fromObject(row.leftItem.skuSpecifications).getString("size")}</p>
        </div>
        <p class="price"><spring:message code="shoppingCart.unitPrice"/>　¥ ${row.leftItem.goodsPrice}</p>
        <ul class="do-list-icon">
            <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.leftItem.id}"><svg><use xlink:href="#bag"></use></svg></a></li>
            <li><a href="javascript:;" class="j_collect" data-value="${row.leftItem.id}"><svg><use xlink:href="#heart"></use></svg></a></li>
            <li><a href="" class="deleteBtn" data-value="${row.leftItem.id}"><svg><use xlink:href="#close"></use></svg></a></li>
        </ul>
    </div>
    <c:if test="${not empty row.rightItem}">
    <div class="order-main-right goodsDiv">
        <p class="product-num"><spring:message code="shoppingCart.no"/> ${row.rightItem.goodsCode}</p>
        <img src="${ImageModel.toFirst(row.rightItem.goodsItemDomain.thumb).file}" alt="" style="height: 120px;width: 100px;">
        <p class="product-name">${sessionScope.language=='en_US'?row.rightItem.goodsEnName:row.rightItem.goodsName}</p>
        <div class="color-size">
            <p>${sessionScope.language=='en_US'?row.rightItem.goodsItemDomain.enName:row.rightItem.goodsItemDomain.name}</p>
            <p><spring:message code="shoppingCart.size"/>:&nbsp;${JSONObject.fromObject(row.rightItem.skuSpecifications).getString("size")}</p>
        </div>
        <p class="price"><spring:message code="shoppingCart.unitPrice"/>　¥ ${row.rightItem.goodsPrice}</p>
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
            <a href="javascript:void(0)"><spring:message code="register"/>&nbsp;/&nbsp;<spring:message code="login"/></a>
        </div>
    </c:if>
    <c:if test="${not empty preOderItemList}">
    <div class="choose-store">
        <a href="/reservation/initChoose"><spring:message code="reservation.findstore"/></a>
    </div>
    </c:if>
    <ul>
        <li><a href="/goods/list?categoryId=1"><spring:message code="shoppingCart.selectWoman"/><span>></span></a></li>
        <li><a href="/goods/list?categoryId=8"><spring:message code="shoppingCart.selectMan"/><span>></span></a></li>
        <li><a href="#" class="whatBoutique"><spring:message code="reservation.what"/>?<span>></span></a></li>
    </ul>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {
        //console.log('${goodsList}');

        $(".choose-store").find("a").click(function(){
            var href = "${ctx}/passport/toLogin";
            var user = '${sessionScope.user_context}';
            if(user!=''){
                href = '${ctx}/passport/toRegister';
            }
            location.href = href;
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



        //iframe窗
        $(".whatBoutique").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="reservation.what"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['100%', '80%'],
                content: ['${ctx}/content/whatBoutique'],//iframe的url，no代表不显示滚动条
                shadeClose: true
            });
        });
    });
</script>
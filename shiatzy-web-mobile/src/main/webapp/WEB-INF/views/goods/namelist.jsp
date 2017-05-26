<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="container">
    <ul class="do-pro-list">
        <c:forEach var="goods" items="${goodsDomainPageList.list}" varStatus="num">
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
            <li>
                <a href="/goods/details/${firstItem.id}">
                    <div class="do-img">
                        <img src="${ImageModel.toFirst(goods.thumb).file}" alt="" style="height: 120px;">
                    </div>
                    <p class="do-pro-t ellipsis-2l" name="goodsName">${goods.name}</p>
                    <p class="do-pro-price ellipsis" name="goodsPrice">${firstItem.price}</p>
                    <ul class="do-list-color" name="skuId" data-value="">
                        <c:forEach var="goods" items="${goods.goodsItemList}">
                            <li style="background: ${goods.colorValue}"></li>
                        </c:forEach>
                    </ul>
                </a>
                <!--Todo:收藏按钮-->
                <i class="icon-collect j_collect active" data-value="${firstItem.id}" data-ids="${sizeList[num.count-1].id}">
                    <svg class="do-heart"><use xlink:href="#heart"></use></svg>
                </i>
            </li>
        </c:forEach>
    <c:if test="${ empty goodsDomainPageList.list}" >
        <h4 style="margin: auto;text-align: center;margin-top: 5rem">没有商品信息</h4>
    </c:if>
    </ul>
</div>
<div id="j_panel_cat" class="pro-filter-panel panel-cat">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <ul class="do-sort-list">
        <c:forEach var="item" items="${categoryList}">
            <li><a href="/goods/list?categoryId=${item.id}">${item.name}</a></li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>

    $(function () {
        //console.log('${goodsList}');

        //var cartNum  = $(".do-num").val();
        $(".j_collect").click(function () {
            var isLogin = '${sessionScope.user_context}';
            if(isLogin==null || isLogin ==""){
                location.href="${ctx}/passport/toLogin";
            }
            var selectSizeId=$(this).attr("data-ids");
            var skuId = $(this).attr("data-value");
            var isAdd =  $(this).find("use").attr("xlink:href");
            console.log(isAdd);
            var data = {"itemId":skuId,"num":1,"sizeId":selectSizeId,"type":2};
            console.log(data);
            var url = "";
            if(isAdd=="#heart-red"){
                url = "/cart/addToCart";
            }else if(isAdd=="#heart"){
                url = "/cart/removeFromWish";
            }
            console.log(url);
            $.post(url,data,function (result) {
                console.log(result);
                if(result.code==200){
                    console.log(result.message);
                }
            });
        });

    });
</script>

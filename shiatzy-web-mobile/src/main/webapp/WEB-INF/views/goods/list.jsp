<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="container">
    <div class="do-list-header">
        <a href="javascript:;" class="link-down font-16 j_panel_trigger" data-panel="j_panel_cat">111</a>
        <div class="pull-right font-12">
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_filter">筛选</a>
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_sort">排序</a>
        </div>
    </div>

    <ul class="do-pro-list">
        <c:forEach var="goods" items="${goodsDomainPageList.list}">
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
        <li>
            <a href="/goods/details/${firstItem.id}">
                <div class="do-img">
                    <img src="${ImageModel.toFirst(firstItem.thumb).file}" alt="" style="height: 120px;">
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
            <i class="icon-collect j_collect active" data-value="${row.id}">
                <svg class="do-heart"><use xlink:href="#heart"></use></svg>
            </i>
        </li>
        </c:forEach>
    </ul>
    <div class="font-12 text-center do-load-list">
        <span class="link-down-before">向下自动载入</span>
        <!--<span>-已到底部-</span>-->
    </div>
</div>
<div id="j_panel_cat" class="pro-filter-panel panel-cat">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <ul class="do-sort-list">
         <c:forEach var="item" items="${categoryList}">
            <li><a href="${UrlUtils.setParam("categoryId",item.id)}">${item.name}</a></li>
         </c:forEach>
    </ul>
</div>
<div id="j_panel_filter" class="pro-filter-panel panel-filter">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <form action="">
        <input type="hidden" name="categoryId" value="${categoryId}">
        <div class="do-sort link-down">筛选<button type="reset" class="btn-reset">重置筛选</button></div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">颜色</div>
            <c:forEach var="item" items="${colorList}">
            <div class="do-sort-group">
                <input type="checkbox" name="colorId" id="color${item.id}" value="${item.id}">
                <label for="color${item.id}">${item.name}(10)</label>
            </div>
            </c:forEach>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">材质</div>
            <div class="do-sort-group">
                <input type="checkbox" id="material1">
                <label for="material1">全部（23）</label>
            </div>
            <div class="do-sort-group">
                <input type="checkbox" id="material2">
                <label for="material2">天然皮革(10)</label>
            </div>
            <div class="do-sort-group">
                <input type="checkbox" id="material3">
                <label for="material3">人造皮革(5)</label>
            </div>
            <div class="do-sort-group">
                <input type="checkbox" id="material4">
                <label for="material4">刺繡(8)</label>
            </div>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">尺寸</div>
            <c:forEach var="item" items="${sizeList}">
                <div class="do-sort-group">
                    <input type="checkbox" name="sizeId" id="size${item.id}" value="${item.id}">
                    <label for="size${item.id}">${item.name}(10)</label>
                </div>
            </c:forEach>
        </div>
        <div class="text-center"><button type="submit" class="btn-submit">应用</button></div>
    </form>
</div>
<div id="j_panel_sort" class="pro-filter-panel">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <div class="do-sort">排序</div>
    <ul class="do-sort-list">
        <li><a href="">售价（高-低）</a></li>
        <li><a href="">售价（低-高）</a></li>
    </ul>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>

    $(function () {
        //console.log('${goodsList}');

        //var cartNum  = $(".do-num").val();
        $(".j_collect").each(function () {
            var skuId = $(this).attr("data-value");

            $(this).click(function () {
                var isLogin = '${sessionScope.user_context}';
                if(isLogin==null || isLogin ==""){
                    location.href="${ctx}/passport/toLogin";
                }
                var isAdd =  $(this).find("use").attr("xlink:href");
                console.log(isAdd);
                var data  = {"skuId":skuId,"num":1,"type":2};
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
    });
</script>

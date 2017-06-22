<%--suppress ALL --%>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="com.dookay.coral.common.web.HttpContext" %>
<%@ page import="com.dookay.coral.shop.goods.domain.GoodsColorDomain" %>
<%@ page import="com.dookay.coral.shop.goods.domain.GoodsDomain" %>
<%@ page import="com.dookay.coral.shop.goods.domain.GoodsItemDomain" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="container">
    <div class="do-list-header">
        <a href="javascript:;" class="link-down font-14 j_panel_trigger" data-panel="j_panel_cat">${categoryName}</a>
        <div class="pull-right font-14">
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_filter">筛选</a>
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_sort">排序</a>
        </div>
    </div>

    <ul class="do-pro-list j_scroll_list">
        <c:forEach var="goods" items="${goodsDomainPageList.list}" varStatus="num" begin="0" end="3" step="1">

            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
            <c:if test="${not empty firstItem}">
                <li>
                    <a href="/goods/details/${firstItem.id}">
                        <div class="do-img">
                            <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                        </div>
                        <p class="do-pro-t ellipsis-2l" name="goodsName">${goods.name}</p>
                        <p class="do-pro-price" name="goodsPrice">${firstItem.price}</p>
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
            </c:if>
        </c:forEach>
        <c:if test="${ empty goodsDomainPageList.list[0].goodsItemList}" >
            <h4 style="margin-top: 2rem;text-align: center">没有商品信息</h4>
        </c:if>

    </ul>
    <c:if test="${not empty goodsDomainPageList.list[0]}" >
    <div class="font-12 text-center do-load-list">
        <span class="link-down-before">向下自动载入</span>
        <span style="display: none">-已到底部-</span>
    </div>
    </c:if>
</div>
<div id="j_panel_cat" class="pro-filter-panel panel-cat">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <ul class="do-sort-list hide">
        <c:forEach var="item" items="${categoryList}">
            <li><a href="/goods/list?categoryId=${item.id}">${item.name}</a></li>
        </c:forEach>
    </ul>
</div>
<div id="j_panel_filter" class="pro-filter-panel panel-filter">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <form action="" class="filterForm">
        <input type="hidden" name="categoryId" value="${categoryId}">
        <div class="do-sort link-down">筛选<button type="reset" class="btn-reset">重置筛选</button></div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">颜色</div>
           <div class="do-sort-group-wrap">
                <c:forEach var="item" items="${colorList}">
               <div class="do-sort-group">
                   <div class="do-color-show"></div>
                   <input type="checkbox" name="colorIds" id="color${item.id}" value="${item.id}">
                   <label for="color${item.id}">${item.name}(10)</label>
               </div>
               </c:forEach>
           </div>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">材质</div>
            <div class="do-sort-group-wrap">
                <c:forEach var="item" items="${attributeList}">
                    <div class="do-sort-group">
                        <input type="checkbox" name="attributeIds" id="color${item.id}" value="${item.id}">
                        <label for="color${item.id}">${item.value}(10)</label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">尺寸</div>
          <div class="do-sort-group-wrap">
                <c:forEach var="item" items="${sizeList}">
                  <div class="do-sort-group">
                      <input type="checkbox" name="sizeIds" id="size${item.id}" value="${item.id}">
                      <label for="size${item.id}">${item.name}(10)</label>
                  </div>
              </c:forEach>
          </div>
        </div>
        <div class="text-center"><button type="button" class="btn-submit">应用</button></div>
    </form>
</div>
<div id="j_panel_sort" class="pro-filter-panel">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <div class="do-sort">排序</div>
    <ul class="do-sort-list">
            <li class="j_price_order" data-order="0">售价（高-低)</li>
            <li class="j_price_order" data-order="1">售价（低-高)</li>
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

        $(".btn-submit").click(function () {
            var data = $(".filterForm").serializeArray();
            console.log(data)
            $(".filterForm").submit();
        });
        //价格点击事件
        $('.j_price_order').click(function () {
            var $this = $(this);
            var priceorder = $this.attr('data-order');
            var href = window.location.href;
            var priceWay="priceWay";
            setQueryString(priceWay,priceorder,href);
        });

    });
</script>

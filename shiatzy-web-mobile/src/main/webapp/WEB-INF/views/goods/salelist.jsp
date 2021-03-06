<%--suppress ALL --%>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="net.sf.json.JSONObject" %>
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
        <a href="javascript:;" class="link-down font-14 j_panel_trigger" data-panel="j_panel_cat">SALE</a>
        <div class="pull-right font-14">
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_filter"><spring:message code="goods.list.filter"/></a>
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_sort"><spring:message code="goods.list.sort"/></a>
        </div>
    </div>

    <ul class="do-pro-list j_scroll_list">
        <c:forEach var="goods" items="${goodsDomainPageList.list}" varStatus="num" >
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
            <li>
                <a href="/goods/details/${firstItem.id}">
                    <div class="do-img">
                        <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                    </div>
                    <p class="do-pro-t ellipsis-25" name="goodsName">${web:selectLanguage()=='en_US'?goods.enName:goods.name}</p>
                    <p class="do-pro-price <c:if test="${firstItem.discountPrice != 0}">xzc-price</c:if>" name="goodsPrice" data-value="${firstItem.price}">${firstItem.price}</p>
                    <c:if test="${firstItem.discountPrice != 0}">
                        <p class="do-pro-price xzc-dis-price"  data-value="${firstItem.discountPrice}">${firstItem.discountPrice}</p>
                    </c:if>
                    <ul class="do-list-color" name="skuId" data-value="">
                        <c:forEach var="item" items="${goods.goodsItemList}">
                            <li style="background: ${item.colorValue}"></li>
                        </c:forEach>
                    </ul>
                </a>
                <!--Todo:收藏按钮-->
                <i class="icon-collect j_collect hide" data-value="${firstItem.id}" data-ids="${goods.sizeDomainList[0].id}" >
                    <svg class="do-heart"><use xlink:href="#heart"></use></svg>
                </i>
            </li>
        </c:forEach>
        <c:if test="${ empty goodsDomainPageList.list}" >
            <h4 style="margin-top: 2rem;text-align: center"><spring:message code="goods.list.noneGoods"/></h4>
        </c:if>

    </ul>
    <c:if test="${not empty goodsDomainPageList.list && goodsDomainPageList.list.size()>19}" >
        <div class="font-12 text-center do-load-list">
            <span class="link-down-before moreGoods"><spring:message code="goods.list.autoLoad"/></span>
            <span class="overGoods" style="display: none">-<spring:message code="goods.list.downOver"/>-</span>
        </div>
    </c:if>
</div>
<div id="j_panel_cat" class="pro-filter-panel panel-cat">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <ul class="do-sort-list">
        <c:forEach var="item" items="${categoryList}">
            <li><a href="/goods/list?categoryId=${item.id}">${web:selectLanguage()=='en_US'?item.enName:item.name}</a></li>
        </c:forEach>
    </ul>
</div>
<div id="j_panel_filter" class="pro-filter-panel panel-filter">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <form action="" class="filterForm">
        <input type="hidden" name="categoryId" value="${categoryId}">
        <div class="do-sort link-down"><spring:message code="goods.list.filter"/><button type="reset" class="btn-reset"><spring:message code="goods.list.reset"/></button></div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down"><spring:message code="goods.detail.colors"/></div>
            <div class="do-sort-group-wrap">
                <c:forEach var="item" items="${colorList}">
                    <div class="do-sort-group">
                        <div class="do-color-show" style="background-color: ${item.color}"></div>
                        <input type="checkbox" name="colorIds"
                        <c:forEach var="row" items="${colorIds}">
                               <c:if test="${row==item.id}">checked="checked"</c:if>
                        </c:forEach>
                               id="color${item.id}" value="${item.id}">
                        <label for="color${item.id}">${web:selectLanguage()=='en_US'?item.enName:item.name}</label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down"><spring:message code="goods.list.attr"/></div>
            <div class="do-sort-group-wrap">
                <c:forEach var="item" items="${attributeList}">
                    <div class="do-sort-group">
                        <input type="checkbox" name="attributeIds"
                        <c:forEach var="row" items="${attrIds}">
                               <c:if test="${row==item.id}">checked="checked"</c:if>
                        </c:forEach>
                               id="color${item.id}" value="${item.id}">
                        <label for="color${item.id}">${web:selectLanguage()=='en_US'?item.enValue:item.value}</label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down"><spring:message code="shoppingCart.size"/></div>
            <div class="do-sort-group-wrap">
                <c:forEach var="item" items="${sizeList}">
                    <div class="do-sort-group">
                        <input type="checkbox" name="sizeIds"
                        <c:forEach var="row" items="${sizeIds}">
                               <c:if test="${row==item.id}">checked="checked"</c:if>
                        </c:forEach>
                               id="size${item.id}" value="${item.id}">
                        <label for="size${item.id}">${web:selectLanguage()=='en_US'?item.enName:item.name}</label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="text-center"><button type="button" class="btn-submit"><spring:message code="subscription.complete"/></button></div>
    </form>
</div>
<div id="j_panel_sort" class="pro-filter-panel">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <div class="do-sort"><spring:message code="goods.list.sort"/></div>
    <ul class="do-sort-list">
        <li class="j_price_order" data-order="0"><spring:message code="goods.list.price.hign2low"/></li>
        <li class="j_price_order" data-order="1"><spring:message code="goods.list.price.low2hign"/></li>
    </ul>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    function getJsonObjLength(jsonObj) {
        console.log("jsonObjlength:"+jsonObj.length);
        return jsonObj.length;
    }
    function addToWish(now,type){
        var isLogin = '${isGuest}'=='onLine'?true:false;
        var selectSizeId=$(now).attr("data-ids");
        var selectItemId = $(now).attr("data-value");
        var selectSizeId=selectSizeId;
        var itemId = selectItemId;
        var isAdd =  $(now).find("use").attr("xlink:href");
        console.log(isAdd);
        var data = {"itemId":itemId,"num":1,"sizeId":selectSizeId,"type":2};
        console.log(data);
        var url = "";
        if(type==1 && isAdd=="#heart-red"){
            url = "/cart/addToCart";
        }else if(isLogin && type==1 && isAdd=="#heart"){
            url = "/cart/removeFromWish";
        }else if(isLogin && type==2 && isAdd=="#heart-red"){
            $(now).find("use").attr("xlink:href","#heart");
            url = "/cart/removeFromWish";
        }else if(type==2 && isAdd=="#heart"){
            $(now).find("use").attr("xlink:href","#heart-red");
            url = "/cart/addToCart";
        }else if(!isLogin && type==1 && isAdd=="#heart"){
            url = "/cart/removeFromSessionWish";
        }else if(!isLogin && type==2 && isAdd=="#heart-red"){
            $(now).find("use").attr("xlink:href","#heart");
            url = "/cart/removeFromSessionWish";
        }
        console.log(url);
        $.post(url,data,function (result) {
            console.log(result);
            if(result.code==200){
                console.log(result.message);
            }
        });
    }
    $(function () {
        //显示价格
        setPrice();
        //console.log('${goodsList}');
        //var cartNum  = $(".do-num").val();
        $(".j_collect").click(function () {
            addToWish(this,1);
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
            var newHref = setQueryString(priceWay,priceorder,href);
            console.log(newHref);
            location.href = newHref;
        });
        //reset
        $("button[type=reset]").click(function () {
            $("input[type=checkbox]:checked").removeAttr("checked");
        });
        //加载更多商品
        var offset = '${goodsDomainPageList.list.size()}'*1-1;
        var page = 2;
        console.log("pageSize:"+'${goodsDomainPageList.startRowIndex}');
        $(".moreGoods").click(function () {
            loadMore();
        });
    });

    //加载更多商品
    var offset = '${goodsDomainPageList.list.size()}'*1-1;
    var page = 1;
    console.log("pageSize:"+'${goodsDomainPageList.startRowIndex}');

    var totalPage = parseInt('${goodsDomainPageList.totalPage}');
    function loadMore() {
        //当前分类
        var categoryId = '${goodsCategoryDomain.id}'
        //当前排列的方式
        var priceWay = "${sessionScope.priceWay}";
        //当前页
        page++;

        var data2 = {"categoryId":categoryId,"priceWay":priceWay,"pageIndex":page}
        console.log("data2:"+data2+" offset:"+offset);
        if(page<=totalPage){
            $.get("/goods/list",data2,function (data) {
                $(".j_scroll_list").append($(data).find(".j_scroll_list").html())
                setPrice();
            });
        }else{
            $(".moreGoods").text("-已到底部-")
        }

    };


    //var initHeight = $(".moreGoods").offset().top;
    var moveHeight = ($(".do-pro-list").find("li").height())*19;//Li 的高度
    moveHeight = parseInt(moveHeight);
    window.onscroll = function(){
        //console.log("initHeight:"+initHeight+"   moveHeight:"+moveHeight);
        //为了保证兼容性，这里取两个值，哪个有值取哪一个
        var scrollTop = getScrollTop();
        var maxHeight = $(window).height();//可视区域
        scrollTop = parseInt(scrollTop);
        var pageNum = Math.abs(scrollTop%moveHeight);

        if(0==pageNum){
            console.log("加载下一页了");
            loadMore();
        }else if(getWindowHeight()+scrollTop==getScrollHeight()){
            loadMore();
            console.log("底部");
        }
        //scrollTop就是触发滚轮事件时滚轮的高度});

    };


    //滚动条在Y轴上的滚动距离
    function getScrollTop(){
        var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
        if(document.body){
            bodyScrollTop = document.body.scrollTop;
        }
        if(document.documentElement){
            documentScrollTop = document.documentElement.scrollTop;
        }
        scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
        return scrollTop;
    }

    //文档的总高度
    function getScrollHeight(){
        var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
        if(document.body){
            bodyScrollHeight = document.body.scrollHeight;
        }
        if(document.documentElement){
            documentScrollHeight = document.documentElement.scrollHeight;
        }
        scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
        return scrollHeight;
    }
    //浏览器视口的高度
    function getWindowHeight(){
        var windowHeight = 0;
        if(document.compatMode == "CSS1Compat"){
            windowHeight = document.documentElement.clientHeight;
        }else{
            windowHeight = document.body.clientHeight;
        }
        return windowHeight;
    }

</script>

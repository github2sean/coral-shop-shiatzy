<%--suppress ALL --%>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page import="com.dookay.coral.common.web.HttpContext" %>
<%@ page import="com.dookay.coral.shop.goods.domain.GoodsColorDomain" %>
<%@ page import="com.dookay.coral.shop.goods.domain.GoodsDomain" %>
<%@ page import="com.dookay.coral.shop.goods.domain.GoodsItemDomain" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:useBean id="query" scope="request" type="com.dookay.coral.shop.goods.query.GoodsQuery"/>
<jsp:useBean id="colorList" scope="request" type="java.util.List<com.dookay.coral.shop.goods.domain.GoodsColorDomain>"/>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>


<div class="container">
    <div class="do-list-header">
        <a href="javascript:;" class="link-down font-16 j_panel_trigger" data-panel="j_panel_cat">${sessionScope.language=='en_US'?goodsCategoryDomain.enName:goodsCategoryDomain.name}</a>
        <div class="pull-right font-12">
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_filter">筛选</a>
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_sort">排序</a>
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
                <p class="do-pro-t ellipsis-25" name="goodsName">${goods.name}</p>
                <p class="do-pro-price" name="goodsPrice">${firstItem.price}</p>
                <ul class="do-list-color" name="skuId" data-value="">
                <c:forEach var="goods" items="${goods.goodsItemList}">
                    <li style="background: ${goods.colorValue}"></li>
                </c:forEach>
                </ul>
            </a>
            <!--Todo:收藏按钮-->
            <i class="icon-collect j_collect" data-value="${firstItem.id}" data-ids="${sizeList[num.count-1].id}">
                <svg class="do-heart"><use xlink:href="#heart"></use></svg>
            </i>
        </li>
        </c:forEach>
        <c:if test="${ empty goodsDomainPageList.list}" >
            <h4 style="margin-top: 2rem;text-align: center">没有商品信息</h4>
        </c:if>

    </ul>
    <c:if test="${not empty goodsDomainPageList.list && goodsDomainPageList.list.size()>19}" >
    <div class="font-12 text-center do-load-list">
        <span class="link-down-before moreGoods">向下自动载入</span>
        <span class="overGoods" style="display: none">-已到底部-</span>
    </div>
    </c:if>
</div>
<div id="j_panel_cat" class="pro-filter-panel panel-cat">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <ul class="do-sort-list">
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
            <c:forEach var="item" items="${colorList}">
            <div class="do-sort-group">
                <input type="checkbox" name="colorIds" id="color${item.id}" value="${item.id}">
                <label for="color${item.id}">${item.name}(10)</label>
            </div>
            </c:forEach>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">材质</div>
            <c:forEach var="item" items="${attributeList}">
                <div class="do-sort-group">
                    <input type="checkbox" name="attributeIds" id="color${item.id}" value="${item.id}">
                    <label for="color${item.id}">${item.value}(10)</label>
                </div>
            </c:forEach>
        </div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">尺寸</div>
            <c:forEach var="item" items="${sizeList}">
                <div class="do-sort-group">
                    <input type="checkbox" name="sizeIds" id="size${item.id}" value="${item.id}">
                    <label for="size${item.id}">${item.name}(10)</label>
                </div>
            </c:forEach>
        </div>
        <div class="text-center"><button type="button" class="btn-submit">完成</button></div>
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
    function getJsonObjLength(jsonObj) {
        console.log("jsonObjlength:"+jsonObj.length);
        return jsonObj.length;
    }

    function addToWish(now,type){
        var isLogin = '${sessionScope.user_context}';
        if(isLogin==null || isLogin ==""){
            location.href="${ctx}/passport/toLogin";
        }
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
        }else if(type==1 && isAdd=="#heart"){
            url = "/cart/removeFromWish";
        }else if(type==2 && isAdd=="#heart-red"){
            $(now).find("use").attr("xlink:href","#heart");
            url = "/cart/removeFromWish";
        }else if(type==2 && isAdd=="#heart"){
            $(now).find("use").attr("xlink:href","#heart-red");
            url = "/cart/addToCart";
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
    var page = 2;
    console.log("pageSize:"+'${goodsDomainPageList.startRowIndex}');
    function loadMore() {
        //当前分类
        var categoryId = '${goodsCategoryDomain.id}'
        //当前排列的方式
        var priceWay = "${sessionScope.priceWay}";
        //当前页
        var nowPage = page++;

        var data2 = {"categoryId":categoryId,"priceWay":priceWay,"offset":offset,"nowPage":nowPage}
        console.log("data2:"+data2+" offset:"+offset);
        $.post("/goods/listMore",data2,function (data) {

            if(data.code==200){
                var moreListJson = eval(data.data);
                var moreList = moreListJson.list;
                var nowSize = getJsonObjLength(moreList);
                offset = offset+nowSize;
                console.log(moreList+" nowSize:"+nowSize);

                if(moreList!=''){
                    console.log("exe")
                    for(var i=0;i<nowSize;i++){
                        var firstItem = moreList[i].goodsItemList[0];
                        var srcJson = eval(firstItem.thumb);
                        var src = srcJson[0].file;
                        var sizeIds = new Array();
                        var strrrr = '${sizeList}';
                        console.log("moreList:"+moreList[i]);
                        var colorList  = moreList[i].goodsColorDomainList;
                        console.log("moreList:"+moreList[i]+" colorList:"+colorList);
                        var colorStr ="<ul class='do-list-color' name='skuId' data-value=''>";
                        for(var j=0;j<getJsonObjLength(colorList);j++){
                            var nowJson =colorList[j];
                            var nowColor = nowJson.color;
                            colorStr += "<li style='background:"+nowColor+"'></li> ";
                        }
                        colorStr += "</ul>";
                        // var colorStr = "<ul class='do-list-color' name='skuId' data-value=''><li style='background: #000000'></li> </ul>";
                        var firstSizeid = moreList[i].firstSizeDomain.id;
                        console.log("firstSizeid:"+firstSizeid);

                        var now = 't'+'h'+'i'+'s';
                        var str = " <li>" +
                                " <a href='/goods/details/"
                                +firstItem.id+
                                "' ><div class='do-img'><img src="
                                +src+
                                " alt='' style='height: 120px;'/></div>  " +
                                " <p class='do-pro-t ellipsis-25' name='goodsName'>"
                                +moreList[i].name+
                                " </p> " +
                                " <p class='do-pro-price' name='goodsPrice'>"
                                +firstItem.price+
                                " </p> " +
                                colorStr +
                                "</a> " +
                                "<i class='icon-collect j_collect' onclick='addToWish("+now+","+2+")'  data-value="
                                +firstItem.id+
                                " data-ids="+firstSizeid+"> <svg class='do-heart'><use xlink:href='#heart'></use></svg> </i> </li>";
                        //console.log(str);
                        //滚动条滚动一段距离
                        $(".j_scroll_list").append(str);
                        /*  var t = $(window).scrollTop();
                         $('body,html').animate({scrollTop:t+500},100);*/
                    }
                }else {
                    $(".moreGoods").hide().siblings(".overGoods").show();
                }
            }else {
                layer.msg("加载失败");
            }
        });
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

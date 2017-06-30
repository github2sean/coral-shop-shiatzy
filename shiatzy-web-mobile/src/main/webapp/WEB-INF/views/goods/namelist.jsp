<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="container">
    <div class="do-list-header">
        <a href="javascript:;" class="link-down font-14 j_panel_trigger" data-panel="j_panel_cat"><spring:message code="goods.all"/></a>
        <div class="pull-right font-14">
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_filter"><spring:message code="goods.list.filter"/></a>
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_sort"><spring:message code="goods.list.sort"/></a>
        </div>
    </div>


    <ul class="do-pro-list">
        <c:forEach var="goods" items="${goodsDomainPageList.list}" varStatus="num">
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
            <li>
                <a href="/goods/details/${firstItem.id}">
                    <div class="do-img">
                        <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                    </div>
                    <p class="do-pro-t ellipsis-2l" name="goodsName">${web:selectLanguage()=='en_US'?goods.enName:goods.name}</p>
                    <p class="do-pro-price ${not empty goods.disPrice?'xzc-price':''}" name="goodsPrice" data-value="${firstItem.price}"></p>
                    <c:if test="${not empty goods.disPrice}">
                        <p class="do-pro-price xzc-dis-price"  data-value="${goods.disPrice}"></p>
                    </c:if>
                    <ul class="do-list-color" name="skuId" data-value="">
                        <c:forEach var="goods" items="${goods.goodsItemList}">
                            <li style="background: ${goods.colorValue}"></li>
                        </c:forEach>
                    </ul>
                </a>
                <!--Todo:收藏按钮-->
                <i class="icon-collect j_collect hide" data-value="${firstItem.id}" data-ids="${sizeList[num.count-1].id}">
                    <svg class="do-heart"><use xlink:href="#heart"></use></svg>
                </i>
            </li>
        </c:forEach>
    <c:if test="${ empty goodsDomainPageList.list}" >
        <h4 style="margin: auto;text-align: center;margin-top: 5rem"><spring:message code="goods.none"/></h4>
    </c:if>
    </ul>
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
        <input type="hidden" name="goodsName" value="${keyword}">
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

    $(function () {

        setPrice();
        //console.log('${goodsList}');

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
    var page = 2;
    console.log("pageSize:"+'${goodsDomainPageList.startRowIndex}');
    function loadMore() {
        //当前分类
        var goodsName = '${keyword}'
        //当前排列的方式
        var priceWay = "${sessionScope.priceWay}";
        //当前页
        var nowPage = page++;

        var data2 = {"goodsName":goodsName,"priceWay":priceWay,"offset":offset,"nowPage":nowPage}
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
                    var isEn = ${web:selectLanguage()=='en_US'};
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
                        var goodsName = moreList[i].name;
                        if(isEn){
                            goodsName = moreList[i].enName;
                        }

                        var disPri = firstItem.disPrice;
                        var priceStr = disPri!=''
                                ?" <p class='do-pro-price xzc-price' name='goodsPrice' data-value='"+firstItem.price+"'>"
                        +"&nbsp;"+" </p><p class='do-pro-price xzc-dis-price' name='goodsPrice' data-value='"+firstItem.disPrice+"'>"
                        +"&nbsp;"+" </p> "
                                :" <p class='do-pro-price' name='goodsPrice' data-value='"+firstItem.price+"'>"
                        +"&nbsp;"+" </p> ";

                        var now = 't'+'h'+'i'+'s';
                        var str = " <li>" +
                                " <a href='/goods/details/"
                                +firstItem.id+
                                "' ><div class='do-img'><img src="
                                +src+
                                " alt='' style='height: 120px;'/></div>  " +
                                " <p class='do-pro-t ellipsis-25' name='goodsName'>"
                                +goodsName+
                                " </p> " + priceStr +
                                colorStr +
                                "</a> " +
                                "<i class='icon-collect j_collect hide' onclick='addToWish("+now+","+2+")'  data-value="
                                +firstItem.id+
                                " data-ids="+firstSizeid+"> <svg class='do-heart hide'><use xlink:href='#heart'></use></svg> </i> </li>";
                        //console.log(str);
                        //滚动条滚动一段距离
                        $(".j_scroll_list").append(str);
                        setPrice();
                        /*  var t = $(window).scrollTop();
                         $('body,html').animate({scrollTop:t+500},100);*/
                    }
                }else {
                    $(".moreGoods").hide().siblings(".overGoods").show();
                }
            }else {
                layer.msg('<spring:message  code="goods.list.loadFailed"/>');
            }
        });
    };

</script>

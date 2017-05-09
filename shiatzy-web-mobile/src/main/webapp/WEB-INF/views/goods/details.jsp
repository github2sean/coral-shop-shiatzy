<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-commodity">
    <div class="content" >
        <div class="title">${goodsDomain.name}</div>
        <div class="number">产品编号 ${goodsItemDomain.goodsNo}</div>
        <a href="javascript:;" class="icon iconfont magnify">&#xe630;</a>
        <div class="dx-bag-slide">
            <ul class="j_s_slider">
                <c:forEach var="item" items="${ImageModel.toList(goodsItemDomain.photos)}">
                    <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="price">&yen; <span>${goodsItemDomain.price}</span><a href="javascript:;" class="j_collect active"><svg><use xlink:href="#heart"></use></svg></a></div>

        <div class="color">
            <div class="title j_choose">${goodsItemDomain.goodsColor.name}(还有${goodsDomain.goodsItemList.size()-1}款颜色)</div>
            <c:if test="${goodsDomain.goodsItemList.size()>1}">
            <ul class="clearfix hide">
            <c:forEach var="item" items="${goodsDomain.goodsItemList}">
                <c:if test="${item.id!=goodsItemDomain.id}">
                 <li><a href="/goods/details/${item.id}"><img src="${ImageModel.toFirst(item.thumb).file}" alt="" style="width:70px;margin-bottom: 10px;"></a></li>
                </c:if>
            </c:forEach>
            </ul>
            </c:if>
        </div>
        <div class="size">
            <div class="title j_choose">选择尺寸 &nbsp; <span>查看尺寸指南</span></div>
            <ul class="hide" id="js_size">
                <c:forEach var="item" items="${sizeList}" varStatus="status" >
                <li class="<c:if test="${status.first}">active</c:if>" data-value="${item.id}"><a href="#" >${item.name}<span></span></a> </li>
                </c:forEach>
            </ul>
        </div>
        <a type="button" class="addition addToCart">添加到购物袋</a>
        <a type="button" class="order addToBoutique">精品店预约</a>
        <div class="remind"><span class="icon iconfont">&#xe77d;</span>什么是精品店预约</div>
    </div>
    <div class="dx-GoodsDetails">
        <div class="title j_choose">产品详细信息</div>
        <p class="text hide">品牌标志性的玉镯提包，以刺绣形式诠释出台湾插画家川贝母的童趣画作，为整体注入鲜活俏皮的时尚感。单品内设里衬，可轻松收纳你的必备物品；带拉链隔层则可将小件重要物品稳妥存放。不妨用其搭配本季刺绣服饰单品，完成total look的完美搭配。</p>
        <ul class="list hide">
            <li>贴布刺绣，玉镯提手</li>
            <li>100% 成牛皮</li>
            <li>意大利制造</li>
            <li>产品尺寸：24.5厘米/9.6英寸（长），16厘米/6.3英寸（宽），20厘米/7.9英寸（高）</li>
        </ul>
    </div>
    <div class="maybeLike clearfix">
        <div class="title">您也许也喜欢</div>
        <div class="left">
            <div class="pic">
                <a href=""><img src="images/bag_10_07.jpg" alt=""></a>
            </div>
            <div class="name">厚底系带楔型鞋</div>
            <div class="price">&yen; 3,090</div>
            <ul class="color clearfix">
                <li style="background-color: #000"></li>
                <li style="background-color: #b5272d"></li>
                <li style="background-color: #1b1464"></li>
                <li style="background-color: #662d85"></li>
                <li style="background-color: #fff"></li>
            </ul>
        </div>
        <div class="left" style="border-right: none">
            <div class="pic">
                <a href=""><img src="images/bag_10_09.jpg" alt=""></a>
            </div>
            <div class="name">厚底系带楔型鞋</div>
            <div class="price">&yen; 3,090</div>
            <ul class="color clearfix">
                <li style="background-color: #000"></li>
                <li style="background-color: #b5272d"></li>
                <li style="background-color: #1b1464"></li>
                <li style="background-color: #662d85"></li>
                <li style="background-color: #fff"></li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    var selectSizeId=${sizeList[0].id};
    $(function(){
        commonApp.init();

        $(".j_s_slider").bxSlider({
            auto:false,
            pager:false,
            controls:true
        });

        $("#js_size").find("li").each(function () {
            $(this).on("click",function () {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                selectSizeId = $(this).data("value");

            })
        })

        $(".j_choose").on("click",function () {
            $(this).siblings().toggleClass("hide");
        })

        $(".addToCart").click(function () {
            var isLogin = '${user_context}';
            if(isLogin==''){
                location.href="/passport/toLogin";
            }
            var skuId = ${goodsItemDomain.id};
            var url = "/cart/addToCart";
            var data = {"itemId":skuId,"num":1,"sizeId":selectSizeId,"type":1};
            $.post(url,data,function (result) {
                console.log(result);
                if(result.code==200){
                    console.log(result.message);
                    location.href="/cart/list";
                }
            });
        });
        $(".addToBoutique").click(function () {
            var isLogin = '${user_context}';
            if(isLogin==''){
                location.href="/passport/toLogin";
            }
            var skuId = ${goodsItemDomain.id};
            var url = "/boutique/addToBoutique";
            var data = {"skuId":skuId,"num":1,"type":3};
            $.post(url,data,function (result) {
                console.log(result);
                if(result.code==200){
                    console.log(result.message);
                    location.href="/boutique/list";
                }
            });
        });
    });
</script>

<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-wish clearfix">
    <div class="dx-title">愿望清单<a href="我的账户.首页.html">回上页</a></div>
    <div class="content">
        <div class="dx-total">
            <div class="title">您的收藏</div>
            <ul class="list">
                <li>女士</li>
                <li class="active">男士</li>
                <li>包袋</li>
                <li>鞋履</li>
            </ul>
        </div>
        <div class="dx-GoodsDetails">
            <div class="goods clearfix">
                <div class="goods-left">
                    <div class="pic"><img src="images/goods-pic02.jpg" alt=""></div>
                </div>
                <div class="goods-right">
                    <div class="name">玉镯提包系列黑色刺绣托特包</div>
                    <div class="number">产品编号 1B1184 Z</div>
                    <div class="color">黑色<span>M号</span></div>
                    <div class="preferential-price delete">单价&nbsp; &yen; <span>11,504</span></div>
                    <div class="price">优惠价&nbsp; &yen; <span>10,504</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_bag icon-bag"><svg><use xlink:href="#bag"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_appointment"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            <div class="goods clearfix">
                <div class="goods-left">
                    <div class="pic"><img src="images/goods-pic02.jpg" alt=""></div>
                </div>
                <div class="goods-right">
                    <div class="name">玉镯提包系列黑色刺绣托特包</div>
                    <div class="number">产品编号 1B1184 Z</div>
                    <div class="color">黑色<span>M号</span></div>
                    <div class="price"><span class="SoldOut">已告罄</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_bag active icon-bag"><svg><use xlink:href="#bag-active"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_appointment active"><svg><use xlink:href="#ap-active"></use></svg></a></li>
                    <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
        </div>
        <div class="dx-clause">
            <ul>
                <li><a href="#">选购女士</a></li>
                <li><a href="#">选购男士</a></li>
                <li class="last"><a href="#">什么是愿望清单？</a></li>
            </ul>
        </div>
    </div>
</div>
<script>

    $(function () {
        //console.log('${goodsList}');
    });
</script>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


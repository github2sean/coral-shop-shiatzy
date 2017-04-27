<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-shopping">
    <div class="dx-title clearfix">
        <div class="member"><span><svg><use xlink:href="#cart-nav"></use></svg></span>购物车</div>
        <a href="#" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
    <div class="content">
        <div class="dx-GoodsDetails">
            <div class="goods clearfix">
                <div class="goods-left">
                    <div class="pic"><img src="images/goods-pic02.jpg" alt=""></div>
                </div>
                <div class="goods-right">
                    <div class="name">玉镯提包系列黑色刺绣托特包</div>
                    <div class="number">产品编号 1B1184 Z</div>
                    <div class="color">黑色<span>M号</span></div>
                    <div class="quantity">数量: <a href="#" class="minus">-</a><input class="quantitys" type="text" value="1"><a href="#" class="add">+</a></div>
                    <div class="price">单价&nbsp; &yen; <span>11,504</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_appointment"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect"><svg><use xlink:href="#heart"></use></svg></a></li>
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
                    <div class="quantity">数量: <a href="#" class="minus">-</a><input class="quantitys" type="text" value="1"><a href="#" class="add">+</a></div>
                    <div class="price">单价&nbsp; &yen; <span>11,504</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_appointment active"><svg><use xlink:href="#ap-active"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect active"><svg><use xlink:href="#heart-red"></use></svg></a></li>
                    <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            <div class="total">小计 <span>&yen; &nbsp;23,008</span></div>
        </div>
    </div>
    <div class="shopping-start">
        <a href="购物车.结算页.html" class="shopping">查看购物袋 / 结帐</a>
        <div class="dx-clause">
            <ul>
                <li><a href="#">购物说明</a></li>
                <li><a href="#">使用条款</a></li>
                <li class="last"><a href="#">配送规则</a></li>
            </ul>
        </div>
    </div>
</div>
<script>

    $(function () {
        //console.log('${goodsList}');
    });
</script>
<script>
    $(function(){
        commonApp.init();
        //点击数量增加减少
        $(".add").on("click",function () {
            var t = $(this).parent().find(".quantitys");
            t.val(parseInt(t.val())+1);
            $(".minus").removeAttr("disabled");
        });
        $(".minus").on("click",function () {
            var t = $(this).parent().find(".quantitys");
            if (parseInt(t.val())>1){
                t.val(parseInt(t.val())-1);
            }else {
                $("#min").attr("disabled","disabled");
            }
        })

    });
</script>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


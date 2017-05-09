<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-wish clearfix">
    <div class="dx-title">愿望清单<a href="/u/account/index">回上页</a></div>
    <div class="content">
        <c:if test="${not empty wishList}">
        <div id="toggleDiv">
            <div class="dx-total">
            <div class="title">您的收藏</div>
            <ul class="list hide">
                <li>女士</li>
                <li class="active">男士</li>
                <li>包袋</li>
                <li>鞋履</li>
            </ul>
            </div>
        </div>
        </c:if>
        <c:if test="${empty wishList}">
            <div id="toggleDiv2">
            <div class="dx-collect">愿望清单（0）</div>
            </div>
        </c:if>
    </div>
        <div class="dx-GoodsDetails">
            <c:forEach var="row" items="${wishList}">
            <div class="goods clearfix goodsDiv">
                <div class="goods-left">
                    <div class="pic"><img src="images/goods-pic02.jpg" alt=""></div>
                </div>
                <div class="goods-right">
                    <div class="name">${row.goodsName}</div>
                    <div class="number">${row.goodsCode}</div>
                    <div class="goods_color" data-value=${row.skuSpecifications}>黑色&nbsp;&nbsp;&nbsp;&nbsp;<span>M号</span></div>
                    <div class="preferential-price">单价&nbsp; &yen; <span>${row.goodsPrice}</span></div>
                    <div class="price">优惠价&nbsp; &yen; <span>10,504</span></div>
                </div>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_bag icon-bag toCart" data-value="${row.id}"><svg><use xlink:href="#bag"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_appointment toBoutique" data-value="${row.id}"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            </c:forEach>

        </div>
        <div class="dx-clause">
            <ul>
                <li><a href="#">选购女士</a></li>
                <li><a href="#">选购男士</a></li>
                <li class="last"><a href="#">什么是愿望清单？</a></li>
            </ul>
        </div>
</div>



<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    $(function () {

        if(${empty wishList}){
            $("#toggleDiv2").show();
        }else{
            $("#toggleDiv2").hide();
        }

        $(".toCart").each(function () {
            var $nowDiv = $(this);
            $nowDiv.click(function () {
                var cartId = $(this).attr("data-value");
                $.post("/cart/wishToCart",{"shoppingCartItemId":cartId},function (data) {
                    console.log(data);
                    if(data.code==200){
                        console.log(data.code);
                        $nowDiv.parents(".goodsDiv").remove();
                    }
                });
            });
            
        });
        
        

        /*$(".goods_color").each(function () {
            var str = $(this).attr("data-value");
            if(str!=null && str!=""){
                str = str.replace("，",",");
                str = str.replace("：",":");
                str = str.replace("“","\"");
                str = str.replace("”","\"");
                str = str.replace("｛","{");
                str = str.replace("｝","}");
            }
            console.log("str:"+str);
            jsonObj = jQuery.parseJSON(str);
            $(this).text(jsonObj.color).css("font-size",".7rem").append("<span class='goods_size' style='margin-left: 40px'></span>").find(".goods_size").text(jsonObj.size);
        });*/
    });
</script>
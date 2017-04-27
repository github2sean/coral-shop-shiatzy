<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="container">
    <div class="do-list-header">
        <a href="javascript:;" class="link-down font-16 j_panel_trigger" data-panel="j_panel_cat">玉鐲提包</a>
        <div class="pull-right font-12">
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_filter">筛选</a>
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_sort">排序</a>
        </div>
    </div>
    <ul class="do-pro-list">
        <c:forEach var="row" items="${goodsList}">

        <li>
            <a href="商品详情.html">
                <div class="do-img">
                    <img src="images/list-img1.jpg" alt="">
                </div>
                <p class="do-pro-t ellipsis-2l" name="goodsName">${row.name}</p>
                <p class="do-pro-price ellipsis" name="goodsPrice">¥ 3,090</p>
                <ul class="do-list-color" name="skuId" data-value="${row.skuId}">
                    <li style="background: #000000"></li>
                    <li style="background: #b5272d"></li>
                    <li style="background: #1b1464"></li>
                    <li style="background: #662d85"></li>
                    <li style="background: #fff;border:1px solid #000"></li>
                </ul>
            </a>
            <!--Todo:收藏按钮-->
            <i class="icon-collect j_collect active" data-value="${row.id}">
                <svg class="do-heart"><use xlink:href="#heart-red"></use></svg>
            </i>
        </li>

        </c:forEach>
        <%--<li>
            <a href="商品详情.html">
                <div class="do-img">
                    <img src="images/list-img1.jpg" alt="">
                </div>
                <p class="do-pro-t ellipsis-2l">${row.name}</p>
                <p class="do-pro-price ellipsis">¥ 3,090</p>
                <ul class="do-list-color">
                    <li style="background: #000000"></li>
                    <li style="background: #b5272d"></li>
                    <li style="background: #1b1464"></li>
                    <li style="background: #662d85"></li>
                    <li style="background: #fff;border:1px solid #000"></li>
                </ul>
            </a>
            <!--Todo:收藏按钮-->
            <i class="icon-collect j_collect active">
                <svg class="do-heart"><use xlink:href="#heart-red"></use></svg>
            </i>
        </li>
        <li>
            <a href="商品详情.html">
                <div class="do-img">
                    <img src="images/list-img2.jpg" alt="">
                </div>
                <p class="do-pro-t">厚底系带楔型鞋</p>
                <p class="do-pro-price">¥ 3,090</p>
                <ul class="do-list-color">
                    <li style="background: #000000"></li>
                    <li style="background: #b5272d"></li>
                    <li style="background: #1b1464"></li>
                    <li style="background: #662d85"></li>
                    <li style="background: #fff;border:1px solid #000"></li>
                </ul>
            </a>
            <!--Todo:收藏按钮-->
            <i class="icon-collect j_collect">
                <svg class="do-heart"><use xlink:href="#heart"></use></svg>
            </i>
        </li>
        <li>
            <a href="商品详情.已售完.html">
                <div class="do-img">
                    <img src="images/list-img3.jpg" alt="">
                </div>
                <p class="do-pro-t">厚底系带楔型鞋</p>
                <p class="do-pro-price">¥ 3,090</p>
                <ul class="do-list-color">
                    <li style="background: #000000"></li>
                    <li style="background: #b5272d"></li>
                    <li style="background: #1b1464"></li>
                    <li style="background: #662d85"></li>
                    <li style="background: #fff;border:1px solid #000"></li>
                </ul>
            </a>
            <!--Todo:收藏按钮-->
            <i class="icon-collect j_collect">
                <svg class="do-heart"><use xlink:href="#heart"></use></svg>
            </i>
        </li>--%>
    </ul>
    <div class="font-12 text-center do-load-list">
        <span class="link-down-before">向下自动载入</span>
        <!--<span>-已到底部-</span>-->
    </div>
</div>
<div id="j_panel_cat" class="pro-filter-panel panel-cat">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <ul class="do-sort-list">
        <li><a href="">玉鐲提包(5)</a></li>
        <li><a href="">手提包(3)</a></li>
        <li><a href="">晚宴包(1)</a></li>
        <li><a href="">托特包(1)</a></li>
    </ul>
</div>
<div id="j_panel_filter" class="pro-filter-panel panel-filter">
    <a href="javascript:;" class="iconfont j_close_panel do-close-panel">&#xe67d;</a>
    <form action="">
        <div class="do-sort link-down">筛选<button type="reset" class="btn-reset">重置筛选</button></div>
        <div class="do-sort-cat j_sort_cat">
            <div class="cat-t link-down">颜色</div>
            <div class="do-sort-group">
                <input type="checkbox" id="color1">
                <label for="color1">红(10)</label>
            </div>
            <div class="do-sort-group">
                <input type="checkbox" id="color2">
                <label for="color2">蓝(5)</label>
            </div>
            <div class="do-sort-group">
                <input type="checkbox" id="color3">
                <label for="color3">黑(8)</label>
            </div>
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
<script>

    $(function () {
        //console.log('${goodsList}');

        //var cartNum  = $(".do-num").val();
        $(".j_collect").each(function () {
            $(this).click(function () {
                var goodsId = $(this).attr("data-value");
                var data  = {"goodsId":goodsId};
                $.post("/cart/add",data,function (data) {
                    if(data.data==1){
                        console.log(data.message);
                    }
                });
            });

        });

    });
</script>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>


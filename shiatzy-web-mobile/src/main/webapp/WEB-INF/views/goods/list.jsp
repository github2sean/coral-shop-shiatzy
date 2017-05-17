<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="container">
    <div class="do-list-header">
        <a href="javascript:;" class="link-down font-16 j_panel_trigger" data-panel="j_panel_cat">${categoryName}</a>
        <div class="pull-right font-12">
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_filter">筛选</a>
            <a href="javascript:;" class="link-down j_panel_trigger" data-panel="j_panel_sort">排序</a>
        </div>
    </div>

    <ul class="do-pro-list">
        <c:forEach var="goods" items="${goodsDomainPageList.list}" varStatus="num">
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
        <li>
            <a href="/goods/details/${firstItem.id}">
                <div class="do-img">
                    <img src="${ImageModel.toFirst(goods.thumb).file}" alt="" style="height: 120px;">
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
            <i class="icon-collect j_collect active" data-value="${firstItem.id}" data-ids="${sizeList[num.count-1].id}">
                <svg class="do-heart"><use xlink:href="#heart"></use></svg>
            </i>
        </li>
        </c:forEach>
    </ul>
    <div class="font-12 text-center do-load-list">
        <span class="link-down-before">向下自动载入</span>
        <span style="display: none">-已到底部-</span>
    </div>
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
        <li><a href="/goods/list?categoryId=${categoryId}&priceWay=0">售价（高-低）</a></li>
        <li><a href="/goods/list?categoryId=${categoryId}&priceWay=1">售价（低-高）</a></li>
    </ul>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    var hght=0;//初始化滚动条总长
    var top=0;//初始化滚动条的当前位置
    $(document).ready(function(){//DOM的onload事件
        $(".link-down-before").load("list.jsp");//页面内容被加载到link-down-before元素

        $(".link-down-before").scroll( function() {//定义滚动条位置改变时触发的事件。
            hght=this.scrollHeight;//得到滚动条总长，赋给hght变量
            top=this.scrollTop;//得到滚动条当前值，赋给top变量
        });
    });

    setInterval("cando();",2000);//每隔2秒钟调用一次cando函数来判断当前滚动条位置。

    function cando(){
        if(top>parseInt(hght/3)*2)//判断滚动条当前位置是否超过总长的2/3，parseInt为取整函数
            show();//如果是，调用show函数加载内容。
    }

    function show(){
        $.get("list.jsp", function(data){//利用jquery的get方法得到list.jsp内容
            $(".link-down-before").append(data);//用append方法追加内容到link-down-before元素。
            hght=0;//恢复滚动条总长，因为$(”#mypage”).scroll事件一触发，又会得到新值，不恢复的话可能会造成判断错误而再次加载……
            top=0;//原因同上。
        });
    }
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


    });
</script>

<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="order">
    <p class="pull-left">门市预约 / 详细</p>
    <a class="pull-right" href="#">< 回上页</a>
</div>
<div class="order-main clearfix">
    <c:forEach var="row" items="${preOderItemList}">

            <div class="order-main-left">
                <p class="product-num">产品编号 ${row.leftItem.goodsCode}</p>
                <img src="images/order_01.png" alt="">
                <p class="product-name">${row.leftItem.goodsName}</p>
                <div class="color-size">
                    <p>黑色</p>
                    <p>M号</p>
                </div>
                <p class="price">单价　¥ ${row.leftItem.goodsPrice}</p>
                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_appointment"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                    <li><a href="javascript:;" class="j_collect"><svg><use xlink:href="#heart"></use></svg></a></li>
                    <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
                </ul>
            </div>
            <c:if test="${not empty row.rightItem}">
                <div class="order-main-right">
                    <p class="product-num">产品编号 ${row.rightItem.goodsCode}</p>
                    <img src="images/order_02.png" alt="">
                    <p class="product-name">${row.rightItem.goodsName}</p>
                    <div class="color-size">
                        <p>黑色</p>
                        <p>M号</p>
                    </div>
                    <p class="price">单价　¥ ${row.rightItem.goodsPrice}</p>
                    <ul class="do-list-icon">
                        <li><a href="javascript:;" class="j_appointment"><svg><use xlink:href="#ap-small"></use></svg></a></li>
                        <li><a href="javascript:;" class="j_collect"><svg><use xlink:href="#heart"></use></svg></a></li>
                        <li><a href=""><svg><use xlink:href="#close"></use></svg></a></li>
                    </ul>
                </div>
            </c:if>
    </c:forEach>
</div>
<div class="order-price" >
    <p>预计订单总额：¥ 10,404</p>
</div>
<div class="order-stores">
    <h1>选择预约门市</h1>
    <div class="model-select-box">
        <div class="model-select-country" data-value="">
            <span class="pl-2">请选择国家</span>
            <span class="pull-right">v</span>
            <ul class="text-center model-select-option" id="countrySelect">
            <c:forEach var="row" items="${storeCountryList}">
                <li data-option="${row.id}" value="${row.id}">${row.name}</li>
            </c:forEach>
            </ul>
        </div>
    </div>
    <div class="model-select-box">
        <div class="model-select-city" data-value="">
            <span class="pl-2">请选择省/州</span>
            <span class="pull-right">v</span>
        </div>
        <ul class="text-center model-select-option" id="citySelect">
            <c:forEach var="row" items="${storeCityList}">
                <li data-option="${row.id}" value="${row.id}">${row.name}</li>
            </c:forEach>
        </ul>
    </div>
    <div class="model-select-box">
        <div class="model-select-store" data-value="">
            <span class="pl-2">请选择门市</span>
            <span class="pull-right">v</span>
        </div>
        <ul class="text-center model-select-option" id="storeSelect">
        <c:forEach var="row" items="${storeList}">
            <li data-option="${row.id}" value="${row.id}">${row.name}</li>
        </c:forEach>
        </ul>
        <%--<ul class="text-center model-select-option">
            <li data-option="2">上海國金中心精品店</li>
            <li data-option="3">上海青浦米格天地店</li>
            <li class="active" data-option="4">上海恒隆精品店</li>
            <li data-option="5" class="seleced">上海奕歐來店</li>
        </ul>--%>
    </div>
    <div class="stores-introduce">
        <h2>上海恒隆精品店</h2>
        <p>上海市靜安區南京西路1266號恆隆廣場</p>
        <p>3F321鋪</p>
        <p>+86 21 6240 0910</p>
    </div>
    <div class="submit-btn">
        <a href="#" class="sendBtn">送出预约</a>
    </div>
</div>


<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    //下拉列表
    $(function () {
        function selectModel() {
            var $box = $('div.model-select-box');
            var $option = $('ul.model-select-option', $box);
            var $txt = $('div.model-select-store', $box);
            var speed = 10;
            $txt.click(function (e) {
                $option.not($(this).siblings('ul.model-select-option')).slideUp(speed, function () {
                    int($(this));
                });
                $(this).siblings('ul.model-select-option').slideToggle(speed, function () {
                    int($(this));
                });
                return false;
            });
            $option.find('li').each(function (index, element) {
                        if ($(this).hasClass('seleced')) {
                            $(this).addClass('data-selected');
                        }
                    })
                    .mousedown(function () {
                        $(this).parent().siblings('div.model-select-store').text($(this).text())
                                .attr('data-value', $(this).attr('data-option'));
                        $option.slideUp(speed, function () {
                            int($(this));
                        });
                        $(this).addClass('seleced data-selected').siblings('li').removeClass('seleced data-selected');
                        return false;
                    })
                    .mouseover(function () {
                        $(this).addClass('seleced').siblings('li').removeClass('seleced');
                    });
            $(document).click(function (e) {
                $option.slideUp(speed, function () {
                    int($(this));
                });
            });
            function int(obj) {
                obj.find('li.data-selected').addClass('seleced').siblings('li').removeClass('seleced');
            }
        }
        selectModel();


        $(".sendBtn").click(function () {

            var storeId = 1;

            $.post("/reservation/submitPreOrder",{"storeId":storeId},function (data) {
                if(data.code==200){
                    location.href = "/reservation/details?reservationId="+data.data;
                }
            })
        });
    })
</script>

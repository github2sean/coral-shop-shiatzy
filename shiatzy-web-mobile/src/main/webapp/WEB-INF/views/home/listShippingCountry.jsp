<%--suppress ALL --%>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-shopping">
    <div class="dx-title clearfix">
        <div class="member">配送地区</div>
        <a href="/home/index" class="icon iconfont" type="button">&#xe67d;</a>
    </div>
        <div class="content" style="margin-top: 20px">
            <div style="margin-top: 20px;font-size:1.83rem;text-transform:uppercase">配送地区</div>
            <div style="margin-top: 20px;font-size: .75rem">请选择你所在地区</div>
            <div style="margin: auto;width: 20%;height: 5px;background-color: black;margin-top: 20px">
            </div>
            <div class="model-select-box">
                    <div style="margin: auto;height: 2rem;line-height:2rem;border: #cccccc solid 1px;width:50%;text-align: center;font-size: 14px;margin-top: 20px;margin-bottom: 5px" id="chooseCountry">最终订单配送地区<span class="pull-right">v</span></div>
                    <ul class="text-center model-select-option" style="display: none;">
                        <c:forEach var="row" items="${countryList}">
                            <li data-option="${row.id}" value="${row.id}" class="option <c:if test="${row.id==1}">active</c:if>">${row.name}</li>
                        </c:forEach>
                    </ul>
            </div>
            <div class="checked" style="font-size: 1.0821rem;border: 2px solid #cccccc;border-left: none;border-right: none;line-height: 2.5507rem;background-color: #cccccc">
                <c:if test="${not empty sessionScope.shippingCountryId}">
                    <c:forEach var="row" items="${countryList}">
                        <c:if test="${sessionScope.shippingCountryId ==row.id}">${row.name}</c:if>
                    </c:forEach>
                </c:if>
            </div>

        </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function () {

        $("#chooseCountry").click(function () {
            $(".model-select-option").slideToggle(500);
        });
        $(".option").click(function () {
            var active = "active";
            $(this).addClass(active).siblings().removeClass(active).parents(".model-select-option").slideUp(700);
            var name = $(this).text();
            $(".checked").text(name);
            $.post("/home/chooseShippingCountry",{"shippingCountryId":$(this).val()},function (data) {
                console.log(data);
            });
        });
    });
</script>

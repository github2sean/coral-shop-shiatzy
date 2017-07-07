<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>

<div class="dx-shipping">
    <div class="dx-title">${web:t("配送地址 ","SHIPPING ADDRESS")} <a href="/u/returnOrder/chooseReturnWay">${web:t("返回上页 ","GO BACK")}</a></div>
    <div class="content">
        <a href="/checkout/createShipAddress" class="new-address">${web:t("新增地址 ","NEW ADDRESS")} +</a>
        <c:forEach var="row" items="${addressList}" varStatus="num">
            <div class="address addressDiv" >
                <div class="title">${web:t("地址 ","ADDRESS")}${num.count} <span data-value="${row.id}" class="icon iconfont" style="background-color: inherit;border:1px solid #333">&#xe618;</span></div>
                <div class="text clearfix">
                    <div class="text-left">
                        <div class="postcode">${row.title}</div>
                        <div class="street">${row.address}</div>
                        <div class="tel">${web:t("电话 ","PHONE")}：${row.phone}</div>
                    </div>
                    <div class="text-right">
                        <div class="compile"><a href="/checkout/updateShipAddress?addressId=${row.id}" class="updateBtn" data-value="${row.id}">${web:t("编辑 ","EDIT")} ></a></div>
                        <div class="del delBtn" data-value="${row.id}"><a href="#"  >${web:t("删除 ","DELETE")} -</a></div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="btn saveBtn" data-value="unChecked">
        <a href="#" type="button" class="accounts-btn">&lt; ${web:t("确认 ","DONE")}</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>
<script>
    $(function(){

        $(".iconfont").click(function () {
            $(this).css("background-color","#333");
            $(this).parents(".addressDiv").siblings().find(".iconfont").css({"background-color":"white","border":"1px solid black"});
            var id = $(this).attr("data-value");
            $.post("/u/returnOrder/sureReturnWay",{"backWay":1,"addressId":id},function (data) {
                if(data.code==200){
                    $(".saveBtn").attr("data-value","checked");
                }else{
                    layer.msg(data.message);
                }
            });
        });
        $(".saveBtn").click(function(){
            if($(this).attr("data-value")!='checked'){
                layer.msg("${web:t("请先选地址 ","Select Address")}");
                return false;
            }
            location.href = "/u/returnOrder/chooseReturnWay";
        });
        $(".delBtn").click(function () {
            var $now = $(this);
            var id = $now.attr("data-value");
            $.post("/checkout/removeAddress",{"addressId":id},function (data) {
                console.log(data);
                if(data.code==200){
                    $now.parents(".addressDiv").remove();
                }
            });
        });

    });
</script>

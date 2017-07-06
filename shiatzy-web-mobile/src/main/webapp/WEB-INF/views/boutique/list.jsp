<%@ page import="com.dookay.coral.common.model.ImageModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<jsp:include page="/WEB-INF/views/include/header.jsp">
    <jsp:param name="nav" value="商品"/>
    <jsp:param name="pageTitle" value="商品列表"/>
</jsp:include>
<div class="dx-shopping">
<div class="dx-title" style="background-color: #999999">

    <div class="member"><span><svg><use xlink:href="#appointment-nav"></use></svg></span><spring:message
            code="reservation"/></div>

    <a onclick="history.go(-1)" class="icon iconfont" type="button">&#xe67d;</a>
</div>

<c:if test="${empty cartList}">
    <div class="content dx-wish dx-shopping">
        <div id="toggleDiv2">
          <a href="/home/index"> <div class="message"><p><spring:message code="reservation.list"/>(0)</p></div></a>
        </div>
    </div>
</c:if>

    <style>
        .goods-item:nth-of-type(even) {
            border-right: 0;
        }
        .goods-item:nth-of-type(odd) {

        }
        .goods-item{
            float: left;
            padding: 1rem 0;
            border-right: 1px solid #ccc;
            text-align: center;
            font-size: 1rem;
            width: 50%;
            border-bottom: 1px solid #ccc;
            position: relative;
        }
        .goods-item .do-list-icon{ top: 3rem;}
        .goods-item .pic {text-align: center;}
        .goods-item .pic img{    max-height: 100%;width: 10rem;}
    </style>
    <div class="goods-list clearfix">
        <c:forEach var="row" items="${cartList}">
            <div class="goods-item goodsDiv">
                <p class="product-num"><spring:message code="shoppingCart.no"/>${row.goodsName} ${row.goodsCode}</p>
                <div class="pic">
                    <img src="${ImageModel.toFirst(row.goodsItemDomain.thumb).file}" alt="">
                </div>
                <p class="product-name">${web:selectLanguage()=='en_US'?row.goodsEnName:row.goodsName}</p>
                <div class="color-size">
                    <span>${web:selectLanguage()=='en_US'?row.goodsItemDomain.enName:row.goodsItemDomain.name}</span>
                    <span><spring:message
                            code="shoppingCart.size"/>:&nbsp;${web:selectLanguage()=='en_US'?row.sizeDomain.enName:row.sizeDomain.name}</span>
                    &nbsp;&nbsp;
                    <c:if test="${row.stock>0}"><span class="sellOut" data-value="${row.id}"></span></c:if>
                    <c:if test="${row.stock<1}"><span class="sellOut hasOut" data-value="${row.id}">（<spring:message code="sellout" />）</span></c:if>
                </div>
                <p style="display: inline;" class="price ${row.goodsDisPrice!=0?'xzc-price':''}"><spring:message code="shoppingCart.unitPrice"/>&nbsp;
                    <span class="do-pro-price" data-value="${row.goodsPrice}">&nbsp;</span>
                </p>
                <c:if test="${row.goodsDisPrice!=0}">
                    <p class="price xzc-dis-price" style="display: inline;margin-left: 1rem"><spring:message code="shoppingCart.disPrice"/>&nbsp;
                        <span class="do-pro-price" data-value="${row.goodsDisPrice}">&nbsp;</span>
                    </p>
                </c:if>

                <ul class="do-list-icon">
                    <li><a href="javascript:;" class="j_bag icon-bag" data-value="${row.id}"
                           <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if> >
                        <svg>
                            <use xlink:href="#bag"></use>
                        </svg>
                    </a></li>
                    <li><a href="javascript:;" class="j_collect" data-value="${row.id}"
                           <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if>  >
                        <svg>
                            <use xlink:href="#heart"></use>
                        </svg>
                    </a></li>
                    <li><a href="" class="deleteBtn" data-value="${row.id}"
                           <c:if test="${not empty row.formId}">data-formid='${row.formId}'</c:if> >
                        <svg>
                            <use xlink:href="#close"></use>
                        </svg>
                    </a></li>
                </ul>
            </div>
        </c:forEach>
    </div>
<div class="explain">

    <c:if test="${empty cartList}">
        <div class="choose-store">
            <a class="toRegister" href="javascript:void(0)"><spring:message code="register"/>&nbsp;/&nbsp;<spring:message code="login"/></a>
        </div>
    </c:if>
    <c:if test="${not empty cartList}">
    <div class="choose-store">
        <a class="findStore" data-href="${isGuest!='onLine'?'/passport/toLogin':'/reservation/initChoose'}"><spring:message code="reservation.findstore"/></a>
    </div>
    </c:if>
    <ul>
        <li><a href="/goods/list?categoryId=1"><spring:message code="shoppingCart.selectWoman"/><span>></span></a></li>
        <li><a href="/goods/list?categoryId=8"><spring:message code="shoppingCart.selectMan"/><span>></span></a></li>
        <li><a href="#" class="whatBoutique"><spring:message code="reservation.what"/>?<span>></span></a></li>
    </ul>
</div>
</div>

<jsp:include page="/WEB-INF/views/include/footer.jsp">
    <jsp:param name="nav" value="首页"/>
</jsp:include>

<script>

    function checkout() {
        var isLogin = '${isGuest}'=='onLine'?true:false;
        var seconds = 0;
        $(".color-size").find(".sellOut").each(function () {
            var $self = $(this);
            if($self.hasClass("hasOut")){
                seconds = 3000;
                var index = layer.load(2, {
                    shade: [0.4,'#000'] //0.1透明度的白色背景
                });
                var id  = isLogin?$self.attr("data-value"):$self.parents(".color-size").siblings(".do-list-icon").find(".deleteBtn").attr("data-formid");
                var url = isLogin?"/cart/removeFromCart":"/cart/removeFromSessionCart";
                var data = isLogin?{"shoppingCartItemId":id}:{"formId":id};
                $.post(url,data,function (data) {
                    console.log(data);
                    if(data.code==200){
                        $self.parents(".goodsDiv").remove();
                        isDelete = true;
                        console.log("isDelete1:"+isDelete);
                        var  isNull= $(".goodsDiv").length<=0?true:false;
                        setCartNum();
                        if(isNull){
                            setTimeout('window.location.reload();',800);
                        }else{
                            clsTotal();
                        }
                    }
                });
            }
        });

        var href = $(".choose-store").find(".findStore").attr("data-href");
        setTimeout(function(){
            layer.closeAll('loading');
            goTo(href);
        },seconds);
    }
    function goTo(href) {
        location.href=href;
    }
    $(function () {
        $(".top-right-nav").find("li:eq(1)").addClass("active");
        //console.log('${goodsList}');
        setPrice();


        $(".choose-store").find(".findStore").click(function(){
            if($(".goodsDiv").length>5){
                layer.msg('<spring:message code="reservation.list.less5"/>');
                return false;
            }
            var $sellOut = $(".goodsDiv").find(".sellOut");
            if($sellOut.hasClass("hasOut")){
                layer.msg('<spring:message code="shoppingCart.deleteOut"/>');
            }
            setTimeout(checkout,500);
            //location.href = $(this).attr("data-href");
        });

        $(".choose-store").find(".toRegister").click(function(){
            var href = "${ctx}/passport/toLogin";
            var user = '${sessionScope.user_context}';
            if(user!=''){
                href = '${ctx}/passport/toRegister';
            }
            location.href = href;
        });
        var isLogin = '${isGuest}'=='onLine'?true:false;
        $(".deleteBtn").on("click",function () {
            var $self = $(this);
            var id = isLogin?$(this).attr("data-value"):$(this).attr("data-formid");
            var url = isLogin?"/cart/removeFromCart":"/cart/removeFromSessionCart";
            var data = isLogin?{"shoppingCartItemId":id}:{"formId":id};
            console.log(id);
            $.post(url,data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }
                }
            });
        });
        $(".j_collect").on("click",function () {
            var $self = $(this);
            var id = isLogin?$(this).attr("data-value"):$(this).attr("data-formid");
            var url = isLogin?"/cart/boutiqueToWish":"/cart/changeFromSessionCartType";
            var data = isLogin?{"shoppingCartItemId":id}:{"formId":id,"goalType":2};
            console.log(id);
            $.post(url,data,function (data) {
                console.log(data);
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg("<spring:message code="success.towish" />");
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }
                }else{
                    layer.msg(data.message);
                }
            });
        });
        $(".j_bag").on("click",function () {
            var $self = $(this);
            var id = isLogin?$(this).attr("data-value"):$(this).attr("data-formid");
            var url = isLogin?"/cart/boutiqueToCart":"/cart/changeFromSessionCartType";
            var data = isLogin?{"shoppingCartItemId":id}:{"formId":id,"goalType":1};
            $.post(url,data,function (data) {
                if(data.code==200){
                    $self.parents(".goodsDiv").remove();
                    var  isNull= $(".goodsDiv").attr("class");
                    layer.msg('<spring:message code="success.tocart" />');
                    setCartNum();
                    if(typeof (isNull)=="undefined"){
                        window.location.reload();
                    }
                }
            });
        });



        //iframe窗
        $(".whatBoutique").click(function(){
            layer.open({
                type: 2,
                title: '<spring:message code="reservation.what"/>',
                closeBtn: 1, //不显示关闭按钮
                shade: [0],
                area: ['90%', '75%'],
                content: ['${ctx}/content/whatBoutique'],//iframe的url，no代表不显示滚动条
                shadeClose: true
            });
        });
    });
</script>
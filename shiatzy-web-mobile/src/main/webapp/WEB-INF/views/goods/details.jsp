<%@ taglib prefix="sping" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="/WEB-INF/views/include/taglib.jsp" %>

      <jsp:include page="/WEB-INF/views/include/header.jsp">
        <jsp:param name="nav" value="商品" />
        <jsp:param name="pageTitle" value="商品列表" />
      </jsp:include>

      <div class="dx-commodity">
        <div class="content">
          <div class="title">${sessionScope.language=='en_US'?goodsDomain.enName:goodsDomain.name}</div>
          <span class="number"><spring:message code="shoppingCart.no" /> ${goodsItemDomain.goodsNo}</span>
          <a href="javascript:;" class="icon iconfont magnify">&#xe630;</a>
          <div class="dx-bag-slide">
            <ul class="j_s_slider">
              <c:forEach var="item" items="${ImageModel.toList(goodsItemDomain.photos)}">
                <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
                <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
                <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
                <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
              </c:forEach>
            </ul>
          </div>
          <div class="price"><span class="do-pro-price" data-value="${goodsItemDomain.price}">&nbsp;</span>
            <a href="javascript:;" class="j_collect active">
              <svg transform="scale(1.5) ">
                <use xlink:href="#heart-red"></use>
              </svg>
            </a>
          </div>

          <div class="color">
            <div class="title j_choose">${sessionScope.language=='en_US'?goodsItemDomain.goodsColor.enName:goodsItemDomain.goodsColor.name}&nbsp;(<spring:message code="goods.detail.thereAre"/>&nbsp;${goodsDomain.goodsItemList.size()-1}&nbsp;<spring:message code="goods.detail.colors"/>)</div>
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
            <div class="title j_choose">
              <spring:message code="shoppingCart.size" /> &nbsp;&nbsp; <span class="sizeChecked"></span>&nbsp;&nbsp;<span class="sizeNotice"><spring:message code="goods.detail.sizeNotice"/></span></div>
            <ul class="hide" id="js_size">
              <c:forEach var="item" items="${sizeList}" varStatus="status">
                <li class="<c:if test=" ${status.first && item.stock>0}"></c:if>
                  <c:if test="${item.stock<1}">disabled</c:if> sizeIds" data-value="${item.id}"><a href="#">${item.name}&nbsp;&nbsp;&nbsp;<c:if test="${item.stock<1}">(<spring:message code="sellout"/>)</c:if><span></span></a>                  </li>
              </c:forEach>
            </ul>
          </div>
          <a type="button" class="addition addToCart"><spring:message code="goods.detail.add2cart"/></a>
          <a type="button" class="order addToBoutique"><spring:message code="goods.detail.add2reservation"/></a>
          <div class="remind whatBoutique"><span class="icon iconfont ">&#xe77d;</span><spring:message code="reservation.what"/></div>
        </div>
        <div class="dx-GoodsDetails">
          <div class="title j_choose"><spring:message code="goods.detail.details"/></div>
          <p class="text hide">${sessionScope.language=='en_US'?goodsDomain.enDetails:goodsDomain.details}</p>

        </div>
        <div class="maybeLike clearfix">
          <div class="title"><spring:message code="goods.detail.maybeLike"/></div>
          <c:forEach var="goods" items="${historyList}" begin="0" end="3">
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
            <div class="left">
              <a href="/goods/details/${firstItem.id}">
                <div class="pic">
                  <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                </div>
                <div class="name">${sessionScope.language=='en_US'?goodsDomain.enName:goodsDomain.name}</div>
                <div class="price do-pro-price" data-value="${firstItem.price}">&nbsp;</div>
                <ul class="color clearfix">
                  <c:forEach var="goodsItem" items="${goods.goodsItemList}">
                    <li style="background: ${goodsItem.colorValue}"></li>
                  </c:forEach>
                </ul>
              </a>
            </div>
          </c:forEach>
        </div>
      </div>

      <jsp:include page="/WEB-INF/views/include/footer.jsp">
        <jsp:param name="nav" value="首页" />
      </jsp:include>
      <script>
        var selectSizeId = '${sizeList[0].id}';
        $(function () {


          setPrice();
          $(".j_s_slider").bxSlider();

          // 添加相册
          function galleryAppend() {
            var htmlBefore =
              '<div class="gallery-block"><span class="iconfont icon-cha gallery-close"></span><ul class="gallery-img-list">';
            var htmlAfter = '</ul></div>';
            var htmlMain = '';
            $(".j_s_slider li").not(".bx-clone").each(function () {
              var imgUrl = $(this).find("img").attr("src");
              htmlMain += '<li><img src="' + imgUrl + '"></li>'
            })
            $('body').append(htmlBefore + htmlMain + htmlAfter);
            $(".gallery-img-list").bxSlider({
              pager: false
            });
          }

          //相册操作
          $(".dx-commodity .magnify").one("click touchstart", function () {
            if ($('.gallery-block').length === 0) galleryAppend();
          })

          $(".dx-commodity .magnify").on("click touchstart", function () {
            if ($('.gallery-block').is(":hidden")) $('.gallery-block').show();
          })

          $("body").on("click touchstart", '.gallery-close', function (e) {
            $(".gallery-block").hide();
          });


          var isSelected = false;
          $("#js_size").find("li").each(function () {
            if ($(this).hasClass("active")) {
              selectSizeId = $(this).attr("data-value");
              isSelected = true;
            }
          });

          $("#js_size").find("li").on("click", function () {
            if ($(this).hasClass("disabled")) {
              layer.msg('<spring:message code="goods.detail.thisIsSellOut"/>');
              return false;
            }
            $(this).parent("ul").addClass("hide");
            var size = $(this).find('a').text();
            $(".sizeChecked").text(size);
            $(this).addClass("active").siblings().removeClass("active");
            selectSizeId = $(this).data("value");
            isSelected = true;
          });

          $(".j_choose").on("click", function () {
            $(this).siblings().toggleClass("hide");
          });

          var isLogin = '${isGuest}' == 'onLine' ? true : false;
          $(".j_collect").click(function () {
            var url = "";
            var isAdd = $(this).find("use").attr("xlink:href");
            var sizeId = '';

            $(".sizeIds").each(function () {
              if ($(this).hasClass("active")) {
                sizeId = $(this).attr("data-value");
              }
            });
            var data = {
              "itemId": '${goodsItemDomain.id}',
              "num": 1,
              "sizeId": sizeId,
              "type": 2
            };
            console.log(isAdd);
            var url = "";
            if (isAdd == "#heart-red") {
              url = "/cart/addToCart";
            } else if (isLogin && isAdd == "#heart") {
              // $(this).find("use").attr("xlink:href","#heart");
              url = "/cart/removeFromWish";
            } else if (!isLogin && isAdd == "#heart") {
              url = "/cart/removeFromSessionCart";
            }
            console.log(url);
            $.post(url, data, function (result) {
              console.log(result);
              if (result.code == 200) {
                console.log(result.message);
              }
            });
          });
          $(".addToCart").click(function () {
            if (!isSelected) {
              layer.msg("<spring:message code="goods.detail.pleaseSelectSize" />");
              return false;
            }
            //setCartNum("add");
            var skuId = ${goodsItemDomain.id};
            var url = "/cart/addToCart";
            var data = {
              "itemId": skuId,
              "num": 1,
              "sizeId": selectSizeId,
              "type": 1
            };
            $.post(url, data, function (result) {
              console.log(result);
              if (result.code == 200) {
                console.log(result.message);
                layer.msg("<spring:message code="success.tocart" />");
                setCartNum();
                //location.href="/cart/list";
              } else {
                layer.msg('<spring:message code="sellout"/>');
              }
            });
          });
          $(".addToBoutique").click(function () {
            if (!isSelected) {
              layer.msg("<spring:message code="goods.detail.pleaseSelectSize" />");
              return false;
            }
            var skuId = ${goodsItemDomain.id};
            var url = !isLogin ? "/cart/addToCart" : "/boutique/addToBoutique";
            var data = {
              "itemId": skuId,
              "num": 1,
              "sizeId": selectSizeId,
              "type": 3
            };
            console.log("url:" + url);
            $.post(url, data, function (result) {
              console.log(result);
              if (result.code == 200) {
                console.log(result.message);
                layer.msg("<spring:message code="success.toboutique" />");
                setCartNum();
                //location.href="/boutique/list";
              } else {
                layer.msg(result.message);
              }
            });
          });


          //iframe窗
          $(".sizeNotice").click(function () {
            layer.open({
              type: 2,
              title: '<spring:message code="shoppingCart.size"/>' + '<spring:message code="goods.detail.guide"/>',
              closeBtn: 1, //不显示关闭按钮
              shade: [0],
              area: ['90%', '75%'],
              content: ['${ctx}/content/sizeNotice'], //iframe的url，no代表不显示滚动条
              //shadeClose: true,
              shade: [0.5, '#000'] //0.1透明度的白色背景
            });
          });

          $(".whatBoutique").click(function () {
            layer.open({
              type: 2,
              title: '<spring:message code="reservation.what"/>',
              closeBtn: 1, //不显示关闭按钮
              shade: [0],
              area: ['90%', '75%'],
              content: ['${ctx}/content/whatBoutique'], //iframe的url，no代表不显示滚动条
              shade: [0.5, '#000'] //0.1透明度的白色背景
            });
          });

        });
      </script>
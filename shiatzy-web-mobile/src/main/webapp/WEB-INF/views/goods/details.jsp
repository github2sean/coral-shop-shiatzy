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
          <div class="dx-bag-slide" style="height: 28rem;">
            <ul class="j_s_slider">
              <c:forEach var="item" items="${ImageModel.toList(goodsItemDomain.photos)}">
                <li><a href="javascript:;"><img src="${item.file}" alt="" style="height: 25rem;"></a></li>
                <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
                <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
                <li><a href="javascript:;"><img src="${item.file}" alt=""></a></li>
              </c:forEach>
            </ul>
          </div>
          <div class="price"><span class="do-pro-price" data-value="${goodsItemDomain.price}">&nbsp;</span>
            <a href="javascript:;" class="j_collect ">
              <svg transform="scale(2) ">
                <use xlink:href="#heart-red"></use>
              </svg>
            </a>
          </div>

          <div class="color j_collapse">
            <h3 class="title">
            ${sessionScope.language=='en_US'?goodsItemDomain.goodsColor.enName:goodsItemDomain.goodsColor.name}&nbsp;(<spring:message code="goods.detail.thereAre"/>&nbsp;${goodsDomain.goodsItemList.size()-1}&nbsp;<spring:message code="goods.detail.colors"/>)
            </h3>
            <c:if test="${goodsDomain.goodsItemList.size()>0}">
              <ul class="clearfix">
                <c:forEach var="item" items="${goodsDomain.goodsItemList}">
                  <li style="margin-bottom: 2rem; <c:if test="${item.id==goodsItemDomain.id}">background-color: #e6e6e6; </c:if>"><a href="/goods/details/${item.id}"><img src="${ImageModel.toFirst(item.thumb).file}" alt="" style="width:70px;margin-bottom: 10px;"></a></li>
                </c:forEach>
              </ul>
            </c:if>
          </div>

          <div class="size j_collapse">
            <h3 class="title">
              <spring:message code="shoppingCart.size" /> &nbsp;&nbsp; <span class="sizeChecked"></span>&nbsp;&nbsp;<span class="sizeNotice"><spring:message code="goods.detail.sizeNotice"/></span></h3>
            <ul  id="js_size" class="hide">
              <c:forEach var="item" items="${sizeList}" varStatus="status">
                <li class="<c:if test=" ${status.first && item.stock>0}"></c:if>
                  <c:if test="${item.stock<1}">disabled</c:if> sizeIds" data-value="${item.id}"><a href="#">${item.name}&nbsp;&nbsp;&nbsp;<c:if test="${item.stock<1}">(<spring:message code="sellout"/>)</c:if><span></span></a>                  </li>
              </c:forEach>
            </ul>
          </div>
          <div style="margin-top: 2rem;">
          <a  type="button" class="btn-default addToCart" style="background-color: #cecece;color: #222222;border: #cecece solid 2px;width: 100%;"><span style="position: relative;left: 0;top: 6px;margin-right: 8px;color: #2a2a2a" ><svg><use xlink:href="#cart-nav"></use></svg></span><spring:message code="goods.detail.add2cart"/></a>

          <a type="button" class="btn-default addToBoutique" style="background-color: #ffffff;border: #cccccc solid 2px;color: #000000;margin-top: 1rem;width: 100%;"><span style="position: relative;left: 0;top: 6px;margin-right: 8px;"><svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#appointment-nav"></use></svg></span><spring:message code="goods.detail.add2reservation"/></a>
          </div>
          <div class="remind whatBoutique"><span class="icon iconfont ">&#xe77d;</span><spring:message code="reservation.what"/></div>

          <div class="dx-GoodsDetails j_collapse">
            <h3 class="title"><spring:message code="goods.detail.details"/></h3>
            <p class="text">${sessionScope.language=='en_US'?goodsDomain.enDetails:goodsDomain.details}</p>
          </div>
        </div>

        <!--也许喜欢-->
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
          $(".j_s_slider").bxSlider({pager:null});
            $(".j_collapse").on("click",'h3', function () {
                $(this).parent().toggleClass("active").find("#js_size").slideToggle(0);
            });
            $(".j_collapse").on("click", function () {
                $(this).find("#js_size").toggleClass("hide");
            });
          // 添加相册
          function galleryAppend() {
            var htmlBefore =
              '<div class="gallery-block"><span class="iconfont icon-cha gallery-close"></span><ul class="gallery-img-list">';
            var htmlAfter = '</ul></div>';
            var htmlMain = '';
            $(".j_s_slider li").not(".bx-clone").each(function () {
              var imgUrl = $(this).find("img").attr("src");
               htmlMain += '<li style="background-image:url('+imgUrl+')"></li>';
            })
            $('body').append(htmlBefore + htmlMain + htmlAfter).css({
              "position":"relative",
              "overflow":"hidden"
            });
            $(".gallery-img-list").bxSlider({
              controls:false
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
            $("body").css({
              "position":"",
              "overflow":""
            })
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
            $(this).parents("#js_size").hide();
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
            //var isAdd = $(this).find("use").attr("xlink:href");
            var sizeId = '';
            $(".sizeIds").each(function () {
              if ($(this).hasClass("active")) {
                sizeId = $(this).attr("data-value");
              }
            });
            if(sizeId==''){
              layer.msg('<spring:message code="goods.detail.pleaseSelectSize"/>');
              return false;
            }

            var data = {
              "itemId": '${goodsItemDomain.id}',
              "num": 1,
              "sizeId": sizeId,
              "type": 2
            };
            console.log(isAdd);
            var url = "";
              url = "/cart/addToCart";
           /* if (isAdd == "#heart-red") {
                url = "/cart/addToCart";
            } else if (isLogin && isAdd == "#heart") {
              // $(this).find("use").attr("xlink:href","#heart");
              url = "/cart/removeFromWish";
            } else if (!isLogin && isAdd == "#heart") {
              url = "/cart/removeFromSessionCart";
            }*/
            console.log(url);
            $.post(url, data, function (result) {
              console.log(result);
              if (result.code == 200) {
                layer.msg('<spring:message code="success.towish"/>');
              }else{
                layer.msg(result.message);
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
            $("body,html").css({
              "overflow":"hidden",
              "position":"relative"
            });
            layer.open({
              type: 2,
              title: '<spring:message code="shoppingCart.size"/>' + '<spring:message code="goods.detail.guide"/>',
              closeBtn: 1, //不显示关闭按钮
              shade: [0],
              area: ['90%', '75%'],
              content: ['${ctx}/content/sizeNotice'], //iframe的url，no代表不显示滚动条
              shade: [0.5, '#000'] , //0.1透明度的白色背景
              cancel: function(){
                $("body").css({ "position":"", "overflow":"" });
              }
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
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="/WEB-INF/views/include/taglib.jsp" %>

      <jsp:include page="/WEB-INF/views/include/header.jsp">
        <jsp:param name="nav" value="商品" />
        <jsp:param name="pageTitle" value="商品列表" />
      </jsp:include>

      <div class="dx-commodity">
        <div class="content">
          <div class="title">${goodsDomain.name}</div>
          <span class="number">产品编号 ${goodsItemDomain.goodsNo}</span>
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
            <div class="title j_choose">${goodsItemDomain.goodsColor.name}(还有${goodsDomain.goodsItemList.size()-1}款颜色)</div>
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
              <spring:message code="shoppingCart.size" /> &nbsp;&nbsp; <span class="sizeChecked"></span>&nbsp;&nbsp;<span class="sizeNotice">查看尺寸指南</span></div>
            <ul class="hide" id="js_size">
              <c:forEach var="item" items="${sizeList}" varStatus="status">
                <li class="<c:if test=" ${status.first && item.stock>0}"></c:if>
                  <c:if test="${item.stock<1}">disabled</c:if> sizeIds" data-value="${item.id}"><a href="#">${item.name}&nbsp;&nbsp;&nbsp;<c:if test="${item.stock<1}">(已售罄)</c:if><span></span></a>                  </li>
              </c:forEach>
            </ul>
          </div>
          <a type="button" class="addition addToCart">添加到购物袋</a>
          <a type="button" class="order addToBoutique">精品店预约</a>
          <div class="remind whatBoutique"><span class="icon iconfont ">&#xe77d;</span>什么是精品店预约</div>
        </div>
        <div class="dx-GoodsDetails">
          <div class="title j_choose">产品详细信息</div>
          <p class="text hide">${goodsDomain.description}</p>
          <%--<ul class="list hide">
            <li>贴布刺绣，玉镯提手</li>
            <li>100% 成牛皮</li>
            <li>意大利制造</li>
            <li>产品尺寸：24.5厘米/9.6英寸（长），16厘米/6.3英寸（宽），20厘米/7.9英寸（高）</li>
        </ul>--%>
        </div>
        <div class="maybeLike clearfix">
          <div class="title">您也许也喜欢</div>
          <c:forEach var="goods" items="${historyList}" begin="0" end="3">
            <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
            <div class="left">
              <a href="/goods/details/${firstItem.id}">
                <div class="pic">
                  <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                </div>
                <div class="name">${goods.name}</div>
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
          commonApp.init();
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
              layer.msg("该商品已售罄");
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
              layer.msg("请选择选择商品尺寸");
              return false;
            }
            //setCartNum("add");
            var skuId = $ {
              goodsItemDomain.id
            };
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
                layer.msg("加入购物车成功");
                setCartNum();
                //location.href="/cart/list";
              } else {
                layer.msg("没有库存");
              }
            });
          });
          $(".addToBoutique").click(function () {
            if (!isSelected) {
              layer.msg("请选择选择商品尺寸");
              return false;
            }
            var skuId = $ {
              goodsItemDomain.id
            };
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
                layer.msg("加入精品店成功");
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
              title: '<spring:message code="shoppingCart.size"/>' + '指南',
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
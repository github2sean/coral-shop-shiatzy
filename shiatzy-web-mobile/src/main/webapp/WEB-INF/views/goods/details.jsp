<%@ taglib prefix="sping" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.dookay.coral.common.model.ImageModel" %>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ include file="/WEB-INF/views/include/taglib.jsp" %>

      <jsp:include page="/WEB-INF/views/include/header.jsp">
        <jsp:param name="nav" value="商品" />
        <jsp:param name="pageTitle" value="商品列表" />
      </jsp:include>

      <div class="dx-commodity">
        <div class="content">
          <div class="title">${web:selectLanguage()=='en_US'?goodsDomain.enName:goodsDomain.name}</div>
          <span class="number"><spring:message code="shoppingCart.no" /> ${goodsItemDomain.goodsNo}</span>
          <a href="javascript:;" class="icon iconfont magnify">&#xe630;</a>
          <div class="dx-bag-slide" >
            <ul class="j_s_slider">
                <c:set var="photos" value="${ImageModel.toList(goodsItemDomain.photos)}"></c:set>
                <c:set var="startIndex" value="${photos.size()-1 }"></c:set>
                <c:forEach var="item" items="${photos}" varStatus="status">
                <li><a href="javascript:;"><img src="${photos[startIndex-status.index].file}" alt="" style="height: 36rem;"></a></li>
              </c:forEach>
            </ul>
          </div>
          <div class="price"><span class="do-pro-price <c:if test="${not empty  goodsItemDomain.discountPrice}">xzc-price</c:if>" data-value="${goodsItemDomain.price}">&nbsp;</span>
              <c:if test="${not empty  goodsItemDomain.discountPrice}"><span class="do-pro-price xzc-dis-price"  data-value="${goodsItemDomain.discountPrice}">&nbsp;</span></c:if>
            <a href="javascript:;" class="j_collect " style="margin-top:0.8rem;">
              <svg id="add_to_wish" style="transform:scale(2);-webkit-transform:scale(2)">
                <use xlink:href="#heart-red"></use>
              </svg>
            </a>
          </div>

          <div class="color j_collapse">
            <h3 class="title">
            ${web:selectLanguage()=='en_US'?goodsItemDomain.goodsColor.enName:goodsItemDomain.goodsColor.name}&nbsp;(<spring:message code="goods.detail.thereAre"/>&nbsp;${goodsDomain.goodsItemList.size()-1}&nbsp;<spring:message code="goods.detail.colors"/>)
            </h3>
            <c:if test="${goodsDomain.goodsItemList.size()>0}">
              <ul class="clearfix">
                <c:forEach var="item" items="${goodsDomain.goodsItemList}">
                  <li style="margin-bottom: 2rem;  ">
                      <a href="/goods/details/${item.id}">
                      <img src="${ImageModel.toFirst(item.thumb).file}" alt="" style="width:70px;margin-bottom: 6px;<c:if test="${item.id==goodsItemDomain.id}">border-bottom: #e6e6e6 solid 6px</c:if>"></a></li>
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

            <c:if test="${goodsDomain.isPre==1}">
              <a type="button" class="btn-default addToBoutique" style="background-color: #ffffff;border: #cccccc solid 2px;color: #000000;margin-top: 1rem;width: 100%;"><span style="position: relative;left: 0;top: 6px;margin-right: 8px;"><svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#appointment-nav"></use></svg></span><spring:message code="goods.detail.add2reservation"/></a>
              <div class="remind whatBoutique"><span class="icon iconfont ">&#xe77d;</span><spring:message code="reservation.what"/></div>
            </c:if>
          </div>

          <div class="dx-GoodsDetails j_collapse" style="${goodsDomain.isPre==0?'margin-top:2.8rem':''}" >
            <h3 class="title"><spring:message code="goods.detail.details"/></h3>
            <p class="text">${web:selectLanguage()=='en_US'?goodsItemDomain.enDescription:goodsItemDomain.description}</p>
          </div>
        </div>

        <!--也许喜欢-->
        <div class="maybeLike clearfix">
          <div class="title"><spring:message code="goods.detail.maybeLike"/></div>
          <div class="container">
          <ul class="do-pro-list j_scroll_list">
            <c:forEach var="goods" items="${historyList}" varStatus="num"  begin="0" end="3">
              <c:set var="firstItem" value="${goods.goodsItemList[0]}"></c:set>
              <li>
                <a href="/goods/details/${firstItem.id}">
                  <div class="do-img">
                    <img src="${ImageModel.toFirst(goods.thumb).file}" alt="">
                  </div>
                  <p class="do-pro-t ellipsis-25" name="goodsName">${web:selectLanguage()=='en_US'?goods.enName:goods.name}</p>
                  <p class="do-pro-price <c:if test="${not empty  goods.disPrice}">xzc-price</c:if>" name="goodsPrice" data-value="${firstItem.price}">&nbsp;</p>
                    <c:if test="${not empty  goods.disPrice}">
                        <p class="do-pro-price xzc-dis-price" name="goodsPrice" data-value="${goods.disPrice}">&nbsp;</p>
                    </c:if>
                  <ul class="do-list-color" name="skuId" data-value="">
                    <c:forEach var="item" items="${goods.goodsItemList}">
                      <li style="background: ${item.colorValue}"></li>
                    </c:forEach>
                  </ul>
                </a>
                <!--Todo:收藏按钮-->
                <i class="icon-collect j_collect hide" data-value="${firstItem.id}" data-ids="${goods.sizeDomainList[0].id}" >
                  <svg class="do-heart"><use xlink:href="#heart"></use></svg>
                </i>
              </li>
            </c:forEach>
          </ul>
          </div>

        </div>
      </div>


      <jsp:include page="/WEB-INF/views/include/footer.jsp">
        <jsp:param name="nav" value="首页" />
      </jsp:include>

<style>

    .overlay{
        position: fixed;
        top: 0;
        left: 0;
        bottom: 0;
        width: 100%;
        background-color: #fff;
        border-right: 1px solid #ddd;
        overflow-x: hidden;
        -webkit-overflow-scrolling: touch;
        z-index: 99999999999;
        display: none;
    }
    .overlay .overlay-title{
        position: fixed;
        top: 0px;
        width: 100%;
        padding: 0 80px 0 20px;
        height: 42px;
        line-height: 42px;
        border-bottom: 1px solid #eee;
        font-size: 14px;
        color: #333;
        overflow: hidden;
        background-color: #F8F8F8;
    }
    .overlay .overlay-content{
        position: fixed;
        top: 44px;
        overflow: scroll;
        height: 100%;
        z-index: 99999999999;
        width: 100%;
        padding: 10px;
       bottom:0;
    }
    .overlay .overlay-close{
        cursor: pointer;
        border: #F8F8F8 solid 3px;
        position: absolute;
        right: 1rem;
        top:0;
    }
</style>
<div class="overlay">
    <div class="overlay-title">标题 <a href="" class="overlay-close">close</a> </div>
    <div class="overlay-content">
        <div class="content">
            <h3 style="text-align: center;font-size: 1.6rem">尺码建议</h3>
            <p class=""><a href="#">如无特别说明，我们统一采用法码标准，以下表格可以帮助您根据实际尺寸所对应的各地区不同尺码进行参照。</a></p>
            <table class="table table-bordered ">
                <thead style="font-size:12px ">
                <tr>
                    <th colspan="6" style="text-align: center">女士成衣尺码对照表
                    </th>
                </tr>
                </thead>
                <tbody style="font-size:10px ">
                <tr>
                    <td>意大利(IT)</td>
                    <td>38</td>
                    <td>40</td>
                    <td>42</td>
                    <td>44</td>
                    <td>46</td>
                </tr>
                <tr>
                    <td>法国 (FR)</td>
                    <td>36</td>
                    <td>38</td>
                    <td>40</td>
                    <td>42</td>
                    <td>44</td>
                </tr>
                <tr>
                    <td>德国 (DE)</td>
                    <td>32</td>
                    <td>34</td>
                    <td>36</td>
                    <td>38</td>
                    <td>40</td>
                </tr>
                <tr>
                    <td>台湾 (TW)</td>
                    <td>6</td>
                    <td>8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14</td>
                </tr>
                <tr>
                    <td>日本 (JP)</td>
                    <td>7</td>
                    <td>9</td>
                    <td>11</td>
                    <td>13</td>
                    <td>15</td>
                </tr>
                <tr>
                    <td>美国 (US)</td>
                    <td>0</td>
                    <td>2-4</td>
                    <td>6</td>
                    <td>8</td>
                    <td>10-12</td>
                </tr>
                <tr>
                    <td>澳大利亚 (AUS)</td>
                    <td>4</td>
                    <td>6-8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14-16</td>
                </tr>
                <tr>
                    <td>英国 (UK)</td>
                    <td>4</td>
                    <td>6-8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14-16</td>
                </tr>
                <tr>
                    <td>国际 (Intl)</td>
                    <td>XS</td>
                    <td>X</td>
                    <td>S-M</td>
                    <td>M-L</td>
                    <td>L</td>
                </tr>
                <tr>
                    <td>中国 (CN)</td>
                    <td>155</td>
                    <td>160</td>
                    <td>165</td>
                    <td>170</td>
                    <td>175</td>
                </tr>
                <tr>
                    <td>胸围 (厘米)</td>
                    <td>78-82</td>
                    <td>83-87 </td>
                    <td>88-92</td>
                    <td>93-97</td>
                    <td>98-102</td>
                </tr>
                <tr>
                    <td>腰围 (厘米)</td>
                    <td>59-63</td>
                    <td>64-68 </td>
                    <td>69-73</td>
                    <td>74-78</td>
                    <td>79-83</td>
                </tr>
                <tr>
                    <td>臀围 (厘米)</td>
                    <td>85-89</td>
                    <td>90-94 </td>
                    <td>95-99</td>
                    <td>100-104</td>
                    <td>105-109</td>
                </tr>
                </tbody>
            </table>
            <table class="table table-bordered ">
                <thead style="font-size:12px ">
                <tr>
                    <th colspan="6" style="text-align: center">男士成衣尺码对照表
                    </th>
                </tr>
                </thead>
                <tbody style="font-size:10px ">

                <tr>
                    <td>法国 (FR)</td>
                    <td>46</td>
                    <td>48</td>
                    <td>50</td>
                    <td>52</td>
                    <td>54</td>
                </tr>
                <tr>
                    <td>国际 (Intl)</td>
                    <td>S</td>
                    <td>M</td>
                    <td>L</td>
                    <td>XL</td>
                    <td>XXL</td>
                </tr>
                <tr>
                    <td>中国 (CN)</td>
                    <td>170</td>
                    <td>175</td>
                    <td>180</td>
                    <td>185</td>
                    <td>190</td>
                </tr>
                <tr>
                    <td>胸围 (厘米)</td>
                    <td>83-87</td>
                    <td>88-92 </td>
                    <td>93-97</td>
                    <td>98-102</td>
                    <td>103-107</td>
                </tr>
                <tr>
                    <td>腰围 (厘米)</td>
                    <td>74-78</td>
                    <td>79-83 </td>
                    <td>84-88</td>
                    <td>89-93</td>
                    <td>94-98</td>
                </tr>
                <tr>
                    <td>臀围 (厘米)</td>
                    <td>93-97</td>
                    <td>98-102</td>
                    <td>103-107</td>
                    <td>108-112</td>
                    <td>113-117</td>
                </tr>


                </tbody>
            </table>
            <div class="table-responsive">
                <table class="table table-bordered ">
                    <thead style="font-size:12px ">
                    <tr>
                        <th colspan="13" style="text-align: center">女士鞋履尺码对照表
                        </th>
                    </tr>
                    </thead>
                    <tbody style="font-size:10px ">

                    <tr>
                        <td>法国 (FR)</td>
                        <td>35</td>
                        <td>36</td>
                        <td>36.5</td>
                        <td>37</td>
                        <td>37.5</td>
                        <td>38.5</td>
                        <td>38.5</td>
                        <td>39</td>
                        <td>40</td>
                        <td>41</td>
                        <td>42</td>
                        <td>43</td>

                    </tr>
                    <tr>
                        <td>中国 (CN)</td>
                        <td>35</td>
                        <td>36</td>
                        <td>36.5</td>
                        <td>37</td>
                        <td>37.5</td>
                        <td>38.5</td>
                        <td>38.5</td>
                        <td>39</td>
                        <td>40</td>
                        <td>41</td>
                        <td>42</td>
                        <td>43</td>
                    </tr>
                    <tr>
                        <td>意大利(IT)</td>
                        <td>34</td>
                        <td>35</td>
                        <td>35.5</td>
                        <td>36</td>
                        <td>36.5</td>
                        <td>37.5</td>
                        <td>37.5</td>
                        <td>38</td>
                        <td>39</td>
                        <td>40</td>
                        <td>41</td>
                        <td>42</td>
                    </tr>
                    <tr>
                        <td>美国 (US)</td>
                        <td>4</td>
                        <td>5</td>
                        <td>5.5</td>
                        <td>6</td>
                        <td>6.5</td>
                        <td>7.5</td>
                        <td>7.5</td>
                        <td>8</td>
                        <td>9</td>
                        <td>10</td>
                        <td>11</td>
                        <td>12</td>
                    </tr>
                    <tr>
                        <td>英国 (UK)</td>
                        <td>1</td>
                        <td>2</td>
                        <td>2.5</td>
                        <td>3</td>
                        <td>3.5</td>
                        <td>4.5</td>
                        <td>4.5</td>
                        <td>5</td>
                        <td>6</td>
                        <td>7</td>
                        <td>8</td>
                        <td>9</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="table-responsive">
                <table class="table table-bordered ">
                    <thead style="font-size:12px ">
                    <tr>
                        <th colspan="13" style="text-align: center">男士鞋履尺码对照表
                        </th>
                    </tr>
                    </thead>
                    <tbody style="font-size:10px ">

                    <tr>
                        <td>欧洲码 (EU)</td>
                        <td>40</td>
                        <td>41</td>
                        <td>42</td>
                        <td>42.5</td>
                        <td>43</td>
                        <td>43.5</td>
                        <td>44</td>
                        <td>44.5</td>
                        <td>45</td>
                        <td>46</td>
                    </tr>
                    <tr>
                        <td>美国 (US)</td>
                        <td>7</td>
                        <td>8</td>
                        <td>9</td>
                        <td>9.5</td>
                        <td>10</td>
                        <td>10.5</td>
                        <td>11</td>
                        <td>11.5</td>
                        <td>12</td>
                        <td>13</td>
                    </tr>
                    <tr>
                        <td>英国 (UK)</td>
                        <td>6</td>
                        <td>7</td>
                        <td>8</td>
                        <td>8.5</td>
                        <td>9</td>
                        <td>9.5</td>
                        <td>10</td>
                        <td>10.5</td>
                        <td>11</td>
                        <td>12</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <table class="table table-bordered ">
                <thead style="font-size:12px ">
                <tr>
                    <th colspan="8" style="text-align: center">其它配饰尺码对照表（腰带）
                    </th>
                </tr>
                </thead>
                <tbody style="font-size:10px ">

                <tr>
                    <td>法国 (FRANCE)</td>
                    <td>70</td>
                    <td>75</td>
                    <td>80</td>
                    <td>85</td>
                    <td>90</td>
                    <td>95</td>
                    <td>100</td>
                </tr>
                <tr>
                    <td>意大利 (ITALY)</td>
                    <td>38</td>
                    <td>40</td>
                    <td>42</td>
                    <td>44</td>
                    <td>46</td>
                    <td>16</td>
                    <td>50</td>
                </tr>
                <tr>
                    <td>英国 (UK)</td>
                    <td>6</td>
                    <td>8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14</td>
                    <td>16</td>
                    <td>18</td>
                </tr>
                <tr>
                    <td>美国 (US)</td>
                    <td>0</td>
                    <td>2-4</td>
                    <td>4-6</td>
                    <td>8</td>
                    <td>10</td>
                    <td>12</td>
                    <td>14</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
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
            $("#add_to_wish").css("-webkit-transform","scale(2.5)")
              setTimeout('$("#add_to_wish").css("-webkit-transform","scale(2)")',500);
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

            $.post(url, data, function (result) {

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

            layer.open({
              type: 2,
              title: '<spring:message code="shoppingCart.size"/>' + '<spring:message code="goods.detail.guide"/>',
              closeBtn: 1, //不显示关闭按钮
              shade: [0],
              area: ['100%', '100%'],
              content: ['${ctx}/content/sizeNotice'], //iframe的url，no代表不显示滚动条
              shade: [0.5, '#000'] , //0.1透明度的白色背景

              success: function(layero, index){
                $('html').addClass("open-c");
                $('body').addClass("open-c");
                $('.main-content').addClass("open-c");
              },
              end:function () {
                $('html').removeClass("open-c");
                $('body').removeClass("open-c");
                $('.main-content').removeClass("open-c");
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
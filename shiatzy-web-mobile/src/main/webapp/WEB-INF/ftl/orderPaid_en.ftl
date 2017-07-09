<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <style>
        img {
            max-width: 100%;
        }
        p,body,html {
            margin: 0;
            padding: 0;
        }
        ul,li {
            list-style: none;
            margin: 0;
            padding: 0;
        }
        .qmbox ul li {
            list-style: none!important;
        }
        .body ul li, .mycontent ul li {
             list-style-type: none!important;
        }
        .qmbox .email-con .email-txt {
              margin-top: 0px;
              margin-bottom: 0px;
              text-align: left;
        }
        .email-submit {
            width: 85%;
            margin: 0 auto;
            border: 1px solid #000;
            padding-left: 5%;
            padding-right: 5%;
        }
        .email-submit .title {
            text-align: center;
            margin-bottom: 20px;
            margin-top: 20px;
        }
        .email-submit .email-con {
            border-top: 1px solid #000;
            border-bottom: 1px solid #000;
            padding-top: 30px;
            padding-bottom: 30px;
        }
        .email-con .email-title {
            font-family: "lucida Grande",Verdana,"Microsoft YaHei";
            font-size: 20px;
            font-weight: 300;
            margin: 0;
            text-align: center;
        }
        .email-con .email-txt {
            margin-top: 10px;
            margin-bottom: 10px;
            text-align: center;
        }
        .email-con .email-txt .name {
            font-family: "宋体";
        }
        .email-con .email-txt .txt {
            font-size: 14px;
            line-height: 22px;
            font-family: "宋体";
        }
        /*.email-con .email-txt .txt .link-web {*/
            /*text-decoration: underline;*/
            /*color: #2a586f;*/
        /*}*/
        .email-footer {
            padding: 14px 0;
            text-align: center;
            font-size: 14px;
            font-family: 宋体;"
        }
        .dingdan {
            /*background: #F2F2F2;*/
            margin-top: 20px;
            font-family: 宋体;
            text-align: left;

        }
        .dingdan .detail {
            text-align: left;
            font-size: 13px;
            padding-bottom: 5px;
        }
        .dingdan .detail ul li {
            line-height: 24px;
            font-size: 14px;
        }
        /*.dingdan .detail ul li:first-child {*/
            /*border-bottom: 2px solid #cccccc;*/
            /*margin-bottom: 5px;*/
        /*}*/
        .goods-detail ul li:first-child {
            border-bottom: 2px solid #cccccc;
            margin-bottom: 5px;
        }
        .dingdan .dingdan-title {
            /*font-weight: bold;*/
            margin-bottom: 10px;
        }
        .bill-title,.diverse-title,.goods-detail {
            margin-bottom: 10px;
            border-bottom: 2px solid #cccccc;
            padding-bottom: 5px;
            text-align: left;
            font-family: 宋体;
        }
        .dingdan .list-name {
            /*font-weight: bold;*/
        }
        .email-txt .summary {
            font-family: 宋体;
            text-align: center;
            font-size: 14px;
            padding-top: 10px;
            padding-bottom: 10px;
            border-top: 1px solid #000;
        }
        .dingdan-list:after {
            content: "";
            display: block;
            clear: both;
        }
        .dingdan-list {
            border-bottom: 1px solid #ccc;
        }
        .dingdan-list .list-left {
            float: left;
            width: 30%;
            padding-top: 0px;
            padding-bottom: 0px;
        }
        .dingdan-list .list-right {
            float: right;
            text-align: left;
            width: 70%;
            padding-top: 0px;
            padding-bottom: 0px;
        }
        .dingdan-list .list-right .pro-title {
            font-family: "宋体";
            font-size: 14px;
            font-weight: normal;
            line-height: 18px;
            margin: 0;
            margin-bottom: 10px;
        }
        .dingdan-list .list-right .pro-kind {
            font-family: "宋体";
            font-size: 14px;
            color:#ACACAC;

        }
        .hr {
            height: 0;
            border:none;
            border-bottom:1px solid #000;
            margin-top: 5px;
            margin-bottom: 5px;
        }
        .pro-value {
            font-family: "宋体";
            font-size: 14px;
            color:#000;
            line-height: 20px;
            margin-bottom: 11px;
        }
        .pro-value:last-child {
            margin-bottom: 0;
        }
        .dingdan-sum {
            padding-top: 30px;
            text-align: left;
        }
        .dingdan-sum-tip {
            font-family: "宋体";
            font-size: 24px;
            margin-right: 10px;
        }
        .dingdan-money {
            font-family: "宋体";
            font-size: 16px;
        }
        .xzc-detail {
            margin-bottom: 20px;
        }
        .bill-detail li{
            overflow: hidden;
        }
        .bill-lf {
            float: left;
        }
        .bill-rt {
            float: right;
        }
        .bill-detail li {
            padding-left: 10px;
            padding-right: 10px;
        }
        .bill-detail li:last-child {
            border-top: 2px solid #ccc;
            margin-top: 10px;
            padding-top: 5px;
            padding-left: 0px;
            padding-right: 0px;
        }
    </style>
</head>
<body>
    <div class="email-submit">
        <p class="title"><img src="${picUrl}" alt=""></p>
        <div class="email-con">
            <h1 class="email-title">${title}</h1>
            <div class="email-txt">
                <p class="name">dear ${name},</p>
                <br>
                <p class="txt">
                    ${content}</p>
                <div class="dingdan">
                    <div class="xzc-detail goods-detail">
                        <p class="dingdan-title">order details</p>
                        <div class="detail">
                            <ul class="detail-list">
                                <li>
                                    <span class="list-name">orderNo : </span><span> ${order.orderNo}</span>
                                </li>
                                <li>
                                    <span class="list-name">orderTime : </span><span>${date}</span>
                                </li>
                                <li>
                                    <span class="list-name">status : </span><span>${status}</span>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>
                <div class="dingdan-list">
                    <p class="goods-detail">goods details</p>


                    <#if orderItem?? && (orderItem?size > 0) >
                        <#list orderItem as it>
                            <div class="list-left"><img src="${it.goodsItemDomain.picUrl}" alt=""></div>
                            <div class="list-right">
                                <h3 class="pro-title">${it.goodsName} ${it.goodsCode}</h3>
                                <p class="pro-value">${it.goodsItemDomain.enName}</p>
                                <p class="pro-value">size：  ${it.sizeDomain.enName}</p>
                                <p class="pro-value">number：${it.num}</p>
                                <p class="pro-value">unit price：
                                <#if order.currentCode=="USD">$
                                <#elseif order.currentCode=="EUR">€
                                <#else >¥
                                </#if>
                                ${it.goodsPrice}</p>
                                <#if it.goodsDisPrice??>
                                    <p class="pro-value">discount price：
                                        <#if order.currentCode=="USD">$
                                        <#elseif order.currentCode=="EUR">€
                                        <#else >¥
                                        </#if>
                                    ${it.goodsDisPrice}</p>
                                </#if>
                            </div>

                        </#list>
                    </#if>

                </div>
                <div class="dingdan">
                    <div class="xzc-detail">
                        <p class="bill-title">detail</p>
                        <div class="detail bill-detail">
                            <ul class="detail-list">
                                <li>
                                    <span class="bill-lf">Before discount: </span><span class="bill-rt"><#if order.currentCode=="USD">$
                                <#elseif order.currentCode=="EUR">€
                                <#else >¥
                                </#if>   ${order.goodsTotal}</span>
                                </li>
                                <li>
                                    <span class="bill-lf">discount : </span><span class="bill-rt"><#if order.currentCode=="USD">$
                                <#elseif order.currentCode=="EUR">€
                                <#else >¥
                                </#if>   -${order.couponDiscount!'0'}</span>
                                </li>
                                <li>
                                    <span class="bill-lf">Art Club discount : </span><span class="bill-rt"><#if order.currentCode=="USD">$
                                <#elseif order.currentCode=="EUR">€
                                <#else >¥
                                </#if>   -${order.memberDiscount!'0'}</span>
                                </li>
                                <li>
                                    <span class="bill-lf">shipping fee : </span><span class="bill-rt"><#if order.currentCode=="USD">$
                                <#elseif order.currentCode=="EUR">€
                                <#else >¥
                                </#if>   ${order.shipFee!'0'}</span>
                                </li>
                                <li>
                                    <span class="bill-lf">total : </span><span class="bill-rt"><#if order.currentCode=="USD">$
                                <#elseif order.currentCode=="EUR">€
                                <#else >¥
                                </#if>   ${order.orderTotal}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="xzc-detail">
                        <p class="diverse-title">shipping information</p>
                        <div class="detail">
                            <ul class="detail-list">
                                <li>
                                    <span class="list-name">${order.shipAddress}</span>
                                </li>
                                <li>
                                    <span class="list-name">${order.shipTitle}</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!--<div class="dingdan-sum">-->
                    <!--<span class="dingdan-sum-tip">订单合计</span><span class="dingdan-money">¥1</span>-->
                <!--</div>-->
            </div>
        </div>
        <div class="email-footer">© shiatzy chen. all rights reserved</div>
    </div>
</body>
</html>
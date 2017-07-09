<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
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
            padding: 10px;
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
        .dingdan .detail ul li:first-child {
            border-bottom: 2px solid #cccccc;
            margin-bottom: 5px;
        }

        .dingdan .dingdan-title {
            /*font-weight: bold;*/
            margin-bottom: 10px;
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
        .dingdan-list .list-left {
            float: left;
            width: 30%;
            padding-top: 20px;
            padding-bottom: 20px;
        }
        .dingdan-list .list-right {
            float: right;
            text-align: left;
            width: 70%;
            padding-top: 20px;
            padding-bottom: 20px;
        }
        .dingdan-list .list-right .pro-title {
            font-family: "宋体";
            color:#777;
            font-size: 14px;
            font-weight: normal;
            margin: 0;
            margin-bottom: 7px;
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
            font-size: 12px;
            color:#000;
            line-height: 20px;
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
            margin-bottom: 10px;
        }
        .dingdan-list {
            padding: 10px;
        }
        .yuyue-title {
            border-bottom: 2px solid #cccccc;
            margin-bottom: 5px;
            font-family: "宋体";
            text-align: left;
            font-size: 14px;
            line-height: 24px;
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
                <div class="xzc-detail">
                    <p class="dingdan-title">reservation details</p>
                    <div class="detail">
                        <ul class="detail-list">
                            <li>
                                <span class="list-name">reservationNO : </span><span>${order.reservationNo}</span>
                            </li>
                            <li>
                                <span class="list-name">time : </span><span>${date}</span>
                            </li>
                            <li>
                                <span class="list-name">reservation store : </span><span>${order.storeDomain.enTitle}</span>
                            </li>
                            <li>
                                <span class="list-name">address : </span><span>${order.storeDomain.enAddress}</span>
                            </li>
                            <li>
                                <span class="list-name">phone number : </span><span>${order.storeDomain.tel}</span>
                            </li>
                            <li>
                                <span class="list-name">business hours : </span><span>${openDate}</span>
                            </li>
                            <li>
                                <span class="list-name">status : </span><span>${status}</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="dingdan-list">
                <p class="yuyue-title">goods details</p>

            <#if orderItem?? && (orderItem?size > 0) >
                <#list orderItem as it>
                    <div class="list-left"><img src="${it.goodsItemDomain.picUrl}" alt=""></div>
                    <div class="list-right">
                        <h3 class="pro-title">${it.goodsName} ${it.skuCode}</h3>
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
                <#--<div class="list-left"><img src="images/1C179-B_sb.jpg" alt=""></div>
                <div class="list-right">
                    <h3 class="pro-title">凸面金属浮雕花朵戒指</h3>
                    <p class="pro-kind">1C179 B</p>
                    <hr class="hr">
                    <p class="pro-value">颜色 : 粉色</p>
                    <p class="pro-value">尺寸 : S</p>
                    <p class="pro-value">数量 : 1</p>
                    <p class="pro-value">单价 : ¥1</p>
                    <p class="pro-value">状态 : 已提交</p>
                </div>-->
            </div>
            <div class="dingdan-sum">
                <span class="dingdan-sum-tip">Estimated total </span><span class="dingdan-money"><#if order.currentCode=="USD">$
            <#elseif order.currentCode=="EUR">€
            <#else >¥
            </#if>${totalFee}</span>
            </div>
        </div>
    </div>
    <div class="email-footer">© shiatzy chen. all rights reserved</div>
</div>
</body>
</html>
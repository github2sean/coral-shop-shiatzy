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
        }
        .email-con .email-txt {
            margin-top: 10px;
            margin-bottom: 10px;
        }
        .email-con .email-txt .txt {
            font-size: 14px;
            line-height: 22px;
            margin-bottom: 15px;
        }
        .email-con .email-txt .txt .link-web {
            text-decoration: underline;
            color: #2a586f;
        }
        .email-submit .email-footer {
            padding: 14px 0;
        }

    </style>
</head>
<body>
    <div class="email-submit">
        <p class="title"><img src="${picUrl}" alt=""></p>
        <div class="email-con">
            <h1 class="email-title">${title}</h1>
            <div class="email-txt">
                <p class="name">亲爱的 ${name},</p>
                <br>
                <p class="txt">我们刚刚为您创建了一个新密码。出于安全考虑，我们建议您在下一次登录时更改此密码。 <a href="#" class="link-web">customercare@shiatzychen.com</a>.</p>
                <p class="txt">
                    如果您还有其他问题，请与我们联系。<br>
                    <a href="#" class="link-web">customercare@shiatzychen.com</a>
                </p>
                <p class="txt" style="margin-bottom: 5px">
                    您的新密码是
                </p>
                <p style="padding: 10px ; display:block; width: 300px;   background: #eee; color: #000; letter-spacing: 2px; text-decoration: none; cursor:pointer ">${newPass}</p>
            </div>
        </div>
        <p class="email-footer">
            <span style="">© shiatzy chen. all rights reserved</span>
        </p>
    </div>
</body>
</html>
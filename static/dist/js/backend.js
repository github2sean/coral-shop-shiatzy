/*js 原生扩展*/
Array.prototype.remove = function (dx) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == dx) {
            this.splice(i, 1);
        }
    }
    return this;
}
function checkNumberInput(event) {
    var code = event.keyCode;
    if ((code >= 49 && code <= 57) || (code >= 97 && code <= 105))
        return true;
    return false;
}
Array.prototype.has = function (dx) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == dx) {
            return true;
        }
    }
    return false;
}

Array.prototype.contains = function (obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}

Date.prototype.format = function (format) {
    /* 
     * eg:format="yyyy-MM-dd hh:mm:ss"; 
     */
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }
    var o = {
        "M+": this.getMonth() + 1, // month  
        "d+": this.getDate(), // day  
        "h+": this.getHours(), // hour  
        "m+": this.getMinutes(), // minute  
        "s+": this.getSeconds(), // second  
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S": this.getMilliseconds()
        // millisecond  
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
            - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

String.prototype.escapeRegExp = function () {
    return this.replace(/[.*+?^${}()|[\]\/\\]/g, "\\$0");
};
String.prototype.trimEnd = function (c) {
    if (c)
        return this.replace(new RegExp(c.escapeRegExp() + "*$"), '');
    return this.replace(/\s+$/, '');
}
String.prototype.trimStart = function (c) {
    if (c)
        return this.replace(new RegExp("^" + c.escapeRegExp() + "*"), '');
    return this.replace(/^\s+/, '');
}

//设置和获取url参数
getQueryString = function (key, query) {
    if (!query)
        query = window.location.search;
    var re = new RegExp("[?|&]" + key + "=(.*?)&");
    var matches = re.exec(query + "&");
    if (!matches || matches.length < 2)
        return "";
    return decodeURIComponent(matches[1].replace("+", " "));
}

setQueryString = function (key, value, href) {
    href = href || window.location.href;
    var queryReg = /(\?.*)/;
    var queryMathes = queryReg.exec(href);
    var query = queryMathes && queryMathes.length >= 2 ? queryMathes[1] : '';
    query = query.trimEnd('&').trimEnd('#');

    var hashReg = /(#.*)/;
    var hashMatches = hashReg.exec(href);
    var hash = hashMatches && hashMatches.length >= 2 ? hashMatches[1] : '';

    query = query.replace(hash, '');
    var q = query + "&";
    var re = new RegExp("[?|&]" + key + "=.*?&");
    if (!re.test(q)) {
        if (q == '&') {
            q = '';
        }
        q += key + "=" + encodeURIComponent(value);
    }
    else
        q = q.replace(re, "&" + key + "=" + encodeURIComponent(value) + "&");
    q = q.trimStart("&").trimEnd("&");
    q = (q[0] == "?" ? q : ("?" + q)) + hash;
    return href.replace(/\?.*/, '').replace(hash, '') + q;
}

var backendApp = function () {
    var initFun = function () {
        (function () {
            $.fn.serializeObject = function () {
                var keyValues = this.serializeArray();
                var pattern = /\[(\d+)\]/;
                var filter_keys = [],
                    normal_keys = keyValues.filter(function (v, i) {
                        if (~v.name.indexOf('.') || ~pattern.test(v.name)) {
                            filter_keys.push(v);
                            return false;
                        }
                        return true;
                    });
                var resultJson = {};
                filter_keys.forEach(function (v, i, ary) {
                    var v_ary = v.name.split('.'),
                        v_last = v_ary.length - 1;
                    var deal = function (obj, j, array) {
                        var me = arguments.callee,
                            islast = j === v_last ? true : false;
                        if (islast && !pattern.test(array[j])) {
                            if (typeof obj == 'array') {
                                return obj.push(v.value);
                            } else {
                                if (obj[array[j]]) {
                                    obj[array[j]] += ',' + v.value.toString() || '';
                                } else {
                                    obj[array[j]] = v.value.toString() || '';
                                }
                                return;
                            }
                        } else if (islast && pattern.test(array[j])) {
                            var name = array[j].substr(0, array[j].indexOf('['));
                            var index = array[j].match(pattern)[1];
                            if (!obj[name]) {
                                obj[name] = [];
                            }
                            if (obj[name].length <= index) {
                                return obj[name].push(v.value || '');
                            }
                        }
                        var nextObj;
                        if (pattern.test(array[j])) {
                            var name = array[j].substr(0, array[j].indexOf('['));
                            var index = array[j].match(pattern)[1];
                            if (!obj[name]) {
                                obj[name] = [];
                            }
                            if (obj[name].length <= index) {
                                nextObj = {};
                                obj[name].push(nextObj);
                            } else {
                                nextObj = obj[name][index];
                            }
                        } else {
                            if (!obj[array[j]]) {
                                obj[array[j]] = {};
                            }
                            nextObj = obj[array[j]];
                        }
                        return me(nextObj, j + 1, array);
                    }
                    deal(resultJson, 0, v_ary);
                });
                normal_keys.forEach(function (v) {
                    resultJson[v.name] = v.value;
                });
                return resultJson;
            }

            $.fn.changeItemsName = function () {
                var selectors = null;
                if (arguments.length > 0)
                    selectors = arguments;
                this.each(function (i, n) {
                    var $this = $(this);
                    var changeName = function ($this, callback) {
                        $this.find(':input').each(function () {
                            var $formElement = $(this);
                            var name = $formElement.attr("name");
                            if (name) {
                                name = callback(name);
                                $formElement.attr("name", name);
                            }
                        });
                    }
                    changeName($this, function (name) {
                        return name.replace(/\[\d+\]/g, "[" + i + "]");
                    });
                    if (selectors != null && selectors.length > 0) {
                        for (var k = 0; k <= selectors.length; k++) {
                            var selector = selectors[k];
                            $this.find(selector).each(function (j, x) {
                                changeName($(this), function (name) {
                                    return name.replace(/\[\d+\]/g, "[" + j + "]").replace(/\[\d+\]/, "[" + i + "]");
                                });
                            });
                        }
                    }
                });
            }

            var hasLayer = false;
            var loadingIndex = -999;
            if (typeof layer != "undefined" && layer != undefined && layer != null) {
                hasLayer = true;
                layer.config({
                    extend: 'extend/layer.ext.js'
                });
            }

            $.extend({
                alert: function (msg, type, callback) {
                    var img;
                    var icon = 1;
                    if (type == undefined || type == null || type == "")
                        type = "success";
                    if (type == "error") {
                        icon = 2;
                    }
                    if (hasLayer) {
                        layer.alert(msg, {icon: icon, title: '消息提示', shadeClose: true}, callback || function () {
                            });
                    } else {
                        alert(msg);
                        callback();
                    }
                },
                confirm: function (msg, callback, cancelCallback) {
                    if (hasLayer) {
                        layer.confirm(msg, {
                            icon: 3,
                            title: '消息提示',
                            shadeClose: false,
                            closeBtn: false,
                            skin: 'dk-skin'
                        }, function (index) {
                            if (callback != undefined) {
                                callback();
                            }
                            layer.close(index);
                        }, function (index) {
                            if (cancelCallback != undefined) {
                                cancelCallback();
                            }
                            layer.close(index);
                        });
                    } else {
                        if (window.confirm(msg)) {
                            if (callback != undefined) {
                                callback();
                            }
                        } else {
                            if (cancelCallback != undefined) {
                                cancelCallback();
                            }
                        }
                    }
                },
                tip: function (msg) {
                    return layer.msg(msg, {time: 2000});
                },
                loading: function (options) {

                    if (options != undefined && options === false) {
                        if (loadingIndex != -999) {
                            layer.close(loadingIndex);
                            loadingIndex = -999;
                        }
                    } else {
                        loadingIndex = layer.load(1, {
                            shade: [0.1, '#fff'] //0.1透明度的白色背景
                        });
                    }
                }
            });

            $.fn.loadIframeDialog = function (options) {
                var url = this.data('href');
                if (!url) {
                    url = this.attr('href');
                }
                var params = {
                    url: url,
                    size: 'lg',
                    success: function () {

                    }
                }
                var $commonDialog = $('#commonDialog');
                var $iframe = $commonDialog.find('iframe');
                $iframe.height(500);
                this.click(function (e) {
                    e.preventDefault();
                    $iframe.attr('src', params.url);
                    $commonDialog.modal('show');
                });

                $.extend(params, options);
            };

            // $.fn.uploadFile = function () {
            //     var settings = {
            //         href: '/Host/common/fineuploader',
            //         params: { "module": "Frontend" },
            //         uploadButton: '<div><span class=""></span>选择文件</div>',
            //         allowedExtensions: ['doc', 'docx', 'ppt', 'pptx', 'pps', 'xls', 'xlsx', 'txt', 'pdf', 'mp3', 'mp4', 'wma'],
            //         sizeLimit: 41943040,
            //         complete: ''
            //     };
            //
            //     function Uploader(obj, params) {
            //         this._obj = obj;
            //         this._params = $.extend({}, settings, params);
            //     }
            //     Uploader.prototype.render = function () {
            //         var $obj = this._obj;
            //         var params = this._params;
            //         //var tip;
            //         var alter;
            //         var manualuploader = new qq.FineUploader({
            //             element: $obj[0],
            //             request: {
            //                 endpoint: params.href,
            //                 params: params.params
            //             },
            //             messages: {
            //                 sizeError: '文件"{file}"大小超过{sizeLimit}',
            //                 typeError: '请上传{extensions}格式文件',
            //             },
            //             maxConnections: 1,
            //             autoUpload: true, //是否自动上传
            //             text: {
            //                 //按钮文字支持html
            //                 uploadButton: params.uploadButton
            //             },
            //             retry: {
            //                 enableAuto: false,
            //                 showButton: false
            //             },
            //             showMessage: function (message) {
            //                 $.tip(message);
            //             },
            //             multiple: false, //是否允许多条上传
            //             validation: {
            //                 //上传验证
            //                 allowedExtensions: params.allowedExtensions,
            //                 sizeLimit: params.sizeLimit
            //             },
            //             callbacks: {
            //                 onError: function (id, name, reason, xhr) {
            //                     //tip.close();
            //                     if (alter == null)
            //                         alter = $.tip("天了噜！文件上传失败！请重新上传，狗妹拿塞！", "error");
            //                 },
            //                 onUpload: function (id, name, uploadedBytes, totalBytes) {
            //                     //tip = $.tip("文件上传中，请耐心等待...");
            //                 },
            //                 onComplete: function (id, fileName, result) {
            //                     if (result.success) {
            //                         if (params.complete != null && params.complete != "" && typeof (eval(params.complete)) == "function") {
            //                             eval(params.complete + "($obj, fileName, result)");
            //                         } else {
            //                             var html = '[{"filePath":"' + result.file + '","title":"' + fileName + '","description":""}]';
            //                             $obj.prev().val(html);
            //                         }
            //                         //tip.close();
            //                     } else {
            //                         if (alter == null)
            //                             alter = $.tip("天了噜！文件上传失败！请重新上传，狗妹拿塞！", "error");
            //                     }
            //                 }
            //             }
            //         });
            //         return manualuploader;
            //     }
            //     return this.each(function () {
            //         var params = $(this).metadata();
            //         var uploader = new Uploader($(this), params);
            //         uploader.render();
            //     });
            // }
            $.fn.uploadImage = function (params) {
                var settings = {
                    href: '/common/upload',
                    params: {"module": "frontend"},
                    uploadButton: '<div><span class=""></span>选择图片</div>',
                    allowedExtensions: ['jpeg', 'jpg', 'gif', 'png'],
                    sizeLimit: 41943040,
                    complete: function ($obj, result) {
                        if (result.code == "200") {
                            if ($obj.prev().is(":input")) {
                                $obj.prev().val(result.data);
                            }
                            //tip.close();
                        } else {
                            $.tip("图片上传失败！", "error");
                        }
                    }
                };

                function Uploader(obj, params) {
                    this._obj = obj;
                    this._params = $.extend({}, settings, params);
                }

                Uploader.prototype.render = function () {
                    var $obj = this._obj;
                    var params = this._params;
                    var manualuploader = new qq.FineUploader({
                        element: $obj[0],
                        request: {
                            endpoint: params.href,
                            params: params.params
                        },
                        messages: {
                            sizeError: '文件"{file}"大小超过{sizeLimit}',
                            typeError: '请上传{extensions}格式文件',
                        },
                        maxConnections: 1,
                        autoUpload: true, //是否自动上传
                        text: {
                            //按钮文字支持html
                            uploadButton: params.uploadButton
                        },
                        retry: {
                            enableAuto: false,
                            showButton: false
                        },
                        showMessage: function (message) {
                            $.tip(message);
                        },
                        multiple: false, //是否允许多条上传
                        validation: {
                            //上传验证
                            allowedExtensions: params.allowedExtensions,
                            sizeLimit: params.sizeLimit
                        },
                        callbacks: {
                            onError: function (id, name, reason, xhr) {

                            },
                            onUpload: function (id, name, uploadedBytes, totalBytes) {
                                $.tip("图片上传中，请耐心等待...");
                            },
                            onComplete: function (id, fileName, result) {
                                params.complete($obj, result);
                            }
                        }
                    });
                    return manualuploader;
                }
                return this.each(function () {
                    var uploader = new Uploader($(this), params);
                    uploader.render();
                });
            }

            var loginDialog = (function () {
                function LoginDialogConstructor(options) {
                    this.options = options || {};
                    var _obj = this;
                    $.get("/u/passport/ajaxlogin", function (data) {
                        var loginModelDialog = dialog({
                            content: data,
                            padding: 0,
                            onclose: function () {
                                _obj.destroy();
                            }
                        }).showModal();
                        _obj._dialog = loginModelDialog;
                        var $loginContainer = loginModelDialog.getJquery();
                        $loginContainer.find(".js_close").click(function () {
                            _obj.destroy();
                        });
                        $loginContainer.find("form").ajaxSubmitForm({
                            success: function ($form, loginData) {
                                if (loginData.Type == 4 && options.callback != undefined && options.callback != null && typeof (options.callback) === "function") {
                                    options.callback();
                                } else if (loginData.Type == 2) {
                                    $.confirm2(loginData.Msg, function () {
                                        options.callback();
                                        window.open("/u/passport/sinaOauthLogin");
                                    }, function () {
                                        options.callback();
                                    });
                                } else {
                                    $.tip(loginData.Msg, "error");
                                }
                            }
                        });
                    });
                }

                LoginDialogConstructor.prototype.destory = function () {
                    this._dialog.remove();
                    this._dialog = null;
                };

                var instance = null;
                return {
                    getInstance: function (options) {
                        if (instance == null) {
                            instance = new LoginDialogConstructor(options);
                        }
                        return instance;
                    }
                };
            })();
            $.loginExcute = function (callback) {
                $.post("/u/passport/IsLogin", function (data) {
                    if (data.code == 4) {
                        callback();
                    } else {
                        loginDialog.getInstance({callback: callback});
                    }
                });
            }

            $.fn.loginAjaxForm = function (options) {
                this.submit(function (e) {
                    e.preventDefault();
                    var $this = $(this);
                    var $form = $this;
                    $.loginExcute(function () {
                        $form.ajaxSubmitForm(options);
                    });
                    return false;
                });
                return this;
            }

            $.fn.ajaxSubmitForm = function (options) {
                var params = {
                    before: function ($form) {
                        return true;
                    },
                    success: function ($form, data) {
                        if (data.code == 200) {
                            function okfun() {
                                if ($form.attr("data-next") != null && $form.attr("data-next") != "" && $form.attr("data-next") != "#")
                                    location.href = $form.attr("data-next");
                                else if (data.NextUrl != null && data.NextUrl != "") {
                                    location.href = data.NextUrl;
                                } else {
                                    location.reload();
                                }
                            }

                            var time = parseInt($form.data('time'));
                            if (isNaN(time)) {
                                time = 2000;
                            }

                            if (time <= 0) {
                                okfun();
                            } else {
                                $.tip(data.message);
                                setTimeout(function () {
                                    okfun();
                                }, time);
                            }
                        }
                        // 未登录
                        else if (data.data == 403) {
                            $.confirm("您还未登录，确认先去登录吗？", function () {
                                location.href = '/passport/login?ref=' + encodeURI(location.href);
                            });
                        }
                        else {
                            $.tip(data.message);
                        }
                    }
                };
                $.extend(params, options);
                this.submit(function (e) {
                    e.preventDefault();
                    var $form = $(this);
                    $form.validator();
                    var result = params.before($form);
                    if (result == false)
                        return false;
                    else {
                        $form.find(':submit').attr('disabled', 'disabled');
                        $form.find(':submit').addClass('disabled');
                        $form.find(':focus').blur();
                        if ($form.data("validator") != undefined && $form.data("validator") != null) {
                            $form.isValid(function (v) {
                                if (v) {
                                    post();
                                } else {
                                    $form.find(':submit').removeAttr('disabled');
                                    $form.find(':submit').removeClass('disabled');
                                }
                            });
                        }
                    }

                    function post() {
                        $.ajax({
                            type: $form.attr("method") || "post",
                            url: $form.attr("action"),
                            data: $form.serialize(),
                            beforeSend: function () {
                                $.loading('表单提交中，请稍后...');
                            },
                            complete: function () {
                                $.loading(false);
                            },
                            error: function (xmlHttpRequest, textStatus, errorThrown) {
                                $.tip('很遗憾，提交失败');
                                $form.find(':submit').removeAttr('disabled');
                                $form.find(':submit').removeClass('disabled');
                            },
                            success: function (data) {
                                if (data != null) {
                                    params.success($form, data);
                                }
                                $form.find(':submit').removeAttr('disabled');
                                $form.find(':submit').removeClass('disabled');
                            }
                        });
                    }

                    return false;
                });
                return this;
            }

            $.fn.loadTemplate = function (idOrTpl, data) {
                var bt = baidu.template;
                bt.LEFT_DELIMITER = '<%';
                bt.RIGHT_DELIMITER = '%>';
                var html = bt(idOrTpl, data);
                this.html(html);
                return this;
            }

            $.loadTemplate = function (idOrTpl, data) {
                var bt = baidu.template;
                bt.LEFT_DELIMITER = '<%';
                bt.RIGHT_DELIMITER = '%>';
                var html = bt(idOrTpl, data);
                return html;
            }

            $.fn.appendTemplete = function (idOrTpl, data) {
                var bt = baidu.template;
                bt.LEFT_DELIMITER = '<%';
                bt.RIGHT_DELIMITER = '%>';
                var html = bt(idOrTpl, data);
                this.append(html);
                return this;
            }

            $.fn.pageLoad = function (option) {
                var $this = $(this);
                var params = {
                    max: $this.data('max'),
                    trigger: $($this.data('trigger')),
                    model: 'append',
                    url: window.location.href
                };
                $.extend(params, option);
                var max = parseInt(params.max);

                var currentPage = parseInt(getQueryString('page', params.url) || 1);
                params.trigger.click(function () {
                    currentPage++;
                    var href = setQueryString('page', currentPage, params.url);
                    if (params.model == 'switch') {
                        $this.load(href + ' ' + $this.selector, function (html) {
                            alert(html);
                        });
                    } else {
                        $.get(href, function (html) {
                            $this.append($(html).find($this.selector).html());
                        });
                    }
                    if (currentPage == max) {
                        params.trigger.hide();
                    }
                });
            }

            $.fn.confirmPost = function (options) {
                var params = {
                    before: function ($trigger) {
                        return true;
                    },
                    success: function ($trigger, data) {
                        if (data.code == 200) {
                            $.tip(data.message);
                            setTimeout(function () {
                                if (params.nextUrl != null && params.nextUrl != "" && params.nextUrl != "#") {
                                    location.href = params.nextUrl;
                                } else {
                                    location.reload();
                                }
                            }, 1500);
                        } else {
                            $.tip(data.message, "error");
                        }
                    },
                    msg: '',
                    href: '',
                    nextUrl: ''
                };
                $.extend(params, options);


                var $this = $(this);
                var beforeResult = params.before($this);
                if (beforeResult == false)
                    return false;

                if (params.msg == null || params.msg == "") {
                    post();
                } else {
                    $.confirm(params.msg, function () {
                        post();
                    });
                }

                function post() {
                    $.post(params.href, function (data) {
                        if (data != null) {
                            params.success($this, data);
                        }
                    });
                };

                return $this;
            };
            $("body").on("click", ".j_confirm", function (e) {
                e.preventDefault();
                e.stopPropagation();
                var $this = $(this);
                $this.confirmPost({
                    msg: $this.data('msg'),
                    href: $this.data('href') || $this.attr('href'),
                    nextUrl: $this.data('next')
                });
            });
            $("body").on("click", ".j_loginConfirm", function (e) {
                e.preventDefault();
                e.stopPropagation();
                var $this = $(this);
                $.loginExcute(function () {
                    $this.confirmPost({
                        msg: $this.data('msg'),
                        href: $this.data('href') || $this.attr('href'),
                        nextUrl: $this.data('next')
                    });
                });
            });


            // 跳转链接前验证
            $.fn.ajaxCheck = function (options) {
                var $this = $(this);
                var params = {
                    url: $this.data('url'),
                    after: function () {
                        var nextUrl = $this.attr('href');
                        if (nextUrl != null) {
                            location.href = nextUrl;
                        }
                    }
                };
                $.extend(params, options);
                $this.click(function (e) {
                    e.preventDefault();
                    $.ajax({
                        url: params.url,
                        method: 'get',
                        success: function (data) {
                            // 验证通过
                            if (data.code == 200) {
                                params.after();
                            }
                            else {
                                console.info("未知返回码");
                                console.log(data);
                            }
                        },
                        error: function () {
                            console.info("验证请求失败");
                            params.after();
                        },
                    });
                });
            }

            // 异步加载Banner
            $.fn.loadBanner = function (options) {
                var $this = $(this);
                var params = {
                    url: $this.data('url'),
                    src: $this.data('src'),
                    after: function () {
                    }
                };
                $.extend(params, options);
                $.ajax({
                    url: params.url,
                    method: 'get',
                    success: function (data) {
                        if (data.code == 200 && data.data != null) {
                            appendBanner($this, data.data);
                        } else {
                            $this.attr('src', params.src); // 默认图片
                        }
                        params.after();
                    },
                    error: function () {
                        $this.attr('src', params.src); // 默认图片
                        params.after();
                    }
                });
                function appendBanner($img, banner) {
                    // console.info('加载banner');
                    // console.log(banner);
                    $img.attr('src', banner.imageUrl); // 更改图片
                    $img.attr('alt', banner.fileName); // alt
                    if (banner.isLinkEnabled) {   // 添加链接
                        if (banner.target == 1) {
                            $img.wrap('<a href="' + banner.linkUrl + '"></a>');
                        } else {
                            $img.wrap('<a href="' + banner.linkUrl + '" target="_blank"></a>');
                        }
                    }
                }
            };

            // 异步加载Banner列表
            $.fn.loadBannerList = function (options) {
                var $this = $(this);
                var params = {
                    url: $this.data('url'),
                    src: $this.data('src'),
                    after: function () {
                    }
                };
                $.extend(params, options);

                $.ajax({
                    url: params.url,
                    method: 'get',
                    success: function (data) {
                        // console.log(data);
                        if (data.code == 200 && data.data != null && data.data.length > 0) {
                            // console.info("加载异步banner");
                            appendBannerList($this, data.data);
                        }
                        else {
                            // console.info("加载默认banner");
                            defaultBanner($this, params.src);
                        }
                        params.after();
                    },
                    error: function () {
                        // console.info("加载默认banner");
                        defaultBanner($this, params.src);
                        params.after();
                    }
                });

                function appendBannerList($ul, bannerList) {
                    if (bannerList != null && bannerList.length > 0) {
                        for (var i = 0; i < bannerList.length; i++) {
                            var banner = bannerList[i];
                            var $li = $('<li><a><img></a></li>');
                            $li.find('img').attr('src', banner.imageUrl); // 更改图片
                            $li.find('img').attr('alt', banner.fileName); // alt
                            if (banner.isLinkEnabled) {
                                $li.find('a').attr('href', banner.linkUrl); // 添加链接
                                if (banner.target == 2) {
                                    $li.find('a').attr('target', '_blank');
                                }
                            }
                            $ul.append($li);
                        }
                    }
                }

                function defaultBanner($ul, src) {
                    var $li = $('<li><img></li>');
                    $li.find('img').attr('src', src);
                    $ul.append($li);
                }
            };


            $(".j_loginAjaxForm").loginAjaxForm();
            $(".j_uploadImage").uploadImage();
            // $(".j_uploadFile").uploadFile();
            $(".j_ajaxForm").ajaxSubmitForm();
            $('.j_page').pageLoad();
            $('.j_check').ajaxCheck();


            $('.j_iframe').on('click', function (e) {
                e.preventDefault();
                var $this = $(this);
                var url = $this.data('href');
                var title = $this.data('title');
                var width = $this.data('width') || '860px';
                var height = $this.data('height') || '580px';
                layer.open({
                    type: 2,
                    title: title,
                    area: [width, height],
                    shade: 0.8,
                    shadeClose: true,
                    content: url,
                    success: function (ayero, index) {
                        layer.getChildFrame('.j_close', index).click(function (e) {
                            e.preventDefault();
                            layer.close(index);
                        })
                    }
                });
            });

        })(jQuery);
    }

    return {
        init: function () {
            initFun();
        }
    }
}();

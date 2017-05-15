/**
 * 基于百度编辑器的上传组件
 * 其中附件上传需要修改源码
 * 需要在ueditor.all.min.js文件中找到d.execCommand("insertHtml",l)之后插入d.fireEvent('afterUpfile',c) 这个c可能根据实际情况修改
 *
 * html结构：
 * <div class="clearfix" id="upload">
 *  <input class="upload_input" type="hidden" name="imgUpload" value='{这里是从数据库读出的值}'>
 *    <ul class="upload_list"></ul>
 *    <a class="upload_btn" href="javascript:;">上传</a>
 *</div>
 *
 * 使用方法：
 * uploadUeditor.multipleImage($('#upload'));
 *
 * uploadUeditor.singleImage($('#upload'));
 *
 * uploadUeditor.multipleAttachment($('#upload'));
 *
 * uploadUeditor.singleAttachment($('#upload'));
 * */
(function () {
  var uploadUeditor = function () {


    var uploadEditor, // ueditor实例
      type, // 上传类型 0：多图上传 1：单图片上传 2：更换图片
      $trigger, // 当前触发的项目
      _tpl,
      _callback,
      settings = {
        tpl:null,
        callback:null
      };



    /**
     * 初始化
     * */
    var _initFun = function () {

      if (typeof UE == 'undefined') {
        console.log('没有引入ueditor');
        return false;
      }

      $('body').append('<textarea id="uploadEditor" style="height: 50px;display: none;"></textarea>');
      uploadEditor = UE.getEditor("uploadEditor", {
        isShow: false,
        focus: false,
        enableAutoSave: false,
        autoSyncData: false,
        autoFloatEnabled: false,
        wordCount: false,
        sourceEditor: null,
        scaleEnabled: true,
        toolbars: [["insertimage", "attachment"]]
      });
      uploadEditor.ready(function () {
        uploadEditor.addListener("beforeInsertImage", _beforeInsertImage);
        uploadEditor.addListener("afterUpfile", _afterUpfile);
      });
      return true;
    }();

    // 插入图片监听
    function _beforeInsertImage(t, result) {
      var $eles = getElements($trigger);
      var new_data = JSON.parse($eles.upload_input.val() || '[]');

      if (type == 2) {
        var index = $eles.upload_list.data('index');
        new_data[index].file = result[0].src;
        $eles.upload_list.find('.upload_item:eq(' + index + ') img').attr('src', result[0].src);
      } else {
        if (type == 1 && new_data.length > 0) return;

        var tmpArr = [];
        for (var i in result) {
          var tmp = {
            alt: result[i].alt,
            file: result[i].src
          };
          tmpArr.push(tmp);
          new_data.push(tmp);
          if (type == 1) break;
        }

        if(typeof _tpl == 'function'){
          $eles.upload_list.append(_tpl(tmpArr, type));
        }else {
          $eles.upload_list.append(_createImageHtml(tmpArr, type));
        }
        _tpl = false;
      }

      //$eles.upload_input.val(JSON.stringify(new_data));
      updateInputValue($eles.upload_input,new_data);

      if (type == 1) $eles.upload_btn.hide();
    }

    // 插入附件监听
    function _afterUpfile(t, result) {
      var $eles = getElements($trigger);

      var new_data = JSON.parse($eles.upload_input.val() || '[]');

      if (type == 2) {
        var index = $eles.upload_list.data('index');
        new_data[index].file = result[0].url;
        new_data[index].title = result[0].title;
        $eles.upload_list.find('.upload_item:eq(' + index + ') .t').attr('href', result[0].url).html(_getAttachmentIcon(result[0].title) + result[0].title);
      } else {
        if (type == 1 && new_data.length > 0) return;

        var tmpArr = [];
        for (var i in result) {
          var tmp = {
            title: result[i].title,
            file: result[i].url
          };
          tmpArr.push(tmp);
          new_data.push(tmp);
          if (type == 1) break;
        }

        if(typeof _tpl == 'function'){
          $eles.upload_list.append(_tpl(tmpArr, type));
        }else {
          $eles.upload_list.append(_createAttachmentHtml(tmpArr, type));
        }
        _tpl = false;
      }

      //$eles.upload_input.val(JSON.stringify(new_data));
      updateInputValue($eles.upload_input,new_data);

      if (type == 1) $eles.upload_btn.hide();
    }

    if (!_initFun) return false;

    /**
     * 更新input 内容
     * @param $input
     * @param $value
     */
    function updateInputValue($input,$value) {
      if(typeof $value != 'string') $value =  JSON.stringify($value);
      $input.val($value);

      //console.log(typeof _callback );
      if(typeof _callback == 'function'){
        _callback($input,$value);
        _callback = null;
      }
    }

    /**
     * 返回控件元素jq对象
     * @param $wrapper
     * @returns {{upload_btn: *, upload_list: *, upload_input: *}}
     */
    function getElements($wrapper) {
      return {
        upload_btn: $wrapper.find('.upload_btn'),
        upload_list: $wrapper.find('.upload_list'),
        upload_input: $wrapper.find('.upload_input')
      }
    }

    /*********************************************************************************************************************
     * 图片上传初始化
     * @private
     */

      // 单图片上传
    var _singleImage = function ($object,conf) {
      if(typeof conf == 'undefined') conf = {};
        _triggerImageDialog($object, 1, function (dialog) {
          type = 1;
          dialog.iframeUrl = dialog.iframeUrl + '?type=1';
          dialog.title = '单图片上传';
        },$.extend({},settings,conf));
      };

    // 多图片上传
    var _multipleImage = function ($object,conf) {
      if(typeof conf == 'undefined') conf = {};
      _triggerImageDialog($object, 0, function (dialog) {
        type = 0;
        dialog.title = '多图片上传';
      },$.extend({},settings,conf));
    };

    // ueditor图片上传组件
    function _triggerImageDialog($object, uploadType, callback,conf) {
      $object.each(function () {
        var $this = $(this),
          $eles = getElements($this);

        $this.addClass('upload_wrapper');

        //初始化显示图片
        if(typeof conf.tpl == "function"){
          $eles.upload_list.html(conf.tpl(JSON.parse($eles.upload_input.val() || '[]'), uploadType));

        }else{
          $eles.upload_list.html(_createImageHtml(JSON.parse($eles.upload_input.val() || '[]'), uploadType));
        }


        if (uploadType == 1 && $eles.upload_input.val()) $eles.upload_btn.hide();

        if($this.data('uploadUeditor')) return false;
        $this.data('uploadUeditor',true);

        // 弹出框
        $eles.upload_btn.click(function () {
          $trigger = $this;
          _tpl = conf.tpl;
          _callback = conf.callback;
          var dialog = uploadEditor.getDialog("insertimage");
          callback(dialog);
          dialog.render();
          dialog.open();
        });

        // 删除
        $this.on('click', '.upload_del', function () {
          _callback = conf.callback;
          _uploadDel($(this), $eles.upload_input, $eles.upload_btn);
        });

        // 更换图片
        $this.on('click', '.upload_change', function () {
          var $li = $(this).parents('.upload_item'),
            dialog = uploadEditor.getDialog("insertimage");
          _tpl = conf.tpl;
          _callback = conf.callback;

          $eles.upload_list.data('index', $li.index());
          type = 2;
          $trigger = $eles.upload_list.parents('.upload_wrapper');
          dialog.iframeUrl = dialog.iframeUrl + '?type=1';
          dialog.title = '更换图片';
          dialog.render();
          dialog.open();
        });

        // 预览
        $this.on('click', '.upload_preview', function () {
          window.open($(this).parents('.upload_item').find('img').attr('src'));
        });

        // 更改图片描述
        $this.on('change', '.upload_info', function () {
          var $this = $(this),
            _index = $this.parents('.upload_item').index(),
            _val = JSON.parse($eles.upload_input.val() || '[]');
          _val[_index].alt = $this.val();
          //$eles.upload_input.val(JSON.stringify(_val));
          _callback = conf.callback;
          updateInputValue($eles.upload_input,_val);
        });

        // 排序
        $this.on('click', '.upload_move_up,.upload_move_down', function () {
          var $this = $(this),
            $li = $this.parents('.upload_item'),
            isUp = $this.hasClass('upload_move_up'),
            $obj = isUp ? $li.prev() : $li.next();

          if ($obj.size() < 1) return;

          var _val = JSON.parse($eles.upload_input.val() || '[]'),
            _index = $li.index(),
            _currData = _val[_index];

          if (isUp) {
            _val[_index] = _val[_index - 1];
            _val[_index - 1] = _currData;
            $obj.before($li.clone());
          } else {
            _val[_index] = _val[_index + 1];
            _val[_index + 1] = _currData;
            $obj.after($li.clone())
          }
          $li.remove();
          //$eles.upload_input.val(JSON.stringify(_val));
          _callback = conf.callback;
          updateInputValue($eles.upload_input,_val);
        });
      });
    }

    /**
     * 生成图片html
     * @param data
     * @param uploadType
     * @returns {string}
     * @private
     */
    function _createImageHtml(data, uploadType) {
      var _html = '';
      for (var i in data) {
        _html += '<li class="upload_item"><div class="pull-left"><div class="pic-wraper"><div class="pic"><div class="inner">' +
          '<a class="upload_preview" href="javascript:;"><img src="' + data[i].file + '" alt="' + data[i].alt + '"></a></div></div></div></div>' +
          '<div class="info-form"><textarea class="form-control upload_info" name="upload_description" maxlength="200" placeholder="图片描述">' + (data[i].alt || '') + '</textarea>' +
          '<div class="opt">' +
          (uploadType == 1 ? '' : '<a class="upload_move_down pull-right" href="javascript:;" title="下移"><span class="ion ion-arrow-down-a"></span></a><a class="upload_move_up pull-right" href="javascript:;" title="上移"><span class="ion ion-arrow-up-a"></span></a>') +
          '<a class="btn btn-xs btn-info upload_change" href="javascript:;">更换图片</a>' +
          '<a class="btn btn-xs btn-link upload_del" data-type="' + uploadType + '" href="javascript:;">删除图片</a></div></div></li>';

        if (uploadType == 1) break;
      }
      return _html;
    }

    /*********************************************************************************************************************
     * 附件上传初始化
     * @private
     */
    var _singleAttachment = function ($object,conf) {
      if(typeof conf == 'undefined') conf = {};
      _triggerAttachmentDialog($object, 1, function (dialog) {
        type = 1;
        dialog.iframeUrl = dialog.iframeUrl + '?type=1';
        dialog.title = '单文件上传';
      },$.extend({},settings,conf));
    };

    var _multipleAttachment = function ($object,conf) {
      if(typeof conf == 'undefined') conf = {};
      _triggerAttachmentDialog($object, 0, function (dialog) {
        type = 0;
        dialog.title = '多文件上传';
      },$.extend({},settings,conf));
    };

    // ueditor附件上传组件
    function _triggerAttachmentDialog($object, uploadType, callback,conf) {
      $object.each(function () {
        var $this = $(this),
          $eles = getElements($this);

        $this.addClass('upload_wrapper');

        //初始化显示附件列表
        if(typeof conf.tpl == 'function'){
          $eles.upload_list.html(conf.tpl(JSON.parse($eles.upload_input.val() || '[]'), uploadType));
        }else{
          $eles.upload_list.html(_createAttachmentHtml(JSON.parse($eles.upload_input.val() || '[]'), uploadType));
        }

        if (uploadType == 1 && $eles.upload_input.val()) $eles.upload_btn.hide();

        if($this.data('uploadUeditor')) return false;
        $this.data('uploadUeditor',true);

        // 弹出框
        $eles.upload_btn.click(function () {
          $trigger = $this;
          _tpl = conf.tpl;
          _callback = conf.callback;
          var dialog = uploadEditor.getDialog("attachment");
          callback(dialog);
          dialog.render();
          dialog.open();
        });

        // 删除
        $this.on('click', '.upload_del', function () {
          _callback = conf.callback;
          _uploadDel($(this), $eles.upload_input, $eles.upload_btn);
        });

        // 更换文件
        $this.on('click', '.upload_change', function () {
          var $li = $(this).parents('.upload_item'),
            dialog = uploadEditor.getDialog("attachment");
          _tpl = conf.tpl;
          _callback = conf.callback;
          $eles.upload_list.data('index', $li.index());
          type = 2;
          $trigger = $eles.upload_list.parents('.upload_wrapper');
          dialog.iframeUrl = dialog.iframeUrl + '?type=1';
          dialog.title = '更换附件';
          dialog.render();
          dialog.open();
        });

        // 修改信息
        $this.on('click', '.upload_edit', function () {
          var $p = $(this).parents('.upload_item'),
            $t = $p.find('.t'),
            $n = $t.find('.n'),
            _title = prompt("请输入文件标题", $n.text());
          if (_title) {
            $n.html(_title);
            var _index = $p.index(),
              _val = JSON.parse($eles.upload_input.val());
            _val[_index].title = _title;
            //$eles.upload_input.val(JSON.stringify(_val));
            _callback = conf.callback;
            updateInputValue($eles.upload_input,_val);
          }
        });

      });
    }

    /**
     * 生成附件列表html
     * @param data
     * @param uploadType
     * @returns {string}
     * @private
     */
    function _createAttachmentHtml(data, uploadType) {
      var _html = '';
      for (var i in data) {
        _html += '<li class="upload_item"><span class="opt fade"><a class="upload_edit" href="javascript:;">修改</a><a class="upload_change" href="javascript:;">更换</a>' +
          '<a class="upload_del" data-type="' + uploadType + '" href="javascript:;">删除</a></span>' +
          '<a class="t" href="' + data[i].file + '" target="_blank">' + _getAttachmentIcon(data[i].file) + '<span class="n">' + data[i].title + '</span></a> </li>';
        if (uploadType == 1) break;
      }
      return _html;
    }

    /**
     * 获取附件图标
     * @param $file
     * @private
     */
    function _getAttachmentIcon($file) {
      return '<span class="ion ion-document-text"></span>';
    }

    /**
     * 删除附件
     * @param $del
     * @param $input
     * @param $btn
     * @private
     */
    function _uploadDel($del, $input, $btn) {
      var $li = $del.parents('.upload_item');

      $li.fadeOut(function () {
        var _data = JSON.parse($input.val());
        _data.splice($li.index(), 1);
        //$input.val(_data.length > 0 ? JSON.stringify(_data) : '');
        updateInputValue($input,_data.length > 0 ? JSON.stringify(_data) : '');
        $li.remove();
        if ($del.data('type') == 1) $btn.fadeIn();
      });
    }

    return {
      singleImage: _singleImage,
      multipleImage: _multipleImage,
      singleAttachment: _singleAttachment,
      multipleAttachment: _multipleAttachment
    }
  }();

  window.uploadUeditor = uploadUeditor;
})();

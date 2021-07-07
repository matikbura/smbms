<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
<script src="${pageContext.request.contextPath}/js/layui.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/layui.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/cropper.css" />
<style>
    #choice-btn {
        display: inline-block;
        height: 38px;
        line-height: 38px;
        padding: 0 18px;
        background-color: #009688;
        color: #fff;
        white-space: nowrap;
        text-align: center;
        font-size: 14px;
        border: none;
        border-radius: 2px;
        cursor: pointer;
    }
</style>
<div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>头像修改</span>
            </div>
            <div class="providerAdd">
                <div style="margin: 20px;">
                    <div style="margin-bottom:30px;">
                        <label for="cropper_imgUpload">
                            <span id="choice-btn">选择图片</span>
                        </label>
                        <input type="file" id="cropper_imgUpload" name="file" style="display:none" accept="image/*">
                    </div>
                    <div class="layui-row layui-col-space15">
                        <div class="layui-col-xs9">
                            <div id="readyimg"
                                 style="height: 360px;width: 100%;border: 1px dashed black;background-color: rgb(247, 247, 247);">
                                <img id="img" src="" alt="">
                            </div>
                        </div>
                        <div class="layui-col-xs3">
                            <div>预览:</div>
                            <div id="img-preview" style="width: 180px;height: 120px;overflow: hidden;border: 1px dashed black;">
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-hide oper-btn" style="margin-top: 20px;">
                        <div class="layui-col-xs9">
                            <button type="button" class="layui-btn layui-icon layui-icon-left" cropper-event="rotate" data-option="-15"
                                    title="Rotate -90 degrees"> 向左旋转
                            </button>
                            <button type="button" class="layui-btn layui-icon layui-icon-right" cropper-event="rotate" data-option="15"
                                    title="Rotate 90 degrees"> 向右旋转
                            </button>
                            <button type="button" class="layui-btn layui-icon layui-icon-refresh" cropper-event="reset"
                                    title="reset-image">重置图片
                            </button>
                        </div>
                        <div class="layui-col-xs2 layui-col-xs-offset1">
                            <button type="button" class="layui-btn layui-btn-fluid" id="keep-save" cropper-event="confirmSave">保存修改
                            </button>
                        </div>
                    </div>
                </div>
            </div>
</div>
    </section>

<script>
    layui.config({
        base: '${pageContext.request.contextPath }/js/'
    }).extend({
        cropper: 'cropper'
    }).use(['element', 'layer', 'cropper', 'jquery', 'jqcropper'], function () {
        var element = layui.element;
        var $ = layui.jquery;
        var layer = layui.layer;
        var cropper = layui.cropper;

        var imageEle = $("#img")
            , preview = '#img-preview'
            , file = $("input[name='file']")
            , uploadImageMaxSize = 2048 //文件上传大小限制
            , options = {
            aspectRatio: 3 / 2,
            viewMode: 1,
            preview: preview,
            dragMode: 'none',
            responsive: false,
            restore: false
            // cropBoxMovable:false,
            // cropBoxResizable:false,
        };
        // 找到上传图片的input标签绑定change事件
        $("#cropper_imgUpload").change(function () {
            $(".oper-btn").removeClass("layui-hide");
            // 1. 创建一个读取文件的对象
            var fileReader = new FileReader();
            fileReader.readAsDataURL(this.files[0]);
            fileReader.onload = function () {
                // 2. 等上一步读完文件之后才 把图片加载到img标签中
                imageEle.attr("src", fileReader.result); //图片链接（base64）
                imageEle.cropper(options);
            };
        });
        function blobToDataURL(blob, callback) {
            var a = new FileReader();
            a.onload = function (e) { callback(e.target.result); }
            a.readAsDataURL(blob);
        }
        $(".layui-btn").on('click', function () {
            var event = $(this).attr("cropper-event");
            //监听确认保存图像
            if (event === 'confirmSave') {
                imageEle.cropper("getCroppedCanvas").toBlob(function (blob) {
                    var filesize = (blob["size"] / 1024).toFixed(2);
                    if (filesize < uploadImageMaxSize) {
                        var formData = new FormData();
                        blobToDataURL(blob, function (dataurl) {
                            $.ajax({
                                type: "post",
                                url: '${pageContext.request.contextPath }/user/uploadHeadImage', //用于文件上传的服务器端请求地址
                                data: {imgStr:dataurl},
                                success: function (response) {
                                        alert("ok");
                                        location.reload();
                                },
                                error: function (response) {
                                    layer.alert("网络异常", {icon: 2});
                                }
                        });
                    });
                    } else {
                        layer.alert("上传图片大小不超过" + uploadImageMaxSize + "KB", {icon: 2})
                    }
                });
            } else if (event === 'rotate') {
                //监听旋转
                var option = $(this).attr('data-option');
                imageEle.cropper('rotate', option);
            } else if (event === 'reset') {
                //重设图片
                imageEle.cropper('reset');
            }
        });
    });
</script>
<%@include file="/jsp/common/foot.jsp" %>
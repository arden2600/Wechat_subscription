<%@ page language="java" import="java.util.*,com.whoshell.common.JSSDKConfigUtil" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

 Map<String,Object>  ret = new HashMap<String,Object> ();  
 ret=JSSDKConfigUtil.getWxConfig(request);  
 request.setAttribute("appId", ret.get("appId"));  
 request.setAttribute("timestamp", ret.get("timestamp"));  
 request.setAttribute("nonceStr", ret.get("nonceStr"));  
 request.setAttribute("signature", ret.get("signature"));  
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>信息提示:</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script type="text/javascript">
$(function(){
wx.config({
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp:'${timestamp}' , // 必填，生成签名的时间戳
    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
    signature: '${signature}',// 必填，签名，见附录1
    jsApiList: [
    	'checkJsApi',
        'openLocation',
        'getLocation',
        'onMenuShareTimeline',
        'onMenuShareAppMessage'
    ] 
	});
	
wx.ready(function () {
wx.getLocation({
    success: function (res) {
        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
        var speed = res.speed; // 速度，以米/每秒计
        var accuracy = res.accuracy; // 位置精度
    },
    cancel: function (res) {
        alert('用户拒绝授权获取地理位置');
    }
});

wx.onMenuShareTimeline({
    title: '享必得', // 分享标题
    link: 'https://wx.example.com', // 分享链接
    imgUrl: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492689877062&di=9d7993cf1085eb0aabecb10fd82450a2&imgtype=0&src=http%3A%2F%2Fpicture.ik123.com%2Fuploads%2Fallimg%2F160811%2F3-160Q11AR2.jpg', // 分享图标
    success: function () { 
        // 用户确认分享后执行的回调函数
        alert('分享chenggong');
    },
    cancel: function () { 
        // 用户取消分享后执行的回调函数
        alert('分享shibai');
    }
    
});
  wx.checkJsApi({
            jsApiList: [
                'getLocation',
                'onMenuShareTimeline',
                'onMenuShareAppMessage'
            ],
            success: function (res) {
                alert(JSON.stringify(res));
            }
        });
   function share() {
        wx.showOptionMenu();
   }


});


});


</script>
</head>
  
  <body>
  <button onclick="share();">分享到朋友圈</button>
  </body>
</html>

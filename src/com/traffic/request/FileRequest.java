package com.traffic.request;

public class FileRequest extends BaiduRequest {
	final int HOMEPAGESOCKETTIMEOUT = 3000;
	final int HOMEPAGECONNECTTIMEOUT = 3000;
	@Override
	public void init() {
		setScheme("https");
		setHost("www.baidu.com");
		setPath("/");
		setHeaderStr("Connection keep-alive\r\nAccept-Encoding gzip, deflate, sdch\r\nHost www.baidu.com\r\nAccept-Language zh-CN,zh;q=0.8\r\nUpgrade-Insecure-Requests 1\r\nUser-Agent Mozilla/5.0 (Linux; Android 4.2.2; GT-I9505 Build/JDQ39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36");
		setHeaders();
		setSocketTimeout(HOMEPAGESOCKETTIMEOUT);
		setConnectTimeout(HOMEPAGECONNECTTIMEOUT);
	}
	
	public void setParamar(){
		String paramerStr = "?action=static&ms=1&version=css_page_2@0,css_callapp@0,css_weather@0,css_icon@0,css_plus@0,css_edit@0,css_modal@0,css_widget_sug@0,js_zepto@0,js_index@0,js_banner_ctrl@0,js_inputlog@0,js_bdnow@0,js_tpl_function@0,js_widget_textinput@0,js_widget_sug@0,js_fastclick@0,js_mp@0,js_hash_lib@0,js_utils@0,js_prefetch@0,js_sug@0,js_iscroll@0,js_init@0,js_geolocation@0,js_login@0,js_plus@0,js_md5@0,js_plus_edit@0,js_lswrite@0,js_modal@0,js_baiduloc@0,js_callbaiduapp_android@0&callback=B.getCode&r=16";
	}
	
}

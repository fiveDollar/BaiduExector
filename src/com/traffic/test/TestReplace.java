package com.traffic.test;

public class TestReplace {
	public static void main(String[] args) {
		String t = "css_page_2@2,css_callapp@1,css_weather@1,css_icon@1,css_plus@1,css_edit@1,css_modal@1,css_widget_sug@1,js_zepto@1,js_index@4,js_banner_ctrl@2,js_inputlog@2,js_bdnow@5,js_tpl_function@2,js_widget_textinput@1,js_widget_sug@1,js_fastclick@1,js_hash_lib@1,js_utils@2,js_prefetch@2,js_sug@2,js_iscroll@1,js_init@2,js_geolocation@1,js_login@1,js_plus@3,js_md5@1,js_plus_edit@2,js_lswrite@1,js_modal@1,js_setSearchEngine@1,js_callbaiduapp_ios@2";
		System.out.println(t.replaceAll("@\\d", "@0"));
	}
}

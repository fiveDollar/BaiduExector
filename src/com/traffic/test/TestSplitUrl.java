package com.traffic.test;

public class TestSplitUrl {
	public static void main(String[] args) {
		String a = "http://m.baidu.com/from=844b/bd_page_type=1/ssid=0/uid=0/pu=usm%400%2Csz%401320_1001%2Cta%40iphone_2_4.2_3_537/baiduid=0A7754E7546AC3171E6A96F24FD658B2/w=0_10_%E8%8B%8F%E5%B7%9E%E9%97%BB%E9%81%93/t=iphone/l=1/tc?ref=www_iphone&lid=11646302960875233254&order=5&vit=osres&tj=www_normal_5_0_10_title&waput=4&waplogo=1&cltj=normal_title&dict=-1&nt=wnor&title=%E8%8B%8F%E5%B7%9E%E9%97%BB%E9%81%93%E7%BD%91%E7%BB%9C%E7%A7%91%E6%8A%80%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B82015%E6%9C%80%E6%96%B0%E6%8B%9B%E8%81%98%E4%BF%A1..._%E4%B8%AD%E5%8D%8E%E8%8B%B1%E6%89%8D%E7%BD%91&sec=8025&di=981c8f684b6037e7&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IEQGG_ytQ_zSvpVvte4viZQRAYTzyQHaMCoCb9jDOvBgFuHyi_HhunM5X";
		for(String c : a.split("[/&\\?]")){
			System.out.println(c);
		}
	}
}

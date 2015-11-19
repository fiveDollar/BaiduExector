package cm.traffic.send;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.traffic.Response.MyResponse;
import com.traffic.httpclientUtil.CookieUtil;
import com.traffic.httpclientUtil.EntityReaderUtil;
import com.traffic.httpclientUtil.HttpGetBuilderUtil;
import com.traffic.httpclientUtil.MyHttpClients;
import com.traffic.httpclientUtil.RequestSenderUtil;
import com.traffic.request.FileRequest;
import com.traffic.request.HomePageRequest;
import com.traffic.request.OpenappRequest;
import com.traffic.request.PreRequest;
import com.traffic.request.TCRequest1;
import com.traffic.request.TCRequest2;
import com.traffic.request.TCRequest3;
import com.traffic.request.TCRequest4;
import com.traffic.request.TCRequestShop;
import com.traffic.request.WeatherRequest;
import com.traffic.request.WordPreRequest;

public class Sender {
	public static HttpContext context = new BasicHttpContext();
	public static HttpClientContext httpContext = HttpClientContext
			.adapt(context);
	public static CloseableHttpClient httpClient = MyHttpClients.custom()
			.setConnectionManager(100)
			// .setProxy("222.92.117.87", 31287)
			.setRetryHandler().build();
	public static String word = "苏州闻道";
	public static CookieStore cs = new BasicCookieStore();
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		
		httpContext.setCookieStore(cs);
		HomePageRequest homeR = new HomePageRequest();
		homeR.init();
		HttpGet httpGet = HttpGetBuilderUtil.Builder(homeR);
		MyResponse myR = RequestSenderUtil.send(httpClient, httpGet,
				httpContext, true);
		// 获取doc
		String webCon = EntityReaderUtil.ReadeEntity(myR.getBhe(), "utf-8");
		Document doc = Jsoup.parse(webCon, "utf-8");
		Header[] homeHeader = myR.getHttpClientContext().getResponse()
				.getAllHeaders();
		cs.addCookie(CookieUtil.GetCookieFromHeader(homeHeader, "__bsi"));

		FileRequest frequest = new FileRequest();
		frequest.init(homeHeader, doc);
		HttpGet fileGet = HttpGetBuilderUtil.Builder(frequest);
		Header[] fileHeader = RequestSenderUtil
				.send(httpClient, fileGet, httpContext, false)
				.getHttpClientContext().getResponse().getAllHeaders();

		TCRequest1 tcRequest = new TCRequest1();
		tcRequest.init(homeHeader, doc);
		HttpGet tcGet = HttpGetBuilderUtil.Builder(tcRequest);
		MyResponse tc1Response = RequestSenderUtil.send(httpClient, tcGet,
				httpContext, false);
		Header[] tc1Headers = tc1Response.getHttpClientContext().getResponse()
				.getAllHeaders();
		printHeader(tc1Headers, "__bsi");

		cs.addCookie(CookieUtil.GetCookieFromHeader(fileHeader, "__bsi"));
		WeatherRequest weatherRequset = new WeatherRequest();
		weatherRequset.init(homeHeader, doc, httpContext.getResponse()
				.getAllHeaders());
		HttpGet weatherGet = HttpGetBuilderUtil.Builder(weatherRequset);
		MyResponse weatherResponse = RequestSenderUtil.send(httpClient,
				weatherGet, httpContext, false);
		// printHeader(weatherResponse.getHttpClientContext().getRequest().getAllHeaders());

		cs.addCookie(CookieUtil.CookieBuilder("plus_lsv",
				doc.getElementById("index-card").attr("data-lsversion"),
				"m.baidu.com", "/", 604800000));
		cs.addCookie(CookieUtil.CookieBuilder("PLUS", "1", "m.baidu.com", "/",
				604800000));
		cs.addCookie(CookieUtil.GetCookieFromHeader(tc1Headers, "__bsi"));
		TCRequest2 tc2 = new TCRequest2();
		tc2.init(doc);
		HttpGet tc2Get = HttpGetBuilderUtil.Builder(tc2);
		MyResponse tc2Response = RequestSenderUtil.send(httpClient, tc2Get,
				httpContext, false);
		printHeader(tc2Response.getHttpClientContext().getRequest()
				.getAllHeaders(), "__bsi");

		cs.addCookie(CookieUtil.GetCookieFromHeader(tc1Headers, "__bsi"));
		TCRequestShop tcShop = new TCRequestShop();
		tcShop.init(doc);
		HttpGet tcShopGet = HttpGetBuilderUtil.Builder(tcShop);
		MyResponse tcShopResponse = RequestSenderUtil.send(httpClient,
				tcShopGet, httpContext, false);
		printHeader(tcShopResponse.getHttpClientContext().getRequest()
				.getAllHeaders(), "__bsi");

		cs.addCookie(CookieUtil.GetCookieFromHeader(tc1Headers, "__bsi"));
		OpenappRequest openRequest = new OpenappRequest();
		openRequest.init(doc);
		HttpGet openGet = HttpGetBuilderUtil.Builder(openRequest);
		MyResponse openResponse = RequestSenderUtil.send(httpClient, openGet,
				httpContext, false);
		printHeader(openResponse.getHttpClientContext().getRequest()
				.getAllHeaders(), "__bsi");

		cs.addCookie(CookieUtil.GetCookieFromHeader(tc1Headers, "__bsi"));
		TCRequest3 tc3 = new TCRequest3();
		tc3.init(doc);
		HttpGet tc3Get = HttpGetBuilderUtil.Builder(tc3);
		MyResponse tc3Response = RequestSenderUtil.send(httpClient, tc3Get,
				httpContext, false);
		printHeader(tc3Response.getHttpClientContext().getRequest()
				.getAllHeaders(), "__bsi");

		cs.addCookie(CookieUtil.GetCookieFromHeader(tc1Headers, "__bsi"));
		TCRequest4 tc4 = new TCRequest4();
		tc4.init(doc);
		HttpGet tc4Get = HttpGetBuilderUtil.Builder(tc4);
		MyResponse tc4Response = RequestSenderUtil.send(httpClient, tc4Get,
				httpContext, false);
		printHeader(tc4Response.getHttpClientContext().getRequest()
				.getAllHeaders(), "__bsi");

		removeCookie(new String[]{"__bsi"});
		System.out.println(cs.getCookies());
		String[] wordSlice = word.split("");
		String currenWord = "";
		for (int i = 1; i < wordSlice.length; i++) {
			System.out.println(wordSlice[i]);
			PreRequest pr = new PreRequest();
			currenWord += wordSlice[i];
			pr.init(doc, i + 1, currenWord);
			HttpGet prGet = HttpGetBuilderUtil.Builder(pr);
			MyResponse prResponse = RequestSenderUtil.send(httpClient, prGet,
					httpContext, true);
//			printHeader(prResponse.getHttpClientContext().getRequest().getAllHeaders());
			String jsonStr= EntityReaderUtil.ReadeEntity(prResponse.getBhe(),
					 "utf-8");
			System.out.println(jsonStr);
			ArrayList<String> preWords = praseJson(jsonStr);
			sendWordPre(preWords, doc);
			cs.addCookie(CookieUtil.GetCookieFromHeader(prResponse.getHttpClientContext().getResponse().getAllHeaders(), "__bsi","m.baidu.com"));
//			System.out.println(cs.getCookies());
		}


	}
	public static void removeCookie(String[] removeKey){
		List<Cookie> cookieList = cs.getCookies();
		cs.clear();
		for (Cookie cookie : cookieList) {
			boolean flag = true;
			for (String key : removeKey) {
				if (cookie.getName().equals(key)) {
					flag = false;
					break;
				}	
			}
			if(flag)
				cs.addCookie(cookie);
		}
	}
	public static void sendWordPre(ArrayList<String> preWords,Document doc){
		
		
		for (String preWord : preWords) {
			removeCookie(new String[]{"__bsi"});
			WordPreRequest wordPredRequst = new WordPreRequest();
			wordPredRequst.init(doc, preWord.split(",")[0], Integer.parseInt(preWord.split(",")[1]));
			HttpGet wordPreGet = HttpGetBuilderUtil.Builder(wordPredRequst);
			MyResponse wordPreResponse = RequestSenderUtil.send(httpClient, wordPreGet, httpContext, false);
//			printHeader(wordPreResponse.getHttpClientContext().getRequest().getAllHeaders());
		}
		
	}
	
	public static ArrayList<String> praseJson(String jsonStr) {
		ArrayList<String> preWord = new ArrayList<>();
		if(!jsonStr.contains("pre:"))
			return preWord;
		jsonStr = jsonStr.split("s:\\[")[1];
		String s[] = jsonStr.split("],")[0].replace("\"", "").split(",");
		String pre[] = jsonStr.split("pre:\\[")[1].replace("\"", "")
				.replace("]});", "").split(",");
		
		for (int i = 0; i < pre.length; i++) {
			if (pre[i].equals("1"))
				preWord.add(s[i-1]+","+i);
		}
		return preWord;
	}

	public static CookieStore cookieAdjust(CookieStore cookieStore,
			String needCookie[]) {
		CookieStore tmpCookieStore = new BasicCookieStore();
		List<Cookie> cookieList = cookieStore.getCookies();
		for (String cookieName : needCookie) {
			for (Cookie cookie : cookieList) {
				if (cookieName.equals(cookie.getName())) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					if (cookie.getExpiryDate() != null)
						System.out.println(cookie.getName() + ":"
								+ sdf.format(cookie.getExpiryDate()));

					tmpCookieStore.addCookie(cookie);
				}
			}
		}
		return tmpCookieStore;
	}

	public static CookieStore changeCookie(CookieStore originCs,
			CookieStore CurrentCs) {
		CookieStore csTmp = new BasicCookieStore();
		List<Cookie> originCookieList = originCs.getCookies();
		List<Cookie> currentCookieList = CurrentCs.getCookies();
		List<Cookie> cookieAll = new ArrayList<Cookie>();
		cookieAll.addAll(originCookieList);
		cookieAll.addAll(currentCookieList);
		for (int i = 0; i < cookieAll.size(); i++) {
			for (int j = i + 1; j < cookieAll.size(); j++) {
				if (cookieAll.get(i).getName() == cookieAll.get(j).getName()) {
					csTmp.addCookie(cookieAll.get(j));
					break;
				}
				if (j == cookieAll.size() - 1)
					csTmp.addCookie(cookieAll.get(i));
			}
			if (i == cookieAll.size() - 1)
				csTmp.addCookie(cookieAll.get(i));
		}
		return csTmp;
	}

	public static void printHeader(Header[] headers) {
		for (Header header : headers) {
			System.out.println(header);
		}
	}

	public static void printHeader(Header[] headers, String key) {
		for (Header header : headers) {
			if (header.getName().toLowerCase().contains(key.toLowerCase())
					|| header.getValue().toLowerCase()
							.contains(key.toLowerCase()))
				System.out.println(header);
		}
	}
}

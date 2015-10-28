package cm.traffic.send;

import java.io.IOException;
import java.net.URISyntaxException;
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
import com.traffic.request.TCRequest;
import com.traffic.request.TjRequest;
import com.traffic.request.WeatherRequest;


public class Sender {
	public static HttpContext context = new BasicHttpContext();
	public static HttpClientContext httpContext = HttpClientContext.adapt(context); 
	public static CloseableHttpClient httpClient = MyHttpClients.custom()
			.setConnectionManager(100)
			.setProxy("222.92.117.87", 31287)
			.setRetryHandler()
			.build();

	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		CookieStore cs = new BasicCookieStore();
		httpContext.setCookieStore(cs);
		HomePageRequest homeR = new HomePageRequest();
		homeR.init();
		HttpGet httpGet = HttpGetBuilderUtil.Builder(homeR);
		MyResponse myR = RequestSenderUtil.send(httpClient, httpGet, httpContext, true);
		//ªÒ»°doc
		String webCon = EntityReaderUtil.ReadeEntity(myR.getBhe(), "utf-8");
		Document doc = Jsoup.parse(webCon,"utf-8");
		Header[] homeHeader = myR.getHttpClientContext().getResponse().getAllHeaders();
		cs.addCookie(CookieUtil.GetCookieFromHeader(homeHeader, "__bsi"));
		
		FileRequest frequest = new FileRequest();
		frequest.init(homeHeader, doc);
		HttpGet fileGet = HttpGetBuilderUtil.Builder(frequest);
		Header[] fileHeader = RequestSenderUtil.send(httpClient, fileGet, httpContext, false).getHttpClientContext().getResponse().getAllHeaders();
		cs.addCookie(CookieUtil.GetCookieFromHeader(homeHeader, "__bsi"));
		
		TjRequest tjRequest1 = new TjRequest();
		tjRequest1.init(homeHeader, doc, null);
		HttpGet tjGet1 = HttpGetBuilderUtil.Builder(tjRequest1);
		RequestSenderUtil.send(httpClient, tjGet1, httpContext, false);
		
		cs.addCookie(CookieUtil.GetCookieFromHeader(fileHeader, "__bsi"));
		WeatherRequest weatherRequset = new WeatherRequest();
		weatherRequset.init(homeHeader, doc, httpContext.getResponse().getAllHeaders());
		HttpGet weatherGet = HttpGetBuilderUtil.Builder(weatherRequset);
		RequestSenderUtil.send(httpClient, weatherGet, httpContext, false);
		printHeader(httpContext.getRequest().getAllHeaders());
		
		cs.addCookie(CookieUtil.CookieBuilder("plus_lsv",doc.getElementById("index-card").attr("data-lsversion"), ".baidu.com", "/",604800000));
		cs.addCookie(CookieUtil.CookieBuilder("PLUS","1", ".baidu.com", "/",604800000));
		System.out.println(cs.getCookies());
		TCRequest tcRequest = new TCRequest();
		tcRequest.init(homeHeader, doc);
		HttpGet tcGet = HttpGetBuilderUtil.Builder(tcRequest);
		RequestSenderUtil.send(httpClient, tcGet, httpContext, false);
		System.out.println(tcGet.getURI());
		printHeader(httpContext.getRequest().getAllHeaders());
		
	}
	public static CookieStore changeCookie(CookieStore originCs,CookieStore CurrentCs){
		CookieStore csTmp = new BasicCookieStore();
		List<Cookie> originCookieList = originCs.getCookies();
		List<Cookie> currentCookieList = CurrentCs.getCookies();
		List<Cookie> cookieAll = new ArrayList<Cookie>();
		cookieAll.addAll(originCookieList);
		cookieAll.addAll(currentCookieList);
		for (int i = 0; i < cookieAll.size(); i++) { 
			for (int j = i+1; j < cookieAll.size(); j++) {
				if (cookieAll.get(i).getName() ==  cookieAll.get(j).getName()) {
					csTmp.addCookie(cookieAll.get(j));
					break;
				}
				if(j == cookieAll.size()-1)
					csTmp.addCookie(cookieAll.get(i));
			}
			if(i == cookieAll.size()-1)
				csTmp.addCookie(cookieAll.get(i));
		}
		return csTmp;
	}
	public static void printHeader(Header[] headers){
		for (Header header : headers) {
			System.out.println(header);
		}
	}
	public static void printHeader(Header[] headers,String key){
		for (Header header : headers) {
			if(header.getName().toLowerCase().contains(key.toLowerCase())||header.getValue().toLowerCase().contains(key.toLowerCase()))
				System.out.println(header);
		}
	}
}

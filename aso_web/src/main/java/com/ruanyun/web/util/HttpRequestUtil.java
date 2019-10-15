package com.ruanyun.web.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public class HttpRequestUtil {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
    
   
    //此方法用来跳过ssl证书验证
    public static DefaultHttpClient getNewHttpsClient(HttpClient httpClient){ 
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
 
				public void checkClientTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}
 
				public void checkServerTrusted(X509Certificate[] arg0,
						String arg1) throws CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
			return new DefaultHttpClient(mgr, httpClient.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	
	}

    public static void main(String[] args) throws Exception {
    	System.out.print(posts());
	}
    
    public static String posts() throws Exception  {

    	  String result = "";
    	  //post 参数设置
    	  Map<String, String> obj = new HashMap<String, String>(); 
    	  obj.put("deviceNames", "rr");
          obj.put("deviceNumbers", "d8f6caa0fe41e169e72607e9864476162aae5651");
          obj.put("devicePlatforms", "ios");
          obj.put("register", "multiple");
          obj.put("teamId", "6X3N2VKGF2");
    	  HttpClient client = new DefaultHttpClient();
    	  //获取跳过ssl验证的client
    	  client = getNewHttpsClient(client);
         //配置post请求
    	  HttpPost post = new HttpPost("https://developer.apple.com/services-account/QH65B2/account/device/validateDevices.action");
        
         post.setHeader("Accept", "application/json, text/plain, */*");
         post.setHeader("Accept-Encoding", "gzip, deflate, br");
         post.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
         post.setHeader("Connection", "keep-alive");
         post.setHeader("Content-Type", "application/x-www-form-urlencoded");
         post.setHeader("Cookie", "s_fid=301B31DD85270E65-1194E4464EB0D174; s_vi=[CS]v1|2E9C83B8052E3075-60000C53E0004752[CE]; POD=cn~zh; dssid2=0fa5be90-e0c0-4216-97c8-3e52234a4ddf; dssf=1; as_sfa=Mnxjbnxjbnx8emhfQ058Y29uc3VtZXJ8aW50ZXJuZXR8MHwwfDE=; pxro=1; xp_ci=3zjC0wgzq1z5J9zCyhz5eA3LOQc; as_cn=~UhvO98uViVRUvWu6m6JcaoISF8JCqCqcVdCQ8bBuj4A=; as_loc=3987354829522ff603d4add82498495f14b0df5a41018c21b33e0758bda01884bb7c48e326bd9d1a8d1c661db2bc92744994761861c1ecdd1f7ffa172353438bd7ba82b5779c5b0566056060a05bd80ddcbb69bdd4ddae60025e7e4a9ddff2029625a0a024f3aec19974f28d9ffbc331110a0a3df99f67fda6c89958173bfcff; XID=640124a04c6c5e387a731e1a4b244750; as_disa=AAAjAAAB2L1jRe_cc-ty_L1Ru6QpsnRlUWnHmp_O8gfmBWm6zpGQ4XUGJ2SpvczwsezyDkKYAAIBYUkzFGv03nYt_t_BAROm8_KoRkcHbcVP-yg1KKYm4ks=; as_rec=85f823694d44793befb854b80c5b31972f5ae6ea44aee8af778cbdc92a4aa731bbe1bf5c372117501c04896a0efbb7d79e61667b2c73596481215ff536b4b064a79908c630e8439e457b9edca07009a8; dslang=CN-ZH; as_dc=nwk; geo=CN; ccl=y6AskEiujq2c/sxPS/XqTg==; s_vnum_n2_us=4|3; s_cc=true; site=CHN; acn01=0y+ICdWyvNI/U83Zdid+nxvTsX3Sd/53WgAdOk9o2k61; myacinfo=DAWTKNV2277a6bde812e2d25e70f1757ca102417ff5c97f56b6c3dd1b5d0c7f7078d10df6e36160a45fa7300e4edad0438d3a7d893c6467a42f21b8e20086c77a74568d6b257d053e9ce14e7a90f245fff6c7fa675a67165214119a5116a5c2ba99361d9c7706bef6b89403f54dfa1b266b59b370d81d01f3953d0f4378f4aa54f44607a4430bac0b41cc87182f9a83a147a321797da87dbfd5ae3fd72d0c4b60db20e8225bd68d8d720788215572dc828b8fa755b384e161203f49c0ff7ab3e7a3b46db9aede48e26d61ba238b83d6000946a44b3f4d500868daf4edce1a60827d9ea9d5f4658b2e4894347e5b2b4b692faceccee92c8c4fb239c0877df4bda1170f4fa30623132393630353732343434363064356339323563653834643762646531333261336266303433MVRYV2; DSESSIONID=17ocqeppmgm17r6a6draa7rirdjhv2ucfljj9h2eqsfu1i3c8ui0; s_pathLength=homepage%3D1%2Cmac.tab%2Bother%3D1%2Cmacbookpro%3D1%2Cdeveloper%3D2%2C; s_sq=awdappledeveloper%3D%2526pid%253Daccount%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fdeveloper.apple.com%25252Faccount%25252Fresources%25252Fcertificates%25252Flist%2526ot%253DA");
          List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        //遍历参数 map,添加参数
          for (String key : obj.keySet()) {
        	  nvps.add(new BasicNameValuePair(key, obj.get(key)));
          }
        //参数集合传入到一个UrlEncodedFormEntity中并设置编码
          post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
          
          // 发送请求
          HttpResponse httpResponse = client.execute(post);

          //请求结果
          InputStream is= httpResponse.getEntity().getContent();
          //请求到的结果是经过压缩，需要用此方法来处理
          is= new GZIPInputStream(is);
          BufferedReader br = new BufferedReader(new InputStreamReader(is));
          String line = null;
          StringBuffer sb = new StringBuffer();
            while((line = br.readLine())!=null) {
             sb.append(line);
            }
            
          result = sb.toString();

           return result;
    }
    
//    public static String getStringFromResponse(HttpResponse response) {
//        String responseText = "";
//        InputStream is = response.getEntity().getContent();
//        //InputStream in = getInputStreamFromResponse(response);
//        Header[] headers = response.getHeaders("Content-Encoding");
//        for(Header h : headers){
//            if(h.getValue().indexOf("gzip") > -1){
//                //For GZip response
//                try{
//                    GZIPInputStream gzin = new GZIPInputStream(is);
//                    InputStreamReader isr = new InputStreamReader(gzin,"utf-8");
//                    responseText = Utils.getStringFromInputStreamReader(isr);
//                }catch (IOException exception){
//                    exception.printStackTrace();
//                }
//                break;
//            }
//        }
//        responseText = Utils.getStringFromStream(in);
//        return responseText;
//    }
    
    
    public static String post(String URL, JSONObject json) throws Exception  {
        HttpClient client = new DefaultHttpClient();
       HttpPost post = new HttpPost(URL);
       String result = "";
       post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
       
        
        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    System.out.println("请求服务器成功，code:200");
            } else {
                throw new Exception("post HTTP ERROR Status: " + httpResponse.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }
    
    
    /**
	 * post请求(用于key-value格式的参数)
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map params){
		BufferedReader in = null;  
        try {  
            // 定义HttpClient  
            HttpClient client = new DefaultHttpClient();  
            // 实例化HTTP方法  
            HttpPost request = new HttpPost();  
            request.setURI(new URI(url));
            
            //设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>(); 
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
    			String name = (String) iter.next();
    			String value = String.valueOf(params.get(name));
    			nvps.add(new BasicNameValuePair(name, value));
    			
    			//System.out.println(name +"-"+value);
    		}
            request.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
            
            HttpResponse response = client.execute(request);  
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
            	in = new BufferedReader(new InputStreamReader(response.getEntity()  
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");  
                String line = "";  
                String NL = System.getProperty("line.separator");  
                while ((line = in.readLine()) != null) {  
                    sb.append(line + NL);  
                }
                
                in.close();  
                
                return sb.toString();
            }
            else{	//
            	System.out.println("状态码：" + code);
            	return null;
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        	
        	return null;
        }
	}
	
	/**
	 * 发送HTTPS	POST请求
	 * 
	 * @param 要访问的HTTPS地址,POST访问的参数Map对象
	 * @return  返回响应值
	 * */
	public static String sendHttpsRequestByPost(String url, Map<String, String> params) {
		String responseContent = null;
		HttpClient httpClient = new DefaultHttpClient();
		//创建TrustManager
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		//这个好像是HOST验证
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
			public void verify(String arg0, SSLSocket arg1) throws IOException {}
			public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
			public void verify(String arg0, X509Certificate arg1) throws SSLException {}
		};
		try {
			//TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");
			//使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			//创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			socketFactory.setHostnameVerifier(hostnameVerifier);
			//通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (entity != null) {
				responseContent = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
	 public static JSONObject httpGet(String url, boolean noNeedResponse)
	    {
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        JSONObject jsonResult = null;
	        HttpGet httpGet = new HttpGet(url);
	        try 
	        {
	            HttpResponse httpResponse = httpClient.execute(httpGet);
	            if (httpResponse.getStatusLine().getStatusCode() == 200) 
	            {
	                String str = "";
	                try
	                {
	                    str = EntityUtils.toString(httpResponse.getEntity());
	                    if (noNeedResponse)
	                    {
	                        return null;
	                    }
	                    
	                    jsonResult = JSONObject.fromObject(str);
	                } catch (Exception e) 
	                {
	                    //logger.error("get请求提交失败:" + url, e);
	                	System.out.println("get请求提交失败:" + url);
	                	System.out.println(e);
	                }
	            }
	        }
	        catch (IOException e)
	        {
	        	//logger.error("get请求提交失败:" + url, e);
	        	System.out.println("get请求提交失败:" + url);
	        	System.out.println(e);
	        }
	        
	        return jsonResult;
	    }
}

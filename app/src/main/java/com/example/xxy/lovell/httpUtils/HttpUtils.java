package com.example.xxy.lovell.httpUtils;

import android.util.Log;

import com.example.xxy.lovell.constants.Constant;
import com.example.xxy.lovell.httpUtils.bean.accesstoken;
import com.example.xxy.lovell.httpUtils.bean.dynamic_data;
import com.example.xxy.lovell.httpUtils.bean.dynamic_item;
import com.example.xxy.lovell.httpUtils.bean.get_usersig;
import com.example.xxy.lovell.httpUtils.bean.logintoken;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpUtils {
  public static final String ROOT_URL="http://lovefor7days.applinzi.com";
  public static final String GET_USERSIG="https://gxwylovesig.applinzi.com";
  public static final int HTTP_OK=2000;
	public static HttpURLConnection getConnection(URL url){
		try{
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestProperty("Connection","Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			return httpURLConnection;
		}
		catch (Exception e) {
                 e.printStackTrace();
		}
		return null;
	}

	public static dynamic_data getDynamic(Map<String, String> params, String encode,String AccessToken) {
		StringBuffer sb=getRequestData(params, encode);
		byte[] data=null;
		if(sb!=null){
			data = sb.toString().getBytes();
		}
		try {
			URL url=new URL(ROOT_URL+"/Community/GET/dynamic");
			HttpURLConnection  httpURLConnection=getConnection(url);
			httpURLConnection.setRequestProperty("AccessToken", AccessToken);
			if(data!=null){
				httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
				OutputStream outputStream = httpURLConnection.getOutputStream();
				outputStream.write(data);
			}
			int response = httpURLConnection.getResponseCode();
			if(response == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = httpURLConnection.getInputStream();
				String temp=dealResponseResult(inputStream);
				Gson gson = new Gson();
				dynamic_data dd = gson.fromJson(temp, dynamic_data.class);
				if(dd.getState()==HTTP_OK)
					return dd;
			}
			else{
				Log.e("获取动态:","返回码错误 "+response);

			}
		} catch (Exception e) {

			Log.e("获取动态:","网络异常");
		}
		return null;
	}

	public static accesstoken getUserAccessToken(Map<String, String> params, String encode, String AccessToken) {
		byte[] data = getRequestData(params, encode).toString().getBytes();
		try {
			URL url=new URL(ROOT_URL+"/Home/GET/init");
			HttpURLConnection  httpURLConnection=getConnection(url);
			httpURLConnection.setRequestProperty("AccessToken", AccessToken);
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(data);
			int response = httpURLConnection.getResponseCode();
			if(response == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = httpURLConnection.getInputStream();
				String temp=dealResponseResult(inputStream);
				Gson gson = new Gson();
				accesstoken ac = gson.fromJson(temp, accesstoken.class);
				if(ac.getState()==HTTP_OK)
				    return ac;
			}
			else{
				Log.d("获取用户访问令牌:","返回码错误 "+response);

			}
		} catch (Exception e) {

			Log.d("获取用户访问令牌:","网络异常");
		}
		return null;
	}
	public static logintoken getUserLoginToken(Map<String, String> params, String encode) {
		byte[] data = getRequestData(params, encode).toString().getBytes();
		try {
			URL url=new URL(GET_USERSIG+"/GET/alltoken");
			HttpURLConnection  httpURLConnection=getConnection(url);
			httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(data);
			int response = httpURLConnection.getResponseCode();
			if(response == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = httpURLConnection.getInputStream();
				String temp=dealResponseResult(inputStream);
				Gson gson = new Gson();
				logintoken lt = gson.fromJson(temp, logintoken.class);
				if(lt.getState()==HTTP_OK)
					return lt;
			}
			else{
				Log.d("获取用户登录令牌:","返回码错误 "+response);

			}
		} catch (Exception e) {

			Log.d("获取用户登录令牌:","网络异常");
		}
		return null;
	}
	public static void refreshAceesToken(){
		Map<String,String> map=new HashMap<String, String>();
		map.put("username",Constant.userName);
		get_usersig gu=getUserSign(map,Constant.ENCODE);
		if(gu!=null&&gu.getState()==HTTP_OK){
			map.put("usersig",gu.getUsersig());
			logintoken  lt=getUserLoginToken(map,Constant.ENCODE);
			if(lt!=null&&lt.getState()==HTTP_OK){
				map.remove("usersig");
				map.put("LoginToken",lt.getLoginToken());
				accesstoken at=getUserAccessToken(map,Constant.ENCODE,"false");
				if(at!=null&&at.getState()==HTTP_OK){
					Constant.AccessToken=at.getAccessToken();
				}
			}

		}

	}
	public static List<dynamic_item> getDynamicData(Map<String,String> dataMap)
	{
		dynamic_data dd;
		if(Constant.AccessToken==""){
			refreshAceesToken();
			 dd=getDynamic(dataMap,Constant.ENCODE,Constant.AccessToken);
		}
       else{
			 dd=getDynamic(dataMap,Constant.ENCODE,Constant.AccessToken);
			if(dd==null||dd.getState()!=HTTP_OK){
				refreshAceesToken();//可能是AceessToken过期了
			}

		}
		if(dd!=null&&dd.getState()==HTTP_OK){
			return dd.getData();
		}
		return null;
	}
	public static get_usersig getUserSign(Map<String, String> params,String encode) {
		byte[] data = getRequestData(params, encode).toString().getBytes();
		try {
		     URL url=new URL(GET_USERSIG+"/GET/usersig");
			 HttpURLConnection  httpURLConnection=getConnection(url);
			 httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
			 OutputStream outputStream = httpURLConnection.getOutputStream();
			 outputStream.write(data);
			 int response = httpURLConnection.getResponseCode();
			 if(response == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = httpURLConnection.getInputStream();
				String temp=dealResponseResult(inputStream);
				Gson gson = new Gson();
				get_usersig gus = gson.fromJson(temp, get_usersig.class);
				return gus;
			}
			else{
				Log.d("获取用户签名:","返回码错误 "+response);

			}
		} catch (Exception e) {

			Log.d("获取用户签名:","网络异常");
		}
       return null;
	}

	public static void submitPostData(Map<String, String> params,
			String encode) {
		byte[] data = getRequestData(params, encode).toString().getBytes();

		try {

			URL url=new URL("http://jut33amdemo.applinzi.com/appjoin.php");
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(5000);        
            httpURLConnection.setDoInput(true);                  
            httpURLConnection.setDoOutput(true);                 
            httpURLConnection.setRequestMethod("POST"); 

            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);
            int response = httpURLConnection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK) {
                 InputStream inptStream = httpURLConnection.getInputStream();
                 String temp=dealResponseResult(inptStream);


            }
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("网络异常");
		}
		

	}

	     public static String dealResponseResult(InputStream inputStream) {
	         String resultData = null;      //�洢������
	          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	          byte[] data = new byte[1024];
	         int len = 0;
	         try {
	             while((len = inputStream.read(data)) != -1) {
	                 byteArrayOutputStream.write(data, 0, len);
	             }
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	       
	         resultData = new String(byteArrayOutputStream.toByteArray());    
	         return resultData;
	     }
	private static StringBuffer  getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer=new StringBuffer();
		for(Map.Entry<String, String> entry:params.entrySet()){
			 try {
				stringBuffer.append(entry.getKey())
				 .append("=")
				 .append(URLEncoder.encode(entry.getValue(), encode))
				 .append("&");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(stringBuffer.length()>=1)
		    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		else
		    return null;
		
		
		return stringBuffer;
	}

}

package com.api;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class TaoKeAPI {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://api.dataoke.com/index.php?r=Port/index&type=top100&appkey=hct7zebyzg&v=2");

 //           System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            JsonParser parse =new JsonParser();
            JsonObject json=(JsonObject) parse.parse(responseBody);
            JsonArray array=json.get("result").getAsJsonArray();
            for(int i = 0; i < array.size(); i++){
            	JsonObject subObject=array.get(i).getAsJsonObject();
            	
            	insert(subObject);
            	System.out.println(i);
            }        
        } finally {
            httpclient.close();
        }
	}
	
	
	
	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/data?useUnicode=true&characterEncoding=UTF-8";
	    String username = "root";
	    String password = "";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection)DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	private static int update(String sql) {
	    Connection conn = getConn();
	    int i = 0;
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("resutl: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	private static boolean insert(JsonObject goodJson){
		Connection conn = getConn();
	    int i = 0;
	    boolean flag=true;
	    String sql = "insert into data_t (ID,GoodsID,Title,D_Title,Pic,Cid,Org_Price,Price,IsTmall,Sales_Num,Dsr,SellerID,Commision,Commision_Jihua,Commision_Queqiao,Jihua_Link,Que_SiteID,Jihua_Shenhe,Introduce,Quan_ID,Quan_Price,Quan_Time,Quan_Surplus,Quan_Recieve,Quan_Condition,Quan_M_Link,Quan_Link,Status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement ps;
	    try {
	        ps = (PreparedStatement) conn.prepareStatement(sql);
	        ps.setString(1, goodJson.get("ID").getAsString());
	        ps.setString(2, goodJson.get("GoodsID").getAsString());
	        ps.setString(3, goodJson.get("Title").getAsString());
	        ps.setString(4, goodJson.get("D_title").getAsString());
	        ps.setString(5, goodJson.get("Pic").getAsString());
	        ps.setString(6, goodJson.get("Cid").getAsString());
	        ps.setString(7, goodJson.get("Org_Price").getAsString());
	        ps.setString(8, goodJson.get("Price").getAsString());
	        ps.setString(9, goodJson.get("IsTmall").getAsString());
	        ps.setString(10, goodJson.get("Sales_num").getAsString());
	        ps.setString(11, goodJson.get("Dsr").getAsString());
	        ps.setString(12, goodJson.get("SellerID").getAsString());
	        ps.setString(13, goodJson.get("Commission").getAsString());
	        ps.setString(14, goodJson.get("Commission_jihua").getAsString());
	        ps.setString(15, goodJson.get("Commission_queqiao").getAsString());
	        ps.setString(16, goodJson.get("Jihua_link").getAsString());
	        ps.setString(17, goodJson.get("Que_siteid").getAsString());
	        ps.setString(18, goodJson.get("Jihua_shenhe").getAsString());
	        ps.setString(19, goodJson.get("Introduce").getAsString());
	        ps.setString(20, goodJson.get("Quan_id").getAsString());
	        ps.setString(21, goodJson.get("Quan_price").getAsString());
	        ps.setString(22, goodJson.get("Quan_time").getAsString());
	        ps.setString(23, goodJson.get("Quan_surplus").getAsString());
	        ps.setString(24, goodJson.get("Quan_receive").getAsString());
	        ps.setString(25, goodJson.get("Quan_condition").getAsString());
	        ps.setString(26, goodJson.get("Quan_m_link").getAsString());
	        ps.setString(27, goodJson.get("Quan_link").getAsString());
	        ps.setString(28, "0");
            i=ps.executeUpdate();
            if(i==0){
                flag=false;
            }
	        ps.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return flag;
	}
	
	public static List<String> getTongYongGoods(String sql) throws SQLException{
		List<String> list = new ArrayList<String>();
		Connection conn = getConn();
	    PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
	    ResultSet rs = (ResultSet) ps.executeQuery(sql);// 返回结果集  
        // next()将光标向后一行  
        while (rs.next()) {  
            String id = rs.getString(1);
            list.add(id);
        }  
		return list;
	}
}

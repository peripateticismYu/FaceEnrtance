package enrtance.cqs.com.faceenrtance.andservice.handler;

import android.text.TextUtils;

import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.RequestMethod;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;

import enrtance.cqs.com.faceenrtance.bean.PassRecord;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;
import enrtance.cqs.com.faceenrtance.utils.Utils;

public class GetRecordByTime implements RequestHandler {

    private OrderDao orderDao;
    private String startTime = "0000-00-00",endTime ="";
    private List<PassRecord> passRecordList = new ArrayList<>();
    private JSONArray jsonArray;//JSONObject对象，处理一个一个集合或者数组
    private JSONObject object = null;  //JSONObject对象，处理一个一个的对象
    private JSONObject object2 = null;
    private String jsonString = null,status ="",messsage="";  //保存带集合的json字符串

    @RequestMapping(method = {RequestMethod.POST})
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException{
        Map<String, String> params = HttpRequestParser.parseParams(request);
        jsonArray = new JSONArray();
        object = new JSONObject();

         if (params.size() > 0) {
             orderDao = SQLiteUtils.getOrderDao();
             startTime = URLDecoder.decode(params.get("startTime"), "utf-8");
             endTime = URLDecoder.decode(params.get("endTime"), "utf-8");

             if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)  ){
                 try {
                     object.put("recordDate", jsonArray); //再把JSONArray数据加入JSONObject对象里面(数组也是对象)
                     object.put("status", "unSuccess");
                     object.put("messsage", "查询失败，请检查询参数");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }else {
                 // 开始查询
                 passRecordList = orderDao.getRecordByTime(Integer.valueOf(startTime),Integer.valueOf(endTime));  //得到所有的数据

                 for (int i = 0; i < passRecordList.size(); i++) {  //遍历上面初始化的集合数据，把数据加入JSONObject里面
                     object2 = new JSONObject();
                     try {
                         object2.put("id", passRecordList.get(i).Id);  //从集合取出数据，放入JSONObject里面 JSONObject对象和map差不多用法,以键和值形式存储数据
                         object2.put("userId", passRecordList.get(i).userId);
                         object2.put("name", passRecordList.get(i).Name);
                         object2.put("recordTime", passRecordList.get(i).recordTime);
                         object2.put("imageUrl", passRecordList.get(i).imageId);
                         object2.put("status", passRecordList.get(i).status);
                         jsonArray.put(object2);      //把JSONObject对象装入jsonArray数组里面
                     } catch (JSONException e) {
                         e.printStackTrace();
                    }
                 }
                 status = "success";
                 messsage = "请求成功";
             }
         }else {
             status = "unSuccess";
             messsage = "请求失败";
         }

        try {
            object.put("status", status);
            object.put("messsage", messsage);
            object.put("recordData", jsonArray); //再把JSONArray数据加入JSONObject对象里面(数组也是对象)

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonString = null;
        jsonString = object.toString(); //把JSONObject转换成json格式的字符串

        StringEntity stringEntity = new StringEntity(jsonString, "utf-8");
        response.setStatusCode(200);
        response.setEntity(stringEntity);

    }
}

package enrtance.cqs.com.faceenrtance.andservice.handler;

import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.RequestMethod;
import com.yanzhenjie.andserver.annotation.RequestMapping;

import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enrtance.cqs.com.faceenrtance.bean.PassRecord;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;


public class GetAllRecord implements RequestHandler{

    private List<PassRecord> passRecordList = new ArrayList<>();
    private JSONArray jsonArray;       //JSONObject对象，处理一个一个集合或者数组
    private JSONObject object = null;  //JSONObject对象，处理一个一个的对象
    private JSONObject object2 = null; //一个user对象，使用一个JSONObject对象来装
    private String jsonString = null,messsage="",status = "";  //保存带集合的json字符串
    private  OrderDao orderDao;

    @RequestMapping(method = {RequestMethod.GET})
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {

        orderDao =  SQLiteUtils.getOrderDao();
        passRecordList = orderDao.getAllRecordDate();

        jsonArray = new JSONArray();
        object=new JSONObject();
        if (passRecordList != null && passRecordList.size() > 0) {
            for (int i = 0; i < passRecordList.size(); i++) {  //遍历上面初始化的集合数据，把数据加入JSONObject里面
                object2 = new JSONObject();
                try {
                    object2.put("id", passRecordList.get(i).Id);  //从集合取出数据，放入JSONObject里面 JSONObject对象和map差不多用法,以键和值形式存储数据
                    object2.put("userId", passRecordList.get(i).userId);
                    object2.put("name", passRecordList.get(i).Name);
                    object2.put("recordTime", passRecordList.get(i).recordTime);
                    object2.put("imageId", passRecordList.get(i).imageId);
                    object2.put("status", passRecordList.get(i).status);
                    jsonArray.put(object2); //把JSONObject对象装入jsonArray数组里面
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            status = "success";
            messsage = "请求成功";
         //   orderDao.cleanRecordData();  //清除数据

        }else {
            status = "success";
            messsage = "请求成功,但未查询到数据";
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

package enrtance.cqs.com.faceenrtance.andservice.handler;

import android.util.Log;

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

import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;

public class CleanAllData implements RequestHandler {

    private OrderDao orderDao;
    private JSONObject object = null;  //JSONObject对象，处理一个一个的对象
    private String jsonString;  //保存带集合的json字符串
    private boolean code ;
    private String status,messsage;

    @RequestMapping(method = {RequestMethod.POST})
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException{

        orderDao = SQLiteUtils.getOrderDao();
        code =  orderDao.cleanData();

        if (code){
            status = "success";
            messsage = "请求成功";
        }else {
            status = "unSuccess";
            messsage = "请求失败";
        }

        object=new JSONObject();
        try {
            object.put("status", status);
            object.put("messsage", messsage);

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

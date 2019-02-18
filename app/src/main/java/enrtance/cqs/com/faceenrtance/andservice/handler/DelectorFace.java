package enrtance.cqs.com.faceenrtance.andservice.handler;

import android.util.Log;

import com.yanzhenjie.andserver.RequestHandler;
import com.yanzhenjie.andserver.RequestMethod;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import enrtance.cqs.com.faceenrtance.App;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;

public class DelectorFace implements RequestHandler {

    private OrderDao orderDao;

    private JSONObject object = null;  //JSONObject对象，处理一个一个的对象
    private String jsonString;  //保存带集合的json字符串
    private boolean code,faceDelectCode ;
    private String status,messsage;


    @RequestMapping(method = {RequestMethod.POST})
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException{
        Map<String, String> params = HttpRequestParser.parseParams(request);
        //获取需要删除数据的id
        String id = URLDecoder.decode(params.get("id"), "utf-8");
        //获取操作类的对象
        orderDao = SQLiteUtils.getOrderDao();
        //  删除
        code = orderDao.deleteOrder(id);  //删除数据库的
        faceDelectCode =  ((App) SQLiteUtils.getActivity().getApplicationContext()).mFaceDB.deleteById(id);  //删除注册的本地人脸

        if (code && faceDelectCode){
            status = "success";
            messsage = "删除成功";
        }else {
            status = "unSuccess";
            messsage = "删除失败";
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

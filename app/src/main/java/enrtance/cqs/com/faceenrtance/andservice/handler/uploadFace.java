package enrtance.cqs.com.faceenrtance.andservice.handler;

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

import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.utils.RegisterFaceUtils;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;

public class uploadFace implements RequestHandler {

    private OrderDao orderDao;
    private JSONObject object;  //JSONObject对象，处理一个一个的对象
    private String jsonString;  //保存带集合的json字符串
    private boolean status;
    private Thread registerThread;
    private String id, name, sex, imageData;

    @RequestMapping(method = {RequestMethod.POST})
    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Map<String, String> params = HttpRequestParser.parseParams(request);
        //得到一人一档的数据，存入人脸库
        object = null;
        object = new JSONObject();
        jsonString = null;

        if (params.size() > 0) {
            id = URLDecoder.decode(params.get("id"), "utf-8");
            name = URLDecoder.decode(params.get("name"), "utf-8");
            sex = URLDecoder.decode(params.get("sex"), "utf-8");
            imageData = params.get("imageData");

          //  SQLiteUtils.setFaceId(id);
            //先将数据存入数据库 TABLE_Face表中
              orderDao = SQLiteUtils.getOrderDao();
              status = orderDao.insertDateFace(id, name, sex, imageData, "");

            if (status) {
                try {
                    //再把JSONArray数据加入JSONObject对象里面(数组也是对象)
                    object.put("status", "success");
                    object.put("message", "请求成功");
                    //object.put("time", "2013-11-14"); //这里还可以加入数据，这样json型字符串，就既有集合，又有普通数据
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                   //成功之后开启线程 注册人脸
                registerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RegisterFaceUtils.registerFace(imageData,id);
                   //     EventBus.getDefault().post(new RefreshEvent("updata"));
                    }
                });
                registerThread.start();

            } else {
                try {
                    object.put("status", "unSuccess"); //再把JSONArray数据加入JSONObject对象里面(数组也是对象)
                    object.put("message", "请求失败");
                    //object.put("time", "2013-11-14"); //这里还可以加入数据，这样json型字符串，就既有集合，又有普通数据
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                object.put("status", "unSuccess"); //再把JSONArray数据加入JSONObject对象里面(数组也是对象)
                object.put("message", "请求失败，请检查上传参数");
                //object.put("time", "2013-11-14"); //这里还可以加入数据，这样json型字符串，就既有集合，又有普通数据
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        jsonString = object.toString(); //把JSONObject转换成json格式的字符串
        StringEntity stringEntity = new StringEntity(jsonString, "utf-8");
        response.setStatusCode(200);
        response.setEntity(stringEntity);
    }
}



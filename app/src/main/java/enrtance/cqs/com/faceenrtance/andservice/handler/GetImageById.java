package enrtance.cqs.com.faceenrtance.andservice.handler;

import com.yanzhenjie.andserver.RequestHandler;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import enrtance.cqs.com.faceenrtance.bean.ImageBean;
import enrtance.cqs.com.faceenrtance.bean.PassRecord;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;

public class GetImageById implements RequestHandler {

    private OrderDao orderDao;
    private List<ImageBean> imageBeanList = new ArrayList<>();
    private JSONArray jsonArray;//JSONObject对象，处理一个一个集合或者数组
    private JSONObject object = null;  //JSONObject对象，处理一个一个的对象
    private JSONObject object2 = null;
    private String jsonString = null,status ="",messsage="";  //保存带集合的json字符串

    @Override
    public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        Map<String, String> params = HttpRequestParser.parseParams(request);

        String id = URLDecoder.decode(params.get("id"), "utf-8");
        orderDao = SQLiteUtils.getOrderDao();
        imageBeanList = orderDao.getImagedById(id);

        jsonArray = new JSONArray();
        object = new JSONObject();
        if (imageBeanList != null) {

            for (int i = 0; i < imageBeanList.size(); i++) {  //遍历上面初始化的集合数据，把数据加入JSONObject里面
                object2 = new JSONObject();//一个user对象，使用一个JSONObject对象来装
                try {
                    object2.put("id", imageBeanList.get(i).Id);  //从集合取出数据，放入JSONObject里面 JSONObject对象和map差不多用法,以键和值形式存储数据
                    object2.put("imageData", imageBeanList.get(i).imageData);
                    object2.put("isDownload", imageBeanList.get(i).isDownload);
                    object2.put("recordTime", imageBeanList.get(i).recordTime);
                    jsonArray.put(object2); //把JSONObject对象装入jsonArray数组里面
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
          //  orderDao.deleteImageById(id);   //删除此条数据
            orderDao.updateImage(id);         // 修改此条数据
            status = "success";
            messsage = "请求成功";
        }else {
            status = "success";
            messsage = "请求成功,但未能查询到数据";
        }

        try {
            object.put("data", jsonArray); //再把JSONArray数据加入JSONObject对象里面(数组也是对象)
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

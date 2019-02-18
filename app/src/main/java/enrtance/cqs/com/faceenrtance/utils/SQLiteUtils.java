package enrtance.cqs.com.faceenrtance.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import enrtance.cqs.com.faceenrtance.bean.Order;
import enrtance.cqs.com.faceenrtance.bean.PassRecord;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;

public class SQLiteUtils {

    public static OrderDao orderDao;
    public static  List<Order> orderList;
    public static  List<PassRecord> passRecordList;
    public static  String delectId;
    public static  String startTime;
    public static  String endtTime;
    public static Activity activity;
    public static String faceId;

    public static String getFaceId() {
        return faceId;
    }

    public static void setFaceId(String faceId) {
        SQLiteUtils.faceId = faceId;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        SQLiteUtils.activity = activity;
    }

    public static String getStartTime() {
        return startTime;
    }

    public static void setStartTime(String startTime) {
        SQLiteUtils.startTime = startTime;
    }

    public static String getEndtTime() {
        return endtTime;
    }

    public static void setEndtTime(String endtTime) {
        SQLiteUtils.endtTime = endtTime;
    }

    public static OrderDao getOrderDao() {
        return orderDao;
    }

    public static void setOrderDao(OrderDao orderDao) {
        SQLiteUtils.orderDao = orderDao;
    }

    public static String getDelectId() {
        return delectId;
    }

    public static void setDelectId(String delectId) {
        SQLiteUtils.delectId = delectId;
    }

    public static List<Order> getOrderList() {
        return orderList;
    }

    public static void setOrderList(List<Order> orderList) {
        SQLiteUtils.orderList = orderList;
    }

    public static List<PassRecord> getPassRecordList() {
        return passRecordList;
    }

    public static void setPassRecordList(List<PassRecord> passRecordList) {
        SQLiteUtils.passRecordList = passRecordList;
    }
}

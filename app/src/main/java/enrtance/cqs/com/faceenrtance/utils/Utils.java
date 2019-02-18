package enrtance.cqs.com.faceenrtance.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    /**
     * 日期转换成秒数
     * */
    public static long getSecondsFromDate(String expireDate){
        if(expireDate==null||expireDate.trim().equals(""))
            return 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try{
            date=sdf.parse(expireDate);
            return (long)(date.getTime()/1000);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
            return 0L;
        }
    }



    /**
     * bitmap转为base64
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    /**
     * 获取时间间隔
     *
     * @param startTime 传入的时间格式必须类似于“yyyy-MM-dd HH:mm:ss”这样的格式
     * @return
     **/

    public static String getTimeDiff(String startTime) {
        if (startTime.length() != 19) {
            return startTime;
        }
        String result = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date d1 = (Date) sd.parse(startTime, pos);
            // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
            long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒\
            if (time / 1000 <= 0) {
                result = "0";
            } else if (time / 1000 < 60) {
                // 如果时间间隔小于60秒则显示多少秒前
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                result = sdf.format(d1.getTime());
            } else if (time / 3600000 < 24) {
                // 如果时间间隔小于24小时则显示多少小时前
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                result = sdf.format(d1.getTime());

            } else if (time / 86400000 < 2) {
                // 如果时间间隔小于2天则显示昨天
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                result = sdf.format(d1.getTime());
                result = "昨天" +
                        result;
            } else if (time / 86400000 < 7) {
                // 如果时间间隔小于3天则显示前天
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                result = sdf.format(d1.getTime());
                result = getWeek(startTime) +
                        result;
            } else if (time / 86400000 < 30) {
                // 如果时间间隔小于30天则显示多少天前
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
                result = sdf.format(d1.getTime());
            } else {
//                // 大于1年，显示年月日时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                result = sdf.format(d1.getTime());
            }
        } catch (Exception e) {
            return startTime;
        }
        return result;
    }




    public static String getWeek(String pTime) {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }







    /**
     * @param format yyyy-MM-dd HH:mm:ss
     * @return String
     * @throws
     * @author liyong
     * @Description: 将时间字符串转化为时间戳
     * @since 2015-11-11
     */
    public static String convertTime(String selectTime, String format) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date day = null;
        try {
            day = sdf.parse(selectTime);
            long l = day.getTime();
            String time = String.valueOf(l);
            re_time = time.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }



    /**
     * @param timeStamp
     * @param format    yyyy-MM-dd HH:mm:ss
     * @return String
     * @throws
     * @author chenzheng
     * @Description: 将时间戳转化为时间字符串
     * @since 2014-5-26
     */
    @SuppressLint("SimpleDateFormat")
    public static String convertDate(String timeStamp, String format) {
        if (TextUtils.isEmpty(timeStamp)) {
            return "";
        }
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long lcc_time = Long.valueOf(timeStamp);
        String newDate = sdf.format(new Date(lcc_time * 1000L));
        return newDate;
    }



}

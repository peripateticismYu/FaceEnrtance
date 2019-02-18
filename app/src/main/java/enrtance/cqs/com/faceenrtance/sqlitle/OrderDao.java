package enrtance.cqs.com.faceenrtance.sqlitle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import enrtance.cqs.com.faceenrtance.R;
import enrtance.cqs.com.faceenrtance.bean.FaceRegistBean;
import enrtance.cqs.com.faceenrtance.bean.ImageBean;
import enrtance.cqs.com.faceenrtance.bean.Order;
import enrtance.cqs.com.faceenrtance.bean.PassRecord;
import enrtance.cqs.com.faceenrtance.bean.Personnel;

public class OrderDao {

    private static final String TAG = "OrdersDao";

    // 列定义
    private final String[] ORDER_COLUMNS_FACE = new String[] {"Id","Name" ,"Sex", "FaceImageUrl","FaceEigenvalue"};
    private final String[] ORDER_COLUMNS_RECORD = new String[] {"Id","userId","Name", "recordTime", "imageId","status"};
    private final String[] ORDER_COLUMNS_IMAGE = new String[] {"Id" ,"imageData" ,"isDownload" ,"recordTime" };
    private Context context;
    private OrderDBHelper ordersDBHelper;

    public OrderDao(Context context) {
        this.context = context;
        ordersDBHelper = new OrderDBHelper(context);
    }

    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist(String tabName){
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query(tabName, new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }


    /**
     * 初始化数据  表1
     */
    public void initTable(){
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.execSQL("insert into " + OrderDBHelper.TABLE_Faces + " (Id, Name,Sex,FaceImageUrl,FaceEigenvalue) values ('1','许茹芸','女','E:\\WechatDemo\\FaceEntranceGuard\\image\\img_add_face.jpg','2a5456456adwc')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Faces + " (Id, Name,Sex,HouseholdNumber,FaceEigenvalue, FaceImageUrl) values ('2', '赵德闲','0','2栋10楼1004', '1231231derefw', 'E:\\WechatDemo\\FaceEntranceGuard\\image\\img_add_face.jpg')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Faces + " (Id, Name,Sex,HouseholdNumber,FaceEigenvalue, FaceImageUrl) values ('3', '李国','0','1栋5楼503',   'fgre5342re8AA', 'E:\\WechatDemo\\FaceEntranceGuard\\image\\img_add_face.jpg')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Faces + " (Id, Name,Sex,HouseholdNumber,FaceEigenvalue, FaceImageUrl) values ('4', '李太白','0','10栋14楼1402', '45324sdaaDDE4', 'E:\\WechatDemo\\FaceEntranceGuard\\image\\img_add_face.jpg')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Faces + " (Id, Name,Sex,HouseholdNumber,FaceEigenvalue, FaceImageUrl) values ('5', '白居易','0','12栋15楼1502', 'c4s23d4dFGawd2', 'E:\\WechatDemo\\FaceEntranceGuard\\image\\img_add_face.jpg')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Faces + " (Id, Name,Sex,HouseholdNumber,FaceEigenvalue, FaceImageUrl) values ('6', '云霓裳','1','1栋15楼1502', 'DE14551asdasdq', 'E:\\WechatDemo\\FaceEntranceGuard\\image\\img_add_face.jpg')");

            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(TAG,e+"");
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 初始化数据  表2
     */
    public void initTableRecord(){
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('1', '1','石肖', '2018-01-01','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('2', '1','石11', '2018-05-01','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('3', '1','石WK', '2018-05-03','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('4', '1','厉鬼', '2018-05-17','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('5', '1','效益', '2018-06-10','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('6', '1','上的风景', '2018-06-11','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('7', '1','白色', '2018-07-10','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('8', '1','咯', '2018-07-11','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
//            db.execSQL("insert into " + OrderDBHelper.TABLE_Record + " (Id, UserId,Name, time, imageUrl,status) values ('9', '1','加数据肖', '2018-06-11','D:\\桌面壁纸\\install\\搜狗壁纸\\wi.jpg' ,'通过')");
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(TAG,e+"");
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 执行自定义SQL语句
     */
    public void execSQL(String sql) {
        SQLiteDatabase db = null;

        try {
            if (sql.contains("select")){
                Toast.makeText(context, R.string.strUnableSql, Toast.LENGTH_SHORT).show();
            }else if (sql.contains("insert") || sql.contains("update") || sql.contains("delete")){
                db = ordersDBHelper.getWritableDatabase();
                db.beginTransaction();
                db.execSQL(sql);
                db.setTransactionSuccessful();
                Toast.makeText(context, R.string.strSuccessSql, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, R.string.strErrorSql, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 查询数据库中 表 TABLE_NAME 中所有数据
     */
    public List<Order> getAllDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();

            cursor = db.query(OrderDBHelper.TABLE_Faces, ORDER_COLUMNS_FACE, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<Order> orderList = new ArrayList<Order>(cursor.getCount());
                while (cursor.moveToNext()) {
                    orderList.add(parseOrder(cursor));
                }
                return orderList;
            }
        }
        catch (Exception e) {
            Log.d(TAG,e+"");
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }


    /**
     * 查询数据库中 表 TABLE_Faces 中所有数据
     */
    public List<Personnel> getFaceDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            cursor = db.query(OrderDBHelper.TABLE_Faces, ORDER_COLUMNS_FACE, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<Personnel> orderList = new ArrayList<Personnel>(cursor.getCount());
                while (cursor.moveToNext()) {
                    orderList.add(parsePersonnel(cursor));
                }
                return orderList;
            }
        }
        catch (Exception e) {
            Log.d(TAG,e+"");
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    /**
     * 查询数据库中 表 TABLE_Record 中所有数据
     */
    public List<PassRecord> getAllRecordDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(OrderDBHelper.TABLE_Record, ORDER_COLUMNS_RECORD, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<PassRecord> orderList = new ArrayList<PassRecord>(cursor.getCount());
                while (cursor.moveToNext()) {
                    orderList.add(passRecord(cursor));
                }
                return orderList;
            }
        }
        catch (Exception e) {
            Log.d(TAG,e+"");
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }


    /**
     * 查询数据库中 表 TABLE_Record 中所有数据
     */
    public List<ImageBean> getAllImageDate(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select * from Orders
            cursor = db.query(OrderDBHelper.TABLE_Image, ORDER_COLUMNS_IMAGE, null, null, null, null, null);

            if (cursor.getCount() > 0) {
                List<ImageBean> imageBeanList = new ArrayList<ImageBean>(cursor.getCount());
                while (cursor.moveToNext()) {
                    imageBeanList.add(imageBean(cursor));
                }
                return imageBeanList;
            }
        }
        catch (Exception e) {
            Log.d(TAG,e+"");
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    /**
     *   在表TABLE_image中新增一条数据
     */
    public boolean insertDateImage(String id,String imageData,String isDownload,int recordTime){
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", id);
            contentValues.put("imageData", imageData);
            contentValues.put("isDownload", isDownload);
            contentValues.put("recordTime",recordTime);
            db.insertOrThrow(OrderDBHelper.TABLE_Image, null, contentValues);

            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    /**
     *   在表TABLE_Faces中新增一条数据
     */
    public boolean insertDateFace(String id,String name,String sex,String imageUrl,String FaceEigenvalue){
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", id);
            contentValues.put("Name", name);
            contentValues.put("Sex", sex);
            contentValues.put("FaceImageUrl",imageUrl);
            contentValues.put("FaceEigenvalue",FaceEigenvalue);
            db.insertOrThrow(OrderDBHelper.TABLE_Faces, null, contentValues);

            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    /**
     *   在表TABLE_Record中新增一条数据
     */
    public boolean insertDateRecord(String id,String userId,String Name,int time,String imageId,String status){
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", id);
            contentValues.put("userId", userId);
            contentValues.put("Name", Name);
            contentValues.put("recordTime",time);
            contentValues.put("imageId",imageId);
            contentValues.put("status",status);
            db.insertOrThrow(OrderDBHelper.TABLE_Record, null, contentValues);

            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 删除一条数据  此处删除Id的数据   表TABLE_Faces
     */
    public boolean deleteOrder(String id) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(OrderDBHelper.TABLE_Faces, "Id = ?", new String[]{id});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    /**
     * 删除一条数据  此处删除Id的数据   表TABLE_Faces
     */
    public boolean deleteImageById(String id) {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(OrderDBHelper.TABLE_Image, "Id = ?", new String[]{id});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    /**
     * 清除所有的表数据
     */
    public boolean cleanData() {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();
            db.delete(OrderDBHelper.TABLE_Faces,"",null);
            db.delete(OrderDBHelper.TABLE_Record,"",null);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    /**
     * 清除所有的表数据
     */
    public boolean cleanRecordData() {
        SQLiteDatabase db = null;

        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();
            db.delete(OrderDBHelper.TABLE_Record,"",null);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 修改一条数据  此处将Id为6的数据的OrderPrice修改了800
     */
    public boolean updateOrder(int id){
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            // update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
          //  cv.put("OrderPrice", 800);
            db.update(OrderDBHelper.TABLE_Faces,
                    cv,
                    "Id = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }


    /**
     * 修改一条数据  此处将Id的数据的
     */
    public boolean updateImage(String id){
        SQLiteDatabase db = null;
        try {
            db = ordersDBHelper.getWritableDatabase();
            db.beginTransaction();

            // update Orders set OrderPrice = 800 where Id = 6
            ContentValues cv = new ContentValues();
            cv.put("isDownload", "1");
            db.update(OrderDBHelper.TABLE_Image, cv, "Id = ?",new String[]{id});
            db.setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }

        return false;
    }


    /**
     * 数据查询  根据ID查询 出行记录
     */
    public List<PassRecord> getRecordById(String Id){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();

            cursor = db.query(OrderDBHelper.TABLE_Record, ORDER_COLUMNS_RECORD, "userId = ?", new String[] {Id},
                    null, null, null);

            if (cursor.getCount() > 0) {
                List<PassRecord> orderList = new ArrayList<PassRecord>(cursor.getCount());
                while (cursor.moveToNext()) {
                    PassRecord order = passRecord(cursor);
                    orderList.add(order);
                }
                return orderList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }


    /**
     * 数据查询  根据ID查询 人脸图片
     */
    public List<ImageBean> getImagedById(String Id){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();

            cursor = db.query(OrderDBHelper.TABLE_Image, ORDER_COLUMNS_IMAGE, "Id = ?", new String[] {Id},
                    null, null, null);

            if (cursor.getCount() > 0) {
                List<ImageBean> imageBeanList = new ArrayList<ImageBean>(cursor.getCount());
                while (cursor.moveToNext()) {
                    ImageBean imageBean = imageBean(cursor);
                    imageBeanList.add(imageBean);
                }
                return imageBeanList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 数据查询  根据时间查询 出行记录
     */
    public List<PassRecord> getRecordByTime(int startTime ,int endTime){
        String sql = "select * from " + ordersDBHelper.TABLE_Record + " where recordTime>="+startTime+ " and recordTime<="+endTime;

        List<PassRecord> passRecordList = new ArrayList<PassRecord>();
        Cursor cursor = ordersDBHelper.getWritableDatabase().rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            List<PassRecord> orderList = new ArrayList<PassRecord>(cursor.getCount());
            while (cursor.moveToNext()) {
                orderList.add(passRecord(cursor));
            }
            passRecordList.addAll(orderList);
        }
        return passRecordList;
    }

    /**
     * 数据查询  此处将根据用户名为将信息提取出来
     */
    public List<Order> getBorOrder(String name){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();

            cursor = db.query(OrderDBHelper.TABLE_Faces, ORDER_COLUMNS_FACE, "Name = ?", new String[] {name},
                    null, null, null);

            if (cursor.getCount() > 0) {
                List<Order> orderList = new ArrayList<Order>(cursor.getCount());
                while (cursor.moveToNext()) {
                    Order order = parseOrder(cursor);
                    orderList.add(order);
                }
                return orderList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 统计查询  此处查询Country为China的用户总数
     */
    public int getChinaCount(String status){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(OrderDBHelper.TABLE_Faces,
                    new String[]{"COUNT(Id)"},
                    "Phone = ?",
                    new String[] {status},
                    null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return count;
    }

    /**
     * 比较查询  此处查询单笔数据中OrderPrice最高的
     */
    public Order getMaxOrderPrice(){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = ordersDBHelper.getReadableDatabase();
            // select Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders
            cursor = db.query(OrderDBHelper.TABLE_Faces, new String[]{"Id", "CustomName", "Max(OrderPrice) as OrderPrice", "Country"}, null, null, null, null, null);

            if (cursor.getCount() > 0){
                if (cursor.moveToFirst()) {
                    return parseOrder(cursor);
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 保存object数据
     * @param
     */
    public void saveData(FaceRegistBean person) {

        SQLiteDatabase db = null;
        Cursor cursor = null;

        db = ordersDBHelper.getReadableDatabase();

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(person);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();

            db.execSQL("insert into Faces (FaceEigenvalue) values(?)", new Object[]{data});
            db.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



//    public ArrayList<FaceRegistBean> getAllObject() {
//        ArrayList<FaceRegistBean> persons = new ArrayList<FaceRegistBean>();
//
//        SQLiteDatabase db = null;
//        Cursor cursor = null;
//      //  SQLiteDatabase database = db.getReadableDatabase();
//
//        db = ordersDBHelper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("select * from Test", null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                Log.d("data-id",cursor.getString(0));
//                byte data[] = cursor.getBlob(cursor.getColumnIndex("person"));
//                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
//                try {
//                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
//                    FaceRegistBean person = (FaceRegistBean) inputStream.readObject();
//                    persons.add(person);
//                    inputStream.close();
//                    arrayInputStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        Log.d("Persons-Count",Integer.toString(persons.size()));
//        return persons;
//    }




        /**
         * 将查找到的数据转换成Order类
         */
    private Order parseOrder(Cursor cursor){
        Order order = new Order();
        order.Id = (cursor.getString(cursor.getColumnIndex("Id")));
        order.Name = (cursor.getString(cursor.getColumnIndex("Name")));
        order.Sex = (cursor.getString(cursor.getColumnIndex("Name")));
        order.FaceEigenvalue = (cursor.getString(cursor.getColumnIndex("FaceEigenvalue")));
        order.FaceImageUrl = (cursor.getString(cursor.getColumnIndex("FaceImageUrl")));
        return order;
    }

    /**
     * 将查找到的数据转换成parsePersonnel类
     */
    private Personnel parsePersonnel(Cursor cursor){
        Personnel personnel = new Personnel();
        personnel.Id = (cursor.getString(cursor.getColumnIndex("Id")));
        personnel.Name = (cursor.getString(cursor.getColumnIndex("Name")));
        personnel.ImageUrl = (cursor.getString(cursor.getColumnIndex("ImageUrl")));
        return personnel;
    }

    /**
     * 将查找到的数据转换成PassRecord类
     */
    private PassRecord passRecord(Cursor cursor){
        PassRecord passRecord = new PassRecord();
        passRecord.Id = (cursor.getString(cursor.getColumnIndex("Id")));
        passRecord.userId = (cursor.getString(cursor.getColumnIndex("userId")));
        passRecord.Name = (cursor.getString(cursor.getColumnIndex("Name")));
        passRecord.imageId = (cursor.getString(cursor.getColumnIndex("imageId")));
        passRecord.recordTime = (cursor.getInt(cursor.getColumnIndex("recordTime")));
        passRecord.status = (cursor.getString(cursor.getColumnIndex("status")));
        return passRecord;
    }


    /**
     * 将查找到的数据转换成image类
     */
    private ImageBean imageBean(Cursor cursor){
        ImageBean imageBean = new ImageBean();
        imageBean.Id = (cursor.getString(cursor.getColumnIndex("Id")));
        imageBean.imageData = (cursor.getString(cursor.getColumnIndex("imageData")));
        imageBean.isDownload = (cursor.getString(cursor.getColumnIndex("isDownload")));
        imageBean.recordTime = (cursor.getInt(cursor.getColumnIndex("recordTime")));
        return imageBean;
    }


}

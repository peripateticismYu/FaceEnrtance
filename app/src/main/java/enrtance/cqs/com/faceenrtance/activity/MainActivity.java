package enrtance.cqs.com.faceenrtance.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import enrtance.cqs.com.faceenrtance.App;
import enrtance.cqs.com.faceenrtance.R;
import enrtance.cqs.com.faceenrtance.adapter.MainFaceAdapter;
import enrtance.cqs.com.faceenrtance.andservice.ServerManager;
import enrtance.cqs.com.faceenrtance.bean.FaceRegistBean;
import enrtance.cqs.com.faceenrtance.bean.ImageBean;
import enrtance.cqs.com.faceenrtance.bean.Order;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDBHelper;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.adapter.OrderListAdapter;
import enrtance.cqs.com.faceenrtance.bean.PassRecord;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;
import enrtance.cqs.com.faceenrtance.utils.Utils;

public class MainActivity extends Activity implements View.OnClickListener{


    private final String TAG = this.getClass().toString();

    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_IMAGE_OP = 2;
    private static final int REQUEST_CODE_OP = 3;
    private ServerManager mServerManager;
    private String mRootUrl;

    private Button mBtnStart,mBtnAdd,mBtnDelector,mBtnUpdate,mBtnQuery,mbtnAddFace,mBtnFace;
    public static  OrderDao ordersDao;
    public List<Order> orderList = new ArrayList<>();
    public List<PassRecord> passRecordList = new ArrayList<>();
    public List<ImageBean> imageBeanList = new ArrayList<>();
    private List<FaceRegistBean> registerlist;
    private OrderListAdapter adapter;
    private ListView showDateListView;
    private int selectorId;
    private Order order;
    private MainFaceAdapter mainFaceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();

        registerlist = ((App) MainActivity.this.getApplicationContext()).mFaceDB.mRegister;
        mainFaceAdapter = new MainFaceAdapter(MainActivity.this,registerlist);
        showDateListView.setAdapter(mainFaceAdapter);
        // andService 服务开启
//        mServerManager = new ServerManager(this);
//        mServerManager.register();
//        mBtnStart.performClick();

        //  SQLite开启
        ordersDao = new OrderDao(this);
//        if (!ordersDao.isDataExist(OrderDBHelper.TABLE_Faces)){
//            ordersDao.initTable();
//        }
//        if (! ordersDao.isDataExist(OrderDBHelper.TABLE_Record)){
//            ordersDao.initTableRecord();
//        }

        showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.show_sql_item, null), null, false);

          // 获取数据库数据  表
        passRecordList = ordersDao.getAllRecordDate();
        orderList = ordersDao.getAllDate();
        imageBeanList = ordersDao.getAllImageDate();

        if (orderList != null){
            adapter = new OrderListAdapter(this, orderList);
            showDateListView.setAdapter(adapter);
        }

        SQLiteUtils.setOrderList(orderList);
        SQLiteUtils.setPassRecordList(passRecordList);
        SQLiteUtils.setOrderDao(ordersDao);
        SQLiteUtils.setActivity(MainActivity.this);
    }


    private void initComponent(){
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnDelector = (Button) findViewById(R.id.btn_delector);
        mBtnUpdate = (Button) findViewById(R.id.btn_update);
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mbtnAddFace =  (Button) findViewById(R.id.btn_addFace);
        mBtnFace =  (Button) findViewById(R.id.btn_Face);
        showDateListView = (ListView)findViewById(R.id.showDateListView);

        mBtnStart.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
        mBtnDelector.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
        mbtnAddFace.setOnClickListener(this);
        mBtnFace.setOnClickListener(this);
    }

    private void refreshOrderList(){
        // 注意：千万不要直接赋值，如：orderList = ordersDao.getAllDate() 此时相当于重新分配了一个内存 原先的内存没改变 所以界面不会有变化
        // Java中的类是地址传递 基本数据才是值传递
        orderList.clear();
        orderList.addAll(ordersDao.getAllDate());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_start: {
                mServerManager.startService();
                break;
            }
            case R.id.btn_add: {

                //   ordersDao.insertDate(index,"青青","2018-07-12  14:38:56","放行");
             //   refreshOrderList();
                break;
            }
            case R.id.btn_delector: {
//              Toast.makeText(MainActivity.this,"修改中",Toast.LENGTH_LONG);
//                int index = orderList.size();
//                index--;
//                ordersDao.deleteOrder(index);
//                refreshOrderList();

                break;
            }
            case R.id.btn_update: {
                Toast.makeText(MainActivity.this,"修改中",Toast.LENGTH_LONG);

//                String startTime = "2018-05-01";
//                String endTime = "2018-06-11";
//                passRecordList = ordersDao.getRecordByTime(Integer.valueOf(Utils.convertTime(startTime,"yyyy-MM-dd HH:mm:ss")),Integer.valueOf(Utils.convertTime(endTime,"yyyy-MM-dd HH:mm:ss")));

                registerlist = ((App) MainActivity.this.getApplicationContext()).mFaceDB.mRegister;
                break;
            }
            case R.id.btn_query: {
               Toast.makeText(MainActivity.this,"暂无",Toast.LENGTH_LONG);
               orderList = ordersDao.getAllDate();
//                ordersDao.cleanData();
//                passRecordList = ordersDao.getAllRecordDate();
                break;
            }
            case R.id.btn_addFace:{
                passRecordList = ordersDao.getAllRecordDate();
                orderList = ordersDao.getAllDate();
                imageBeanList = ordersDao.getAllImageDate();

//                Intent getImageByalbum = new Intent(Intent.ACTION_GET_CONTENT);
//                getImageByalbum.addCategory(Intent.CATEGORY_OPENABLE);
//                getImageByalbum.setType("image/jpeg");
//                startActivityForResult(getImageByalbum, REQUEST_CODE_IMAGE_OP);
                break;
            }
            case R.id.btn_Face:{
            startDetector(1);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_OP && resultCode == RESULT_OK) {
            Uri mPath = data.getData();
            String file = getPath(mPath);
            Bitmap bmp = App.decodeImage(file);
            if (bmp == null || bmp.getWidth() <= 0 || bmp.getHeight() <= 0 ) {
                Log.e(TAG, "error");
            } else {
                Log.i(TAG, "bmp [" + bmp.getWidth() + "," + bmp.getHeight());
            }
            startRegister(bmp, file);
        } else if (requestCode == REQUEST_CODE_OP) {
            Log.i(TAG, "RESULT =" + resultCode);
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            String path = bundle.getString("imagePath");
            Log.i(TAG, "path="+path);
        } else if (requestCode == REQUEST_CODE_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Uri mPath = ((App)(MainActivity.this.getApplicationContext())).getCaptureImage();
            String file = getPath(mPath);
            Bitmap bmp = App.decodeImage(file);
            startRegister(bmp, file);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServerManager.unRegister();
    }

    /**
     * Start notify.
     */
    public void serverStart(String ip) {
        if (!TextUtils.isEmpty(ip)) {
            List<String> addressList = new LinkedList<>();
            mRootUrl = "http://" + ip + ":8080/";
            addressList.add(mRootUrl);
            addressList.add("http://" + ip + ":8080/getAllRecord");
            addressList.add("http://" + ip + ":8080/uploadFace");
            addressList.add("http://" + ip + ":8080/login.html");
            addressList.add("http://" + ip + ":8080/delectFace");
            addressList.add("http://" + ip + ":8080/clean");
            addressList.add("http://" + ip + ":8080/getRecordByTime");
            addressList.add("http://" + ip + ":8080/getRecordById");
            addressList.add("http://" + ip + ":8080/getImageById");
            mBtnStart.setText("ip:"+mRootUrl);
        } else {
            mRootUrl = null;
        }
    }

    /**
     * Error notify.
     */
    public void serverError(String message) {

        mRootUrl = null;

    }

    /**
     * Stop notify.
     */
    public void serverStop() {
        mRootUrl = null;
    }


    /**
     * @param uri
     * @return
     */
    private String getPath(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(this, contentUri, null, null);
                } else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(this, contentUri, selection, selectionArgs);
                }
            }
        }
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = this.getContentResolver().query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        String end = img_path.substring(img_path.length() - 4);
        if (0 != end.compareToIgnoreCase(".jpg") && 0 != end.compareToIgnoreCase(".png")) {
            return null;
        }
        return img_path;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param mBitmap
     */
    private void startRegister(Bitmap mBitmap, String file) {
        Intent it = new Intent(MainActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

    private void startDetector(int camera) {
        Intent it = new Intent(MainActivity.this, DetecterActivity.class);
        it.putExtra("Camera", camera);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

}

package enrtance.cqs.com.faceenrtance.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.ageestimation.ASAE_FSDKAge;
import com.arcsoft.ageestimation.ASAE_FSDKEngine;
import com.arcsoft.ageestimation.ASAE_FSDKError;
import com.arcsoft.ageestimation.ASAE_FSDKFace;
import com.arcsoft.ageestimation.ASAE_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKMatching;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.arcsoft.facetracking.AFT_FSDKEngine;
import com.arcsoft.facetracking.AFT_FSDKError;
import com.arcsoft.facetracking.AFT_FSDKFace;
import com.arcsoft.facetracking.AFT_FSDKVersion;
import com.arcsoft.genderestimation.ASGE_FSDKEngine;
import com.arcsoft.genderestimation.ASGE_FSDKError;
import com.arcsoft.genderestimation.ASGE_FSDKFace;
import com.arcsoft.genderestimation.ASGE_FSDKGender;
import com.arcsoft.genderestimation.ASGE_FSDKVersion;
import com.guo.android_extend.java.AbsLoop;
import com.guo.android_extend.java.ExtByteArrayOutputStream;
import com.guo.android_extend.tools.CameraHelper;
import com.guo.android_extend.widget.CameraFrameData;
import com.guo.android_extend.widget.CameraGLSurfaceView;
import com.guo.android_extend.widget.CameraSurfaceView;
import com.guo.android_extend.widget.CameraSurfaceView.OnCameraListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import enrtance.cqs.com.faceenrtance.App;
import enrtance.cqs.com.faceenrtance.R;
import enrtance.cqs.com.faceenrtance.adapter.GlideImageLoader;
import enrtance.cqs.com.faceenrtance.andservice.ServerManager;
import enrtance.cqs.com.faceenrtance.arcface.FaceDB;
import enrtance.cqs.com.faceenrtance.bean.FaceRegistBean;
import enrtance.cqs.com.faceenrtance.bean.ImageBean;
import enrtance.cqs.com.faceenrtance.bean.Order;
import enrtance.cqs.com.faceenrtance.bean.PassRecord;
import enrtance.cqs.com.faceenrtance.interfacerequest.RefreshEvent;
import enrtance.cqs.com.faceenrtance.sqlitle.OrderDao;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;
import enrtance.cqs.com.faceenrtance.utils.TTSUtils;
import enrtance.cqs.com.faceenrtance.utils.Utils;

/**
 * Created by gqj3375 on 2017/4/28.
 */

public class DetecterActivity extends Activity implements OnCameraListener, View.OnTouchListener, Camera.AutoFocusCallback {
	private final String TAG = this.getClass().getSimpleName();

	private int mWidth, mHeight, mFormat;
	private CameraSurfaceView mSurfaceView;
	private CameraGLSurfaceView mGLSurfaceView;
	private Camera mCamera;

	AFT_FSDKVersion version = new AFT_FSDKVersion();
	AFT_FSDKEngine engine = new AFT_FSDKEngine();
	ASAE_FSDKVersion mAgeVersion = new ASAE_FSDKVersion();
	ASAE_FSDKEngine mAgeEngine = new ASAE_FSDKEngine();
	ASGE_FSDKVersion mGenderVersion = new ASGE_FSDKVersion();
	ASGE_FSDKEngine mGenderEngine = new ASGE_FSDKEngine();
	List<AFT_FSDKFace> result = new ArrayList<>();
	List<ASAE_FSDKAge> ages = new ArrayList<>();
	List<ASGE_FSDKGender> genders = new ArrayList<>();

	private boolean mOpenBackCamera = true;
	private String base64Str = "",userId = "",recordId,imageId,status,userName = "未识别",mNameShow;
	int mCameraID;
	int mCameraRotate;
	boolean mCameraMirror;
	byte[] mImageNV21 = null;
	FRAbsLoop mFRAbsLoop = null;
	AFT_FSDKFace mAFT_FSDKFace = null;
	Handler mHandler;
	boolean isPostted = false,imageCode = false,flag = true;
	int timeS = 0;
	public static  List<FaceRegistBean> mResgist ;
	public static List<Order> orderList = new ArrayList<>();
	private ServerManager mServerManager;
	private String mRootUrl;

	public static  OrderDao ordersDao;
	public List<Order> orderList1 = new ArrayList<>();
	public List<PassRecord> passRecordList = new ArrayList<>();
	public List<ImageBean> imageBeanList = new ArrayList<>();
	private List<FaceRegistBean> registerlist;
	private Thread SQLthread;
	byte[] dataBase64;
	private Thread cleanThread;

	Runnable hide = new Runnable() {
		@Override
		public void run() {
			isPostted = false;
		}
	};

	class FRAbsLoop extends AbsLoop {

		AFR_FSDKVersion version = new AFR_FSDKVersion();
		AFR_FSDKEngine engine = new AFR_FSDKEngine();
		AFR_FSDKFace result = new AFR_FSDKFace();


		@Override
		public void setup() {
			AFR_FSDKError error = engine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
			Log.d(TAG, "AFR_FSDK_InitialEngine = " + error.getCode());
			error = engine.AFR_FSDK_GetVersion(version);
			Log.d(TAG, "FR=" + version.toString() + "," + error.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
		}

		@Override
		public void loop() {
			//当前时间和上次存入数据库的时间差小于10s则不进行识别
			if (getTime() - timeS >= 10) {
				if (mImageNV21 != null) {
					AFR_FSDKError error = engine.AFR_FSDK_ExtractFRFeature(mImageNV21, mWidth, mHeight, AFR_FSDKEngine.CP_PAF_NV21, mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree(), result);
					Log.d(TAG, "Face=" + result.getFeatureData()[0] + "," + result.getFeatureData()[1] + "," + result.getFeatureData()[2] + "," + error.getCode());
					AFR_FSDKMatching score = new AFR_FSDKMatching();
					float max = 0.0f;
					String name = null;
					for (FaceRegistBean fr : mResgist) {
						for (AFR_FSDKFace face : fr.mFaceList) {
							error = engine.AFR_FSDK_FacePairMatching(result, face, score);
							Log.d(TAG, "Score:" + score.getScore() + ", AFR_FSDK_FacePairMatching=" + error.getCode());
							if (max < score.getScore()) {
								max = score.getScore();
								name = fr.mName;
								userId = fr.faceId;
							}
						}
					}
					//crop
//					YuvImage yuv = new YuvImage(data, ImageFormat.NV21, mWidth, mHeight, null);
//					ExtByteArrayOutputStream ops = new ExtByteArrayOutputStream();
//					yuv.compressToJpeg(mAFT_FSDKFace.getRect(), 80, ops);
//					final Bitmap bmp = BitmapFactory.decodeByteArray(ops.getByteArray(), 0, ops.getByteArray().length);
//					try {
//						ops.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}

					if (max > 0.6f) {
						//fr success.
						final float max_score = max;
						Log.d(TAG, "fit Score:" + max + ", NAME:" + name);
						userName = name;
						status = "通过";
						mNameShow = name;
						mHandler.removeCallbacks(hide);
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								TTSUtils.getInstance().speak("识别成功");
							}
						});
					} else {
						status = "未通过";
						userId = "";
						mNameShow = "未识别";
						DetecterActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								TTSUtils.getInstance().speak("未识别");
							}
						});
					}

					dataBase64 = mImageNV21;
					mImageNV21 = null;
					//	if (getTime() - timeS >= 3) {
//					SQLthread = new Thread(new Runnable() {
//						@Override
//						public void run() {

                    if (dataBase64 != null){
                          String userId1 = userId;
							String userName1 = userName;
							String status1 = status;

							//byte 数组转为格式化
							Bitmap bigBitMap = rawByteArray2RGBABitmap2(dataBase64, mWidth, mHeight);
							//bitMap转base64
							base64Str = Utils.bitmapToBase64(bigBitMap);

							imageId = java.util.UUID.randomUUID().toString();      //生成随机图片id
							recordId = java.util.UUID.randomUUID().toString();     //生成随机记录id
							//将图片存入
							timeS = getTime();   //第一次记录时间的时间戳
							imageCode = SQLiteUtils.getOrderDao().insertDateImage(imageId, base64Str, "0", getTime());
							if (imageCode) {
								SQLiteUtils.getOrderDao().insertDateRecord(recordId, userId1, userName1, getTime(), imageId, status1);
								userId = "";
							}
                         }
//						}
//					});

//					if (dataBase64 != null) {
//						SQLthread.start();
//					}
				}

			}
		}
		@Override
		public void over() {
			AFR_FSDKError error = engine.AFR_FSDK_UninitialEngine();
			Log.d(TAG, "AFR_FSDK_UninitialEngine : " + error.getCode());
		}
	}

	private int getTime(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
         //获取当前时间
		Date date = new Date(System.currentTimeMillis());
	    int time =  Integer.valueOf(Utils.convertTime(simpleDateFormat.format(date),"yyyy-MM-dd HH:mm:ss"));
		return time;
	}

	private TextView mTextIp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//拍照过程屏幕一直处于高亮

        EventBus.getDefault().register(this);  //注册广播

		mCameraID = 1 == 0 ? Camera.CameraInfo.CAMERA_FACING_BACK : Camera.CameraInfo.CAMERA_FACING_FRONT;
		mCameraRotate = 1 == 0 ? 0 : 0;
		mCameraMirror = 1 == 0 ? false : true;
		mWidth = 1280;
		mHeight = 960;
		mFormat = ImageFormat.NV21;
		mHandler = new Handler();

		mTextIp = (TextView) findViewById(R.id.tv_Ip);
		mGLSurfaceView = (CameraGLSurfaceView) findViewById(R.id.glsurfaceView);
		mGLSurfaceView.setOnTouchListener(this);
		mSurfaceView = (CameraSurfaceView) findViewById(R.id.surfaceView);
		mSurfaceView.setOnCameraListener(this);
		mSurfaceView.setupGLSurafceView(mGLSurfaceView, true, false, 90);
		mSurfaceView.debug_print_fps(true, false);

		AFT_FSDKError err = engine.AFT_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.ft_key, AFT_FSDKEngine.AFT_OPF_0_HIGHER_EXT, 16, 5);
		err = engine.AFT_FSDK_GetVersion(version);

		ASAE_FSDKError error = mAgeEngine.ASAE_FSDK_InitAgeEngine(FaceDB.appid, FaceDB.age_key);
		error = mAgeEngine.ASAE_FSDK_GetVersion(mAgeVersion);

		ASGE_FSDKError error1 = mGenderEngine.ASGE_FSDK_InitgGenderEngine(FaceDB.appid, FaceDB.gender_key);
		error1 = mGenderEngine.ASGE_FSDK_GetVersion(mGenderVersion);

		startService();
		startSQL();
		startBanner();
		updataRegFace();

        mFRAbsLoop = new FRAbsLoop();
        mFRAbsLoop.start();

		cleanThread = new Thread(new Runnable() {
			@Override
			public void run() {
				delectImage();
				delectRecord();
			}
		});
	//	cleanThread.start();
	}

	private void startService(){
		// andService 服务开启
		mServerManager = new ServerManager(this);
		mServerManager.register();
		mServerManager.startService();
	}

	private void startSQL(){
		ordersDao = new OrderDao(this);

		passRecordList = ordersDao.getAllRecordDate();
		orderList = ordersDao.getAllDate();
		imageBeanList = ordersDao.getAllImageDate();

		SQLiteUtils.setOrderList(orderList);
		SQLiteUtils.setPassRecordList(passRecordList);
		SQLiteUtils.setOrderDao(ordersDao);
		SQLiteUtils.setActivity(DetecterActivity.this);
	}

	private void startBanner(){
		//轮播图开始
		//设置图片资源:url或本地资源
	//	List<String> images = new ArrayList<String>();
//		images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532506694427&di=f7887dceee4a975cb0314d610f2353ac&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201702%2F21%2F145936ziaivvxt2i8922u5.jpg");
//		images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532506694427&di=3f97363a5c4665402dd65fda8104734e&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fmobile%2Fd%2F53c8d6382b5b0.jpg");
//		images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532506809652&di=9b1592d18e462eb45907e4468cf0b808&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3553157884%2C2650523377%26fm%3D214%26gp%3D0.jpg");
//		images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532506694426&di=62c25afb76749502284ba20ce060a137&imgtype=0&src=http%3A%2F%2Fwww.desktx.cc%2Fd%2Ffile%2Fphone%2Ffengjing%2F20161227%2F752008321076f07601d375f0c8a31733.jpg");
//		images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532506838722&di=4d170e7e50cc4cdbaeb0683c2874320b&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1237061219%2C2974268158%26fm%3D214%26gp%3D0.jpg");
//		images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532506860054&di=2b858426efb0e2a284d1f0579dbacfab&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170701%2Ffb8d9714ce1140d5abb7e0a377f38961_th.jpg");
//		images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532506860043&di=05579e9ba8db54e85b3113c5cae02b36&imgtype=0&src=http%3A%2F%2Fi9.download.fd.pchome.net%2Ft_600x1024%2Fg1%2FM00%2F0E%2F0B%2FoYYBAFTudeqIZs2YAAO-b09Dbc0AACT1AK09u0AA76H032.jpg");

        List<Integer> images = new ArrayList<Integer>();
		images.add(R.mipmap.img_chinese_dream1);
        images.add(R.mipmap.img_chinese_dream);
		images.add(R.mipmap.img_g_2);
		images.add(R.mipmap.img_g_jiu);
		images.add(R.mipmap.img_g_z);
		images.add(R.mipmap.img_tz);

		//设置图片标题:自动对应
		String[] titles = new String[]{"A1","B2","C3","D4","E5","G6"};

		Banner banner = (Banner) findViewById(R.id.banner);
		//设置banner样式
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
		//设置图片加载器
		banner.setImageLoader(new GlideImageLoader());
		//设置图片集合
		banner.setImages(images);
		//设置banner动画效果
		banner.setBannerAnimation(Transformer.DepthPage);
		//设置标题集合（当banner样式有显示title时）
		banner.setBannerTitles(Arrays.asList(titles));
		//设置自动轮播，默认为true
		banner.isAutoPlay(true);
		//设置轮播时间
		banner.setDelayTime(4000);
		//设置指示器位置（当banner模式中有指示器时）
		banner.setIndicatorGravity(BannerConfig.CENTER);
		//banner设置方法全部调用完毕时最后调用
		banner.start();
		//轮播图结束
	}

	public static void updataRegFace(){
		//获取注册的人脸
		mResgist = ((App) SQLiteUtils.getActivity().getApplicationContext()).mFaceDB.mRegister;
		OrderDao orderDao = SQLiteUtils.getOrderDao();
		orderList = orderDao.getAllDate();
		// 循环遍历，更新数据

		if (orderList != null && orderList.size() >0) {
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
				for (int j = 0; j < mResgist.size(); j++){
					FaceRegistBean faceRegistBean = mResgist.get(j);
					if (order.Id.equals(faceRegistBean.mName)) {
						faceRegistBean.faceId = order.Id;
						faceRegistBean.mName = order.Name;
					}
				}
			}
		}
		//更新注册的人脸
		((App)  SQLiteUtils.getActivity().getApplicationContext()).mFaceDB.mRegister = mResgist;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mFRAbsLoop.shutdown();
	}

	@Override
	public Camera setupCamera() {
		// TODO Auto-generated method stub
		mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);

		try {
			Camera.Parameters parameters = mCamera.getParameters();
			parameters.setPreviewSize(mWidth, mHeight);
			parameters.setPreviewFormat(mFormat);
			mSurfaceView.setupGLSurafceView(mGLSurfaceView, true, true, 90);

			for( Camera.Size size : parameters.getSupportedPreviewSizes()) {
				Log.d(TAG, "SIZE:" + size.width + "x" + size.height);
			}
			for( Integer format : parameters.getSupportedPreviewFormats()) {
				Log.d(TAG, "FORMAT:" + format);
			}

			List<int[]> fps = parameters.getSupportedPreviewFpsRange();
			for(int[] count : fps) {
				Log.d(TAG, "T:");
				for (int data : count) {
					Log.d(TAG, "V=" + data);
				}
			}
			//parameters.setPreviewFpsRange(15000, 30000);
			//parameters.setExposureCompensation(parameters.getMaxExposureCompensation());
			//parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
			//parameters.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
			//parmeters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			//parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
			//parameters.setColorEffect(Camera.Parameters.EFFECT_NONE);
			mCamera.setParameters(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (mCamera != null) {
			mWidth = mCamera.getParameters().getPreviewSize().width;
			mHeight = mCamera.getParameters().getPreviewSize().height;
		}
		return mCamera;
	}

	@Override
	public void setupChanged(int format, int width, int height) {

	}

	@Override
	public boolean startPreviewImmediately() {
		return true;
	}

	@Override
	public Object onPreview(byte[] data, int width, int height, int format, long timestamp) {
		AFT_FSDKError err = engine.AFT_FSDK_FaceFeatureDetect(data, width, height, AFT_FSDKEngine.CP_PAF_NV21, result);
		Log.d(TAG, "AFT_FSDK_FaceFeatureDetect =" + err.getCode());
		Log.d(TAG, "Face=" + result.size());
		for (AFT_FSDKFace face : result) {
			Log.d(TAG, "Face:" + face.toString());
		}
		if (mImageNV21 == null) {
			if (!result.isEmpty()) {
				mAFT_FSDKFace = result.get(0).clone();
				mImageNV21 = data.clone();
			} else {
				mHandler.postDelayed(hide, 3000);
			}
		}

		Rect[] rects = new Rect[result.size()];
		for (int i = 0; i < result.size(); i++) {
			rects[i] = new Rect(result.get(i).getRect());
		}


		if(rects.length>0) {
			this.mGLSurfaceView.setAlpha(1.0f);
		}
		else {
			this.mGLSurfaceView.setAlpha(0.5f);
			mImageNV21 = null;
		 }
		result.clear();
		return rects;
	}

	public Bitmap rawByteArray2RGBABitmap2(byte[] data, int width, int height) {
		int frameSize = width * height;
		int[] rgba = new int[frameSize];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				int y = (0xff & ((int) data[i * width + j]));
				int u = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 0]));
				int v = (0xff & ((int) data[frameSize + (i >> 1) * width + (j & ~1) + 1]));
				y = y < 16 ? 16 : y;
				int r = Math.round(1.164f * (y - 16) + 1.596f * (v - 128));
				int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128));
				int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));
				r = r < 0 ? 0 : (r > 255 ? 255 : r);
				g = g < 0 ? 0 : (g > 255 ? 255 : g);
				b = b < 0 ? 0 : (b > 255 ? 255 : b);
				rgba[i * width + j] = 0xff000000 + (b << 16) + (g << 8) + r;
			}
		Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		bmp.setPixels(rgba, 0 , width, 0, 0, width, height);
		return bmp;
	}

	@Override
	public void onBeforeRender(CameraFrameData data) {

	}

	@Override
	public void onAfterRender(CameraFrameData data) {
		mGLSurfaceView.getGLES2Render().draw_rect((Rect[])data.getParams(), Color.GREEN, 2);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		CameraHelper.touchFocus(mCamera, event, v, this);
		return false;
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if (success) {
			Log.d(TAG, "Camera Focus SUCCESS!");
		}
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
			mTextIp.setText("ip:"+ mRootUrl);
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
	 * 广播接受的方法，用于更新注册的人脸
	 */
    @Subscribe
    public void onRefreshEvent(RefreshEvent event) {
      String  flag = event.getType();
        Log.e("############","flag="+flag);
        if("updata".equals(event.getType())){
            updataRegFace();
        }
    }


	/**
	 * 清除已经下载过的图片，在子线程中调用
	 */
    private void delectImage(){
    List<ImageBean>	imageList = new ArrayList();
    imageList =	ordersDao.getAllImageDate();
    for (int i = 0; i < imageList.size(); i++) {
     if ("1".equals(imageList.get(i).isDownload)) {
     	ordersDao.deleteImageById(imageList.get(i).Id);
		}
	  }
	}



	/**
	 * 清除已经获取过的记录，在子线程中调用
	 */
	private void delectRecord(){
		ordersDao.cleanRecordData();
	}
    }
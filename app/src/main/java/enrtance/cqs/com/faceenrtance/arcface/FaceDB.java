package enrtance.cqs.com.faceenrtance.arcface;

import android.util.Log;


import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.java.ExtInputStream;
import com.guo.android_extend.java.ExtOutputStream;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import enrtance.cqs.com.faceenrtance.bean.FaceRegistBean;
import enrtance.cqs.com.faceenrtance.interfacerequest.RefreshEvent;
import enrtance.cqs.com.faceenrtance.utils.SQLiteUtils;

/**
 * Created by gqj3375 on 2017/7/11.
 */

public class FaceDB {
	private final String TAG = this.getClass().toString();

	public static String appid = "2dkZXLirRcjk4aCoiWNRrK8fXnhn9SQCGA7xoFeEqk2r";
	public static String ft_key = "D2w4ury7gNiLCDtCaZxuFszR6dfKNrQP5WdnPgYWFJjy";
	public static String fd_key = "D2w4ury7gNiLCDtCaZxuFszYG2vXNrToHPSahwCFZyyt";
	public static String fr_key = "D2w4ury7gNiLCDtCaZxuFszfRSBfUMLRro5LHL496rFa";
	public static String age_key = "D2w4ury7gNiLCDtCaZxuFt1HESVYCu3QjELcQUj3WoZw";
	public static String gender_key = "D2w4ury7gNiLCDtCaZxuFt1QPqkioZHRNpMPEqd2ibKE";

	String mDBPath;
	public List<FaceRegistBean> mRegister;
	AFR_FSDKEngine mFREngine;
	AFR_FSDKVersion mFRVersion;
	boolean mUpgrade;

	public FaceDB(String path) {
		mDBPath = path;
		mRegister = new ArrayList<>();
		mFRVersion = new AFR_FSDKVersion();
		mUpgrade = false;
		mFREngine = new AFR_FSDKEngine();
		AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
		if (error.getCode() != AFR_FSDKError.MOK) {
			Log.e(TAG, "AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
		} else {
			mFREngine.AFR_FSDK_GetVersion(mFRVersion);
			Log.d(TAG, "AFR_FSDK_GetVersion=" + mFRVersion.toString());
		}
	}

	public void destroy() {
		if (mFREngine != null) {
			mFREngine.AFR_FSDK_UninitialEngine();
		}
	}

	private boolean saveInfo() {
		try {
			FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt");
			ExtOutputStream bos = new ExtOutputStream(fs);
			bos.writeString(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel());
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean loadInfo() {
		if (!mRegister.isEmpty()) {
			return false;
		}
		try {
			FileInputStream fs = new FileInputStream(mDBPath + "/face.txt");
			ExtInputStream bos = new ExtInputStream(fs);
			//load version
			String version_saved = bos.readString();
			if (version_saved.equals(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel())) {
				mUpgrade = true;
			}
			String name;

			if (version_saved != null) {
				for ( name = bos.readString(); name != null; name = bos.readString()){
					if (new File(mDBPath + "/" + name + ".data").exists()) {
						mRegister.add(new FaceRegistBean(new String(name)));
					}
				}
			}
			bos.close();
			fs.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean loadFaces(){
		if (loadInfo()) {
			try {
				for (FaceRegistBean face : mRegister) {
					FileInputStream fs = new FileInputStream(mDBPath + "/" + face.mName + ".data");
					ExtInputStream bos = new ExtInputStream(fs);
					AFR_FSDKFace afr = null;
					do {
						if (afr != null) {
							if (mUpgrade) {
								//upgrade data.
							}
							face.mFaceList.add(afr);
						}
						afr = new AFR_FSDKFace();
					} while (bos.readBytes(afr.getFeatureData()));
					bos.close();
					fs.close();
					Log.d(TAG, "load name: size = " + face.mFaceList.size());
				}
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public	void addFace(String name, AFR_FSDKFace face) {
//		try {
//			//check if already registered.
//			boolean add = true;
//			for (FaceRegistBean frface : mRegister) {
//				if (frface.mName.equals(name)) {
//					frface.mFaceList.add(face);
//					add = false;
//					break;
//				}
//			}
//			if (add) { // not registered.
//				FaceRegistBean frface = new FaceRegistBean(name);
//				frface.mFaceList.add(face);
//				mRegister.add(frface);
//			}
//
//			if (saveInfo()) {
//				FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
//				ExtOutputStream bos = new ExtOutputStream(fs);
//				for (FaceRegistBean frface : mRegister) {
//					bos.writeString(frface.mName);
//			//		bos.writeString(frface.faceId);
//				}
//				bos.close();
//				fs.close();
//				//save new feature
//				fs = new FileOutputStream(mDBPath + "/" + name + ".data", true);
//				bos = new ExtOutputStream(fs);
//				bos.writeBytes(face.getFeatureData());
//				bos.close();
//				fs.close();
//
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


		try {
			//check if already registered.
			boolean add = true;
			for (FaceRegistBean frface : mRegister) {
				if (frface.mName.equals(name)) {
					frface.mFaceList.add(face);
					add = false;
					break;
				}
			}
			if (add) { // not registered.
				FaceRegistBean frface = new FaceRegistBean(name);
				frface.mFaceList.add(face);
				mRegister.add(frface);
			}

			if (!new File(mDBPath + "/face.txt").exists()) {
				if (!saveInfo()) {
					Log.e(TAG, "save fail!");
				}
			}

			//save name
			FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
			ExtOutputStream bos = new ExtOutputStream(fs);
			bos.writeString(name);
			bos.close();
			fs.close();

			//save feature
			fs = new FileOutputStream(mDBPath + "/" + name + ".data", true);
			bos = new ExtOutputStream(fs);
			bos.writeBytes(face.getFeatureData());
			bos.close();
			fs.close();
			EventBus.getDefault().post(new RefreshEvent("updata"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public boolean deleteById(String faceId) {
		try {
			//check if already registered.
			boolean find = false;
			for (FaceRegistBean frface : mRegister) {
				if (frface.faceId.equals(faceId)) {
					File delfile = new File(mDBPath + "/" + faceId + ".data");
					if (delfile.exists()) {
						delfile.delete();
					}
					mRegister.remove(frface);
					find = true;
					break;
				}
			}

			if (find) {
				if (saveInfo()) {
					//update all names
					FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
					ExtOutputStream bos = new ExtOutputStream(fs);
					for (FaceRegistBean frface : mRegister) {
						bos.writeString(frface.mName);
					}
					bos.close();
					fs.close();
				}
			}
			return find;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public boolean delete(String name) {
		try {
			//check if already registered.
			boolean find = false;
			for (FaceRegistBean frface : mRegister) {
				if (frface.mName.equals(name)) {
					File delfile = new File(mDBPath + "/" + name + ".data");
					if (delfile.exists()) {
						delfile.delete();
					}
					mRegister.remove(frface);
					find = true;
					break;
				}
			}

			if (find) {
				if (saveInfo()) {
					//update all names
					FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
					ExtOutputStream bos = new ExtOutputStream(fs);
					for (FaceRegistBean frface : mRegister) {
						bos.writeString(frface.mName);
					}
					bos.close();
					fs.close();
				}
			}
			return find;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean upgrade() {
		return false;
	}
}

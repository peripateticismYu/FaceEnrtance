package enrtance.cqs.com.faceenrtance.bean;

import com.arcsoft.facerecognition.AFR_FSDKFace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FaceRegistBean implements Serializable {
    public String mName;
    public String faceId;
    public List<AFR_FSDKFace> mFaceList;

    public FaceRegistBean(String name) {
        mName = name;
        mFaceList = new ArrayList<>();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public List<AFR_FSDKFace> getmFaceList() {
        return mFaceList;
    }

    public void setmFaceList(List<AFR_FSDKFace> mFaceList) {
        this.mFaceList = mFaceList;
    }
}

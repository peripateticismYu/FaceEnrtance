package enrtance.cqs.com.faceenrtance.bean;

public class ImageBean {

    public String Id ;
    public String imageData;
    public String isDownload;
    public int recordTime;

    public ImageBean() {
    }

    public ImageBean(String id,String imageData, String isDownload, int recordTime) {
        this.Id = id;
        this.imageData = imageData;
        this.isDownload = isDownload;
        this.recordTime = recordTime;
    }


}

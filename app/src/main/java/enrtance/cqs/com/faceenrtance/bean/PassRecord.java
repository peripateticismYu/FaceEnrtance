package enrtance.cqs.com.faceenrtance.bean;

public class PassRecord {

    public String Id;
    public String userId;
    public String Name;
    public int recordTime;
    public String imageId;
    public String status;

    public PassRecord() {
    }

    public PassRecord(String id,String userId, String Name, int recordTime, String imageId,String status) {
        this.Id = id;
        this.userId = userId;
        this.Name = Name;
        this.recordTime = recordTime;
        this.imageId = imageId;
        this.status = status;
    }
}

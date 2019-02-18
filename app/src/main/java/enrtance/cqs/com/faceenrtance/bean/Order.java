package enrtance.cqs.com.faceenrtance.bean;

public class Order {

    public String Id;
    public String Name;
    public String Sex;

    public String FaceEigenvalue;
    public String FaceImageUrl;

    public Order() {
    }

    public Order(String id, String Name,String Sex,String FaceEigenvalue, String FaceImageUrl) {
        this.Id = id;
        this.Name = Name;
        this.Sex = Sex;
        this.FaceEigenvalue = FaceEigenvalue;
        this.FaceImageUrl = FaceImageUrl;
    }
}

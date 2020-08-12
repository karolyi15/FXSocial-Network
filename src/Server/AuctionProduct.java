package Server;

import org.json.simple.JSONObject;

public class AuctionProduct {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private String name;
    private String description;
    private long basePrice;
    private long topPrice;
    private String image;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public AuctionProduct(){

        this.name  = "";
        this.description = "";
        this.basePrice = 0;
        this.topPrice = 0;
        this.image = "";
    }

    public AuctionProduct(String name, String description, long basePrice, long topPrice, String image){

        this.name  = name;
        this.description = description;
        this.basePrice = basePrice;
        this.topPrice = topPrice;
        this.image = image;
    }

    public AuctionProduct(JSONObject productData){

        this.name  = (String) productData.get("Name");
        this.description = (String) productData.get("Description");
        this.basePrice = (long) productData.get("BasePrice");
        this.topPrice = (long) productData.get("TopPrice");
        this.image = (String) productData.get("Image");
    }

    //Setters & Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(long basePrice) {
        this.basePrice = basePrice;
    }

    public long getTopPrice() {
        return topPrice;
    }

    public void setTopPrice(long topPrice) {
        this.topPrice = topPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //Communication
    public JSONObject toJson(){

        JSONObject outputJson = new JSONObject();

        outputJson.put("Name", this.name);
        outputJson.put("Description", this.description);
        outputJson.put("BasePrice", this.basePrice);
        outputJson.put("TopPrice", this.topPrice);
        outputJson.put("Image", this.image);

        return outputJson;
    }
}

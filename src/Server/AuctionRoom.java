package Server;

import org.json.simple.JSONObject;

public class AuctionRoom {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private String name;
    private String description;
    private AuctionProduct product;
    private String start;
    private String end;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public AuctionRoom(){

        this.name = "";
        this.description = "";
        this.product = new AuctionProduct();
        this.start = "";
        this.end = "";
    }

    public AuctionRoom(JSONObject auctionData){

        this.name = (String) auctionData.get("Name");
        this.description = (String) auctionData.get("Description");
        this.product = new AuctionProduct((JSONObject) auctionData.get("Product"));
        this.start =( String) auctionData.get("Start");
        this.end = (String) auctionData.get("End");
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

    public AuctionProduct getProduct() {
        return product;
    }

    public void setProduct(AuctionProduct product) {
        this.product = product;
    }

    //Communication
    public JSONObject toJson(){

        JSONObject outputJson = new JSONObject();

        outputJson.put("Name", this.name);
        outputJson.put("Description", this.description);
        outputJson.put("Product", this.product.toJson());
        outputJson.put("Start", this.start);
        outputJson.put("End", this.end);

        return outputJson;
    }
}

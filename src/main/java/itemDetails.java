public class itemDetails {
    private String websiteURL;
    private String prodName;
    private Double prodPrice;
    private String prodLink;

    public itemDetails(String websiteURL, String prodName, Double prodPrice, String prodLink) {
        this.websiteURL = websiteURL;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodLink = prodLink;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public String getProdName() {
        return prodName;
    }

    public Double getProdPrice() {
        return prodPrice;
    }

    public String getProdLink() {
        return prodLink;
    }


}

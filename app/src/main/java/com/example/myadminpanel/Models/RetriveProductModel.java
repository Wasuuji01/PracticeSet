package com.example.myadminpanel.Models;

public class RetriveProductModel {

    String Productname;
    String Productdesp;
    long Productprice;
    long ProductSprice;

    public RetriveProductModel() {
    }

    public RetriveProductModel(String productname, String productdesp, long productprice, long productSprice) {
        Productname = productname;
        Productdesp = productdesp;
        Productprice = productprice;
        ProductSprice = productSprice;
    }

    public String getProductname() {
        return Productname;
    }

    public void setProductname(String productname) {
        Productname = productname;
    }

    public String getProductdesp() {
        return Productdesp;
    }

    public void setProductdesp(String productdesp) {
        Productdesp = productdesp;
    }

    public long getProductprice() {
        return Productprice;
    }

    public void setProductprice(long productprice) {
        Productprice = productprice;
    }

    public long getProductSprice() {
        return ProductSprice;
    }

    public void setProductSprice(long productSprice) {
        ProductSprice = productSprice;
    }
}

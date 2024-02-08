package com.example.myadminpanel.Models;

public class CategoryImageModel {

    String categoryimg,categoryname;

    public CategoryImageModel() {
    }

    public CategoryImageModel(String categoryimg, String categoryname) {
        this.categoryimg = categoryimg;
        this.categoryname = categoryname;
    }

    public String getCategoryimg() {
        return categoryimg;
    }

    public void setCategoryimg(String categoryimg) {
        this.categoryimg = categoryimg;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}

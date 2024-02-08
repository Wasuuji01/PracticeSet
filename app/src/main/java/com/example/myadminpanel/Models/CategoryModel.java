package com.example.myadminpanel.Models;

public class CategoryModel {

    String categoryname;
    String categoryimg;

    public CategoryModel() {
    }

    public CategoryModel(String categoryname, String categoryimg) {
        this.categoryname = categoryname;
        this.categoryimg = categoryimg;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategoryimg() {
        return categoryimg;
    }

    public void setCategoryimg(String categoryimg) {
        this.categoryimg = categoryimg;
    }
}

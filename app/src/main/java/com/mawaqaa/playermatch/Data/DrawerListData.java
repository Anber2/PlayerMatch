package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/9/2017.
 */

public class DrawerListData {


    private String name;

    private int imageId;


    public DrawerListData() {

    }

    public DrawerListData(String name ){
        this.name = name;
     }

    public DrawerListData(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

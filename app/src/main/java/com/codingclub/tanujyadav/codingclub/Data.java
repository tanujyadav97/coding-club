package com.codingclub.tanujyadav.codingclub;

/**
 * Created by navneet on 20/11/16.
 */

public class Data {

    private String heading;
    private String description;

    private String imagePath;

    public Data(String head,String imagePath, String description) {
        this.heading=head;
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getheading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setDescription(String desc) {
        description=desc;
    }

    public void setImagePath(String ss) {
        imagePath=ss;
    }
}

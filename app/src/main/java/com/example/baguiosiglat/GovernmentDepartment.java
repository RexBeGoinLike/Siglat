package com.example.baguiosiglat;

public class GovernmentDepartment {
    private String name;
    private String image;
    private String description;

    private String link;


    public GovernmentDepartment(String name, String image, String description, String link) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

package com.example.baguiosiglat;

public class GovernmentDepartment {
    private String name;
    private String logoLink;
    private String description;

    private String siteLink;

    public GovernmentDepartment(String name, String image, String description, String link) {
        this.name = name;
        this.logoLink = image;
        this.description = description;
        this.siteLink = link;
    }

    public GovernmentDepartment(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }
}

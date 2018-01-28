package com.example.rechee.sharkfeed.MainScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rechee on 1/27/2018.
 */
public class Photo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private Integer farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private Integer ispublic;
    @SerializedName("isfriend")
    @Expose
    private Integer isfriend;
    @SerializedName("isfamily")
    @Expose
    private Integer isfamily;
    @SerializedName("url_t")
    @Expose
    private String urlT;
    @SerializedName("height_t")
    @Expose
    private String heightT;
    @SerializedName("width_t")
    @Expose
    private String widthT;
    @SerializedName("url_c")
    @Expose
    private String urlC;

    @SerializedName("url_o")
    @Expose
    private String urlO;

    @SerializedName("height_c")
    @Expose
    private Integer heightC;
    @SerializedName("width_c")
    @Expose
    private String widthC;
    @SerializedName("url_n")
    @Expose
    private String urlN;
    @SerializedName("height_l")
    @Expose
    private String heightL;
    @SerializedName("width_l")
    @Expose
    private String widthL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getFarm() {
        return farm;
    }

    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    public Integer getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(Integer isfriend) {
        this.isfriend = isfriend;
    }

    public Integer getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(Integer isfamily) {
        this.isfamily = isfamily;
    }

    public String getUrlT() {
        return urlT;
    }

    public void setUrlT(String urlT) {
        this.urlT = urlT;
    }

    public String getHeightT() {
        return heightT;
    }

    public void setHeightT(String heightT) {
        this.heightT = heightT;
    }

    public String getWidthT() {
        return widthT;
    }

    public void setWidthT(String widthT) {
        this.widthT = widthT;
    }

    public String getUrlC() {
        return urlC;
    }

    public void setUrlC(String urlC) {
        this.urlC = urlC;
    }

    public String getUrlO() {
        return urlO;
    }

    public void seturlO(String urlO) {
        this.urlO = urlO;
    }

    public Integer getHeightC() {
        return heightC;
    }

    public void setHeightC(Integer heightC) {
        this.heightC = heightC;
    }

    public String getWidthC() {
        return widthC;
    }

    public void setWidthC(String widthC) {
        this.widthC = widthC;
    }

    public String getUrlN() {
        return urlN;
    }

    public void setUrlN(String urlN) {
        this.urlN = urlN;
    }

    public String getHeightL() {
        return heightL;
    }

    public void setHeightL(String heightL) {
        this.heightL = heightL;
    }

    public String getWidthL() {
        return widthL;
    }

    public void setWidthL(String widthL) {
        this.widthL = widthL;
    }

}

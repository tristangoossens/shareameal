package com.example.shareameal.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meal {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("isVega")
    @Expose
    private Boolean isVega;
    @SerializedName("isVegan")
    @Expose
    private Boolean isVegan;
    @SerializedName("isToTakeHome")
    @Expose
    private Boolean isToTakeHome;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("updateDate")
    @Expose
    private String updateDate;
    @SerializedName("maxAmountOfParticipants")
    @Expose
    private Integer maxAmountOfParticipants;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("allergenes")
    @Expose
    private List<Object> allergenes = null;
    @SerializedName("cook")
    @Expose
    private Cook cook;
    @SerializedName("participants")
    @Expose
    private List<Participant> participants = null;

    public Meal(Integer id, String name, String description, Boolean isActive, Boolean isVega, Boolean isVegan, Boolean isToTakeHome, String dateTime, String createDate, String updateDate, Integer maxAmountOfParticipants, String price, String imageUrl, List<Object> allergenes, Cook cook, List<Participant> participants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.isVega = isVega;
        this.isVegan = isVegan;
        this.isToTakeHome = isToTakeHome;
        this.dateTime = dateTime;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.maxAmountOfParticipants = maxAmountOfParticipants;
        this.price = price;
        this.imageUrl = imageUrl;
        this.allergenes = allergenes;
        this.cook = cook;
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsVega() {
        return isVega;
    }

    public void setIsVega(Boolean isVega) {
        this.isVega = isVega;
    }

    public Boolean getIsVegan() {
        return isVegan;
    }

    public void setIsVegan(Boolean isVegan) {
        this.isVegan = isVegan;
    }

    public Boolean getIsToTakeHome() {
        return isToTakeHome;
    }

    public void setIsToTakeHome(Boolean isToTakeHome) {
        this.isToTakeHome = isToTakeHome;
    }

    public String getDateTime() {
        String t = this.dateTime.substring(0, 10);
        LocalDate d = LocalDate.parse(t);
        return String.format("%s %s %s", d.getDayOfMonth(), d.getMonth().toString().toLowerCase(), d.getYear());
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getMaxAmountOfParticipants() {
        return maxAmountOfParticipants;
    }

    public void setMaxAmountOfParticipants(Integer maxAmountOfParticipants) {
        this.maxAmountOfParticipants = maxAmountOfParticipants;
    }

    public String getPrice() {
        return String.format("€ %s", this.price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Object> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(List<Object> allergenes) {
        this.allergenes = allergenes;
    }

    public Cook getCook() {
        return cook;
    }

    public void setCook(Cook cook) {
        this.cook = cook;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

}
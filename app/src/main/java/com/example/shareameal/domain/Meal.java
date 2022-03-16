package com.example.shareameal.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Meal {

    @SerializedName("id")
    @Expose
    @PrimaryKey()
    private Integer id;
    @SerializedName("name")
    @Expose
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("description")
    @Expose
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @SerializedName("isActive")
    @Expose
    @NonNull
    @ColumnInfo(name = "isActive")
    private Boolean isActive;
    @SerializedName("isVega")
    @Expose
    @NonNull
    @ColumnInfo(name = "isVega")
    private Boolean isVega;
    @SerializedName("isVegan")
    @Expose
    @NonNull
    @ColumnInfo(name = "isVegan")
    private Boolean isVegan;
    @SerializedName("isToTakeHome")
    @Expose
    @NonNull
    @ColumnInfo(name = "isToTakeHome")
    private Boolean isToTakeHome;
    @SerializedName("dateTime")
    @Expose
    @NonNull
    @ColumnInfo(name = "dateTime")
    private String dateTime;
    @SerializedName("createDate")
    @Expose
    @NonNull
    @ColumnInfo(name = "createDate")
    private String createDate;
    @SerializedName("updateDate")
    @Expose
    @NonNull
    @ColumnInfo(name = "updateDate")
    private String updateDate;
    @SerializedName("maxAmountOfParticipants")
    @Expose
    @NonNull
    @ColumnInfo(name = "maxAmountOfParticipants")
    private Integer maxAmountOfParticipants;
    @SerializedName("price")
    @Expose
    @NonNull
    @ColumnInfo(name = "price")
    private String price;
    @SerializedName("imageUrl")
    @Expose
    @NonNull
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;
    @SerializedName("allergenes")
    @Expose
    @Ignore
    private List<Object> allergenes = null;
    @SerializedName("cook")
    @Expose
    @Ignore
    private Cook cook;
    @SerializedName("participants")
    @Expose
    @Ignore
    private List<Participant> participants = null;

    public Meal(Integer id, String name, String description, Boolean isActive, Boolean isVega, Boolean isVegan, Boolean isToTakeHome, String dateTime, String createDate, String updateDate, Integer maxAmountOfParticipants, String price, String imageUrl) {
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
    }

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
        try{
            String t = this.dateTime.substring(0, 10);
            LocalDate d = LocalDate.parse(t);

            return String.format("%s %s %s", d.getDayOfMonth(), d.getMonth().toString().toLowerCase(), d.getYear());
        }catch (Exception e){
            return this.dateTime;
        }
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
        return this.price;
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
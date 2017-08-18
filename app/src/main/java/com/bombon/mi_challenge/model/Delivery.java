package com.bombon.mi_challenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vaughn on 8/18/17.
 */

public class Delivery extends RealmObject{
    @PrimaryKey
    public Integer id;

    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("imageUrl")
    @Expose
    public String imageUrl;
    @SerializedName("location")
    @Expose
    public Location location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

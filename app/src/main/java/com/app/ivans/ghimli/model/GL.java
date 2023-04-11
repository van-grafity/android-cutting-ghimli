package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GL implements Parcelable {
    private int id;
    @SerializedName("gl_number")
    private String glNo;
    private String season;
    @SerializedName("size_order")
    private String sizeOrder;
    private Buyer buyer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGlNo() {
        return glNo;
    }

    public void setGlNo(String glNo) {
        this.glNo = glNo;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSizeOrder() {
        return sizeOrder;
    }

    public void setSizeOrder(String sizeOrder) {
        this.sizeOrder = sizeOrder;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.glNo);
        dest.writeString(this.season);
        dest.writeString(this.sizeOrder);
        dest.writeParcelable(this.buyer, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.glNo = source.readString();
        this.season = source.readString();
        this.sizeOrder = source.readString();
        this.buyer = source.readParcelable(Buyer.class.getClassLoader());
    }

    public GL() {
    }

    protected GL(Parcel in) {
        this.id = in.readInt();
        this.glNo = in.readString();
        this.season = in.readString();
        this.sizeOrder = in.readString();
        this.buyer = in.readParcelable(Buyer.class.getClassLoader());
    }

    public static final Creator<GL> CREATOR = new Creator<GL>() {
        @Override
        public GL createFromParcel(Parcel source) {
            return new GL(source);
        }

        @Override
        public GL[] newArray(int size) {
            return new GL[size];
        }
    };
}

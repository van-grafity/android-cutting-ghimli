package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Buyer implements Parcelable {
    private int id;
    private String name;
    private String address;
    @SerializedName("shipment_address")
    private String shipmentAddress;
    private String code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.shipmentAddress);
        dest.writeString(this.code);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
        this.address = source.readString();
        this.shipmentAddress = source.readString();
        this.code = source.readString();
    }

    public Buyer() {
    }

    protected Buyer(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
        this.shipmentAddress = in.readString();
        this.code = in.readString();
    }

    public static final Creator<Buyer> CREATOR = new Creator<Buyer>() {
        @Override
        public Buyer createFromParcel(Parcel source) {
            return new Buyer(source);
        }

        @Override
        public Buyer[] newArray(int size) {
            return new Buyer[size];
        }
    };
}

package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LayingPlanning implements Parcelable {
    private int id;
    @SerializedName("serial_number")
    private String serialNumber;
    private GL gl;
    private Style style;
    private Buyer buyer;
    private Color color;
    @SerializedName("order_qty")
    private int orderQty;
    @SerializedName("delivery_date")
    private String deliveryDate;
    @SerializedName("plan_date")
    private String planDate;
    @SerializedName("fabric_po")
    private String fabricPo;
    @SerializedName("fabric_cons")
    private FabricCons fabricCons;
    @SerializedName("fabric_type")
    private FabricType fabricType;
    @SerializedName("fabric_cons_qty")
    private double fabricConsQty;
    @SerializedName("fabric_cons_desc")
    private String fabricConsDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public GL getGl() {
        return gl;
    }

    public void setGl(GL gl) {
        this.gl = gl;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getFabricPo() {
        return fabricPo;
    }

    public void setFabricPo(String fabricPo) {
        this.fabricPo = fabricPo;
    }

    public FabricCons getFabricCons() {
        return fabricCons;
    }

    public void setFabricCons(FabricCons fabricCons) {
        this.fabricCons = fabricCons;
    }

    public FabricType getFabricType() {
        return fabricType;
    }

    public void setFabricType(FabricType fabricType) {
        this.fabricType = fabricType;
    }

    public double getFabricConsQty() {
        return fabricConsQty;
    }

    public void setFabricConsQty(double fabricConsQty) {
        this.fabricConsQty = fabricConsQty;
    }

    public String getFabricConsDesc() {
        return fabricConsDesc;
    }

    public void setFabricConsDesc(String fabricConsDesc) {
        this.fabricConsDesc = fabricConsDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.serialNumber);
        dest.writeParcelable(this.gl, flags);
        dest.writeParcelable(this.style, flags);
        dest.writeParcelable(this.buyer, flags);
        dest.writeParcelable(this.color, flags);
        dest.writeInt(this.orderQty);
        dest.writeString(this.deliveryDate);
        dest.writeString(this.planDate);
        dest.writeString(this.fabricPo);
        dest.writeParcelable(this.fabricCons, flags);
        dest.writeParcelable(this.fabricType, flags);
        dest.writeDouble(this.fabricConsQty);
        dest.writeString(this.fabricConsDesc);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.serialNumber = source.readString();
        this.gl = source.readParcelable(GL.class.getClassLoader());
        this.style = source.readParcelable(Style.class.getClassLoader());
        this.buyer = source.readParcelable(Buyer.class.getClassLoader());
        this.color = source.readParcelable(Color.class.getClassLoader());
        this.orderQty = source.readInt();
        this.deliveryDate = source.readString();
        this.planDate = source.readString();
        this.fabricPo = source.readString();
        this.fabricCons = source.readParcelable(FabricCons.class.getClassLoader());
        this.fabricType = source.readParcelable(FabricType.class.getClassLoader());
        this.fabricConsQty = source.readDouble();
        this.fabricConsDesc = source.readString();
    }

    public LayingPlanning() {
    }

    protected LayingPlanning(Parcel in) {
        this.id = in.readInt();
        this.serialNumber = in.readString();
        this.gl = in.readParcelable(GL.class.getClassLoader());
        this.style = in.readParcelable(Style.class.getClassLoader());
        this.buyer = in.readParcelable(Buyer.class.getClassLoader());
        this.color = in.readParcelable(Color.class.getClassLoader());
        this.orderQty = in.readInt();
        this.deliveryDate = in.readString();
        this.planDate = in.readString();
        this.fabricPo = in.readString();
        this.fabricCons = in.readParcelable(FabricCons.class.getClassLoader());
        this.fabricType = in.readParcelable(FabricType.class.getClassLoader());
        this.fabricConsQty = in.readDouble();
        this.fabricConsDesc = in.readString();
    }

    public static final Creator<LayingPlanning> CREATOR = new Creator<LayingPlanning>() {
        @Override
        public LayingPlanning createFromParcel(Parcel source) {
            return new LayingPlanning(source);
        }

        @Override
        public LayingPlanning[] newArray(int size) {
            return new LayingPlanning[size];
        }
    };
}

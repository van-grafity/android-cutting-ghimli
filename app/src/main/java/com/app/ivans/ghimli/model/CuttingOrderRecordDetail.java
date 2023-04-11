package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CuttingOrderRecordDetail implements Parcelable {
    @SerializedName("cutting_order_record_id")
    private int cuttingOrderRecordId;
    @SerializedName("fabric_roll")
    private String fabricRoll;
    @SerializedName("fabric_batch")
    private String fabricBatch;
    @SerializedName("color_id")
    private int colorId;
    private Color color;
    private double yardage;
    private double weight;
    private int layer;
    private double joint;
    @SerializedName("balance_end")
    private int balanceEnd;
    private String remarks;
    private String operator;

    public CuttingOrderRecordDetail(int cuttingOrderRecordId, String fabricRoll, String fabricBatch, int colorId, Color color, double yardage, double weight, int layer, double joint, int balanceEnd, String remarks, String operator) {
        this.cuttingOrderRecordId = cuttingOrderRecordId;
        this.fabricRoll = fabricRoll;
        this.fabricBatch = fabricBatch;
        this.colorId = colorId;
        this.color = color;
        this.yardage = yardage;
        this.weight = weight;
        this.layer = layer;
        this.joint = joint;
        this.balanceEnd = balanceEnd;
        this.remarks = remarks;
        this.operator = operator;
    }

    public int getCuttingOrderRecordId() {
        return cuttingOrderRecordId;
    }

    public void setCuttingOrderRecordId(int cuttingOrderRecordId) {
        this.cuttingOrderRecordId = cuttingOrderRecordId;
    }

    public String getFabricRoll() {
        return fabricRoll;
    }

    public void setFabricRoll(String fabricRoll) {
        this.fabricRoll = fabricRoll;
    }

    public String getFabricBatch() {
        return fabricBatch;
    }

    public void setFabricBatch(String fabricBatch) {
        this.fabricBatch = fabricBatch;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getYardage() {
        return yardage;
    }

    public void setYardage(double yardage) {
        this.yardage = yardage;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public double getJoint() {
        return joint;
    }

    public void setJoint(double joint) {
        this.joint = joint;
    }

    public int getBalanceEnd() {
        return balanceEnd;
    }

    public void setBalanceEnd(int balanceEnd) {
        this.balanceEnd = balanceEnd;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cuttingOrderRecordId);
        dest.writeString(this.fabricRoll);
        dest.writeString(this.fabricBatch);
        dest.writeInt(this.colorId);
        dest.writeParcelable(this.color, flags);
        dest.writeDouble(this.yardage);
        dest.writeDouble(this.weight);
        dest.writeInt(this.layer);
        dest.writeDouble(this.joint);
        dest.writeInt(this.balanceEnd);
        dest.writeString(this.remarks);
        dest.writeString(this.operator);
    }

    public void readFromParcel(Parcel source) {
        this.cuttingOrderRecordId = source.readInt();
        this.fabricRoll = source.readString();
        this.fabricBatch = source.readString();
        this.colorId = source.readInt();
        this.color = source.readParcelable(Color.class.getClassLoader());
        this.yardage = source.readDouble();
        this.weight = source.readDouble();
        this.layer = source.readInt();
        this.joint = source.readDouble();
        this.balanceEnd = source.readInt();
        this.remarks = source.readString();
        this.operator = source.readString();
    }

    public CuttingOrderRecordDetail() {
    }

    protected CuttingOrderRecordDetail(Parcel in) {
        this.cuttingOrderRecordId = in.readInt();
        this.fabricRoll = in.readString();
        this.fabricBatch = in.readString();
        this.colorId = in.readInt();
        this.color = in.readParcelable(Color.class.getClassLoader());
        this.yardage = in.readDouble();
        this.weight = in.readDouble();
        this.layer = in.readInt();
        this.joint = in.readDouble();
        this.balanceEnd = in.readInt();
        this.remarks = in.readString();
        this.operator = in.readString();
    }

    public static final Creator<CuttingOrderRecordDetail> CREATOR = new Creator<CuttingOrderRecordDetail>() {
        @Override
        public CuttingOrderRecordDetail createFromParcel(Parcel source) {
            return new CuttingOrderRecordDetail(source);
        }

        @Override
        public CuttingOrderRecordDetail[] newArray(int size) {
            return new CuttingOrderRecordDetail[size];
        }
    };
}

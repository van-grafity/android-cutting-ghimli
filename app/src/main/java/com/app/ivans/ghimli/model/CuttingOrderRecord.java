package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CuttingOrderRecord implements Parcelable {
    private int id;
    @SerializedName("serial_number")
    private String serialNumber;
    @SerializedName("cutting_order_record_detail")
    private ArrayList<CuttingOrderRecordDetail> cuttingOrderRecordDetail;
    @SerializedName("laying_planning_detail")
    private LayingPlanningDetail layingPlanningDetail;

    public LayingPlanningDetail getLayingPlanningDetail() {
        return layingPlanningDetail;
    }

    public void setLayingPlanningDetail(LayingPlanningDetail layingPlanningDetail) {
        this.layingPlanningDetail = layingPlanningDetail;
    }

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

    public ArrayList<CuttingOrderRecordDetail> getCuttingOrderRecordDetail() {
        return cuttingOrderRecordDetail;
    }

    public void setCuttingOrderRecordDetail(ArrayList<CuttingOrderRecordDetail> cuttingOrderRecordDetail) {
        this.cuttingOrderRecordDetail = cuttingOrderRecordDetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.serialNumber);
        dest.writeList(this.cuttingOrderRecordDetail);
        dest.writeParcelable(this.layingPlanningDetail, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.serialNumber = source.readString();
        this.cuttingOrderRecordDetail = new ArrayList<CuttingOrderRecordDetail>();
        source.readList(this.cuttingOrderRecordDetail, CuttingOrderRecordDetail.class.getClassLoader());
        this.layingPlanningDetail = source.readParcelable(LayingPlanningDetail.class.getClassLoader());
    }

    public CuttingOrderRecord() {
    }

    protected CuttingOrderRecord(Parcel in) {
        this.id = in.readInt();
        this.serialNumber = in.readString();
        this.cuttingOrderRecordDetail = new ArrayList<CuttingOrderRecordDetail>();
        in.readList(this.cuttingOrderRecordDetail, CuttingOrderRecordDetail.class.getClassLoader());
        this.layingPlanningDetail = in.readParcelable(LayingPlanningDetail.class.getClassLoader());
    }

    public static final Creator<CuttingOrderRecord> CREATOR = new Creator<CuttingOrderRecord>() {
        @Override
        public CuttingOrderRecord createFromParcel(Parcel source) {
            return new CuttingOrderRecord(source);
        }

        @Override
        public CuttingOrderRecord[] newArray(int size) {
            return new CuttingOrderRecord[size];
        }
    };
}

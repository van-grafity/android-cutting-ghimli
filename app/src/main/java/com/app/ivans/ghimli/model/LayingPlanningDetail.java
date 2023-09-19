package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LayingPlanningDetail implements Parcelable {
    private int id;
    @SerializedName("no_laying_sheet")
    private String noLayingSheet;
    @SerializedName("table_number")
    private int tableNumber;
    @SerializedName("layer_qty")
    private int layerQty;
    @SerializedName("marker_code")
    private String markerCode;
    @SerializedName("marker_yard")
    private double markerYard;
    @SerializedName("marker_inch")
    private double markerInch;
    @SerializedName("marker_length")
    private double markerLength;
    @SerializedName("total_length")
    private double totalLength;
    @SerializedName("total_all_size")
    private int totalAllSize;
    @SerializedName("laying_planning")
    private LayingPlanning layingPlanning;
    @SerializedName("cutting_order_record")
    private CuttingOrderRecord cuttingOrderRecord;

    public CuttingOrderRecord getCuttingOrderRecord() {
        return cuttingOrderRecord;
    }

    public void setCuttingOrderRecord(CuttingOrderRecord cuttingOrderRecord) {
        this.cuttingOrderRecord = cuttingOrderRecord;
    }

    public double getMarkerYard() {
        return markerYard;
    }

    public void setMarkerYard(double markerYard) {
        this.markerYard = markerYard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoLayingSheet() {
        return noLayingSheet;
    }

    public void setNoLayingSheet(String noLayingSheet) {
        this.noLayingSheet = noLayingSheet;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getLayerQty() {
        return layerQty;
    }

    public void setLayerQty(int layerQty) {
        this.layerQty = layerQty;
    }

    public String getMarkerCode() {
        return markerCode;
    }

    public void setMarkerCode(String markerCode) {
        this.markerCode = markerCode;
    }

    public double getMarkerInch() {
        return markerInch;
    }

    public void setMarkerInch(double markerInch) {
        this.markerInch = markerInch;
    }

    public double getMarkerLength() {
        return markerLength;
    }

    public void setMarkerLength(double markerLength) {
        this.markerLength = markerLength;
    }

    public double getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(double totalLength) {
        this.totalLength = totalLength;
    }

    public int getTotalAllSize() {
        return totalAllSize;
    }

    public void setTotalAllSize(int totalAllSize) {
        this.totalAllSize = totalAllSize;
    }

    public LayingPlanning getLayingPlanning() {
        return layingPlanning;
    }

    public void setLayingPlanning(LayingPlanning layingPlanning) {
        this.layingPlanning = layingPlanning;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.noLayingSheet);
        dest.writeInt(this.tableNumber);
        dest.writeInt(this.layerQty);
        dest.writeString(this.markerCode);
        dest.writeDouble(this.markerYard);
        dest.writeDouble(this.markerInch);
        dest.writeDouble(this.markerLength);
        dest.writeDouble(this.totalLength);
        dest.writeInt(this.totalAllSize);
        dest.writeParcelable(this.layingPlanning, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.noLayingSheet = source.readString();
        this.tableNumber = source.readInt();
        this.layerQty = source.readInt();
        this.markerCode = source.readString();
        this.markerYard = source.readDouble();
        this.markerInch = source.readDouble();
        this.markerLength = source.readDouble();
        this.totalLength = source.readDouble();
        this.totalAllSize = source.readInt();
        this.layingPlanning = source.readParcelable(LayingPlanning.class.getClassLoader());
    }

    public LayingPlanningDetail() {
    }

    protected LayingPlanningDetail(Parcel in) {
        this.id = in.readInt();
        this.noLayingSheet = in.readString();
        this.tableNumber = in.readInt();
        this.layerQty = in.readInt();
        this.markerCode = in.readString();
        this.markerYard = in.readDouble();
        this.markerInch = in.readDouble();
        this.markerLength = in.readDouble();
        this.totalLength = in.readDouble();
        this.totalAllSize = in.readInt();
        this.layingPlanning = in.readParcelable(LayingPlanning.class.getClassLoader());
    }

    public static final Parcelable.Creator<LayingPlanningDetail> CREATOR = new Parcelable.Creator<LayingPlanningDetail>() {
        @Override
        public LayingPlanningDetail createFromParcel(Parcel source) {
            return new LayingPlanningDetail(source);
        }

        @Override
        public LayingPlanningDetail[] newArray(int size) {
            return new LayingPlanningDetail[size];
        }
    };
}

package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CuttingTicket {
    private int id;
    @SerializedName("ticket_number")
    private int ticketNumber;
    @SerializedName("serial_number")
    private String serialNumber;
    private Size size;
    private int layer;
    @SerializedName("cutting_order_record")
    private CuttingOrderRecord cuttingOrderRecord;
    @SerializedName("cutting_order_record_detail")
    private CuttingOrderRecordDetail cuttingOrderRecordDetail;
    @SerializedName("table_number")
    private int tableNo;
    @SerializedName("fabric_roll")
    private String fabricRoll;
    @SerializedName("bundle_cuts")
    private ArrayList<BundleCut> bundleCuts;

    public int getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ArrayList<BundleCut> getBundleCuts() {
        return bundleCuts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public CuttingOrderRecord getCuttingOrderRecord() {
        return cuttingOrderRecord;
    }

    public void setCuttingOrderRecord(CuttingOrderRecord cuttingOrderRecord) {
        this.cuttingOrderRecord = cuttingOrderRecord;
    }

    public CuttingOrderRecordDetail getCuttingOrderRecordDetail() {
        return cuttingOrderRecordDetail;
    }

    public void setCuttingOrderRecordDetail(CuttingOrderRecordDetail cuttingOrderRecordDetail) {
        this.cuttingOrderRecordDetail = cuttingOrderRecordDetail;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public String getFabricRoll() {
        return fabricRoll;
    }

    public void setFabricRoll(String fabricRoll) {
        this.fabricRoll = fabricRoll;
    }
}

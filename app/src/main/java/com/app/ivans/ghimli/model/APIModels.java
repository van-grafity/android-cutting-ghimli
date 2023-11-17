package com.app.ivans.ghimli.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class APIModels {
    private ArrayList<LayingPlanning> layingPlannings;
    private String token;
    private User user;
    @SerializedName("users")
    private ArrayList<User> users;
    @SerializedName("gls")
    private ArrayList<FGL> FGL;
    @SerializedName("colors")
    private ArrayList<Color> colors;
    @SerializedName("cutting_order_records")
    private ArrayList<CuttingOrderRecord> cuttingOrderRecords;
    @SerializedName("cutting_order_record")
    private CuttingOrderRecord cuttingOrderRecord;
    @SerializedName("cutting_order_record_details")
    private ArrayList<CuttingOrderRecord> cuttingOrderRecordDetails;
    @SerializedName("laying_planning_detail")
    private LayingPlanningDetail layingPlanningDetail;
    @SerializedName("cutting_record_remark")
    private ArrayList<CuttingRecordRemark> cuttingRecordRemarks;
    @SerializedName("cutting_ticket")
    private CuttingTicket cuttingTicket;

    @SerializedName("bundle_status")
    private ArrayList<BundleStatus> bundleStatus;
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("last_page")
    private int lastPage;
    @SerializedName("prev_page_url")
    private String prevPageUrl;
    @SerializedName("next_page_url")
    private String nextPageUrl;
    @SerializedName("total")
    private int totalPage;

    public CuttingOrderRecord getCuttingOrderRecord() {
        return cuttingOrderRecord;
    }

    public void setCuttingOrderRecord(CuttingOrderRecord cuttingOrderRecord) {
        this.cuttingOrderRecord = cuttingOrderRecord;
    }

    public CuttingTicket getCuttingTicket() {
        return cuttingTicket;
    }

    public void setCuttingTicket(CuttingTicket cuttingTicket) {
        this.cuttingTicket = cuttingTicket;
    }

    public ArrayList<CuttingOrderRecord> getCuttingOrderRecordDetails() {
        return cuttingOrderRecordDetails;
    }

    public void setCuttingOrderRecordDetails(ArrayList<CuttingOrderRecord> cuttingOrderRecordDetails) {
        this.cuttingOrderRecordDetails = cuttingOrderRecordDetails;
    }

    public ArrayList<CuttingOrderRecord> getCuttingOrderRecords() {
        return cuttingOrderRecords;
    }

    public void setCuttingOrderRecords(ArrayList<CuttingOrderRecord> cuttingOrderRecords) {
        this.cuttingOrderRecords = cuttingOrderRecords;
    }

    public ArrayList<CuttingRecordRemark> getCuttingRecordRemarks() {
        return cuttingRecordRemarks;
    }

    public void setCuttingRecordRemarks(ArrayList<CuttingRecordRemark> cuttingRecordRemarks) {
        this.cuttingRecordRemarks = cuttingRecordRemarks;
    }

    public ArrayList<BundleStatus> getBundleStatus() {
        return bundleStatus;
    }

    public LayingPlanningDetail getLayingPlanningDetail() {
        return layingPlanningDetail;
    }

    public void setLayingPlanningDetail(LayingPlanningDetail layingPlanningDetail) {
        this.layingPlanningDetail = layingPlanningDetail;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<FGL> getGl() {
        return FGL;
    }

    public void setGl(ArrayList<FGL> FGL) {
        this.FGL = FGL;
    }

    public ArrayList<LayingPlanning> getLayingPlannings() {
        return layingPlannings;
    }

    public void setLayingPlannings(ArrayList<LayingPlanning> layingPlannings) {
        this.layingPlannings = layingPlannings;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}

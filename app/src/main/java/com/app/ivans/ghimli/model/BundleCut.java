package com.app.ivans.ghimli.model;

import com.google.gson.annotations.SerializedName;

public class BundleCut {
    private int id;
    @SerializedName("ticket_id")
    private int ticketId;
    @SerializedName("status_id")
    private int statusId;
    @SerializedName("remarks")
    private String remark;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("cutting_ticket")
    private CuttingTicket cuttingTicket;
    @SerializedName("bundle_status")
    private BundleStatus bundleStatus;

    public int getId() {
        return id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getRemark() {
        return remark;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public CuttingTicket getCuttingTicket() {
        return cuttingTicket;
    }

    public BundleStatus getBundleStatus() {
        return bundleStatus;
    }
}

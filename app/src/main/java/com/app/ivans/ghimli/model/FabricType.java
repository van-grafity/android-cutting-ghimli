package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FabricType implements Parcelable {
    private int id;
    private String description;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.description = source.readString();
        this.name = source.readString();
    }

    public FabricType() {
    }

    protected FabricType(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.name = in.readString();
    }

    public static final Creator<FabricType> CREATOR = new Creator<FabricType>() {
        @Override
        public FabricType createFromParcel(Parcel source) {
            return new FabricType(source);
        }

        @Override
        public FabricType[] newArray(int size) {
            return new FabricType[size];
        }
    };
}

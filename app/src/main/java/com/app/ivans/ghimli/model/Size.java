package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Size implements Parcelable {
    private int id;
    private String size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.size);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.size = source.readString();
    }

    public Size() {
    }

    protected Size(Parcel in) {
        this.id = in.readInt();
        this.size = in.readString();
    }

    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel source) {
            return new Size(source);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };
}

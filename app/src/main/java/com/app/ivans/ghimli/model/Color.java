package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Color implements Parcelable {
    private int id;
    @SerializedName("color")
    private String name;
    @SerializedName("color_code")
    private String colorCode;

    public Color(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.colorCode);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.name = source.readString();
        this.colorCode = source.readString();
    }

    protected Color(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.colorCode = in.readString();
    }

    public static final Creator<Color> CREATOR = new Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel source) {
            return new Color(source);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };
}

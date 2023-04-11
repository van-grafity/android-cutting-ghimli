package com.app.ivans.ghimli.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Style implements Parcelable {
    private int id;
    private String style;
    private String description;
    private GL gl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GL getGl() {
        return gl;
    }

    public void setGl(GL gl) {
        this.gl = gl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.style);
        dest.writeString(this.description);
        dest.writeParcelable(this.gl, flags);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.style = source.readString();
        this.description = source.readString();
        this.gl = source.readParcelable(GL.class.getClassLoader());
    }

    public Style() {
    }

    protected Style(Parcel in) {
        this.id = in.readInt();
        this.style = in.readString();
        this.description = in.readString();
        this.gl = in.readParcelable(GL.class.getClassLoader());
    }

    public static final Creator<Style> CREATOR = new Creator<Style>() {
        @Override
        public Style createFromParcel(Parcel source) {
            return new Style(source);
        }

        @Override
        public Style[] newArray(int size) {
            return new Style[size];
        }
    };
}

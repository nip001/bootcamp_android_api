package com.juaracoding.haniffathullahujian4_ganjil.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variant implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("variant")
    @Expose
    private String variant;
    public final static Parcelable.Creator<Variant> CREATOR = new Creator<Variant>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Variant createFromParcel(Parcel in) {
            return new Variant(in);
        }

        public Variant[] newArray(int size) {
            return (new Variant[size]);
        }

    }
            ;
    private final static long serialVersionUID = 1608127539800620954L;

    protected Variant(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.variant = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Variant() {
    }

    /**
     *
     * @param variant
     * @param id
     */
    public Variant(String id, String variant) {
        super();
        this.id = id;
        this.variant = variant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(variant);
    }

    public int describeContents() {
        return 0;
    }

}
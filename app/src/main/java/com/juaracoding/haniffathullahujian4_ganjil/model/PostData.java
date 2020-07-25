
package com.juaracoding.haniffathullahujian4_ganjil.model;
import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostData implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Parcelable.Creator<PostData> CREATOR = new Creator<PostData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PostData createFromParcel(Parcel in) {
            return new PostData(in);
        }

        public PostData[] newArray(int size) {
            return (new PostData[size]);
        }

    }
            ;
    private final static long serialVersionUID = -5319998379220222564L;

    protected PostData(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public PostData() {
    }

    /**
     *
     * @param message
     * @param status
     */
    public PostData(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}
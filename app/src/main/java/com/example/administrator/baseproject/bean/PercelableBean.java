package com.example.administrator.baseproject.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/11/2.
 */

public class PercelableBean implements Parcelable {

    private int intVar;
    private String stringVar;

    public int getIntVar() {
        return intVar;
    }

    public void setIntVar(int intVar) {
        this.intVar = intVar;
    }

    public String getStringVar() {
        return stringVar;
    }

    public void setStringVar(String stringVar) {
        this.stringVar = stringVar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(intVar);
        parcel.writeString(stringVar);
    }

    public static final Parcelable.Creator<PercelableBean> CREATOR = new Creator<PercelableBean>() {
        @Override
        public PercelableBean createFromParcel(Parcel parcel) {
            PercelableBean bean = new PercelableBean();
            bean.intVar = parcel.readInt();
            bean.stringVar = parcel.readString();
            return bean;
        }

        @Override
        public PercelableBean[] newArray(int i) {
            return new PercelableBean[i];
        }
    };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("intVar");
        sb.append(":");
        sb.append(intVar);
        sb.append(",");
        sb.append("stringVar");
        sb.append(":");
        sb.append(stringVar);
        sb.append("}");
        return sb.toString();
    }
}

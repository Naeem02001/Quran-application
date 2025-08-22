package com.example.projectf;

import android.os.Parcel;
import android.os.Parcelable;

public class SurahItem implements Parcelable {
    private final int number;     // رقم تسلسلي
    private final String name;    // اسم السورة/المقطع
    private final int ayatCount;  // عدد الآيات

    private final String surahType;  // نوع الآيات
    private final int rawResId;   // R.raw.xxx

    public SurahItem(int number, String name, int ayatCount, String surahType, int rawResId) {
        this.number = number;
        this.name = name;
        this.ayatCount = ayatCount;
        this.surahType = surahType;
        this.rawResId = rawResId;
    }

    protected SurahItem(Parcel in) {
        number = in.readInt();
        name = in.readString();
        ayatCount = in.readInt();
        surahType = in.readString();
        rawResId = in.readInt();
    }

    public static final Creator<SurahItem> CREATOR = new Creator<SurahItem>() {
        @Override public SurahItem createFromParcel(Parcel in) { return new SurahItem(in); }
        @Override public SurahItem[] newArray(int size) { return new SurahItem[size]; }
    };

    public int getNumber()    { return number; }
    public String getName()   { return name; }
    public int getAyatCount() { return ayatCount; }

    public String getSurahType() { return surahType; }
    public int getRawResId()  { return rawResId; }

    @Override public int describeContents() { return 0; }
    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(name);
        dest.writeInt(ayatCount);
        dest.writeString(surahType);
        dest.writeInt(rawResId);
    }
}

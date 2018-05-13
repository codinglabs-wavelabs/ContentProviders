package com.example.android.contentproviders;


import android.os.Parcel;
import android.os.Parcelable;

public class ContactDetails implements Parcelable {

    String contactName;
    String phoneNumber;
    String id;


    public ContactDetails(String contactName, String phoneNumber, String id) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public ContactDetails(String contactName, String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }
    public ContactDetails(String contactName) {
        this.contactName = contactName;
    }

    public ContactDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    protected ContactDetails(Parcel in) {
        id = in.readString();
        contactName = in.readString();
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(contactName);
        dest.writeString(phoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactDetails> CREATOR = new Creator<ContactDetails>() {
        @Override
        public ContactDetails createFromParcel(Parcel in) {
            return new ContactDetails(in);
        }

        @Override
        public ContactDetails[] newArray(int size) {
            return new ContactDetails[size];
        }
    };
}

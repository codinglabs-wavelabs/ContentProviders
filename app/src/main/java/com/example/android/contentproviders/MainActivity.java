package com.example.android.contentproviders;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.s.contentproviders.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1010;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<ContactDetails> contactDetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rcView);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)||
                    ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_CONTACTS)) {


            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            readContactsFromContentProvider();
            loadContactsOnRecycler();

        } else {

        }
        return;
    }


    @Override
    protected void onStart() {
        contactDetailsList.clear();
        super.onStart();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED ) {
            readContactsFromContentProvider();
            loadContactsOnRecycler();
        }
    }

    private void loadContactsOnRecycler() {
        adapter = new com.example.s.contentproviders.RecyclerViewAdapter(contactDetailsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    protected void readContactsFromContentProvider() {
        String phoneNumber = null;
        int count=0;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,ContactsContract.Contacts._ID + " ASC");
        if (cursor.moveToFirst()) {
            do {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasPhNo>0) {
                    Cursor phNo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?",new String[]{contactId},null);
                    if(phNo.getCount()>0) {
                        if (phNo.moveToFirst()) {
                            do {
                                phoneNumber = phNo.getString(phNo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            } while (phNo.moveToNext());
                        }
                    }
                }

                ContactDetails contactDetails = new ContactDetails();
                contactDetails.setId(contactId);
                contactDetails.setContactName(displayName);
                contactDetails.setPhoneNumber(phoneNumber);
                contactDetailsList.add(contactDetails);
            } while (cursor.moveToNext());
        }
    }
}

package com.example.s.contentproviders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.contentproviders.ContactDetails;
import com.example.android.contentproviders.R;

import java.util.List;


public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ContactViewHolder> {

    public List<ContactDetails> contactDetails;

    public RecyclerViewAdapter(List<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thisItemsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new ContactViewHolder(thisItemsView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {

        final ContactDetails details = contactDetails.get(position);
        holder.contact_image.setImageResource(R.mipmap.download);
        holder.contact_name.setText(details.getContactName());
        holder.contact_number.setText(details.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactDetails.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public ImageView contact_image;
        public TextView contact_name;
        public TextView contact_number;

        public ContactViewHolder(View itemView) {
            super(itemView);

            contact_image = itemView.findViewById(R.id.iv_contactImage);
            contact_name = itemView.findViewById(R.id.tv_contactName);
            contact_number = itemView.findViewById(R.id.tv_contactNumber);

        }
    }
}

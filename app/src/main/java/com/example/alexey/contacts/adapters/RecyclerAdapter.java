package com.example.alexey.contacts.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alexey.contacts.R;
import com.example.alexey.contacts.data.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 22.03.2016.
 * Adapter for RecyclerView in NotesActivity.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Contact> mContacts;

    public RecyclerAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // Create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(v);
    }

    /**
     * Set element from mContacts at this position of RecyclerView
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int p) {
        int position = holder.getAdapterPosition();


        holder.tvName.
                setText(mContacts.get(position).getFirstName() + " " + mContacts.get(position).getLastName());

        holder.tvPhone.
                setText(mContacts.get(position).getPhone());

        holder.tvEmail
                .setText(mContacts.get(position).getEmail());
    }


    /**
     * Return the counts of items
     */
    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_phone)
        TextView tvPhone;

        @BindView(R.id.tv_email)
        TextView tvEmail;

        ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);
        }
    }
}
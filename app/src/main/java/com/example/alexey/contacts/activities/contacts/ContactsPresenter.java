package com.example.alexey.contacts.activities.contacts;

import android.support.v7.widget.RecyclerView;

import com.example.alexey.contacts.data.Contact;

import java.util.List;

/**
 * Created by alexey on 02/03/17.
 */

public class ContactsPresenter implements ContactsRelations.Presenter {

    private ContactsRelations.View mView;

    ContactsPresenter(ContactsRelations.View view) {
        mView = view;
    }

    @Override
    public void configure() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public RecyclerView.Adapter loadAdapter(List<Contact> notes) {
        return null;
    }
}

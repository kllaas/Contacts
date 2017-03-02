package com.example.alexey.contacts.activities.contacts;

import android.support.v7.widget.RecyclerView;

import com.example.alexey.contacts.activities.BaseRelations;
import com.example.alexey.contacts.data.Contact;

import java.util.List;

/**
 * Created by alexey on 01/03/17.
 */

public interface ContactsRelations {

    interface Presenter extends BaseRelations.Presenter {

        void loadData();

        RecyclerView.Adapter loadAdapter(List<Contact> notes);

        void setCurrentSortType(ContactsSortType type);

        void showCreateFragment();
    }

    interface View extends BaseRelations.View {

        void setEmptyListMessage(boolean visible);

        void refreshData(List<Contact> contacts);

    }

    interface OnCompleteListener {
        void onContactCreated();
    }

}

package com.example.alexey.contacts.data.source;

import com.example.alexey.contacts.activities.contacts.ContactsSortType;
import com.example.alexey.contacts.data.Contact;

import java.util.List;

import rx.Observable;

/**
 * Created by alexey on 02/03/17.
 */
public interface ContactsDataSource {

    Observable<List<Contact>> getContacts(ContactsSortType mCurrentSortType);

    void saveContact(Contact note);

}

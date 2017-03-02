/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.alexey.contacts.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.alexey.contacts.activities.contacts.ContactsSortType;
import com.example.alexey.contacts.data.Contact;
import com.example.alexey.contacts.data.source.ContactsDataSource;
import com.example.alexey.contacts.data.source.local.ContactPersistenceContract.ContactEntry;
import com.example.alexey.contacts.utils.PreferencesUtils;
import com.example.alexey.contacts.utils.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class ContactsLocalDataSource implements ContactsDataSource {

    private static ContactsLocalDataSource INSTANCE;

    private final BriteDatabase mDatabaseHelper;

    private Func1<Cursor, Contact> mContactMapperFunction;


    private ContactsLocalDataSource(@NonNull Context context, @NonNull BaseSchedulerProvider schedulerProvider) {
        ContactsDbHelper mDbHelper = new ContactsDbHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(mDbHelper, schedulerProvider.io());
        mContactMapperFunction = this::getContacts;
    }

    public static ContactsLocalDataSource getInstance(@NonNull Context context, @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new ContactsLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Contact>> getContacts(ContactsSortType type, Context context) {

        return getNotesFromDB(type, context).first();
    }

    private Observable<List<Contact>> getNotesFromDB(ContactsSortType type, Context context) {
        String[] projection = {
                ContactEntry.COLUMN_NAME_ENTRY_ID,
                ContactEntry.FIRST_NAME_COLUMN,
                ContactEntry.LAST_NAME_COLUMN,
                ContactEntry.EMAIL_COLUMN,
                ContactEntry.PHONE_COLUMN,
                ContactEntry.USER_ID_COLUMN,
        };

        String sql = getSqlRequest(type, context, projection);

        return mDatabaseHelper.createQuery(ContactEntry.TABLE_NAME, sql)
                .mapToList(mContactMapperFunction);
    }

    private Contact getContacts(Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(ContactEntry.COLUMN_NAME_ENTRY_ID));

        String f_name = c.getString(c.getColumnIndexOrThrow(ContactEntry.FIRST_NAME_COLUMN));
        String l_name =
                c.getString(c.getColumnIndexOrThrow(ContactEntry.LAST_NAME_COLUMN));
        String email =
                c.getString(c.getColumnIndexOrThrow(ContactEntry.EMAIL_COLUMN));
        String phone =
                c.getString(c.getColumnIndexOrThrow(ContactEntry.PHONE_COLUMN));
        String userId =
                c.getString(c.getColumnIndexOrThrow(ContactEntry.USER_ID_COLUMN));


        return new Contact(itemId, f_name, l_name, phone, email, userId);
    }

    private String getSqlRequest(ContactsSortType type, Context context, String[] projection) {
        String order = "";

        switch (type) {
            case BY_ALPHABET:
                order = " order by " + ContactEntry.FIRST_NAME_COLUMN;
                break;
            case BY_ALPHABET_DESC:
                order = " order by " + ContactEntry.FIRST_NAME_COLUMN + " DESC";
                break;
        }

        return String.format("SELECT %s FROM %s WHERE %s = '%s'" + order,
                TextUtils.join(",", projection),
                ContactEntry.TABLE_NAME,
                ContactEntry.USER_ID_COLUMN,
                PreferencesUtils.getData(PreferencesUtils.USER_ID, context));
    }

    @Override
    public void saveContact(Contact contact, Context context) {
        ContentValues values = new ContentValues();
        values.put(ContactEntry.COLUMN_NAME_ENTRY_ID, contact.getId());
        values.put(ContactEntry.FIRST_NAME_COLUMN, contact.getFirstName());
        values.put(ContactEntry.LAST_NAME_COLUMN, contact.getLastName());
        values.put(ContactEntry.EMAIL_COLUMN, contact.getEmail());
        values.put(ContactEntry.PHONE_COLUMN, contact.getPhone());
        values.put(ContactEntry.USER_ID_COLUMN, PreferencesUtils.getData(PreferencesUtils.USER_ID, context));

        mDatabaseHelper.insert(ContactEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);

    }
}

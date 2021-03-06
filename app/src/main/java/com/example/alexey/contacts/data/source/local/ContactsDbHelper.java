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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "contacts.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactPersistenceContract.ContactEntry.TABLE_NAME + " (" +
                    ContactPersistenceContract.ContactEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    ContactPersistenceContract.ContactEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    ContactPersistenceContract.ContactEntry.FIRST_NAME_COLUMN + TEXT_TYPE + COMMA_SEP +
                    ContactPersistenceContract.ContactEntry.LAST_NAME_COLUMN + TEXT_TYPE + COMMA_SEP +
                    ContactPersistenceContract.ContactEntry.EMAIL_COLUMN + TEXT_TYPE + COMMA_SEP +
                    ContactPersistenceContract.ContactEntry.USER_ID_COLUMN + TEXT_TYPE + COMMA_SEP +
                    ContactPersistenceContract.ContactEntry.PHONE_COLUMN + TEXT_TYPE +
                    " )";

    public ContactsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}

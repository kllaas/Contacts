package com.example.alexey.contacts.activities.contacts;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.alexey.contacts.R;
import com.example.alexey.contacts.activities.BaseActivity;
import com.example.alexey.contacts.data.Contact;
import com.example.alexey.contacts.utils.schedulers.SchedulerProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends BaseActivity implements ContactsRelations.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_data_container)
    View mNoDataView;
    private ContactsRelations.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mPresenter = new ContactsPresenter(this, SchedulerProvider.getInstance());
        mPresenter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public void setEmptyListMessage(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mNoDataView.setVisibility(visibility);
    }

    @Override
    public void refreshData(List<Contact> contacts) {
        RecyclerView.Adapter mAdapter = mPresenter.loadAdapter(contacts);
        mRecyclerView.setAdapter(mAdapter);
    }

}

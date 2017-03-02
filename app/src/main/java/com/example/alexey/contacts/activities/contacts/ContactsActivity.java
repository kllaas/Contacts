package com.example.alexey.contacts.activities.contacts;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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
    public void setEmptyListMessage(boolean visible) {
        int visibility = visible ? View.VISIBLE : View.GONE;
        mNoDataView.setVisibility(visibility);
    }

    @Override
    public void refreshData(List<Contact> contacts) {
        RecyclerView.Adapter mAdapter = mPresenter.loadAdapter(contacts);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                createSortDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_sort_title)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                .setItems(R.array.spinner_list_item_array, (dialog, which) -> {

                    SORT_TYPE type = SORT_TYPE.WITHOUT_SORT;

                    switch (which) {
                        case 0:
                            type = SORT_TYPE.WITHOUT_SORT;
                            break;
                        case 1:
                            type = SORT_TYPE.BY_ALPHABET;
                            break;
                        case 2:
                            type = SORT_TYPE.BY_ALPHABET_DESC;
                            break;
                    }

                    mPresenter.setCurrentSortType(type);
                });

        builder.create().show();
    }
}

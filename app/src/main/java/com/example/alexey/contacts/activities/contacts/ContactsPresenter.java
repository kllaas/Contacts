package com.example.alexey.contacts.activities.contacts;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;

import com.example.alexey.contacts.activities.add_contact.CreateContactDialog;
import com.example.alexey.contacts.adapters.RecyclerAdapter;
import com.example.alexey.contacts.data.Contact;
import com.example.alexey.contacts.data.source.local.ContactsLocalDataSource;
import com.example.alexey.contacts.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by alexey on 02/03/17.
 */

public class ContactsPresenter implements ContactsRelations.Presenter {

    private ContactsRelations.View mView;

    private BaseSchedulerProvider mSchedulerProvider;

    private ContactsLocalDataSource mNotesRepository;

    private CompositeSubscription mSubscriptions;

    private ContactsSortType mCurrentSortType = ContactsSortType.WITHOUT_SORT;

    ContactsPresenter(ContactsRelations.View view, BaseSchedulerProvider schedulerProvider) {
        mView = view;

        mNotesRepository =
                ContactsLocalDataSource.getInstance(mView.getActivity(), schedulerProvider);
        mSchedulerProvider = schedulerProvider;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void configure() {

    }

    @Override
    public void loadData() {
        mView.setLoadingIndicator(true);

        mSubscriptions.clear();
        Subscription subscription = mNotesRepository
                .getContacts(mCurrentSortType, mView.getActivity())
                .flatMap(new Func1<List<Contact>, Observable<Contact>>() {
                    @Override
                    public Observable<Contact> call(List<Contact> contacts) {
                        return Observable.from(contacts);
                    }
                })
                .toList()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        // onNext
                        this::processResult,
                        // onError
                        throwable -> mView.setEmptyListMessage(true),
                        // onCompleted
                        () -> mView.setLoadingIndicator(false));
        mSubscriptions.add(subscription);
    }

    private void processResult(List<Contact> contacts) {
        mView.setEmptyListMessage(contacts.isEmpty());

        if (!contacts.isEmpty())
            mView.refreshData(contacts);
    }

    @Override
    public void setCurrentSortType(ContactsSortType type) {
        mCurrentSortType = type;
        loadData();
    }

    @Override
    public void showCreateFragment() {
        DialogFragment newFragment = CreateContactDialog.newInstance();
        newFragment.show(mView.getActivity().getSupportFragmentManager(), "dialog");
    }

    @Override
    public RecyclerView.Adapter loadAdapter(List<Contact> notes) {
        return new RecyclerAdapter(notes);
    }

}

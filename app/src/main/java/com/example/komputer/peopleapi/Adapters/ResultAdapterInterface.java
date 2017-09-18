package com.example.komputer.peopleapi.Adapters;

import com.example.komputer.peopleapi.Model.Result;

import java.util.List;

/**
 * Created by komputer on 2017-08-25.
 */

public interface ResultAdapterInterface {
    List<Result> getCopyList ();
    void onCardPressed (Result person);
    void notifyFavoriteItemOnCopy (Result person);
}

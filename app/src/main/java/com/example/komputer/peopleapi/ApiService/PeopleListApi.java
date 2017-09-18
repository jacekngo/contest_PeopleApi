package com.example.komputer.peopleapi.ApiService;

import com.example.komputer.peopleapi.Model.PeopleList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by komputer on 2017-08-23.
 */
//   https://randomuser.me/api/?results=5&inc=name,picture,email,call&noinfo
public interface PeopleListApi {

    @GET("?results=20&inc=name,picture,email,call&noinfo")
    Observable<PeopleList> getPeopleRxJava();

}

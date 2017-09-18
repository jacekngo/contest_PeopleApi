package com.example.komputer.peopleapi.Fragments;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.komputer.peopleapi.Model.Result;
import com.example.komputer.peopleapi.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.drawable.btn_star_big_on;


public class DetailPersonFragment extends DialogFragment {
    private static final int PICTTURE_SIZE = 120;
    private Result person;
    @BindView(R.id.detail_dialog_image)
    ImageView personImage;
    @BindView(R.id.detail_dialog_star)
    ImageView favoriteStar;
    @BindView(R.id.detail_dialog_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.detail_dialog_first_name)
    TextView firstName;
    @BindView(R.id.detail_dialog_last_name)
    TextView lastName;
    @BindView(R.id.detail_dialog_email)
    TextView email;


    public static DetailPersonFragment newInstance(Result person) {
        DetailPersonFragment fragment = new DetailPersonFragment();
        fragment.setPerson(person);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_person, container, false);
        ButterKnife.bind(this, view);
        firstName.setText(person.getName().getFirst());
        lastName.setText(person.getName().getLast());
        email.setText(person.getEmail());
        if(person.isFavorite()){favoriteStar.setImageResource(btn_star_big_on);}
        Context context = view.getContext();
        Picasso.with(context)
                .load(person.getPicture().getLarge())
                .resize(PICTTURE_SIZE, PICTTURE_SIZE)
                .centerCrop()
                .into(personImage);
        progressBar.setVisibility(View.GONE);
        return view;
    }



    public Result getPerson() {
        return person;
    }

    public void setPerson(Result person) {
        this.person = person;
    }

}

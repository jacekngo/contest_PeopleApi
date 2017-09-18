package com.example.komputer.peopleapi.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.komputer.peopleapi.Model.Result;
import com.example.komputer.peopleapi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.drawable.btn_star_big_off;
import static android.R.drawable.btn_star_big_on;

/**
 * Created by komputer on 2017-08-24.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<Result> peopleList;
    private List<Result> copyPeopleList;
    private ResultAdapterInterface mainInterface;
    final private int PICTTURE_SIZE = 48;

    public ResultAdapter(List<Result> peopleList) {
        this.peopleList = peopleList;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        mainInterface = (ResultAdapterInterface) parent.getContext();
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ResultViewHolder holder, final int position) {
        final Result person = peopleList.get(holder.getAdapterPosition());
        holder.firstname.setText(person.getName().getFirst());
        holder.lastname.setText(person.getName().getLast());

        holder.favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!person.isFavorite()) {
                    person.setFavorite(true);
                    // tak wiem, to słabe  ...
                    mainInterface.notifyFavoriteItemOnCopy(person);
                } else {
                    person.setFavorite(false);
                    mainInterface.notifyFavoriteItemOnCopy(person);
                }
                ResultAdapter.super.notifyItemChanged(holder.getAdapterPosition());
            }
        });

        if (person.isFavorite()) {
            holder.favImage.setImageResource(btn_star_big_on);
        } else {
            holder.favImage.setImageResource(btn_star_big_off);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainInterface.onCardPressed(person);
            }
        });

        Context context = holder.smallPicture.getContext();
        Picasso.with(context)
                .load(person.getPicture().getThumbnail())
                .resize(PICTTURE_SIZE, PICTTURE_SIZE)
                .centerCrop()
                .into(holder.smallPicture);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public void filter(String query) {
        copyPeopleList = new ArrayList<>(mainInterface.getCopyList());
        peopleList.clear();
        if (query.isEmpty()) {
            peopleList.addAll(copyPeopleList);
        } else {
            query = query.toLowerCase();
            for (Result person : copyPeopleList) {
                if (person.getName().getFirst().toLowerCase().contains(query) || person.getName().getLast().toLowerCase().contains(query)) {
                    peopleList.add(person);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_picture)
        ImageView smallPicture;
        @BindView(R.id.row_firstName)
        TextView firstname;
        @BindView(R.id.row_lastName)
        TextView lastname;
        @BindView(R.id.row_iconFav)
        ImageView favImage;
        @BindView(R.id.row_card)
        CardView cardView;


        public ResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

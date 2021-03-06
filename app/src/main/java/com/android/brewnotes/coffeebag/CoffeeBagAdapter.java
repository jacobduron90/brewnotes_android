package com.android.brewnotes.coffeebag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.brewnotes.R;
import com.android.brewnotes.servicelayer.CoffeeBag;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobduron on 9/5/16.
 */
public class CoffeeBagAdapter extends RecyclerView.Adapter<CoffeeBagAdapter.CoffeeBagViewHolder> {

    private List<CoffeeBag> coffeeBagList = new ArrayList<>();

    public interface BagClickListener{
        void onBagClicked(CoffeeBag bag);
    }

    private BagClickListener clickListener;


    public void addCoffeeBags(List<CoffeeBag> bags){
        coffeeBagList.clear();
        coffeeBagList.addAll(bags);
        notifyDataSetChanged();
    }

    public void setClickListener(BagClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public CoffeeBagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coffee_bags, parent, false);
        return new CoffeeBagViewHolder(row);
    }

    @Override
    public void onBindViewHolder(CoffeeBagViewHolder holder, final int position) {
        CoffeeBag bag = coffeeBagList.get(position);
        holder.bagName.setText(bag.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onBagClicked(coffeeBagList.get(position));
            }
        });
        if(bag.photo.iconPhoto != null){
            Glide.with(holder.itemView.getContext())
                    .load(bag.photo.iconPhoto)
                    .into(holder.bagPhoto);
        }

    }

    @Override
    public int getItemCount() {
        return coffeeBagList.size();
    }

    public static class CoffeeBagViewHolder extends RecyclerView.ViewHolder{
        public TextView bagName;
        public ImageView bagPhoto;
        public CoffeeBagViewHolder(View itemView) {
            super(itemView);
            bagName = (TextView)itemView.findViewById(R.id.bag_name);
        }
    }
}

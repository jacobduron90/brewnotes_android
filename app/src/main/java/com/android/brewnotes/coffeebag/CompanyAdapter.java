package com.android.brewnotes.coffeebag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.brewnotes.R;
import com.android.brewnotes.servicelayer.CoffeeCompany;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacobduron on 9/11/16.
 */
public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewholder> {


    private List<CoffeeCompany> companies = new ArrayList<>();
    public interface CompanyListener{
        void onCompanyClicked(CoffeeCompany company);
    }

    private CompanyListener companyListener;

    public void setCompanyListener(CompanyListener companyListener) {
        this.companyListener = companyListener;
    }

    @Override
    public CompanyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View companyRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coffee_bags, parent, false);
        return new CompanyViewholder(companyRow);
    }

    @Override
    public void onBindViewHolder(CompanyViewholder holder, int position) {
        final CoffeeCompany company = companies.get(position);
        holder.companyName.setText(company.name);
        Glide.with(holder.itemView.getContext())
                .load(company.photo.logo)
                .into(holder.companyLogo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyListener.onCompanyClicked(company);
            }
        });

    }

    public void addCompanies(List<CoffeeCompany> coffeeCompanies){
        companies.clear();
        companies.addAll(coffeeCompanies);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public class CompanyViewholder extends RecyclerView.ViewHolder{
        TextView companyName;
        ImageView companyLogo;

        public CompanyViewholder(View itemView) {
            super(itemView);
            companyName = (TextView)itemView.findViewById(R.id.bag_name);
            companyLogo = (ImageView)itemView.findViewById(R.id.company_logo);
        }
    }
}

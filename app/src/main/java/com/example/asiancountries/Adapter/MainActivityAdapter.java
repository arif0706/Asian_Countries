package com.example.asiancountries.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asiancountries.Model.Country;
import com.example.asiancountries.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ListItemHolder> implements Filterable {


    View view;

    Context context;
    List<Country> countryList;
    List<Country> countryListAll;

    ListItemClickListener listItemClickListener;

    public MainActivityAdapter(Context context, List<Country> countryList, ListItemClickListener listItemClickListener) {
        this.context = context;
        this.countryList = countryList;
        this.listItemClickListener = listItemClickListener;

        this.countryListAll=new ArrayList<>(countryList);
    }

    @NonNull
    @NotNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.country_list_item,parent,false);
        return new ListItemHolder(view,listItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListItemHolder holder, int position) {
        Country country = countryList.get(position);

        holder.country_name.setText(country.getName());
        GlideToVectorYou.init()
                .with(context)
                .load(Uri.parse(country.getFlag()),holder.imageView);

    }


    @Override
    public int getItemCount() {
        return countryList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Country> filteredList=new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filteredList.addAll(countryListAll);
            }
            else{
                for(Country country:countryListAll){
                    if(country.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(country);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                countryList.clear();
                countryList.addAll((Collection<? extends Country>) results.values);
                notifyDataSetChanged();
        }
    };


    public class ListItemHolder extends RecyclerView.ViewHolder {

        ListItemClickListener listItemClickListener;

        ImageView imageView;
        TextView country_name;

        public ListItemHolder(@NonNull @NotNull View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            this.listItemClickListener = listItemClickListener;

            imageView = itemView.findViewById(R.id.flag);
            country_name = itemView.findViewById(R.id.country_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItemClickListener.onItemClick(countryList.get(getAbsoluteAdapterPosition()));
                }
            });
        }
    }
    public interface ListItemClickListener{
        void onItemClick(Country country);
    }
}

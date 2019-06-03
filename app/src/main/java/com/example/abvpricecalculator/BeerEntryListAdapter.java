package com.example.abvpricecalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BeerEntryListAdapter extends RecyclerView.Adapter<BeerEntryListAdapter.BeerEntryViewHolder> {

    class BeerEntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView beerEntryName;

        private BeerEntryViewHolder(View itemView) {
            super(itemView);
            beerEntryName = itemView.findViewById(R.id.beer_name);
        }
    }

    private final LayoutInflater layoutInflater;
    private List<BeerEntry> entries; // Cached copy

    BeerEntryListAdapter (Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BeerEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.beer_entry_recycler_item, parent, false);
        return new BeerEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerEntryViewHolder holder, int position) {
        if (entries != null) {
            BeerEntry current = entries.get(position);
            holder.beerEntryName.setText(current.getName());
        } else {
            holder.beerEntryName.setText("No Hay Nada");
        }
    }

    void setEntries(List<BeerEntry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (entries != null) {
            return entries.size();
        }
        return 0;
    }
}

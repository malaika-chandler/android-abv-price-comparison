package com.example.abvpricecalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasurbas.listitemview.ListItemView;

import java.util.List;

public class BeerEntryListAdapter extends RecyclerView.Adapter<BeerEntryListAdapter.BeerEntryViewHolder> {

    class BeerEntryViewHolder extends RecyclerView.ViewHolder {
        private final ListItemView beerItem;

        private BeerEntryViewHolder(View itemView) {
            super(itemView);
            beerItem = itemView.findViewById(R.id.beer_item_view);
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
            holder.beerItem.setTitle(current.getName());
            holder.beerItem.setSubtitle("$" + current.getPrice() + " for "
                    + current.getVolume() + " "
                    + current.getVolumeUnits() + " at "
                    + current.getAbv() + "%");
        } else {
            holder.beerItem.setTitle(R.string.empty_entry_set);
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

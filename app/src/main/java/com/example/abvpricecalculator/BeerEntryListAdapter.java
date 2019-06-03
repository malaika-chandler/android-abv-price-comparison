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
            beerEntryName = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater layoutInflator;
    private List<BeerEntry> entries; // Cached copy

    BeerEntryListAdapter (Context context) {
        layoutInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BeerEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
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

    @Override
    public int getItemCount() {
        if (entries != null) {
            return entries.size();
        }
        return 0;
    }
}

package com.example.abvpricecalculator;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
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

    private Activity mActivity;
    private AdapterDatabaseCallbacksInterface adapterDatabaseCallbacksInterface;
    private BeerEntry mRecentlyDeletedEntry;
    private int mRecentlyDeletedEntryPosition;

    BeerEntryListAdapter (Context context, Activity mActivity, AdapterDatabaseCallbacksInterface adapterDatabaseCallbacksInterface) {
        layoutInflater = LayoutInflater.from(context);
        this.mActivity = mActivity;
        this.adapterDatabaseCallbacksInterface = adapterDatabaseCallbacksInterface;
    }

    @NonNull
    @Override
    public BeerEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.beer_entry_recycler_item, parent, false);
        return new BeerEntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerEntryViewHolder holder, final int position) {
        if (entries != null) {
            BeerEntry current = entries.get(position);
            holder.beerItem.setTitle(current.getName());
            holder.beerItem.setSubtitle("$" + current.getPrice() + " for "
                    + current.getVolume() + " "
                    + current.getVolumeUnits() + " at "
                    + current.getAbv() + "%");
            holder.beerItem.setOnMenuItemClickListener(new ListItemView.OnMenuItemClickListener() {
                @Override
                public void onActionMenuItemSelected(MenuItem item) {
                    if (item.getItemId() == R.id.single_item_menu_action_delete) {
                        deleteEntry(position);
                    }
                }
            });
        } else {
            holder.beerItem.setTitle(R.string.empty_entry_set);
        }
    }

    private void deleteEntry(int position) {
        mRecentlyDeletedEntry = entries.get(position);
        mRecentlyDeletedEntryPosition = position;

        entries.remove(position);
        notifyItemRemoved(position);

        adapterDatabaseCallbacksInterface.deleteEntries(mRecentlyDeletedEntry);

        showUndoSnackBar();
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

    private void showUndoSnackBar() {
        View view = mActivity.findViewById(R.id.coordinator_layout);
        Snackbar snackbar = Snackbar.make(view, R.string.undo_snackbar_text, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo_snackbar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeerEntryListAdapter.this.undoDelete();
            }
        }).show();
    }

    private void undoDelete() {
        entries.add(mRecentlyDeletedEntryPosition, mRecentlyDeletedEntry);
        adapterDatabaseCallbacksInterface.reAddEntries(mRecentlyDeletedEntry);
        notifyItemInserted(mRecentlyDeletedEntryPosition);
    }
}

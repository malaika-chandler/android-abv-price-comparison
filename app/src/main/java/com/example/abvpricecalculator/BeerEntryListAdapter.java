package com.example.abvpricecalculator;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.List;

public class BeerEntryListAdapter extends RecyclerView.Adapter<BeerEntryListAdapter.BeerEntryViewHolder> {

    class BeerEntryViewHolder extends RecyclerView.ViewHolder {

        private final TextView beerEntryNameTextView;
        private final TextView beerEntrySubtitleTextView;
        private final ImageButton beerEntryDeleteButton;

        private BeerEntryViewHolder(View itemView) {
            super(itemView);

            beerEntryNameTextView = itemView.findViewById(R.id.beer_name_text_view);
            beerEntrySubtitleTextView = itemView.findViewById(R.id.beer_subtitle_text_view);
            beerEntryDeleteButton = itemView.findViewById(R.id.beer_entry_delete_button);
        }
    }

    private Resources res;

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
        res = context.getResources();
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
            holder.beerEntryNameTextView.setText(current.getName());

            // TODO: 2019-06-07 Write a cleaner way to print out the details of the drink
            DecimalFormat df = new DecimalFormat("#.##");
            DecimalFormat mf = new DecimalFormat("#.00");

            holder.beerEntrySubtitleTextView.setText(res.getString(
                    R.string.beer_list_view_subtitle_placeholder,
                    mf.format(current.getPrice()),
                    df.format(current.getVolume()),
                    current.getVolumeUnits().toString(),
                    df.format(current.getAbv())));

            holder.beerEntryDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteEntry(position);
                }
            });
        } else {
            holder.beerEntryNameTextView.setText(R.string.empty_entry_set);
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

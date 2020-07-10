package com.example.measure.features.agenda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.measure.R;

import java.util.List;

/**
 * Adapts agenda items for display in a recycler view.
 */
public class AgendaItemAdapter
        extends RecyclerView.Adapter<AgendaItemAdapter.AgendaItemViewHolder> {
    private List<AgendaItem> agendaItems;

    /**
     * Descibes an agenda item view and metadata about its place within the
     * recyclerview.
     */
    public static class AgendaItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView timeText;

        /**
         * Initialize member variables.
         *
         * @param v view to represent the agenda item
         */
        public AgendaItemViewHolder(View v) {
            super(v);
            nameText = v.findViewById(R.id.agenda_item_row_name);
            timeText = v.findViewById(R.id.agenda_item_row_time);
        }
    }

    /**
     * Initialize member variables.
     *
     * @param agendaItems items to be displayed in the agenda
     */
    public AgendaItemAdapter(List<AgendaItem> agendaItems) {
        this.agendaItems = agendaItems;
    }

    /**
     * Create view to represent an item.
     *
     * @param parent   viewgroup to add the new view to after it's bound to an
     *                 adapter position
     * @param viewType view type of the new view
     * @return
     */
    @NonNull
    @Override
    public AgendaItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_agenda_item, parent, false);
        AgendaItemViewHolder vh = new AgendaItemViewHolder(v);
        return vh;
    }

    /**
     * Update the display of the item at the given position.
     *
     * @param holder   viewholder to be updated for item's display
     * @param position index of item within the data set
     */
    @Override
    public void onBindViewHolder(@NonNull AgendaItemViewHolder holder,
                                 int position) {
        holder.nameText.setText(agendaItems.get(position).getName());
        holder.timeText.setText(agendaItems.get(position).getTimeWorked());
    }

    /**
     * Return the total number of items.
     *
     * @return amount of items
     */
    @Override
    public int getItemCount() {
        return agendaItems.size();
    }

    public void setAgendaItems(List<AgendaItem> agendaItems) {
        this.agendaItems = agendaItems;
    }
}

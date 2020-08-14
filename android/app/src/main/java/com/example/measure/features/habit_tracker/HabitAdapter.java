package com.example.measure.features.habit_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.measure.R;
import com.example.measure.models.data.Habit;

import java.util.List;

public class HabitAdapter
        extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {
    private List<Habit> habits;

    /**
     * Describes a habit view and metadata about its place within the
     * recyclerview.
     */
    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;

        /**
         * Initialize member variables.
         *
         * @param v view to represent the agenda item
         */
        public HabitViewHolder(View v) {
            super(v);
            nameText = v.findViewById(R.id.habit_row_name);
        }
    }

    /**
     * Initialize member variables.
     *
     * @param habits habits to be displayed in the habit tracker
     */
    public HabitAdapter(List<Habit> habits) {
        this.habits = habits;
    }

    /**
     * Create view to represent an item.
     *
     * @param parent   viewgroup to add the new view to after it's bound to an
     *                 adapter position
     * @param viewType view type of the new view
     * @return view holder for the habit view
     */
    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_habit, parent, false);
        HabitViewHolder vh = new HabitViewHolder(v);
        return vh;
    }

    /**
     * Update the display of the item at the given position.
     *
     * @param holder   viewholder to be updated for item's display
     * @param position index of item within the data set
     */
    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder,
                                 int position) {
        holder.nameText.setText(habits.get(position).getName());
    }

    /**
     * Return the total number of items.
     *
     * @return amount of items
     */
    @Override
    public int getItemCount() {
        return habits.size();
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }
}

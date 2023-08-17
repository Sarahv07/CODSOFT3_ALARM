package com.sneha.clock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.ViewHolder> {

    private List<Alarm> alarms;

    public AlarmListAdapter(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SwitchMaterial alarmToggleSwitch;
        private final TextView alarmTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            alarmToggleSwitch = itemView.findViewById(R.id.alarmToggleSwitch);
            alarmTimeTextView = itemView.findViewById(R.id.alarmTimeTextView);

            alarmToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Alarm alarm = alarms.get(position);
                        alarm.setEnabled(isChecked);
                        // Perform actions to update alarm's enabled status
                    }
                }
            });
        }

        public void bind(Alarm alarm) {
            alarmToggleSwitch.setChecked(alarm.isEnabled());
            alarmTimeTextView.setText(alarm.getTime());
            // Bind other alarm data if needed
        }
    }
}

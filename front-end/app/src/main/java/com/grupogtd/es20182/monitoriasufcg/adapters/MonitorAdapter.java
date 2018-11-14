package com.grupogtd.es20182.monitoriasufcg.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grupogtd.es20182.monitoriasufcg.R;
import com.grupogtd.es20182.monitoriasufcg.service.domain.User;

import java.util.ArrayList;

public class MonitorAdapter extends RecyclerView.Adapter<MonitorAdapter.MonitorViewHolder> {

    private Context mContext;
    private ArrayList<User> monitorsList;

    public MonitorAdapter(Context mContext, ArrayList<User> monitorsList) {
        this.mContext = mContext;
        this.monitorsList = monitorsList;
    }

    @NonNull
    @Override
    public MonitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_item, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new MonitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MonitorViewHolder holder, final int position) {
        User currentUser = monitorsList.get(position);

        holder.monitorName.setText(currentUser.getName());
        holder.monitorEmail.setText(currentUser.getEmail());
    }

    @Override
    public int getItemCount() {
        return monitorsList.size();
    }

    class MonitorViewHolder extends RecyclerView.ViewHolder {

        TextView monitorName;
        TextView monitorEmail;

        public MonitorViewHolder(View itemView) {
            super(itemView);

            monitorName = itemView.findViewById(R.id.monitor_name);
            monitorEmail = itemView.findViewById(R.id.monitor_email);
        }
    }
}

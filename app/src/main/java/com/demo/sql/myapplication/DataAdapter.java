package com.demo.sql.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<DataItem> dataItems;

    public DataAdapter(List<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem dataItem = dataItems.get(position);

        // Set data to the TextViews in each row
        holder.txtRfid.setText(String.valueOf(dataItem.getRfid()));
        holder.txtProductType.setText(dataItem.getProductType());
        holder.txtRecyclable.setText(dataItem.getRecyclable());
        holder.txtPercentage.setText(String.valueOf(dataItem.getPercentage()));
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtRfid, txtProductType, txtRecyclable, txtPercentage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize TextViews for each column
            txtRfid = itemView.findViewById(R.id.txtRfid);
            txtProductType = itemView.findViewById(R.id.txtProductType);
            txtRecyclable = itemView.findViewById(R.id.txtRecyclable);
            txtPercentage = itemView.findViewById(R.id.txtPercentage);
        }
    }
}

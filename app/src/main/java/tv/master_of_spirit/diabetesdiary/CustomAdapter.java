package tv.master_of_spirit.diabetesdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList data_id, data_time, data_value;

    CustomAdapter(Context context,
                  ArrayList data_id, ArrayList data_time, ArrayList data_value) {
        this.context = context;
        this.data_id = data_id;
        this.data_time = data_time;
        this.data_value = data_value;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infalter = LayoutInflater.from(context);
        View view = infalter.inflate(R.layout.tagebuch_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.data_id_txt.setText(String.valueOf(data_id.get(position)));
        holder.data_time_txt.setText(String.valueOf(data_time.get(position)));
        holder.data_value_txt.setText(String.valueOf(data_value.get(position)));
    }

    @Override
    public int getItemCount() {
        return data_time.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView data_id_txt, data_time_txt, data_value_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data_id_txt = itemView.findViewById(R.id.data_id_txt);
            data_time_txt = itemView.findViewById(R.id.data_time_txt);
            data_value_txt = itemView.findViewById(R.id.data_value_txt);
        }
    }
}

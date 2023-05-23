package tv.master_of_spirit.diabetesdiary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter_clickable extends RecyclerView.Adapter<CustomAdapter_clickable.MyViewHolder>{

    private Context context;
    private ArrayList data_id, data_time, data_value;

    CustomAdapter_clickable(Context context,
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
        return new CustomAdapter_clickable.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter_clickable.MyViewHolder holder, int position) {
        holder.data_time_txt.setText(String.valueOf(data_time.get(position)));
        holder.data_value_txt.setText(String.valueOf(data_value.get(position)));
        holder.tagebuch_row_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewRechnungActivity.class);
                intent.putExtra("timestamp", String.valueOf(data_time.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_time.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView data_time_txt, data_value_txt;
        LinearLayout tagebuch_row_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data_time_txt = itemView.findViewById(R.id.data_time_txt);
            data_value_txt = itemView.findViewById(R.id.data_value_txt);
            tagebuch_row_layout = itemView.findViewById(R.id.tagebuch_row_layout);
        }
    }
}

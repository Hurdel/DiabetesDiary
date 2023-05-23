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

public class RechnungAdapter extends RecyclerView.Adapter<RechnungAdapter.MyViewHolder> {

    private Context context;
    private ArrayList data_pro100, data_gewicht;

    RechnungAdapter(Context context, ArrayList data_pro100, ArrayList data_gewicht) {
        this.context = context;
        this.data_pro100 = data_pro100;
        this.data_gewicht = data_gewicht;
    }

    @NonNull
    @Override
    public RechnungAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rechnung_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RechnungAdapter.MyViewHolder holder, int position) {
        holder.data_pro100_txt.setText(String.valueOf(data_pro100.get(position)));
        holder.data_gewicht_txt.setText(String.valueOf(data_gewicht.get(position)));
    }

    @Override
    public int getItemCount() {
        return data_pro100.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView data_pro100_txt, data_gewicht_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            data_pro100_txt = itemView.findViewById(R.id.data_pro100_txt);
            data_gewicht_txt = itemView.findViewById(R.id.data_gewicht_txt);
        }
    }
}

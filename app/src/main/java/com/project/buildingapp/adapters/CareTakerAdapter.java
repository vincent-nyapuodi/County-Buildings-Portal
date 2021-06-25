package com.project.buildingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.project.buildingapp.R;
import com.project.buildingapp.models.CareTaker;

import org.w3c.dom.Text;

public class CareTakerAdapter extends FirebaseRecyclerAdapter<CareTaker, CareTakerAdapter.MyViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CareTakerAdapter(@NonNull FirebaseRecyclerOptions<CareTaker> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CareTaker model) {
        TextView tvCarertakerName = holder.tvCaretakerName;
        TextView tvCarertakerEmail = holder.tvCaretakerEmail;
        TextView tvCarertakerPhone = holder.tvCaretakerPhone;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_caretaker, parent, false);

        CareTakerAdapter.MyViewHolder myViewHolder = new CareTakerAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvCaretakerName, tvCaretakerPhone, tvCaretakerEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCaretakerName = itemView.findViewById(R.id.tv_caretaker_name);
            tvCaretakerPhone = itemView.findViewById(R.id.tv_caretaker_phone);
            tvCaretakerEmail = itemView.findViewById(R.id.tv_caretaker_email);
        }
    }
}

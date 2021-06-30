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
import com.project.buildingapp.models.Comment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackAdapter extends FirebaseRecyclerAdapter<Comment, FeedbackAdapter.MyViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FeedbackAdapter(@NonNull FirebaseRecyclerOptions<Comment> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Comment model) {
        TextView tvFeedbackTime = holder.tvFeedbackTime;
        TextView tvFeedbackMessage = holder.tvFeedbackMessage;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formatdate = "";
        try {
            Date date = format.parse(model.getTimestamp());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            String strDate = formatter.format(date);
            formatdate = strDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvFeedbackTime.setText(formatdate);
        tvFeedbackMessage.setText(model.getComment());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_feedback, parent, false);

        FeedbackAdapter.MyViewHolder myViewHolder = new FeedbackAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvFeedbackTime, tvFeedbackMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFeedbackTime = itemView.findViewById(R.id.tv_feedback_time);
            tvFeedbackMessage = itemView.findViewById(R.id.tv_feedback_message);
        }
    }
}

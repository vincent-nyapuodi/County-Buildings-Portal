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
import com.project.buildingapp.models.PublicComments;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsersViewCommentAdapter extends FirebaseRecyclerAdapter<PublicComments, UsersViewCommentAdapter.MyViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UsersViewCommentAdapter(@NonNull @NotNull FirebaseRecyclerOptions<PublicComments> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position, @NonNull @NotNull PublicComments model) {
        TextView tvCommentDate = holder.tvCommentDate;
        TextView tvCommentType = holder.tvCommentType;
        TextView tvCommentContext = holder.tvCommentContext;

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

        tvCommentDate.setText(formatdate);
        tvCommentType.setText(model.getCommenttype());
        tvCommentContext.setText(model.getComment());
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_users_comments, parent, false);

        UsersViewCommentAdapter.MyViewHolder myViewHolder = new UsersViewCommentAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCommentDate, tvCommentContext, tvCommentType;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvCommentDate = itemView.findViewById(R.id.tv_viewcomments_date);
            tvCommentContext = itemView.findViewById(R.id.tv_viewcomments_context);
            tvCommentType = itemView.findViewById(R.id.tv_viewcomments_commenttype);
        }
    }
}

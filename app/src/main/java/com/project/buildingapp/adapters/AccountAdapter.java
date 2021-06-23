package com.project.buildingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.buildingapp.R;
import com.project.buildingapp.models.Building;

public class AccountAdapter extends FirebaseRecyclerAdapter<Building, AccountAdapter.MyViewHolder> {

    private Context context;
    private DatabaseReference reference, buildingreference;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AccountAdapter(@NonNull FirebaseRecyclerOptions<Building> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull AccountAdapter.MyViewHolder holder, int position, @NonNull Building model) {
        TextView tvAccountName = holder.tvAccountName;
        tvAccountName.setText(model.getBuildingname() + " - " + model.getBuildingtown() + ", " + model.getBuildingcounty());

        tvAccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildingreference = FirebaseDatabase.getInstance().getReference().child("table_building");
                reference = FirebaseDatabase.getInstance().getReference().child("table_session");
                String refKey = getRef(position).getKey();

                buildingreference.orderByKey().equalTo(refKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String buildingcode = "";
                        if (snapshot != null) {
                            buildingcode = snapshot.child("buildingcode").getValue().toString();
                        }

                        Toast.makeText(context, "Building code = " + buildingcode, Toast.LENGTH_SHORT).show();
                        reference.orderByChild("buildingcode").equalTo(buildingcode).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                if (snapshot != null) {
                                    String key = snapshot.getKey();

                                    reference.child(key).child("status").setValue(true);
                                    reference.child(key).child("email_status").setValue(model.getOwneremail() + "_true");

                                    Navigation.findNavController(view).navigate(R.id.navigateToHome);
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public AccountAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_buildings, parent, false);

        AccountAdapter.MyViewHolder myViewHolder = new AccountAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvAccountName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAccountName = itemView.findViewById(R.id.tv_building_account);
        }
    }
}

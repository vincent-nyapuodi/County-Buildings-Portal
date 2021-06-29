package com.project.buildingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.project.buildingapp.R;
import com.project.buildingapp.UserHomeFragment;
import com.project.buildingapp.UserHomeFragmentDirections;
import com.project.buildingapp.models.Building;

public class UserBuildings extends FirebaseRecyclerAdapter<Building, UserBuildings.MyViewHolder> {

    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserBuildings(@NonNull FirebaseRecyclerOptions<Building> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserBuildings.MyViewHolder holder, int position, @NonNull Building model) {
        RelativeLayout lytBuilding = holder.lytBuilding;
        TextView tvBuildingName = holder.tvListBuildingName;
        TextView tvBuildingResidence = holder.tvListResidenceType;
        TextView tvBuildingLocation = holder.tvListLocation;

        String [] array = model.getBuildingtype().split("_");
        String residencetype = array[0].toString();

        String type = "";

        switch (model.getBuildingtype()) {
            case "private_1":
                type = "city";
                break;
            case "private_2":
                type = "upcountry";
                break;
            case "private_3":
                type = "ghetto";
                break;
            case "private_4":
                type = "estate";
                break;
            case "commercial_1":
                type = "hotel";
                break;
            case "commercial_2":
                type = "rental";
                break;
            case "commercial_3":
                type = "industrial";
                break;
            case "commercial_4":
                type = "office";
                break;
        }

        tvBuildingName.setText(model.getBuildingname());
        tvBuildingResidence.setText(residencetype + " residence | " + type);
        tvBuildingLocation.setText(model.getBuildingtown() + ", " + model.getBuildingcounty());

        lytBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refkey = getRef(position).getKey();
                String buildingcode = model.getBuildingcode();

                UserHomeFragmentDirections.ActionUserHomeFragmentToBuildingDetailsFragment action
                        = UserHomeFragmentDirections.actionUserHomeFragmentToBuildingDetailsFragment(refkey, buildingcode);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @NonNull
    @Override
    public UserBuildings.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_user_buildings, parent, false);

        UserBuildings.MyViewHolder myViewHolder = new UserBuildings.MyViewHolder(view);

        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout lytBuilding;
        TextView tvListBuildingName, tvListResidenceType, tvListLocation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            lytBuilding = itemView.findViewById(R.id.lyt_building);
            tvListBuildingName = itemView.findViewById(R.id.tv_list_buildingname);
            tvListResidenceType = itemView.findViewById(R.id.tv_list_residence);
            tvListLocation = itemView.findViewById(R.id.tv_list_location);
        }
    }
}

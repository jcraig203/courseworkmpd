package eu.pendual.mpdcoursework;

/**
 * Created by James on 27/03/2018.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.MyViewHolder> {

    private ArrayList<Incidents> incidentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, datetime, description;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            datetime = (TextView) view.findViewById(R.id.datetime);
            description = (TextView) view.findViewById(R.id.description);
           // location = (TextView) view.findViewById(R.id.location);
        }
    }


    public IncidentsAdapter(ArrayList<Incidents> incidentsList) {
        this.incidentsList = incidentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incidents_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Incidents incidents = incidentsList.get(position);
        holder.title.setText(incidents.getTitle());
        holder.datetime.setText(incidents.getDatetime());
        holder.description.setText(incidents.getDescription());
       // holder.location.setText(incidents.getLocation());
    }

    @Override
    public int getItemCount() {
        return incidentsList.size();
    }
}
package eu.pendual.mpdcoursework;

/**
 * Created by James Craig S1428641
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.MyViewHolder> {

    private ArrayList<Incidents> incidentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, datetime;
        public RelativeLayout relativeLayout;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            datetime = (TextView) view.findViewById(R.id.datetime);
           // description = (TextView) view.findViewById(R.id.description);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.incidentLayout);
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
        final Incidents incidents = incidentsList.get(position);
        holder.title.setText(incidents.getTitle());
        holder.datetime.setText(incidents.getDatetime());
       // holder.description.setText(incidents.getDescription());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(incidents.getTitle());

                String[] incidentStrings = new String[7];
                incidentStrings[0] = incidents.getTitle();
                incidentStrings[1] = incidents.getDescription();
                incidentStrings[2] = incidents.getUrlLink();
                incidentStrings[3] = incidents.getLocation();
                incidentStrings[4] = incidents.getAuthor();
                incidentStrings[5] = incidents.getComments();
                incidentStrings[6] = incidents.getDatetime();

                Intent intent = new Intent(view.getContext(), DisplayInfoActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("incidentStringArray", incidentStrings);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
       // holder.location.setText(incidents.getLocation());
    }

    @Override
    public int getItemCount() {
        return incidentsList.size();
    }
}
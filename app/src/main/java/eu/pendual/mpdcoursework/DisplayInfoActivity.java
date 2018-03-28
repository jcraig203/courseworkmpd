package eu.pendual.mpdcoursework;

/**
 * Created by James Craig S1428641
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayInfoActivity extends AppCompatActivity {

    String title;
    String description;
    String urlLink;
    String location;
    String author;
    String comments;
    String datetime;
    TextView titleTextView;
    TextView descriptionTextView;
    TextView urlTextView;
    TextView authorTextView;
    TextView commentsTextView;
    TextView datetimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getDisplayInformation();
        displayInfo();
        toolbar.setTitle(title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDisplayInformation(){
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        String[] incidentStrings = new String[7];

        if(extras != null)  //this line is necessary for getting any value
        {
            incidentStrings = i.getStringArrayExtra("incidentStringArray");
        }

        title = incidentStrings[0];
        description = incidentStrings[1];
        urlLink = incidentStrings[2];
        location = incidentStrings[3];
        author = incidentStrings[4];
        comments = incidentStrings[5];
        datetime = incidentStrings[6];
    }

    private void displayInfo() {
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        urlTextView = (TextView) findViewById(R.id.urlTextView);
        authorTextView = (TextView) findViewById(R.id.authorTextView);
        commentsTextView = (TextView) findViewById(R.id.commentsTextView);
        datetimeTextView = (TextView) findViewById(R.id.datetimeTextView);

        titleTextView.setText(title);
        descriptionTextView.setText(description);
        urlTextView.setText(urlLink);
        authorTextView.setText("Author: " + author);
        commentsTextView.setText(comments);
        datetimeTextView.setText(datetime);
    }

}


package eu.pendual.mpdcoursework;

/**
 * Created by James Craig S1428641
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private Button currentButton;
    private Button plannedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureCurrentButton();
        configurePlannedButton();

    }

    private void configureCurrentButton() {
        currentButton = (Button) findViewById(R.id.currentButton);
        currentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, IncidentsActivity.class));
                System.out.println("Current Incidents activity started!");
            }
        });
    }

    private void configurePlannedButton() {
        plannedButton = (Button) findViewById(R.id.plannedButton);
        plannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PlannedActivity.class));
                System.out.println("Planned Roadworks activity started!");
            }
        });
    }
}

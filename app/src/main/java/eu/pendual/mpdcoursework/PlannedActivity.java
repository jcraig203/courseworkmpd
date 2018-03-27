package eu.pendual.mpdcoursework;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PlannedActivity extends AppCompatActivity {
    ArrayList<String> titleList;
    ArrayList<Incidents> incidentList;
    RecyclerView recyclerView;
    IncidentsAdapter iAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plannedRoadworks();

        recyclerView = (RecyclerView) findViewById(R.id.plannedRecycler);

        iAdapter = new IncidentsAdapter(incidentList);
        RecyclerView.LayoutManager iLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(iLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(iAdapter);
    }
    public void plannedRoadworks() {
        try {
            URL url = new URL("https://trafficscotland.org/rss/feeds/currentincidents.aspx");
            URLConnection conn = url.openConnection();

            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document using input stream
            Document document = builder.parse(conn.getInputStream());

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            Element root = document.getDocumentElement();
            System.out.println("THIS IS ROOT NODE" + root.getNodeName());

            //Get all items within the XML file
            NodeList nList = document.getElementsByTagName("item");
            System.out.println("============================");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);

                System.out.println("");    //Just a separator

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Print each employee's detail
                    Element eElement = (Element) node;

                    System.out.println("TEST TITLE : " + eElement.getElementsByTagName("title").item(0).getTextContent());

                    titleList.add(eElement.getElementsByTagName("title").item(0).getTextContent());


                    String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                    String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    String urlLink = eElement.getElementsByTagName("link").item(0).getTextContent();
                    String location = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                    String author = eElement.getElementsByTagName("author").item(0).getTextContent();
                    String comments = eElement.getElementsByTagName("comments").item(0).getTextContent();
                    String datetime = eElement.getElementsByTagName("pubDate").item(0).getTextContent().toString();

                    String longitude = location.substring(0, location.indexOf(" "));
                    String latitude = location.substring(location.lastIndexOf(" ") + 1);

                    DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                    Date dateTime = format.parse(datetime);
                    System.out.println(dateTime);
                    incidentList.add(new Incidents(title, description, urlLink, location, author, comments, dateTime, longitude, latitude, datetime));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


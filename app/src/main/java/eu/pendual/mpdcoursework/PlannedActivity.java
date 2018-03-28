package eu.pendual.mpdcoursework;

/**
 * Created by James Craig S1428641
 */

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PlannedActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    ArrayList<String> titleList;
    ArrayList<Incidents> incidentList = new ArrayList<Incidents>();
    ArrayList<Incidents> pickedDateList = new ArrayList<Incidents>();
    Button datePicker;
    RecyclerView recyclerView;
    PlannedAdapter iAdapter;
    Date pickedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        datePicker = findViewById(R.id.pickDateButton);

        new getXMLTast().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH );
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                pickedDateList = new ArrayList<Incidents>();
                DatePickerDialog datePickerDialog = new DatePickerDialog(PlannedActivity.this, PlannedActivity.this, startYear, startMonth, startDay);
                System.out.println("Opening Datepicker...");
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        System.out.println(i);
        System.out.println(i1);
        System.out.println(i2);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DATE, i2);
        pickedDate = calendar.getTime();
        System.out.println(pickedDate);
        System.out.println("Date set!");
        System.out.println("=====================================");

        new getSpecificDateInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        System.out.println(pickedDateList.size());
        System.out.println("Loaded.");
    }

    private class getXMLTast extends AsyncTask {

        ProgressDialog progDailog = new ProgressDialog(PlannedActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                System.out.println("TRYNA GET URLS");
                URL url = new URL("http://trafficscotland.org/rss/feeds/plannedroadworks.aspx");
                URLConnection conn = url.openConnection();
                System.out.println("url connection found");

                //Get Document Builder
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                System.out.println("document builder got");
                //Build Document using input stream
                Document document = builder.parse(conn.getInputStream());
                System.out.println("document built");
                //Normalize the XML structure
                document.getDocumentElement().normalize();
                System.out.println("xml structure normalised");
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
                        Element eElement = (Element) node;

                        System.out.println("TEST TITLE : " + eElement.getElementsByTagName("title").item(0).getTextContent());


                        String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                        System.out.println(title);
                        String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                        System.out.println(description);
                        String urlLink = eElement.getElementsByTagName("link").item(0).getTextContent();
                        System.out.println(urlLink);
                        String location = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                        System.out.println(location);
                        String author = eElement.getElementsByTagName("author").item(0).getTextContent();
                        System.out.println(author);
                        String comments = eElement.getElementsByTagName("comments").item(0).getTextContent();
                        System.out.println(comments);
                        String datetime = eElement.getElementsByTagName("pubDate").item(0).getTextContent().toString();
                        System.out.println(datetime);

                        String longitude = location.substring(0, location.indexOf(" "));
                        System.out.println(longitude);
                        String latitude = location.substring(location.lastIndexOf(" ") + 1);
                        System.out.println(latitude);

                        DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                        Date dateTime = format.parse(datetime);
                        System.out.println(dateTime);
                        incidentList.add(new Incidents(title, description, urlLink, location, author, comments, dateTime, longitude, latitude, datetime));

                    }
                }
            } catch (Exception e) {
                System.out.println("IT BROKE");
                e.printStackTrace();
            }
            System.out.println(incidentList.size());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            recyclerView = (RecyclerView) findViewById(R.id.plannedRecycler);

            iAdapter = new PlannedAdapter(incidentList);
            RecyclerView.LayoutManager iLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(iLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(iAdapter);
            progDailog.dismiss();
        }
    }

    private class getSpecificDateInfo extends AsyncTask{

        ProgressDialog progDailog = new ProgressDialog(PlannedActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }
        @TargetApi(Build.VERSION_CODES.O)
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                System.out.println("Attempting to connect to URL...");
                URL url = new URL("http://trafficscotland.org/rss/feeds/plannedroadworks.aspx");
                URLConnection conn = url.openConnection();
                System.out.println("url connection established!");

                //Get Document Builder
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                System.out.println("document builder created");
                //Build Document using input stream
                Document document = builder.parse(conn.getInputStream());
                System.out.println("document parsed");
                //Normalize the XML structure
                document.getDocumentElement().normalize();
                System.out.println("xml structure normalised");
                //Here comes the root node
                Element root = document.getDocumentElement();
                System.out.println("THIS IS ROOT NODE: " + root.getNodeName());

                //Get all items within the XML file
                NodeList nList = document.getElementsByTagName("item");
                System.out.println("============================");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node node = nList.item(temp);

                    System.out.println("");    //Just a separator

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;

                        System.out.println("TEST TITLE : " + eElement.getElementsByTagName("title").item(0).getTextContent());


                        String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                        System.out.println(title);
                        String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                        System.out.println(description);
                        String urlLink = eElement.getElementsByTagName("link").item(0).getTextContent();
                        System.out.println(urlLink);
                        String location = eElement.getElementsByTagName("georss:point").item(0).getTextContent();
                        System.out.println(location);
                        String author = eElement.getElementsByTagName("author").item(0).getTextContent();
                        System.out.println(author);
                        String comments = eElement.getElementsByTagName("comments").item(0).getTextContent();
                        System.out.println(comments);
                        String datetime = eElement.getElementsByTagName("pubDate").item(0).getTextContent().toString();
                        System.out.println(datetime);

                        String longitude = location.substring(0, location.indexOf(" "));
                        System.out.println(longitude);
                        String latitude = location.substring(location.lastIndexOf(" ") + 1);
                        System.out.println(latitude);

                        DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                        Date dateTime = format.parse(datetime);
                        System.out.println(dateTime);
                        LocalDate localDateTime = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        System.out.println(localDateTime);
                        LocalDate localPickedDate = pickedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        System.out.println(localPickedDate);
                        System.out.println(localDateTime.toString().equals(localPickedDate));

                        if (localDateTime.equals(localPickedDate)) {
                            pickedDateList.add(new Incidents(title, description, urlLink, location, author, comments, dateTime, longitude, latitude, datetime));
                        }
                        System.out.println("============================");
                    }
                }
            } catch (Exception e) {
                System.out.println("IT BROKE");
                e.printStackTrace();
            }
            System.out.println(pickedDateList.size());
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            recyclerView = (RecyclerView) findViewById(R.id.plannedRecycler);

            iAdapter = new PlannedAdapter(pickedDateList);
            RecyclerView.LayoutManager iLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(iLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(iAdapter);
            progDailog.dismiss();
        }
    }
}


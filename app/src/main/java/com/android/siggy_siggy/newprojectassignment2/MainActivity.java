package com.android.siggy_siggy.newprojectassignment2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;











public class MainActivity extends AppCompatActivity {

  ListView lvRss;
  ArrayList<String>titles;
  ArrayList<String>links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    lvRss = (ListView) findViewById(R.id.lvRss);

    titles = new ArrayList<String>();
    links = new ArrayList<String>();

    lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            // store the link and convert it to a string
            Uri uri = Uri.parse(links.get(position));
            String UriString = uri.toString();

            // jumps to activity 2 / display activity
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra("list", UriString);
            startActivity(intent);
        }
        });

        new ProcessInBackground().execute();
    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch(IOException e)
        {
            return null;
        }
    }

    // process in background
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Busy loading rss feed...please wait...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            try
            {
                URL url = new URL("http://feeds.news24.com/articles/fin24/tech/rss");

                //creates new instance of PullParserFactory that can be used to create XML pull parsers
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                //Specifies whether the parser produced by this factory will provide support
                //for XML namespaces
                factory.setNamespaceAware(false);

                //creates a new instance of a XML pull parser using
                //factory features
                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");


                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG
                int eventType = xpp.getEventType(); //loop control variable

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    //if we are at a START_TAG (opening tag)
                    if (eventType == XmlPullParser.START_TAG) {
                        //if the tag is called "item"
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        }
                        //if the tag is called "title"
                        else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                // extract the text between <title> tags
                                titles.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                // extract the text between <description> tags
                                titles.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {

                            if (insideItem) {
                                links.add(xpp.nextText());

                            }

                        }
                    }
                    //if we are at an END_TAG and the END_TAG is called "item"
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                    {
                        insideItem = false;
                    }

                    eventType = xpp.next(); //move to next element
                }


            }
            catch (MalformedURLException e)
            {
                exception = e;
            }
            catch (XmlPullParserException e)
            {
                exception = e;
            }
            catch (IOException e)
            {
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, titles);

            lvRss.setAdapter(adapter);

            progressDialog.dismiss();
        }
    }


    public void jumpToSettings(View view) {

        // Makes intent and jumps to settings
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);

    }

}
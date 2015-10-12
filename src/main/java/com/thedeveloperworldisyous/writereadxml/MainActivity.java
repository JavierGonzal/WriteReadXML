package com.thedeveloperworldisyous.writereadxml;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.thedeveloperworldisyous.writereadxml.adapters.ListAdapter;
import com.thedeveloperworldisyous.writereadxml.models.Actor;
import com.thedeveloperworldisyous.writereadxml.models.Film;
import com.thedeveloperworldisyous.writereadxml.utils.Utils;
import com.thedeveloperworldisyous.writereadxml.utils.Work;

public class MainActivity extends Activity implements View.OnClickListener{

    private static String sOutPutfile = "Android/data/Films.xml";

    private File mPdfFileOutPut;
    // We don't use namespaces
    private static final String ns = null;

    private Button mReadButton;
    private ListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button writeButton = (Button) findViewById(R.id.activity_main_write);
        mReadButton = (Button) findViewById(R.id.activity_main_read);
        mListView = (ListView) findViewById(R.id.activity_main_list);

        writeButton.setOnClickListener(this);
        mReadButton.setOnClickListener(this);

        // check if external storage is available so that we can dump our PDF
    // file there
        if (!Utils.isExternalStorageAvailable() || Utils.isExternalStorageReadOnly()) {
            Log.d("Main","External Storage not available or you don't have permission to write");

        } else {
            mPdfFileOutPut = new File(Environment.getExternalStorageDirectory(),
                    sOutPutfile);

        }

    }

    public List<Film> createFilms() {
        Actor actor1 = new Actor("Al", "Pacino");
        Actor actor2 = new Actor(" Steven", "Bauer");
        Actor actor3 = new Actor("Michelle", "Pfeiffer");
        Actor actor4 = new Actor("Mary Elizabeth", "Mastrantonio");

        List<Actor> cast = new ArrayList<Actor>();
        cast.add(actor1);
        cast.add(actor2);
        cast.add(actor3);
        cast.add(actor4);
//        Film film = new Film("Scarface", "163 min.", "USA", "Brian De Palma", cast);
        Film filmScarface = new Film("Scarface", "163 min.", "USA", "Brian De Palma");

        Actor actor12 = new Actor("Rupert", "Friend");
        Actor actor22 = new Actor("Zachary", "Quinto");
        Actor actor32 = new Actor("Hannah", "Ware");
        Actor actor42 = new Actor("Ciarán", "Hinds");

        List<Actor> cast1 = new ArrayList<Actor>();
        cast.add(actor12);
        cast.add(actor22);
        cast.add(actor32);
        cast.add(actor42);
//        Film film1 = new Film("Hitman", "96 min.", "USA", "Aleksander Bach", cast1);
        Film filmHitman = new Film("Hitman", "96 min.", "USA", "Aleksander Bach");
        Actor actor11 = new Actor("Robert", "Redford");
        Actor actor21 = new Actor("Meryl", "Streep");
        Actor actor31 = new Actor("Klaus Maria", "Brandauer");
        Actor actor41 = new Actor("Michael", "Kitchen");

        List<Actor> cast11 = new ArrayList<Actor>();
        cast.add(actor11);
        cast.add(actor21);
        cast.add(actor31);
        cast.add(actor41);
//        Film film11 = new Film("Out of Africa", "160 min.", "USA", "Sydney Pollack", cast11);
        Film filmAfrica = new Film("Out of Africa", "160 min.", "USA", "Sydney Pollack");

        List<Film> listFilms = new ArrayList<Film>();
        listFilms.add(filmScarface);
        listFilms.add(filmHitman);
        listFilms.add(filmAfrica);
        return listFilms;
    }

    public void writeXml(List<Film> films) {


        try {
            FileOutputStream fileWrite = new FileOutputStream(mPdfFileOutPut);

            XmlSerializer xmlSerializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            xmlSerializer.setOutput(writer);
            xmlSerializer.startDocument("UTF-8", true);
            xmlSerializer.startTag(null, "doc");

            insertFilms(xmlSerializer,createFilms());

            xmlSerializer.endTag(ns, "doc");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            fileWrite.write(dataWrite.getBytes());
            fileWrite.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (IllegalStateException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    public void insertFilms(XmlSerializer xmlSerializer, List<Film> films) throws IOException {
        final String film = "film";
        String title = "title";
        String runningTime = "runningTime";
        String country = "country";
        String director = "director";
        String cast = "cast";
        Film filmObject;
        for (int i=0; i<films.size(); i++) {
            filmObject = films.get(i);
            xmlSerializer.startTag(null, film);
            xmlSerializer.attribute(null, title, filmObject.getTitle());

            xmlSerializer.startTag(null, runningTime);
            xmlSerializer.text(filmObject.getRunningTime());
            xmlSerializer.endTag(null, runningTime);

            xmlSerializer.startTag(null, country);
            xmlSerializer.text(filmObject.getCountry());
            xmlSerializer.endTag(null, country);

            xmlSerializer.startTag(null, director);
            xmlSerializer.text(filmObject.getDirector());
            xmlSerializer.endTag(null, director);

//            xmlSerializer.startTag(null, director);
//            insertCast(xmlSerializer, filmObject.getCast());
//            xmlSerializer.endTag(null, director);

            xmlSerializer.endTag(null, film);
        }

    }

    public void insertCast (XmlSerializer xmlSerializer, List<Actor> cast) throws IOException {
        String name = "name";
        String surname = "surname";
        Actor actor;
        for (int i=0; i<cast.size(); i++) {
            actor = cast.get(i);
            xmlSerializer.startTag(null, name);
            xmlSerializer.text(actor.getName());
            xmlSerializer.endTag(null, name);

            xmlSerializer.startTag(null, surname);
            xmlSerializer.text(actor.getSurname());
            xmlSerializer.endTag(null, surname);
        }

    }

    @Override
    public void onClick(View v) {
        Message message = new Message();
        switch (v.getId()) {
            case R.id.activity_main_write:


                message.what = Work.WRITE_XML;
                handler.sendMessage(message);



                mReadButton.setEnabled(true);
                break;

            case R.id.activity_main_read:
                message.what = Work.READ_XML;
                handler.sendMessage(message);
                break;
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Work.READ_XML:
                    readFilm();
                    break;

                case Work.WRITE_XML:
                    writeXml(createFilms());
                    break;
            }
        }
    };

    public void readFilm() {
        try {

            InputStream inputStream = new FileInputStream(mPdfFileOutPut);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            List<Film> filmsList =  readDoc(parser);

            ListAdapter adapter = new ListAdapter(filmsList, this);
            mListView.setAdapter(adapter);

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Film> readDoc(XmlPullParser parser) throws XmlPullParserException, IOException {

        List<Film> list = new ArrayList<Film>();
        parser.require(XmlPullParser.START_TAG, ns, "doc");

        while (parser.next() != XmlPullParser.END_TAG) {
            list.add(readFilms(parser));
        }
        return list;
    }



    // Parses the contents of an film. If it encounters a title, runningTime, country, or director, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private Film readFilms(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "film");
        String title = null;

        for(int x=0; x<parser.getAttributeCount(); x++) {
            if (parser.getAttributeName(x).equals("title")) {
                title = parser.getAttributeValue(x);
            }
        }

        String runningTime = null;
        String country = null;
        String director = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("runningTime")) {
                runningTime = readRunningTime(parser);
            } else if (name.equals("country")) {
                country = readCountry(parser);
            } else if (name.equals("director")) {
                director = readDirector(parser);
            } else {
                skip(parser);
            }
        }
        return new Film( title, runningTime, country, director);
    }

    private String readRunningTime(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "runningTime");
        String runningTime = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "runningTime");
        return runningTime;
    }

    private String readCountry(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "country");
        String country = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "country");
        return country;
    }

    private String readDirector(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "director");
        String director = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "director");
        return director;
    }

    // For the tags title and runningTime, country, director their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
package com.thedeveloperworldisyous.writereadxml;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thedeveloperworldisyous.writereadxml.adapters.ListAdapter;
import com.thedeveloperworldisyous.writereadxml.models.Actor;
import com.thedeveloperworldisyous.writereadxml.models.Film;
import com.thedeveloperworldisyous.writereadxml.utils.Utils;
import com.thedeveloperworldisyous.writereadxml.utils.Work;

public class MainActivity extends Activity implements View.OnClickListener{

    private static String sOutPutfile = "Films.xml";
    private File mFileOutPut;

    // We don't use namespaces
    private static final String ns = null;

    private Button mReadButton;
    private ListView mListView;
    private Button mWriteButton;
    private List<Film> mListFilms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button addFilm = (Button) findViewById(R.id.activity_main_add_film);
        mWriteButton = (Button) findViewById(R.id.activity_main_write);
        mReadButton = (Button) findViewById(R.id.activity_main_read);
        mListView = (ListView) findViewById(R.id.activity_main_list);

        addFilm.setOnClickListener(this);
        mWriteButton.setOnClickListener(this);
        mReadButton.setOnClickListener(this);

        // check if external storage is available so that we can dump our PDF file there
        if (!Utils.isExternalStorageAvailable() || Utils.isExternalStorageReadOnly()) {
            Log.d("Main","External Storage not available or you don't have permission to write");

        } else {
//            mPdfFileOutPut = new File(sOutPutfile);
//            mPdfFileOutPut = new File(getApplicationContext().getFileStreamPath("FileName.xml")
//                    .getPath());

        }
        createFilms();

    }

    public List<Film> createFilms() {
        Actor actor1 = new Actor("Al", "Pacino");
        Actor actor2 = new Actor(" Steven", "Bauer");
        Actor actor3 = new Actor("Michelle", "Pfeiffer");
        Actor actor4 = new Actor("Mary Elizabeth", "Mastrantonio");

        List<Actor> castScarface = new ArrayList<Actor>();
        castScarface.add(actor1);
        castScarface.add(actor2);
        castScarface.add(actor3);
        castScarface.add(actor4);
        Film filmScarface = new Film("Scarface", "163 min.", "USA", "Brian De Palma", castScarface);

        Actor actor12 = new Actor("Rupert", "Friend");
        Actor actor22 = new Actor("Zachary", "Quinto");
        Actor actor32 = new Actor("Hannah", "Ware");
        Actor actor42 = new Actor("Ciarán", "Hinds");

        List<Actor> castHitman  = new ArrayList<Actor>();
        castHitman.add(actor12);
        castHitman.add(actor22);
        castHitman.add(actor32);
        castHitman.add(actor42);
        Film filmHitman = new Film("Hitman", "96 min.", "USA", "Aleksander Bach", castHitman);


        Actor actor11 = new Actor("Robert", "Redford");
        Actor actor21 = new Actor("Meryl", "Streep");
        Actor actor31 = new Actor("Klaus Maria", "Brandauer");
        Actor actor41 = new Actor("Michael", "Kitchen");

        List<Actor> castAfrica = new ArrayList<Actor>();
        castAfrica.add(actor11);
        castAfrica.add(actor21);
        castAfrica.add(actor31);
        castAfrica.add(actor41);
        Film filmAfrica = new Film("Out of Africa", "160 min.", "USA", "Sydney Pollack", castAfrica);

        mListFilms = new ArrayList<Film>();
        mListFilms.add(filmScarface);
        mListFilms.add(filmHitman);
        mListFilms.add(filmAfrica);
        return mListFilms;
    }

    public void writeXml(List<Film> films) {


        try {
            mFileOutPut = new File(getFileStreamPath(getNameFile()).getPath());
            FileOutputStream fileWrite = new FileOutputStream(mFileOutPut);

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
            xmlSerializer.startTag(ns, film);
            xmlSerializer.attribute(ns, title, filmObject.getTitle());

            xmlSerializer.startTag(ns, runningTime);
            xmlSerializer.text(filmObject.getRunningTime());
            xmlSerializer.endTag(ns, runningTime);

            xmlSerializer.startTag(ns, country);
            xmlSerializer.text(filmObject.getCountry());
            xmlSerializer.endTag(ns, country);

            xmlSerializer.startTag(ns, director);
            xmlSerializer.text(filmObject.getDirector());
            xmlSerializer.endTag(ns, director);

            xmlSerializer.startTag(ns, cast);
            insertCast(xmlSerializer, filmObject.getCast());
            xmlSerializer.endTag(ns, cast);

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
            case R.id.activity_main_add_film:
                getDialogAddFilm();
                mWriteButton.setEnabled(true);
                break;

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
                    writeXml(mListFilms);
                    break;
            }
        }
    };

    public void readFilm() {
        try {

            InputStream inputStream = new FileInputStream(mFileOutPut);
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
        List<Actor> cast = new ArrayList<Actor>();

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
            } else if (name.equals("cast")) {
                cast = readCast(parser);
            } else {
                skip(parser);
            }
        }
        return new Film( title, runningTime, country, director, cast);
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

    private List<Actor> readCast(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<Actor> listActor= new ArrayList<Actor>();
        parser.require(XmlPullParser.START_TAG, ns, "cast");

        String nameActor = null;
        String surnameActor = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String nameParser = parser.getName();
            if (nameParser.equals("name")) {
                nameActor = readNameActor(parser);
            } else if(nameParser.equals("surname")) {
                surnameActor = readSurnameActor(parser);
                listActor.add(new Actor(nameActor, surnameActor));
            } else {
                skip(parser);
            }

        }
        return listActor;
    }

    private String readNameActor(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "name");
        String nameActor = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "name");
        return nameActor;
    }

    private String readSurnameActor(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "surname");
        String surnameActor = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "surname");
        return  surnameActor;
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


    public void getDialogAddFilm()
    {
        LinearLayout layout = new LinearLayout(this);
        TextView tvMessage = new TextView(this);
        final EditText etTitle = new EditText(this);

        // add LayoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(30, 20, 30, 0);

        tvMessage.setText(getString(R.string.activity_main_add_film_title));
        tvMessage.setLayoutParams(params);

        etTitle.setSingleLine();
        etTitle.setLayoutParams(params);
        etTitle.setSelection(etTitle.getText().length());

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(tvMessage);
        layout.addView(etTitle);

        final AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.activity_main_add_film))
                .setView(layout)
                .setPositiveButton(getString(android.R.string.ok), null)
                .setCancelable(false)
                .create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface arg0) {

                Button okButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // Get the number entered removing all the whitespace
                        String title = etTitle.getText().toString().replace(" ", "");

                        if (!title.isEmpty()) {
                            Actor actor1 = new Actor("Al", "Pacino");
                            Actor actor2 = new Actor(" Steven", "Bauer");
                            Actor actor3 = new Actor("Michelle", "Pfeiffer");
                            Actor actor4 = new Actor("Mary Elizabeth", "Mastrantonio");
                            List<Actor> castScarface = new ArrayList<Actor>();
                            castScarface.add(actor1);
                            castScarface.add(actor2);
                            castScarface.add(actor3);
                            castScarface.add(actor4);
                            Film filmDialog = new Film(title, "163 min.", "USA", "Brian De Palma", castScarface);
                            mListFilms.add(filmDialog);
                            alert.dismiss();

                        }
                        else {
                            Toast.makeText(getBaseContext(), getString(R.string.activity_main_add_film_empty_fild), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        alert.show();
    }
    public String getNameFile(){
        Calendar c = Calendar.getInstance();
        int milliSeconds = c.get(Calendar.MILLISECOND);
        return String.valueOf(milliSeconds);
    }
}
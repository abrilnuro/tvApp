package com.example.aimew.tvshowapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.aimew.tvshowapp.controllers.TvShowController;
import com.example.aimew.tvshowapp.events.ErrorEvent;
import com.example.aimew.tvshowapp.events.SuccessAddTvShow;
import com.example.aimew.tvshowapp.models.TvShow;
import com.squareup.otto.Subscribe;

import junit.framework.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTvShowActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener, View.OnClickListener {

    private EditText editTitle;
    private EditText editLanguage;
    private EditText editGenre;
    private EditText editResume;
    private EditText editReleaseDate;
    private EditText editPoster;
    private CalendarView calendarReleaseDate;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tv_show);
        App.getBus().register(this);

        editTitle = (EditText) findViewById(R.id.edit_title);
        editLanguage = (EditText) findViewById(R.id.edit_language);
        editGenre = (EditText) findViewById(R.id.edit_genre);
        editResume = (EditText) findViewById(R.id.edit_resume);
        editReleaseDate = (EditText) findViewById(R.id.edit_releasedate);
        editPoster = (EditText) findViewById(R.id.edit_pster);
        calendarReleaseDate = (CalendarView) findViewById(R.id.calendar_releasedate);
        buttonAdd = (Button) findViewById(R.id.button_add_tvshow);

        calendarReleaseDate.setOnDateChangeListener(this);
        buttonAdd.setOnClickListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        if(view.getId() == R.id.calendar_releasedate){
            String Date = dayOfMonth + "-" + (month + 1) + "-" + year;
            editReleaseDate.setText(Date);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_add_tvshow){
            String title = editTitle.getText().toString();
            String language = editLanguage.getText().toString();
            String genre = editGenre.getText().toString();
            String resume = editResume.getText().toString();
            String releaseDate = editReleaseDate.getText().toString();
            String poster = editPoster.getText().toString();

            Assert.assertNotNull("You must add title", title);
            Assert.assertTrue("You must add title", !title.isEmpty());

            Assert.assertNotNull("You must add language", language);
            Assert.assertTrue("You must add language", !language.isEmpty());

            Assert.assertNotNull("You must add genre", genre);
            Assert.assertTrue("You must add genre", !genre.isEmpty());

            Assert.assertNotNull("You must add resume", resume);
            Assert.assertTrue("You must add resume", !resume.isEmpty());

            Assert.assertNotNull("You must add releaseDate", releaseDate);
            Assert.assertTrue("You must add releaseDate", !releaseDate.isEmpty());

            Assert.assertNotNull("You must add poster", poster);
            Assert.assertTrue("You must add poster", !poster.isEmpty());

            TvShow tvShow = new TvShow();
            tvShow.setName(title);
            tvShow.setLanguage(language);
            tvShow.setGenre(genre);
            tvShow.setResume(resume);
            tvShow.setPoster(poster);

            Date date= null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse(releaseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvShow.setReleaseDate(date);

            TvShowController.addTvShow(this, tvShow);
        }
    }

    @Subscribe
    public void onSuccessAddTvShow(SuccessAddTvShow successAddTvShow){
        Intent intent = new Intent(AddTvShowActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void onError(ErrorEvent errorEvent) {
        Log.e("OnError Event", errorEvent.getMessage() + "code:"+ errorEvent.getCode() );
    }
}

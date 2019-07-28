package com.example.aimew.tvshowapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.aimew.tvshowapp.controllers.TvShowController;
import com.example.aimew.tvshowapp.events.ErrorEvent;
import com.example.aimew.tvshowapp.events.SuccessAddTvShow;
import com.example.aimew.tvshowapp.models.TvShow;
import com.squareup.otto.Subscribe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

public class AddTvShowActivity extends AppCompatActivity implements View.OnClickListener,
        Validator.ValidationListener {

    @NotEmpty
    private EditText editTitle;

    @NotEmpty
    private EditText editLanguage;

    @NotEmpty
    private EditText editGenre;

    @NotEmpty
    private EditText editResume;

    @NotEmpty
    private EditText editReleaseDate;

    @NotEmpty
    private EditText editPoster;

    private Button buttonAdd;

    private Validator validator;

    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private ImageButton ibObtenerFecha;

    String title;
    String language;
    String genre;
    String resume;
    String releaseDate;
    String poster;

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
        buttonAdd = (Button) findViewById(R.id.button_add_tvshow);

        buttonAdd.setOnClickListener(this);

        validator = new Validator(this);
        validator.setValidationListener(this);

        ibObtenerFecha = (ImageButton) findViewById(R.id.ib_obtener_fecha);
        ibObtenerFecha.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_add_tvshow:
                title = editTitle.getText().toString();
                language = editLanguage.getText().toString();
                genre = editGenre.getText().toString();
                resume = editResume.getText().toString();
                releaseDate = editReleaseDate.getText().toString();
                poster = editPoster.getText().toString();

                validator.validate();

                if (title == null || title.isEmpty()) {
                    editTitle.setError(getText(R.string.error_require_value) + " title");
                }

                if (language == null || language.isEmpty()) {
                    editLanguage.setError(getText(R.string.error_require_value) + " language");
                }

                if (genre == null || genre.isEmpty()) {
                    editGenre.setError(getText(R.string.error_require_value) + " genre");
                }

                if (resume == null || resume.isEmpty()) {
                    editResume.setError(getText(R.string.error_require_value) + " resume");
                }

                if (releaseDate == null || releaseDate.isEmpty()) {
                    editReleaseDate.setError(getText(R.string.error_require_value) + " releaseDate");
                }

                if (poster == null || poster.isEmpty()) {
                    editPoster.setError(getText(R.string.error_require_value) + " poster");
                }
                break;

            case R.id.ib_obtener_fecha:
                obtenerFecha();
                break;
        }
    }

    private void postTvShow() throws ParseException {
        TvShow tvShow = new TvShow();
        tvShow.setName(title);
        tvShow.setLanguage(language);
        tvShow.setGenre(genre);
        tvShow.setResume(resume);
        tvShow.setPoster(poster);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Calendar.getInstance().getTime();
        String releaseDateFormat = df.format(date);
        tvShow.setReleaseDate(releaseDateFormat);

        TvShowController.addTvShow(this, tvShow);
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

    @Override
    public void onValidationSucceeded() {
        try {
            this.postTvShow();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                editReleaseDate.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
}

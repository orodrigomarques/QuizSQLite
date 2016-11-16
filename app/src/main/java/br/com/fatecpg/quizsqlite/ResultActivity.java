package br.com.fatecpg.quizsqlite;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
    private double result;
    private ArrayList<History> hist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //cria a rating bar de score do usuario
        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(1f);
        //text view com mensagem mais nota
        TextView t = (TextView) findViewById(R.id.textResult);

        //exibe o score do jogador
        Bundle b = getIntent().getExtras();
        result = b.getDouble("result");
        if(result == 0) {
            bar.setRating(0);
            t.setText("Sua nota é: " + result + " Você DEVE estudar mais");

        }else if(result < 20) {
            bar.setRating(1);
            t.setText("Sua nota é: " + result + " Você precisa estudar mais");

        }else if(result < 40) {
            bar.setRating(2);
            t.setText("Sua nota é: " + result + " Desempenho +-");

        }else if(result < 60) {
            bar.setRating(3);
                t.setText("Sua nota é: "+ result +" Muito bom!!" );

        }else if(result < 80) {
            bar.setRating(4);
            t.setText("Sua nota é: "+ result +" Muito bom!!" );

        }else {
            bar.setRating(5);
                t.setText("Sua nota é: "+ result +" Excelente" );

        }
    }



    public void inicio(View view){

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    public void history (View view){
        Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(i);
        finish();
    }



}

package br.com.fatecpg.quizsqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import br.com.fatecpg.quizsqlite.db.QuestionsSQLiteHelp;

public class TestActivity extends AppCompatActivity {

    QuestionsSQLiteHelp dbHelper = null;
    SQLiteDatabase db = null;
    ArrayList<Question> quiz = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    public int position = 0;
    public int qtd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        try {
            dbHelper = new QuestionsSQLiteHelp(getApplicationContext());
        }catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getLocalizedMessage()).setPositiveButton("OK",null).show();
        }

        qtd = getIntent().getIntExtra("qtq", 1);

        createTest();
        clearAnswers();
        refreshQuestion();
    }

    private void createTest(){
        db = dbHelper.getReadableDatabase();
        Question q;
        Cursor cursor = db.rawQuery("SELECT * FROM questions", null);
        cursor.moveToFirst();
        String[] opt;
        while (!cursor.isAfterLast()){
            q = new Question();
            q.question = cursor.getString(1);
            q.options = new String[4];
            q.answer = cursor.getString(2);
            opt = cursor.getString(3).split("\",");
            for(int j = 0; j < opt.length ; j++){
                String out = opt[j].replace(']',' ').replace('[', ' ').replace('\"', ' ').trim();
                q.options[j] = out;
            }
            questions.add(q);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        dbHelper.close();

        Collections.shuffle(quiz);
        for (int i = 1; i <= qtd; i++){
            quiz.add(questions.get(i));
        }
    }

    private void refreshQuestion(){
        Question q = quiz.get(position);
        Question q2 = questions.get(position);

        TextView posTextView = (TextView) findViewById(R.id.positionTextView);
        posTextView.setText((position+1)+" de " + qtd);

        TextView qTextView = (TextView) findViewById(R.id.questionTextView);
        qTextView.setText(q.question);

        RadioButton opt1 = (RadioButton) findViewById(R.id.option1Radio);
        opt1.setText(q.options[0]);

        RadioButton opt2 = (RadioButton) findViewById(R.id.option2Radio);
        opt2.setText(q.options[1]);

        RadioButton opt3 = (RadioButton) findViewById(R.id.option3Radio);
        opt3.setText(q.options[2]);

        RadioButton opt4 = (RadioButton) findViewById(R.id.option4Radio);
        opt4.setText(q.options[3]);

        RadioGroup group = (RadioGroup)findViewById(R.id.optionGroup);
        group.check(0);

        if(answers.get(position).equals(opt1.getText()))
            group.check(R.id.option1Radio);
        else if(answers.get(position).equals(opt2.getText()))
            group.check(R.id.option2Radio);
        else if(answers.get(position).equals(opt3.getText()))
            group.check(R.id.option3Radio);
        else if(answers.get(position).equals(opt4.getText()))
            group.check(R.id.option4Radio);

    }

    public void back(View view){
        if(position > 0){
            position--;
            refreshQuestion();
        }
    }

    public void next(View view){
        if(position < quiz.size()-1){
            position++;
            refreshQuestion();
        }
        else if(position == quiz.size()-1){
            Button btn = (Button) findViewById(R.id.btnFinish);
            btn.setVisibility(View.VISIBLE);
        }
    }

    private void clearAnswers(){
        for(Question question: quiz){
            answers.add("");
        }
    }

    public void optionSelection(View view){
        RadioButton opt = (RadioButton)findViewById(view.getId());
        answers.set(position, opt.getText().toString());
    }

    public void finish(View view){
        int sum = 0;
        for(int i=0; i<quiz.size(); i++){
            if(quiz.get(i).answer.equals(answers.get(i))){
                sum++;
            }
        }
        double result = 100.0 * (double)sum / (double)quiz.size();

        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
        i.putExtra("result", result);

        startActivity(i);

        finish();
    }
}
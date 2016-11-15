package br.com.fatecpg.quizsqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;

import java.util.Arrays;

import br.com.fatecpg.quizsqlite.db.QuestionsSQLiteHelp;


public class MainActivity extends AppCompatActivity {

    QuestionsSQLiteHelp dbHelper = null;
    SQLiteDatabase db = null;
    JSONArray arr_options = null;
    int total_q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            dbHelper = new QuestionsSQLiteHelp(getApplicationContext());
        }catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getLocalizedMessage()).setPositiveButton("OK",null).show();
        }

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM questions",null);
        cursor.moveToFirst();

        total_q  = cursor.getInt(0);

        if(total_q == 0){
            defaultQuestions();
        }
    }

    private void defaultQuestions(){
        db = dbHelper.getWritableDatabase();

        Question q = new Question();

        q.question = "Analise as seguintes frases e assinale a alternativa correta: I. Conjunto de programas. II. Usuários ou profissionais em TI. III. Parte física do computador.";
        q.answer = "I- Software, II- Peopleware, III- Hardware.";
        q.options = new String[]{   "I- Hardware, II- Software, III- Firmware.",
                "I- Software, II- Firmware, III- Hardware.",
                "I- Software, II- Peopleware, III- Hardware.",
                "I- Software, II- Tupperware, III- Firmware.",
        };

        arr_options = new JSONArray(Arrays.asList(q.options));
        db.execSQL("INSERT INTO questions (question, answer, options) VALUES ('"+q.question+"','"+q.answer+"','"+arr_options+"')");

        //2
        q = new Question();
        q.question = "Originalmente, o único produto da atividade de Projeto que é realizado como parte do processo XP:";
        q.answer = "São os cartões CRC.";
        q.options = new String[]{   "São os cartões CRC.",
                "São os diagramas de objetos.",
                "É a codificação, feita em pares.",
                "São os diagramas de seqüência."
        };

        arr_options = new JSONArray(Arrays.asList(q.options));
        db.execSQL("INSERT INTO questions (question, answer, options) VALUES ('"+q.question+"','"+q.answer+"','"+arr_options+"')");

        //3
        q = new Question();
        q.question = "Em HTML ára colocar uma barra horizontal em sua página, qual tag devemos usar?";
        q.answer = "<hr/>";
        q.options = new String[]{
                "<hr/>",
                "<br/>",
                "<hr></hr>",
                "<br></br>"
        };

        arr_options = new JSONArray(Arrays.asList(q.options));
        db.execSQL("INSERT INTO questions (question, answer, options) VALUES ('"+q.question+"','"+q.answer+"','"+arr_options+"')");


        //4
        q = new Question();
        q.question = "Todas as afirmações abaixo são verdadeiras, exceto:";
        q.answer = "No diagrama de blocos, o retângulo não representa a entrada de dados.";
        q.options = new String[]{   "O diagrama de blocos é formado apenas por figuras geométricas.",
                "O círculo no diagrama de blocos representa um conector.",
                "O losângo, no diagrama de blocos, representa decisão.",
                "No diagrama de blocos, o retângulo não representa a entrada de dados."
        };

        arr_options = new JSONArray(Arrays.asList(q.options));
        db.execSQL("INSERT INTO questions (question, answer, options) VALUES ('"+q.question+"','"+q.answer+"','"+arr_options+"')");


        //5
        q = new Question();
        q.question = "Assinale a alternativa que não contem uma propriedade de Visual Basic.";
        q.answer = "Label";
        q.options = new String[]{
                "Name",
                "Label",
                "Text",
                "BackColor"
        };

        arr_options = new JSONArray(Arrays.asList(q.options));
        db.execSQL("INSERT INTO questions (question, answer, options) VALUES ('"+q.question+"','"+q.answer+"','"+arr_options+"')");

        db.close();
        dbHelper.close();
    }

    public void initTest(View view){
        EditText qtq = (EditText) findViewById(R.id.questionNumber);

        if(qtq.getText().toString().trim().isEmpty()){
            qtq.setError("É necessário digitar um número!");
        }else if((Integer.parseInt(qtq.getText().toString()) == 0)){
            qtq.setError("É necessário digitar um número maior que 0");
        }else if((Integer.parseInt(qtq.getText().toString()) > 30)){
            qtq.setError("É necessário digitar um número até 30");
        }else{
            String qt = qtq.getText().toString();
            Intent it = new Intent(this, TestActivity.class);
            it.putExtra("qtq", Integer.parseInt(qt));
            startActivity(it);
            finish();
        }
    }
}

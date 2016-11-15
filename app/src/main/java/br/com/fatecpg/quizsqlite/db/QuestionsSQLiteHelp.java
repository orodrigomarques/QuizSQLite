package br.com.fatecpg.quizsqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionsSQLiteHelp extends SQLiteOpenHelper{
    public QuestionsSQLiteHelp(Context context){
        super(context,"questions.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(""+
                "CREATE TABLE IF NOT EXISTS questions("+
                "id INT AUTO_INCREMENT,"+
                "question varchar NOT NULL,"+
                "answer varchar NOT NULL,"+
                "options varchar NOT NULL,"+
                "primary key(id)"+
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TASKS");
        onCreate(sqLiteDatabase);

    }
}

package br.com.fatecpg.quizsqlite;

import java.text.NumberFormat;

public class History{
    private String data;
    private double  nota;

    public double getNote() {
        return nota;
    }

    public String getDate() {
        return data;
    }

    public void setDate(String date) {
        this.data = date;
    }

    public void setNote(double note) {
        this.nota = note;
    }

}

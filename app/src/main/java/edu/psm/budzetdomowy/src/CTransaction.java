package edu.psm.budzetdomowy.src;

import java.util.Date;

public class CTransaction {
    public String id;
    public float value;
    public Date date;
    public int type;
    public String category;
    public String note;

    public CTransaction(String id, float value, Date date, int type, String category, String note) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.type = type;
        this.category = category;
        this.note = note;
    }
}

package edu.psm.budzetdomowy.src;

import java.util.Date;

public class CTransaction {
    public int id;
    public float value;
    public Date date;
    public int type;
    public String category;
    public String note;

    /**
     * Konstruktor tworzący transakcję
     * @param id ID
     * @param value Wartośc transakcji
     * @param date Data transakcji
     * @param type Typ transakcji
     * @param category Kategoria transakcji
     * @param note Notatka transakcji
     */
    public CTransaction(int id, float value, Date date, int type, String category, String note) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.type = type;
        this.category = category;
        this.note = note;
    }
}

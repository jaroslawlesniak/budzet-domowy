package edu.psm.budzetdomowy.src;

import java.util.LinkedList;
import java.util.List;

import edu.psm.budzetdomowy.utils.Transaction;

public class CBalanceSummary {
    public float totalValue = 0.0f;
    public List<CTransaction> transactions = new LinkedList();
    public int type;
    public String category;

    /**
     * Konstruktor tworzący obiekt CBalanceSummary
     * @param type Typ transkacji
     * @param category Kategoria tranaskcji
     */
    public CBalanceSummary(int type, String category) {
        this.type = type;
        this.category = category;
    }

    public CBalanceSummary(int type) {
        this.type = type;
    }
}

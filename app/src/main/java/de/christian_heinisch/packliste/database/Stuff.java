package de.christian_heinisch.packliste.database;

/**
 * Created by chris on 02.01.2017.
 */

public class Stuff {

    private String stuff;
    private boolean checked;
    private boolean buy;
    private long id;

    public Stuff(String stuff, boolean checked, boolean buy, long id){

        this.stuff= stuff;
        this.checked = checked;
        this.buy = buy;
        this.id = id;

    }

    public String getStuff() {
        return stuff;
    }

    public void setStuff(String stuff) {
        this.stuff = stuff;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}

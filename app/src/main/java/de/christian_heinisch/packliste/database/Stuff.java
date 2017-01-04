package de.christian_heinisch.packliste.database;

/**
 * Created by chris on 02.01.2017.
 */

public class Stuff {

    private String stuff;
    private boolean checked;
    private boolean buy;
    private long id;
    private int quantitiy;
    private int cityid;

    public Stuff(String stuff, boolean checked, boolean buy, int quantitiy, int cityid, long id){

        this.stuff= stuff;
        this.checked = checked;
        this.buy = buy;
        this.id = id;
        this.quantitiy = quantitiy;
        this.cityid = cityid;

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

    public void setQuantitiy(int quantitiy) {
        this.quantitiy = quantitiy;
    }

    public int getQuantitiy(){
        return quantitiy;
    }

    public int getCityid() {
        return cityid;
    }

    public void setCityid(int cityid) {
        this.cityid = cityid;
    }
}

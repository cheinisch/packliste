package de.christian_heinisch.packliste.database;

/**
 * Created by chris on 02.01.2017.
 */

public class Stuff {

    private String stuff;
    private String checked;
    private String buy;
    private long id;
    private int quantitiy;
    private int cityid;

    public Stuff(String stuff, String checked, String buy, int quantitiy, int cityid, long id){

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

    public String isChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String isBuy() {
        return buy;
    }

    public void setBuy(String buy) {
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

    @Override
    public String toString() {
        String output = stuff + " x " + quantitiy;

        return output;
    }
}

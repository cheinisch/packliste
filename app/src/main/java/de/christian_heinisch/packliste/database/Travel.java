package de.christian_heinisch.packliste.database;


/**
 * Created by chris on 02.01.2017.
 */

public class Travel {

    private String city;
    private int startdate;
    private int enddate;
    private long id;

    public Travel(String city, int startdate, int enddate, long id){

        this.city = city;
        this.startdate = startdate;
        this.enddate = enddate;
        this.id = id;

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStartdate() {
        return startdate;
    }

    public void setStartdate(int startdate) {
        this.startdate = startdate;
    }

    public int getEnddate() {
        return enddate;
    }

    public void setEnddate(int enddate) {
        this.enddate = enddate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String output = city;

        return output;
    }



}

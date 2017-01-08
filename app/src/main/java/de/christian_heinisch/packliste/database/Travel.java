package de.christian_heinisch.packliste.database;


/**
 * Created by chris on 02.01.2017.
 */

public class Travel {

    public String city;
    public long startdate;
    public long enddate;
    private long id;

    public Travel(String city, long startdate, long enddate, long id){

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

    public long getStartdate() {
        return startdate;
    }

    public void setStartdate(long startdate) {
        this.startdate = startdate;
    }

    public long getEnddate() {
        return enddate;
    }

    public void setEnddate(long enddate) {
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

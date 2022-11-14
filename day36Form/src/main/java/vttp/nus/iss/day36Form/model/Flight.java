package vttp.nus.iss.day36Form.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Flight {


    private String email;
    private Integer adult;
    private String origin;
    private String destination;
    private String date;


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAdult() {
        return this.adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public static Flight create(String payload) {
        

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject rs = reader.readObject();

        Flight p = new Flight();
        p.setEmail(rs.getString("email"));
        p.setAdult(rs.getInt("adult"));
        p.setOrigin(rs.getString("origin"));
        p.setDestination(rs.getString("destination"));
        p.setDate(rs.getString("date"));
        System.out.println(rs.getString("date"));

        return p;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("email", email)
            .add("adult", adult)
            .add("origin", origin)
            .add("destination", destination)
            .add("date", date)
            .build();
    }
    
}

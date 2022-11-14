package vttp.nus.iss.day36Form.model;

import java.sql.SQLException;


import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Login {

    private String email;
    private String password;


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Login create(SqlRowSet rs) throws SQLException{
        Login p = new Login();
        p.setEmail(rs.getString("email"));
        p.setPassword(rs.getString("password"));
        return p;
    }

    public static Login create(String payload) {
        

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject rs = reader.readObject();

        Login p = new Login();
        p.setEmail(rs.getString("email"));
        p.setPassword(rs.getString("password"));

        return p;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("email", email)
            .add("password", password)
            .build();
    }

    
}

package vttp.nus.iss.day36Form.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.nus.iss.day36Form.model.Flight;
import vttp.nus.iss.day36Form.model.Login;

import static vttp.nus.iss.day36Form.repositories.Queries.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository 
public class FlightRepo {

    @Autowired
    private JdbcTemplate template;

    public int insertUser(Login user) {

        int count = 0;

        if (getEmail(user.getEmail())==0) {

            count = template.update(SQL_INSERT_USER,
            user.getEmail(), user.getPassword());

        }
        return count;
    }

    public int getEmail(String email) {

        SqlRowSet rs = template.queryForRowSet(SQL_GET_EMAIL, email);
        if (rs.next()) {
            return 1;
        }

        else 
            return 0;

    }

    public int getUser(Login user) {

        SqlRowSet rs = template.queryForRowSet(SQL_GET_USER, user.getEmail(), user.getPassword());
        if (rs.next()) {
            return 1;
        }

        else 
            return 0;

    }

    public List<Login> getEmails(String email) throws SQLException {

        SqlRowSet rs = template.queryForRowSet(SQL_GET_EMAIL, email);
        List<Login> orders = new ArrayList<>();
        while(rs.next()) {
            orders.add(Login.create(rs));
        }
        return orders;

    }

    public static Login convert(SqlRowSet rs) {
        Login bff = new Login();
        bff.setEmail(rs.getString("email"));
        bff.setPassword(rs.getString("password"));
        return bff;
    }

    public int insertFlight(Flight user) {

        int count = 0;


        count = template.update(SQL_INSERT_USER, user.getEmail(), user.getAdult(), user.getOrigin(), 
        user.getOrigin(), user.getDestination(), user.getDate());

        return count;
    }
    
}

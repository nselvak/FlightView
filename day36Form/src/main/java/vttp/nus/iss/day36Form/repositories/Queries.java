package vttp.nus.iss.day36Form.repositories;

public class Queries {

    public static final String SQL_INSERT_USER = "insert into login(email, password) values (?, ?)";
    public static final String SQL_GET_USER = "select * from login where email =? and password=?";
    public static final String SQL_GET_EMAIL = "select * from login where email =?";

    public static final String SQL_INSERT_FLIGHT = "insert into flight(email, adult, origin, destination, date) values (?, ?, ?, ?, ?)";



    
}

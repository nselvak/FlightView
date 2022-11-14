package vttp.nus.iss.day36Form.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.nus.iss.day36Form.model.Flight;
import vttp.nus.iss.day36Form.model.Form;

import static vttp.nus.iss.day36Form.repositories.Queries.*;

@Service
public class FormService {

    public static final String GIPHY_SEARCH = "https://skyscanner44.p.rapidapi.com/search";

    @Value("${spring.mail.username}")
    private String mail;

    // @Value("${API_KEY}")
    // private String key;
    
    @Autowired
    private JdbcTemplate template;

    public Optional<Form> getPost(Integer postId) {
        return template.query(SQL_GET_EMAIL,
            (ResultSet rs) -> {
                if (!rs.next())
                    return Optional.empty();
                return Optional.of(Form.create(rs));
            },
            postId
        );

    }

    public String getFlights(Flight flight) throws IOException, InterruptedException {

        System.out.println(flight.getDate());

        String url = UriComponentsBuilder.fromUriString(GIPHY_SEARCH)
                .queryParam("adults", flight.getAdult())
                .queryParam("origin", flight.getOrigin())
                .queryParam("destination", flight.getDestination())
                .queryParam("departureDate", flight.getDate())
                .queryParam("currency", "SGD")
                .toUriString(); 
        System.out.println(url);

        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create(url))
		.header("X-RapidAPI-Key","39653ac997msh1f0de2deac9929fp17d509jsnf2f654803273")
		.header("X-RapidAPI-Host", "skyscanner44.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        JsonReader reader = Json.createReader(new StringReader(response.body()));
        JsonObject gifs = reader.readObject();
        JsonObject gifs1 = gifs.getJsonObject("itineraries");

        JsonArray data = gifs1.getJsonArray("buckets");
        System.out.println(data.toString());


        return data.toString();


    }

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to) {

        try {
 
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom(mail);
            message.setTo(to); 
            message.setSubject("Welcome to Flight Viewer"); 
            message.setText("This is the email sent upon successful registration!");
            emailSender.send(message);
            System.out.println("Mail Sent Successfully...");
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println(e);
            System.out.println("Error while Sending Mail");
        }

    }

    
}

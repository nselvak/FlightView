package vttp.nus.iss.day36Form.controller;

import java.io.StringReader;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.nus.iss.day36Form.model.Flight;
import vttp.nus.iss.day36Form.model.Login;
import vttp.nus.iss.day36Form.model.Response;
import vttp.nus.iss.day36Form.repositories.FlightRepo;
import vttp.nus.iss.day36Form.service.FormService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormRESTController {


    private Logger logger = Logger.getLogger(FormRESTController.class.getName());

    @Autowired
    private FlightRepo repo;

    @Autowired
    private FormService flightSvc;

   // check if user is registered and email + pw is correct
   @PostMapping(path="/display", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUsers(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject rs = reader.readObject();


        logger.info("Payload: %s".formatted(payload));

        
        Login reg;
        Response resp;

        try {
            reg = Login.create(payload);
            
            // Registration
            if (rs.getInt("key") == 0) {
                flightSvc.sendSimpleMessage(reg.getEmail());
                resp = new Response();
                resp.setCode(repo.insertUser(reg));
                resp.setMsg("Insert User");
                resp.setData(reg.toJson().toString());

                logger.info("Registration: %s".formatted(resp.toJson().toString()));

        
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(resp.toJson().toString());
            
            // login 
            } else {
                resp = new Response();
                resp.setCode(repo.getUser(reg));
                resp.setMsg("Check User");
                resp.setData(reg.toJson().toString());

                logger.info("Login: %s".formatted(resp.toJson().toString()));

        
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(resp.toJson().toString());

            }
            
        } catch (Exception e) {
            // TODO: handle exception
            resp = new Response();
            resp.setCode(400);
            resp.setMsg(e.getMessage());
            logger.info("Error: %s".formatted(resp.toJson().toString()));

            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        }
    }

    @GetMapping(path="/display/{email}")
    public ResponseEntity<String> getByEmail(@PathVariable String email) {
        try {
            List<Login> orderSummary = repo.getEmails(email);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            orderSummary.forEach(v->{
                arrayBuilder.add(v.toJson());
            });
            
            return ResponseEntity.ok(arrayBuilder.build().toString());

        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path="/up", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSearch(@RequestBody String payload) {

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject rs = reader.readObject();

        logger.info("Payload: %s".formatted(payload));

        
        Flight reg;
        Response resp;

        try {
            reg = Flight.create(payload);

            resp = new Response();
            resp.setCode(200);
            resp.setMsg("Insert Flight");
            logger.info("Registration: %s".formatted(resp.toJson().toString()));


            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(flightSvc.getFlights(reg));

            
        } catch (Exception e) {
            // TODO: handle exception
            resp = new Response();
            resp.setCode(400);
            resp.setMsg(e.getMessage());
            logger.info("Error: %s".formatted(resp.toJson().toString()));

            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        }

        
    }

    
}

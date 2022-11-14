package vttp.nus.iss.day36Form.model;


import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Response {

    private int code = 0;
    private String msg = "";
    private String data = "{}";

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("message", msg)
                .add("code", code)
                .add("data", data)
                .build();
    }

    
}

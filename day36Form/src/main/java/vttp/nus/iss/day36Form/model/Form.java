package vttp.nus.iss.day36Form.model;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Form {

    private Integer postId;
    private String title;
    private String mediatype;
    private byte[] content;


    public Integer getPostId() {
        return this.postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediatype() {
        return this.mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }


    public static Form create(ResultSet rs) throws SQLException{
        Form p = new Form();
        p.setPostId(rs.getInt("post_id"));
        p.setTitle(rs.getString("title"));
        p.setMediatype(rs.getString("media_type"));
        p.setContent(rs.getBytes("pic"));
        return p;
    }
    
}

package models;
 
import java.util.*;
import javax.persistence.*;

import java.net.URL;
import util.Util;

import play.db.jpa.*;
 
@Entity
public class Background extends Model {
 
    @Column(unique=true)
    public String filename;
    public String author;
    public String title;
    public URL url;

    // create Background
    public Background(String filename, String author, String title, String addr) {
        
        this.filename = filename;
        this.author = author;
        this.title = title;
        
        URL u = null;
        try {
            u = new URL(Util.fixURL(addr));
        } catch (Exception e) {
            // chuck the url if we can't parse it
            u = null;
        }
        this.url = u;
    }
    
}

package models;
 
import java.util.*;
import javax.persistence.*;

import org.apache.commons.lang.WordUtils;

import play.db.jpa.*;
 
@Entity
public class User extends Model {
 
    @Column(unique=true)
    public String userid;
    public String name;
    
    public Date created;
    
    //@ManyToMany(mappedBy="tags")
    //public List<Item> items;

    public static User findByUserID(String userid) {

        List<User> users = User.find("byUserid", userid).fetch();

        if (users.size() > 0) {
            return users.get(0);
        }

        // else return null
        return null;
    }

}

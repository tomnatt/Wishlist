package models;
 
import java.util.*;
import javax.persistence.*;

import org.apache.commons.lang.WordUtils;

import play.db.jpa.*;
 
@Entity
public class Tag extends Model {
 
    @Column(unique=true)
    public String name;
    
    public String displayname;
    public Date created;
    
    @ManyToMany(mappedBy="tags")
    public List<Item> items;

    // create Tag
    public Tag(String name) {
        this.name = name.toLowerCase();

        this.displayname = WordUtils.capitalize(name);
        this.created = new Date();
        this.items = new ArrayList<Item>();
    }
    
    public static Tag findByName(String name) {
        return (Tag)Tag.find("byName", name.toLowerCase()).fetch().get(0);
    }

}

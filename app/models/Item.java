package models;
 
import java.util.*;
import javax.persistence.*;

import util.Util;
import java.net.URL;
import org.apache.commons.lang.StringUtils;
 
import play.db.jpa.*;
 
@Entity
public class Item extends Model {
 
    @Column(unique=true)
    public String name;
    public String fullname;
    public URL url;
    
    public Date created;
    public boolean archived;
    public Date dateArchived; // note this date is meaningless if archived=false
    
    @ManyToMany
    public Set<Tag> tags;

    // create an Item, generating the shortname
    public Item(String fullname, String addr) {
        this(fullname.replaceAll("\\W", "").toLowerCase(), fullname, addr);
    }
    
    // create an Item with no URL, generating the shortname
    public Item(String fullname) {
        this(fullname.replaceAll("\\W", "").toLowerCase(), fullname, null);
    }

    // create Item with a URL
    public Item(String name, String fullname, String addr) {
        this.name = name;
        this.fullname = fullname;
        
        // if addr is empty set it to null
        if (!StringUtils.isEmpty(addr)) {
            
            addr = Util.fixURL(addr);
            
            try {
                this.url = new URL(addr);
            } catch (Exception e) {
                // discard url if we can't parse it
                this.url = null;
            }
            
        } else {
            this.url = null;
        }
        
        this.created = new Date();
        this.archived = false;
        this.tags = new HashSet<Tag>();
    }
    
    // TODO really need some tests for these
    
    public static Item findByName(String name) {
        return (Item)Item.find("byName", name.toLowerCase()).fetch().get(0);
    }
    
    public static List findActive() {
        return Item.find("select i from Item i where archived = false").fetch();
    }
    
    public static List findActiveByTag(Tag tag) {
        return Item.find("SELECT i FROM Item i WHERE " + tag.id + " MEMBER OF i.tags AND i.archived = false").fetch();
    }
    
    public static List findArchived() {
        return Item.find("select i from Item i where archived = true").fetch();
    }
    
    public static List findArchivedByTag(Tag tag) {
        return Item.find("SELECT i FROM Item i WHERE " + tag.id + " MEMBER OF i.tags AND i.archived = true").fetch();
    }
    
}

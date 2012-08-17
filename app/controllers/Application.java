package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import java.net.URL;
import org.apache.commons.lang.StringUtils;

import models.*;
import util.Util;

public class Application extends Controller {

    @Before(unless={"testData"})
    static void checkAuthenticated() {
        if (!session.contains("user")) {
            Security.login();
        }
    }

    public static void index(String all) {
        
        List<Item> items = Item.findActive();
        List<Item> archivedItems = null;
        List<Tag> tags = Tag.findAll();
        
        if (!StringUtils.isEmpty(all)) {
            // get the archived items if requested
            archivedItems = Item.findArchived();
        }

        render(items, archivedItems, tags);
    }
    
    public static void testData() {
        
        // TODO move this to a Bootstrap method and replace with Fixtures.loadModel()
        
        Tag t1 = new Tag("book");
        t1.save();
        Tag t2 = new Tag("person");
        t2.save();
        Tag t3 = new Tag("instrument");
        t3.save();

        Item i1 = new Item("learnflute", "Learn the Flute", "http://www.garethgwynn.com");
        i1.tags.add(t1);
        i1.tags.add(t3);
        i1.save();
        
        Item i2 = new Item("tomnatt", "Tom Natt", "www.tomnatt.com");
        i2.tags.add(t2);
        i2.save();
        
        Item i3 = new Item("bible", "Bible", "http://google.com");
        i3.tags.add(t1);
        i3.save();
        
        index("");
    }
    
    public static void itemForm(String itemname) {
        
        // get all of the tags for adding some more
        List<Tag> tags = Tag.findAll();
        
        if (StringUtils.isEmpty(itemname)) {
            // we are adding a new item
            render(tags);
        
        } else {
            // else we are editing an existing item
            Item item = null;
            if (!StringUtils.isEmpty(itemname)) {
                item = Item.findByName(itemname);
            }
            
            // put the item id into the flash scope
            flash.put("id", item.id);
            
            ArrayList<String> tagnames = new ArrayList<String>();
            for (Tag tag : item.tags) {
                tagnames.add(tag.name);
            }
            String tagstring = StringUtils.join(tagnames, ", ");
            
            render(item, tagstring, tags);
        }
    }
    
    public static void saveItem(String name, String url, String tags, String archived) {
        
        // chuck the input if it's empty
        if (!StringUtils.isEmpty(name)) {
            
            Item i = null;
            
            // if we have an item id in scope we're updating
            if (!StringUtils.isEmpty(flash.get("id"))) {
                // get the item and update it 
                i = Item.findById(Long.parseLong(flash.get("id")));
                i.fullname = name;
                
            } else {
                // else it's a new thing
                i = new Item(name);
            }
            
            // add the URL
            URL u = null;
            try {
                u = new URL(Util.fixURL(url));
            } catch (Exception e) {
                u = null;
            }
            i.url = u;
            
            // process the tags
            Set<Tag> taglist = new HashSet<Tag>();
            
            for (String t : tags.split(",")) {
                
                t = t.trim();
                Tag tag = null;
                
                try {
                    // if it already exists, get it
                    tag = Tag.findByName(t);
                } catch (IndexOutOfBoundsException e) {
                    // else create it
                    tag = new Tag(t);
                    tag.save();
                }
                
                // then add it
                taglist.add(tag);
                
            }
            
            i.tags = taglist;
            
            // archive if required
            if (!StringUtils.isEmpty(archived)) {
                i.archived = true;
                i.dateArchived = new Date();
            } else {
                i.archived = false;
                i.dateArchived = null;
            }
            
            // save it
            i.save();
        }
        
        index("");
    }

    public static void listByTag(String tag, String all) {
        Tag t = Tag.findByName(tag);
        List<Item> items = Item.findActiveByTag(t);
        List<Item> archivedItems = null;
        
        if (!StringUtils.isEmpty(all)) {
            // get the archived items if requested
            archivedItems = Item.findArchivedByTag(t);
        }
        render(t, items, archivedItems);
    }

}

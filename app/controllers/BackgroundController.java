package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import models.*;
import util.*;

public class BackgroundController extends Controller {
    
    // look for an image and give the filepath
    public static void index() {
        
        // if there is an id in flash, keep it
        flash.keep("id");
        
        // pick a random one
        List<Background> bgs = Background.findAll();
        Background background = null;
        
        if (bgs.size() > 1) {
            int index = new Random().nextInt(bgs.size());
            background = bgs.get(index);
        } else if (bgs.size() == 1) {
            background = bgs.get(0);
        } else {
            // we'll only get this far if no backgrounds have ever been added to the database
            reload();
        }
        
        renderJSON(background);
        
    }
    
    // clear all backgrounds and reload
    public static void reload() {

        Background.deleteAll();
        BackgroundManagement.loadBackgrounds();
        index();
        
    }
    
}

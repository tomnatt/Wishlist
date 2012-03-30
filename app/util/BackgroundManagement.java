package util;
 
import models.*;

import java.util.HashMap;

import org.yaml.snakeyaml.Yaml;

public class BackgroundManagement {
    
    // TODO replace this with Fixtures.loadModel() - then remove this file entirely
    
    // load the backgrounds from the yaml config file
    public static void loadBackgrounds() {
        
        try {
            
            // load the config file and cast to a hashmap
            String input = play.vfs.VirtualFile.fromRelativePath("/conf/backgrounds.yml").contentAsString();
            Yaml yaml = new Yaml();
            HashMap data = (HashMap)yaml.load(input);

            // load the input into proper objects
            for (Object d : data.values()) {
                
                // cast as a HashMap so we can actually get at the data
                HashMap<String, String> bg = (HashMap<String, String>)d;
                
                // finally add the background to a list of backgrounds
                Background background = new Background(bg.get("filename"), bg.get("author"), bg.get("title"), bg.get("url"));
                background.save();
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}

package controllers;

import play.*;
import play.mvc.*;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;

import java.util.*;

import models.*;

public class Login extends Controller {

    public static void login() {
        render();
    }

    public static void authenticate(String user) {
        
        if (OpenID.isAuthenticationResponse()) {

            UserInfo verifiedUser = OpenID.getVerifiedID();
            session.put("user", verifiedUser.id);
            
            if (User.findByUserID(verifiedUser.id) == null) {
                // if user doesn't exist, send them off to create an account
                newUserNameInput();
            }

            Application.index(null);

        } else {
            OpenID.id(user).verify(); // will redirect the user
        }
    }

    // new user

    public static void newUserNameInput() {
        
        if (session.contains("user")) {
            render();
        } else {
            login();
        }
        
    }
    
    public static void newUserNameSave(String name) {
        
        if (session.contains("user")) {
            User u = new User(session.get("user"));
            u.name = name;
            u.save();
            
            Application.index(null);
            
        } else {
            login();
        }
        
    }

}
                                       

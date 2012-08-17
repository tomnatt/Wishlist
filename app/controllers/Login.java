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

            if (User.findByUserID(verifiedUser.id) == null) {
                // user doesn't exist, fail auth
                verifiedUser = null;
            }

            if (verifiedUser == null) {
                flash.put("error", "failed");
                login();
            }
            session.put("user", verifiedUser.id);
            Application.index(null);

        } else {
            OpenID.id(user).verify(); // will redirect the user
        }
    }

    // new user

    public static void newUser() {
        render();
    }

    public static void newUserAuthenticate(String user) {

        // TODO something different to this

        if (OpenID.isAuthenticationResponse()) {

            UserInfo verifiedUser = OpenID.getVerifiedID();

            if (User.findByUserID(verifiedUser.id) == null) {
                // user doesn't exist, fail auth
                verifiedUser = null;
            }

            if (verifiedUser == null) {
                flash.put("error", "failed");
                login();
            }
            session.put("user", verifiedUser.id);
            Application.index(null);

        } else {
            OpenID.id(user).verify(); // will redirect the user
        }
    }

}
                                       

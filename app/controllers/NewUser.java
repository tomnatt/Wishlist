package controllers;

import play.*;
import play.mvc.*;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;

import java.util.*;

import models.*;

public class NewUser extends Controller {

    @Before(unless={"newUser"})
    static void checkAuthenticated() {
        if (!session.contains("user")) {
            Security.login();
        }
    }

    public static void newUser() {
        render();
    }


}
                                       

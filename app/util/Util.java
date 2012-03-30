package util;
 
public class Util {
    
    // convenience method to sort out the url input
    public static String fixURL(String addr) {
        // add the protocol if it is missing
        if (!addr.startsWith("http")) {
            addr = "http://" + addr;
        }
        return addr;
    }
    
}

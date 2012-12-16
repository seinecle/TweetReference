/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

/**
 *
 * @author C. Levallois
 */
public class AdminPanel {

    public static boolean isDebugMode() {
        return false;
    }

    //the twitter callback returns to localhost
    public static boolean isLocal() {
        return true;
    }
    //the Mongo instance is local, not on cloudbees
    public static boolean isMongoLocal() {
        return false;
    }
}

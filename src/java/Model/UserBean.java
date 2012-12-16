/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controllers.ControllerBean;
import OAuth.RequestSender;
import Utils.PrehistoricJsonParser;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import twitter4j.TwitterException;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@RequestScoped
public class UserBean {

    private String screenNameAuthor;
    private String screenNameTarget;
    private String status = "";
    private String counter;
    @ManagedProperty("#{controllerBean}")
    private ControllerBean controllerBean;
    private boolean unknownUser;

    public void setControllerBean(ControllerBean controllerBean) {
        this.controllerBean = controllerBean;
    }

    public UserBean() {
    }

    public String getScreenNameAuthor() {
        return screenNameAuthor;
    }

    public void setScreenNameAuthor(String screenNameAuthor) {
        this.screenNameAuthor = screenNameAuthor;
    }

    public String getScreenNameTarget() {
        return screenNameTarget;
    }

    public void setScreenNameTarget(String screenNameTarget) {
        if (!screenNameTarget.startsWith("@")) {
            screenNameTarget = "@" + screenNameTarget;
        }
        controllerBean.setSearchTerm(screenNameTarget);
        this.screenNameTarget = screenNameTarget.trim();

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCounter() {
        int i = 140 - status.length();
        if (i < 0) {
            return ("<span style=\"color: #E01B1B;\">" + String.valueOf(i) + ", please make your reference shorter</span>");
        } else {
            return String.valueOf(i);

        }
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public boolean isUnknownUser() {
        return unknownUser;
    }

    public void setUnknownUser(boolean unknownUser) {
        this.unknownUser = unknownUser;
    }
    
    

    public String saveAndSearch() throws TwitterException {
        if (140 - status.length() < 0) {
            return null;
        }
        RequestSender requestSender = new RequestSender(controllerBean.getoAuth(), controllerBean.getAccessToken());
        System.out.println("now searching for the person mentioned in the search:" + controllerBean.getSearchTermWithoutArobase());
        String responseString = requestSender.sendRequest("https://api.twitter.com/1.1/users/show.json?screen_name=" + controllerBean.getSearchTermWithoutArobase());
        if (responseString.contains("Sorry, that page does not exist")) {
            unknownUser = true;
            return null;
        }
        System.out.println("response String for API call on search term:");
        System.out.println(responseString);
        User user = PrehistoricJsonParser.getUser(responseString);
        controllerBean.getDs().delete(controllerBean.getDs().createQuery(User.class).field("screen_name").equal(user.getScreen_name()));
        controllerBean.getDs().save(user);

        Tweet tweet = new Tweet();
        tweet.setSource(controllerBean.getAuthor().getScreen_name());
        tweet.setText(this.status);
        tweet.setTarget(this.screenNameTarget);
        tweet.setDate(new Date());
        ControllerBean.ds.save(tweet);
        controllerBean.setListTweets(ControllerBean.ds.createQuery(Tweet.class).field("target").equal(tweet.getTarget()).asList());
        System.out.println("size of list:" + controllerBean.getListTweets().size());
        System.out.println("target: " + this.screenNameTarget);
        return "searchresult";
    }

    class ArrayUsers {

        int id;
        String name;
        ArrayList<User> users;

        ArrayList<User> getUsers() {
            return users;
        }
    }
}

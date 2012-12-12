/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controllers.ControllerBean;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

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
        controllerBean.setSearchTerm(screenNameTarget);
        this.screenNameTarget = screenNameTarget;

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

    public String saveAndSearch() {
        if (140-status.length()<0){
            return null;
        }
        Tweet tweet = new Tweet();
        tweet.setAuthor(controllerBean.getAuthor());
        tweet.setText(this.status);
        tweet.setTarget(this.screenNameTarget);
        tweet.setDate(new Date());
        ControllerBean.ds.save(tweet);
        controllerBean.setListTweets(ControllerBean.ds.createQuery(Tweet.class).field("target").equal(tweet.getTarget()).asList());
        System.out.println("size of list:" + controllerBean.getListTweets().size());
        System.out.println("this screen name:" + this.screenNameTarget);
        return "searchresult";
    }
}

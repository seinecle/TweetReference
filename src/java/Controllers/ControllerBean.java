/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.Author;
import Model.Tweet;
import Utils.APIkeys;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.common.collect.Lists;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@SessionScoped
public class ControllerBean implements Serializable {

    private boolean local = false;
    public static Datastore ds;
    private Set<Tweet> setTweets = new HashSet();
    private String searchTerm;
    private List<Tweet> listTweets;
    private String dummy;
    private static Mongo m;
    private static Morphia morphia;
    private Author author;

    public ControllerBean() {
    }

    @PostConstruct
    private void init() {
        try {


            if (AdminPanel.isMongoLocal()) {
                m = new Mongo();
                morphia = new Morphia();
                ds = morphia.createDatastore(m, "twitteref");
                morphia.map(Tweet.class);

            } else {
                m = new Mongo("linus.mongohq.com", 10088);
                morphia = new Morphia();
                String pass = APIkeys.getMongosecret();

                ds = morphia.createDatastore(m, APIkeys.getMongoKey(), "seinecle", pass.toCharArray());

                if (ds != null) {
                    System.out.println("Morphia datastore on CloudBees / MongoHQ created!!!!!!!");
                }
                morphia.map(Tweet.class);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MongoException ex) {
            Logger.getLogger(ControllerBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Set<Tweet> getSetTweets() {
        return setTweets;
    }

    public String doSearch() {
        searchTerm = searchTerm.replace("@", "");
        System.out.println("search term: " + searchTerm);
        listTweets = ds.createQuery(Tweet.class).field("target").equal(searchTerm).asList();
        System.out.println("listTweets size: " + listTweets.size());
        return "searchresult?faces-redirect=true";
    }

    public String getSearchTerm() {
        if (searchTerm != null) {
            if (!searchTerm.startsWith("@")) {
                searchTerm = "@" + searchTerm;
            }
        }
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setListTweets(List<Tweet> listTweets) {
        this.listTweets = listTweets;
    }

    public List<Tweet> getListTweets() {
        return Lists.reverse(listTweets);
    }

    public Datastore getDs() {
        return ds;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}

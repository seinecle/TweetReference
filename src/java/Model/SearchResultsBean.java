/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controllers.ControllerBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@RequestScoped
public class SearchResultsBean {

    private Tweet reference;
    private List<Tweet> tweets;
    @ManagedProperty("#{controllerBean}")
    private ControllerBean controllerBean;

    public void setControllerBean(ControllerBean controllerBean) {
        this.controllerBean = controllerBean;
    }

    
    
    public SearchResultsBean() {
    }
    
    @PostConstruct
    public void init(){
        tweets = controllerBean.getListTweets();
        
    };
    

    public List<Tweet> getReferences() {
        return tweets;
    }
    
    
}

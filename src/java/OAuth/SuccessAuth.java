/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OAuth;

import Controllers.ControllerBean;
import Model.User;
import Utils.PrehistoricJsonParser;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.xml.sax.InputSource;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@ViewScoped
public class SuccessAuth implements Serializable {

    String oauth_verifier;
    @ManagedProperty("#{oAuth}")
    private OAuth oAuth;
    Token accessToken;
    private boolean successAuth;
    private String dummy;
    @ManagedProperty("#{controllerBean}")
    private ControllerBean controllerBean;

    public void setControllerBean(ControllerBean controllerBean) {
        this.controllerBean = controllerBean;
    }

    public void setoAuth(OAuth oAuth) {
        this.oAuth = oAuth;
    }

    public SuccessAuth() {
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.oauth_verifier = facesContext.getExternalContext().getRequestParameterMap().get("oauth_verifier");
        successAuth = (this.oauth_verifier == null || this.oauth_verifier.equals("")) ? false : true;
    }

    public String toWrite() throws IOException {

        System.out.println("verifier: " + oauth_verifier);
        System.out.println("oAuth is: " + oAuth.toString());
        Verifier v = new Verifier(oauth_verifier);
        try {
            accessToken = oAuth.getService().getAccessToken(oAuth.getRequestToken(), v);
        } catch (NullPointerException npe) {
            successAuth = false;
            return "authsuccess?faces-redirect=true";
        }
        controllerBean.setAccessToken(accessToken);
        controllerBean.setoAuth(oAuth);
        RequestSender requestSender = new RequestSender(oAuth, accessToken);
        String responseString = requestSender.sendRequest("http://api.twitter.com/1.1/account/verify_credentials.json");
        System.out.println("source: " + responseString);
        User user = PrehistoricJsonParser.getUser(responseString);
        controllerBean.setAuthor(user);
        controllerBean.getDs().delete(controllerBean.getDs().createQuery(User.class).field("screen_name").equal(user.getScreen_name()));
        controllerBean.getDs().save(user);
        return "write?faces-redirect=true";
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    public boolean isSuccessAuth() {
        return successAuth;
    }

    public void setSuccessAuth(boolean successAuth) {
        this.successAuth = successAuth;
    }
}

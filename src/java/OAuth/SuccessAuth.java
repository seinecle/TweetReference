/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OAuth;

import Controllers.ControllerBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
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
    private String oauth_token;
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
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.twitter.com/1/account/verify_credentials.xml");
        oAuth.getService().signRequest(accessToken, request); // the access token from step 4
        Response response = request.send();

//        System.out.println(response.getBody());
        String responseString = response.getBody();
        responseString = responseString.replaceAll("&amp;", "and");

//        BufferedReader br = new BufferedReader(new InputStreamReader(response.getStream()));
//        InputSource is = new InputSource(br);
        InputSource is = new InputSource(new StringReader(responseString));
        TwitterAPIresponseParser parser = new TwitterAPIresponseParser(is);
        controllerBean.setAuthor(parser.parse());
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

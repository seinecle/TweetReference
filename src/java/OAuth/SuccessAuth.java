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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
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
@SessionScoped
public class SuccessAuth implements Serializable {

    String oauth_verifier;
    @ManagedProperty("#{oAuth}")
    private OAuth oAuth;
    private String oauth_token;
    Token accessToken;
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

//    @ManagedProperty(value = "#{param.oauth_token}")
//    private String oauth_token;
//    @ManagedProperty(value = "#{param.oauth_verifier}")
//    private String oauth_verifier;
//    public void setOauth_token(String oauth_token) {
//        this.oauth_token = oauth_token;
//    }
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        this.oauth_verifier = facesContext.getExternalContext().getRequestParameterMap().get("oauth_verifier");
    }

    public String toWrite() throws IOException {

        System.out.println("In the toWrite method");
        System.out.println("verifier: " + oauth_verifier);
        System.out.println("oAuth is: " + oAuth.toString());
        Verifier v = new Verifier(oauth_verifier);
        accessToken = oAuth.getService().getAccessToken(oAuth.getRequestToken(), v); // the requestToken you had from step 2

        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.twitter.com/1/account/verify_credentials.xml");
        oAuth.getService().signRequest(accessToken, request); // the access token from step 4
        Response response = request.send();
//        System.out.println(response.getBody());
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getStream()));
        InputSource is = new InputSource(br);
        TwitterAPIresponseParser parser = new TwitterAPIresponseParser(is);
        controllerBean.setAuthor(parser.parse());
        return null;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OAuth;

import Controllers.AdminPanel;
import Utils.APIkeys;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@SessionScoped
public class OAuth implements Serializable {

//    private FacesContext fCtx = FacesContext.getCurrentInstance();
//    private HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
    private Token requestToken;
    private boolean debug = true;
    private String callback;
    private OAuthService service;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public OAuthService getService() {
        return service;
    }

    public void setService(OAuthService service) {
        this.service = service;
    }

    public Token getRequestToken() {
        return requestToken;
    }

    public void authorize() throws IOException {
        if (AdminPanel.isLocal()) {
            callback = "http://localhost:8080/TweetReference/pages/authsuccess.xhtml";

        } else {
            callback = "http://www.tweetreference.org/pages/authsuccess.xhtml";
        }

        service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(APIkeys.getTwitterAPIKey())
                .apiSecret(APIkeys.getTwitterAPIsecret())
                .callback(callback)
                .build();

        requestToken = service.getRequestToken();
        String urlAuthorization = service.getAuthorizationUrl(requestToken);
        FacesContext.getCurrentInstance().getExternalContext().redirect(urlAuthorization);

    }
}

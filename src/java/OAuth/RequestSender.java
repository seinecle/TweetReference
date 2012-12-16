/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OAuth;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

/**
 *
 * @author C. Levallois
 */
public class RequestSender {

    private OAuth oAuth;
    private Token accessToken;

    public RequestSender(OAuth oAuth, Token accessToken) {
        this.oAuth = oAuth;
        this.accessToken = accessToken;
    }

    public String sendRequest(String urlCall) {

        OAuthRequest request = new OAuthRequest(Verb.GET, urlCall);
        oAuth.getService().signRequest(accessToken, request); // the access token from step 4
        Response response = request.send();
        String responseString = response.getBody();
        responseString = StringUtils.replace(responseString, "&amp;", "and");
        responseString = StringUtils.replace(responseString, "&quot;", "");
        responseString = StringUtils.replace(responseString, "&lt;", "");
        responseString = StringUtils.replace(responseString, "&gt;", "");
        responseString = StringUtils.replace(responseString, "&apos;", "");
        responseString = StringEscapeUtils.unescapeHtml4(responseString);
        return responseString;

    }
}

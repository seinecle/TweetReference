/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.User;
import java.util.Scanner;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author C. Levallois
 */
public class PrehistoricJsonParser {

    public static User getUser(String str) {
        boolean location = true;
        boolean description = true;
        boolean name = true;
        boolean screen_name = true;
        boolean followers_count = true;
        boolean profile_image_url = true;
        boolean created_at = true;
        boolean lang = true;
        boolean url = true;
        Scanner s = new Scanner(str).useDelimiter("\":|:\"|,|,\"|,\\{,\\{\"");
        User user = new User();
        int count = 0;
        String currString;
        while (s.hasNext()) {
            if (count == 9) {
                break;
            }
            String token = StringUtils.removeStart(StringUtils.removeEnd(s.next(), "\""), "\"");
            System.out.println("token: " + token);

            if (token.equalsIgnoreCase("location") & location) {
                user.setLocation(StringUtils.removeStart(StringUtils.removeEnd(s.next(), "\""), "\""));
                count++;
                location = false;

            } else if (token.equalsIgnoreCase("name") & name) {
                user.setName(StringUtils.removeStart(StringUtils.removeEnd(s.next(), "\""), "\""));
                count++;
                name = false;

            } else if (token.equalsIgnoreCase("description") & description) {
                currString = StringEscapeUtils.unescapeHtml4(s.next());
                System.out.println("currString unescaped: " + currString);
                user.setDescription(StringUtils.removeStart(StringUtils.removeEnd(currString, "\""), "\""));
                count++;
                description = false;

            } else if (token.equalsIgnoreCase("screen_name") & screen_name) {
                currString = s.next();
                user.setScreen_name(StringUtils.removeStart(StringUtils.removeEnd(currString, "\""), "\""));
                user.setScreen_name_ignorecase(StringUtils.removeStart(StringUtils.removeEnd(currString, "\""), "\"").toLowerCase());
                count++;
                screen_name = false;

            } else if (token.equalsIgnoreCase("followers_count") & followers_count) {
                user.setFollowers_count(StringUtils.removeStart(StringUtils.removeEnd(s.next(), "\""), "\""));
                count++;
                followers_count = false;


            } else if (token.equalsIgnoreCase("profile_image_url") & profile_image_url) {
                user.setProfile_image_url(StringEscapeUtils.unescapeJava(StringUtils.removeStart(StringUtils.removeEnd(s.next(), "\""), "\"")));
                count++;
                profile_image_url = false;

            } else if (token.equalsIgnoreCase("lang") & lang) {
                user.setLang(StringEscapeUtils.unescapeJava(StringUtils.removeStart(StringUtils.removeEnd(s.next(), "\""), "\"")));
                count++;
                lang = false;

            } else if (token.equalsIgnoreCase("created_at") & created_at) {
                user.setCreated_at(StringEscapeUtils.unescapeJava(StringUtils.removeStart(StringUtils.removeEnd(s.next(), "\""), "\"")));
                count++;
                created_at = false;

            } else if (token.equalsIgnoreCase("url") & url) {
                String candidate = s.next();
                if (StringUtils.endsWith(candidate, "\"")) {
                    user.setUrl(StringEscapeUtils.unescapeJava(StringUtils.removeStart(StringUtils.removeEnd(candidate, "\""), "\"")));
                    count++;
                    url = false;
                }

            }
        }
        return user;

    }
}

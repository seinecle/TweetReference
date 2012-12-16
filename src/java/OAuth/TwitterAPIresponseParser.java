/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OAuth;

import Model.User;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author C. Levallois
 */
public class TwitterAPIresponseParser extends DefaultHandler {

    private InputSource is;
    private boolean newName;
    private boolean newScreenName;
    private boolean newLocation;
    private boolean newDescription;
    private boolean newProfile_image_url;
    private boolean newFollowers_count;
    private StringBuilder sbnewName;
    private StringBuilder sbnewScreenName;
    private StringBuilder sbnewLocation;
    private StringBuilder sbnewDescription;
    private StringBuilder sbnewProfile_image_url;
    private StringBuilder sbnewFollowers_count;
    private StringBuilder sbnewUrl;
    private User author;

    public TwitterAPIresponseParser(InputSource newIs) {
        this.is = newIs;
    }

    public User parse() throws IOException {


        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {

            author = new User();
            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse(is, this);



        } catch (SAXException se) {
            System.out.println("SAXException: " + se);
        } catch (ParserConfigurationException pce) {
            System.out.println("ParserConfigurationException: " + pce);
        } catch (IOException ie) {
            System.out.println("IOException: " + ie);
        }

        System.out.println("author:");
        System.out.println(author.getName());
        System.out.println(author.getDescription());
        System.out.println(author.getProfile_image_url());
        return author;

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {



        if (qName.matches("name")) {
            newName = true;
            sbnewName = new StringBuilder();

        }

        if (qName.matches("screen_name")) {
            sbnewScreenName = new StringBuilder();
            newScreenName = true;
        }

        if (qName.matches("location")) {
            sbnewLocation = new StringBuilder();
            newLocation = true;
        }

        if (qName.matches("description")) {
            sbnewDescription = new StringBuilder();
            newDescription = true;
        }

        if (qName.matches("profile_image_url")) {
            sbnewProfile_image_url = new StringBuilder();
            newProfile_image_url = true;
        }

        if (qName.matches("followers_count")) {
            sbnewFollowers_count = new StringBuilder();
            newFollowers_count = true;
        }


    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (newName) {
            sbnewName.append(ch, start, length);
        }
        if (newScreenName) {
            sbnewScreenName.append(ch, start, length);
        }
        if (newLocation) {
            sbnewLocation.append(ch, start, length);
        }
        if (newDescription) {
            sbnewDescription.append(ch, start, length);
        }
        if (newProfile_image_url) {
            sbnewProfile_image_url.append(ch, start, length);
        }
        if (newFollowers_count) {
            sbnewFollowers_count.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {


        //case when an affiliation is provided with the author
        if (qName.equals("user")) {

            author.setDescription(StringEscapeUtils.unescapeHtml4(sbnewDescription.toString()));
            System.out.println("description in parser: " + StringEscapeUtils.unescapeHtml4(sbnewDescription.toString()));
            author.setLocation(StringEscapeUtils.unescapeHtml4(sbnewLocation.toString()));
            author.setScreen_name(StringEscapeUtils.unescapeHtml4(sbnewScreenName.toString()));
            author.setName(StringEscapeUtils.unescapeHtml4(sbnewName.toString()));
            author.setFollowers_count(sbnewFollowers_count.toString());
            author.setProfile_image_url(StringEscapeUtils.unescapeHtml4(sbnewProfile_image_url.toString()));
        }

        if (qName.equals("name")) {
            newName = false;
        }
        if (qName.equals("screen_name")) {
            newScreenName = false;
        }
        if (qName.equals("location")) {
            newLocation = false;
        }
        if (qName.equals("description")) {
            newDescription = false;
        }
        if (qName.equals("profile_image_url")) {
            newProfile_image_url = false;
        }
        if (qName.equals("followers_count")) {
            newFollowers_count = false;
        }
    }
}

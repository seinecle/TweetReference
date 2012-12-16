/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportPDF;

import Model.Tweet;
import Model.User;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import org.joda.time.DateTime;

/**
 *
 * @author C. Levallois
 */
public class ParagraphBuilder {

    public static final Font HEADERFONT =
            new Font(FontFamily.TIMES_ROMAN,
            20);
    public static final Font SUBHEADERFONT =
            new Font(FontFamily.TIMES_ROMAN,
            12);
    public static final Font TEXTNORMALSIZE =
            new Font(FontFamily.TIMES_ROMAN,
            12);
    private User user;
    private Tweet tweet;

    public ParagraphBuilder(User user) {
        this.user = user;
    }

    public ParagraphBuilder(Tweet tweet) {
        this.tweet = tweet;
    }

    public Paragraph getHeader() {
        Paragraph header = new Paragraph();
        header.setFont(HEADERFONT);
        header.setAlignment(Element.ALIGN_CENTER);
        header.add(new Chunk("Tweet references for " + user.getName()));
        header.add(new Chunk(" (@" + user.getScreen_name() + ")"));
        return header;
    }

    public Paragraph getSubHeader() {
        Paragraph subHeader = new Paragraph();
        subHeader.setFont(SUBHEADERFONT);
        subHeader.setAlignment(Element.ALIGN_CENTER);

        subHeader.add(new Chunk("(generated "));
        subHeader.add(new Chunk(String.valueOf(new DateTime().getDayOfMonth())));
        subHeader.add(new Chunk(" "));
        subHeader.add(new Chunk(String.valueOf(new DateTime().monthOfYear().getAsText())));
        subHeader.add(new Chunk(" "));
        subHeader.add(new Chunk(String.valueOf(new DateTime().getYear())));
        subHeader.add(new Chunk(")"));
        return subHeader;
    }

    public Paragraph getCountPapers() {
        Paragraph subHeader = new Paragraph();
        subHeader.setFont(SUBHEADERFONT);
        subHeader.setAlignment(Element.ALIGN_LEFT);

        subHeader.add(new Chunk("test"));

        return subHeader;
    }

    public Paragraph getIdentity() {
        Paragraph identity = new Paragraph();
        identity.setFont(TEXTNORMALSIZE);
        identity.setAlignment(Element.ALIGN_LEFT);
        identity.add(new Chunk(tweet.getSource_user().getName()));
        identity.add(new Chunk(" (@"));
        identity.add(new Chunk(tweet.getSource_user().getScreen_name()));
        identity.add(new Chunk(")\n"));
        identity.add(new Chunk(tweet.getSource_user().getDescription()));
        identity.add(new Chunk("\n"));
        identity.add(new Chunk(tweet.getSource_user().getLocation()));
        identity.add(new Chunk("\n"));
        identity.add(new Chunk("["));
        identity.add(new Chunk(tweet.getSource_user().getFollowers_count()));
        identity.add(new Chunk(" followers on Twitter]\n"));
        identity.add(new Chunk("\n"));
        identity.add(new Chunk("\n"));
        identity.add(new Chunk("says: \""+tweet.getText()+"\""));
        identity.add(new Chunk("\n"));
        identity.add(new Chunk("\n"));
        identity.add(new Chunk("\n"));
        
        identity.add(new Chunk("\n"));
        return identity;
    }
}
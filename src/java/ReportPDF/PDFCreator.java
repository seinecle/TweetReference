/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReportPDF;

import Model.Tweet;
import Model.User;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author C. Levallois
 */
public class PDFCreator {

    private StreamedContent file;
    private User target;
    private List<Tweet> listTweets;

    public PDFCreator() {
    }

    public StreamedContent getPDF(List<Tweet> listTweets, User target) throws IOException, DocumentException {
        this.target = target;
        this.listTweets = listTweets;

        Document document = new Document();
        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
        PdfWriter docWriter;
        docWriter = PdfWriter.getInstance(document, baosPDF);

        document.open();
        ParagraphBuilder pb = new ParagraphBuilder(this.target);
        document.add(pb.getHeader());
        document.add(pb.getSubHeader());
        int count = 0;

        for (Tweet tweet : listTweets) {
            if (count > 0 & count % 3 == 0) {
                document.newPage();
            }
            System.out.println("");
            System.out.println("");
            System.out.println("");
            User source = tweet.getSource_user();
            pb = new ParagraphBuilder(tweet);
            URL url = new URL(source.getProfile_image_url());
            BufferedImage src = ImageIO.read(url);
            System.out.println("source width: " + src.getWidth());
            System.out.println("source height: " + src.getHeight());
            Image imgIText = Image.getInstance(src, null);
            imgIText.scaleAbsolute(25, 25);
            imgIText.setAlignment(Element.ALIGN_LEFT);
            document.add(imgIText);
            document.add(pb.getIdentity());
            count++;

        }

//        imgIText.setAbsolutePosition(Utilities.millimetersToPoints(30f), Utilities.millimetersToPoints(170f));


//        imgIText.setCompressionLevel(9);

//        ParagraphBuilder pb = new ParagraphBuilder();
//        document.add(pb.getHeader());
//        document.add(pb.getSubHeader());
//        document.add(pb.getIdentity());
//        document.add(pb.getCountPapers());

        document.close();
        docWriter.close();

        InputStream stream = new ByteArrayInputStream(baosPDF.toByteArray());
        file = new DefaultStreamedContent(stream, "application/pdf", "tweet_references");

        return file;
    }
}

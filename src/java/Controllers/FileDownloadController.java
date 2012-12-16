/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Model.User;
import ReportPDF.PDFCreator;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author C. Levallois
 */
@ManagedBean
@RequestScoped
public class FileDownloadController implements Serializable {

    @ManagedProperty("#{controllerBean}")
    private ControllerBean controllerBean;

    public void setcontrollerBean(ControllerBean controllerBean) {
        this.controllerBean = controllerBean;
    }
    private StreamedContent file;

    public FileDownloadController() {
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public StreamedContent getFile() throws IOException, DocumentException {

        System.out.println("search term in getFile: " + controllerBean.getSearchTermWithoutArobase());

        User user = controllerBean.getDs().find(User.class).field("screen_name_ignorecase").equal(controllerBean.getSearchTermWithoutArobase().toLowerCase()).get();
        System.out.println("user retrieved from user db: " + user.getDescription());
        PDFCreator pdfCreator = new PDFCreator();
        file = pdfCreator.getPDF(controllerBean.getListTweets(), user);
        System.out.println("file: " + file);
        return file;
    }
}

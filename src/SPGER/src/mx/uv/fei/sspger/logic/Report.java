package mx.uv.fei.sspger.logic;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {

    protected String title;
    protected Date creationDate;
    protected Font titleFont = new Font();
    protected Font subtitleFont = new Font();
    protected Font subtitle2Font = new Font();
    protected Paragraph titleParagraph;
    protected Paragraph dateLineParagraph;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Font getTitleFont() {
        setTitleFont();
        return titleFont;
    }

    private void setTitleFont() {
        titleFont.setColor(45, 82, 100);
        titleFont.setSize(20);
        titleFont.setStyle(Font.BOLD);
    }

    public Font getSubtitleFont() {
        setSubtitleFont();
        return subtitleFont;
    }

    private void setSubtitleFont() {
        subtitleFont.setSize(14);
        subtitleFont.setStyle(Font.BOLD);
    }

    public Font getSubtitle2Font() {
        setSubtitle2Font();
        return subtitle2Font;
    }

    private void setSubtitle2Font() {
        subtitle2Font.setStyle(Font.BOLD);
    }

    public String getReportDateFormated() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(creationDate);
    }

    public Paragraph getTitleParagraph() {
        setTitleParagraph();
        return titleParagraph;
    }

    private void setTitleParagraph() {
        titleParagraph = new Paragraph(title, titleFont);
    }

    public Paragraph getDateLineParagraph() {
        setDateLineParagraph();
        return dateLineParagraph;
    }

    private void setDateLineParagraph() {
        dateLineParagraph = new Paragraph("Fecha de generación de reporte: " + getReportDateFormated(), subtitleFont);
    }

}

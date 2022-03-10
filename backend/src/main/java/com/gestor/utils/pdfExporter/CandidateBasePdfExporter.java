package com.gestor.utils.pdfExporter;

import com.gestor.domains.presenter.CandidatePresenter;
import com.gestor.domains.presenter.ExperiencePresenter;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;

public class CandidateBasePdfExporter {

    final CandidatePresenter candidate;
    final Color customBlue;
    final Color customBlack = new Color(87, 87, 87);
    final com.lowagie.text.Font custom18BlueBold = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18, com.lowagie.text.Font.BOLD, customBlack);
    final com.lowagie.text.Font custom15Black = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 15, com.lowagie.text.Font.HELVETICA, customBlack);
    final com.lowagie.text.Font custom15BlackThin = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 15, com.lowagie.text.Font.NORMAL, customBlack);
    final PdfPCellEvent event = new PdfPCellEvent() {
        @Override
        public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.BACKGROUNDCANVAS];
            cb.roundRectangle(
                    rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                    rect.getHeight() - 3, 4);
            cb.setColorFill(customBlue);
            cb.fill();
        }
    };

    public CandidateBasePdfExporter(CandidatePresenter candidatePresenter, Color customBlue) {
        this.candidate = candidatePresenter;
        this.customBlue = customBlue;
    }

    private void writeTableHeaderCandidate(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setPaddingLeft(8);
        cell.setBorder(0);
        cell.setCellEvent(event);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);


        cell.setPhrase(new Phrase("Nombre", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Apellido", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nacimiento", font));
        table.addCell(cell);
    }

    private void writeTableDataCandidate(PdfPTable table) {

        PdfPCell cell = new PdfPCell();
        cell.setBorder(0);

        cell.setPhrase(new Phrase(candidate.getFirstName(), custom15BlackThin));
        table.addCell(cell);
        cell.setPhrase(new Phrase(candidate.getLastName(), custom15BlackThin));
        table.addCell(cell);
        String candidateDate = dateFormatter(candidate.getBirthDate());
        cell.setPhrase(new Phrase(candidateDate, custom15BlackThin));
        table.addCell(cell);
    }

    private void writeTableHeaderObservation(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPaddingBottom(8);
        cell.setPaddingLeft(8);
        cell.setBorder(0);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);

        cell.setCellEvent(event);

        cell.setPhrase(new Phrase("Observaciones", font));
        table.addCell(cell);

    }

    private void writeTableDataObservation(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(0);

        cell.setPhrase(new Phrase(candidate.getObservation(), custom15BlackThin));
        table.addCell(cell);

    }

    private String dateFormatter(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    void addLogo(Document document) throws IOException {

        Image image = Image.getInstance("src/main/java/com/gestor/resources/");
        image.scaleAbsolute(0, 0);
        image.setAbsolutePosition(0, 0);
        document.add(image);
    }

    public void generateDocument(Document document) throws IOException {

        document.open();

        addLogo(document);

        document.add(new Paragraph(" "));
        Paragraph p = new Paragraph("CANDIDATO", custom18BlueBold);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        // adding candidate table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{3.3f, 4f, 1.5f,});
        table.setSpacingBefore(10);

        writeTableHeaderCandidate(table);
        writeTableDataCandidate(table);
        document.add(table);

        // adding observations table
        PdfPTable tableObservation = new PdfPTable(1);
        tableObservation.setWidthPercentage(100f);
        tableObservation.setWidths(new float[]{9.2f});
        tableObservation.setSpacingBefore(10);

        writeTableHeaderObservation(tableObservation);
        writeTableDataObservation(tableObservation);
        document.add(tableObservation);

        //adding experience table
        Set<ExperiencePresenter> experiences = candidate.getExperiences();

        Paragraph lineHeader = new Paragraph("EXPERIENCIAS", custom18BlueBold);
        lineHeader.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(lineHeader);

        if (experiences.isEmpty()) {
            Paragraph noExperience = new Paragraph("Sin experiencia registrada", custom15BlackThin);
            noExperience.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(noExperience);
        } else {

            for (Iterator<ExperiencePresenter> it = experiences.iterator(); it.hasNext(); ) {
                ExperiencePresenter experience = it.next();

                Paragraph titleHeader = new Paragraph("Puesto", custom18BlueBold);
                titleHeader.setAlignment(Paragraph.ALIGN_LEFT);
                Paragraph titleData = new Paragraph(experience.getTitle(), custom15BlackThin);
                titleData.setAlignment(Paragraph.ALIGN_LEFT);

                document.add(titleHeader);
                document.add(titleData);

                Paragraph companyHeader = new Paragraph("Empresa", custom18BlueBold);
                companyHeader.setAlignment(Paragraph.ALIGN_LEFT);
                Paragraph companyData = new Paragraph(experience.getCompany(), custom15BlackThin);
                companyData.setAlignment(Paragraph.ALIGN_LEFT);

                document.add(companyHeader);
                document.add(companyData);

                Paragraph startDateHeader = new Paragraph("Fecha inicio: " + dateFormatter(experience.getStartDate()), custom15Black);
                startDateHeader.setAlignment(Paragraph.ALIGN_LEFT);
                document.add(startDateHeader);
                if (!experience.isCurrentlyWorking()) {
                    Paragraph endDateHeader = new Paragraph("Fecha     fin : " + dateFormatter(experience.getEndDate()), custom15Black);
                    endDateHeader.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(endDateHeader);
                } else {
                    Paragraph endDateHeader = new Paragraph("Fecha     fin :  Puesto presente", custom15Black);
                    endDateHeader.setAlignment(Paragraph.ALIGN_LEFT);
                    document.add(endDateHeader);
                }
                ;

                Paragraph descriptionHeader = new Paragraph("Descripción", custom18BlueBold);
                descriptionHeader.setAlignment(Paragraph.ALIGN_LEFT);
                Paragraph descriptionData = new Paragraph(experience.getDescription(), custom15BlackThin);
                descriptionData.setAlignment(Paragraph.ALIGN_LEFT);

                document.add(descriptionHeader);
                document.add(descriptionData);

                Paragraph endOfExperienceData = new Paragraph("---------------------------------------------------------------------", custom15BlackThin);
                endOfExperienceData.setAlignment(Paragraph.ALIGN_LEFT);

                document.add(endOfExperienceData);
            }
        }
        document.close();
    }

    //triggers the whole class
    public void export(HttpServletResponse response)
            throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        generateDocument(document);
    }

}

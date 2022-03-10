package com.gestor.utils.pdfExporter;

import java.awt.Color;
import java.io.IOException;

import com.gestor.domains.presenter.CandidatePresenter;
import com.lowagie.text.*;

public class CandidateMintExporterPDF extends CandidateBasePdfExporter {

    public CandidateMintExporterPDF(CandidatePresenter candidatePresenter, Color customBlue) {
        super(candidatePresenter, customBlue);
    }

    @Override
    void addLogo(Document document) throws IOException {

        Image image = Image.getInstance("src/main/java/com/gestor/resources/mint.png");
        image.scaleAbsolute(100, 60);
        image.setAbsolutePosition(39, 765);
        document.add(image);
    }
}

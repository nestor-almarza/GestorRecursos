package com.gestor.utils.pdfExporter;

import com.gestor.domains.presenter.CandidatePresenter;
import com.lowagie.text.*;
import com.lowagie.text.Image;

import java.awt.*;
import java.io.IOException;

public class CandidateManjaroExporterPDF extends CandidateBasePdfExporter{


    public CandidateManjaroExporterPDF(CandidatePresenter candidatePresenter, Color customBlue) {
        super(candidatePresenter, customBlue);
    }

    @Override
    void addLogo(Document document) throws IOException {
        Image image = Image.getInstance("src/main/java/com/gestor/resources/manjaro.png");
        image.scaleAbsolute(160, 80);
        image.setAbsolutePosition(22, 750);
        document.add(image);
    }

  }

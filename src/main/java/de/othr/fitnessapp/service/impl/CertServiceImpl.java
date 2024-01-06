package de.othr.fitnessapp.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import de.othr.fitnessapp.model.Course;
import de.othr.fitnessapp.service.CertServiceI;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Random;

@Service
@Log4j2
@AllArgsConstructor
public class CertServiceImpl implements CertServiceI {

    @Override
    public ResponseEntity<byte[]> getCert(Course course) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            document.add(new Paragraph("Certificate for : " + "Name"));
            document.add(new Paragraph("Course Name: " + course.getName()));
            document.add(new Paragraph("Course Date: " + course.getDate()));

            document.close();

            int randomNum = new Random().nextInt(10000);

            byte[] pdfBytes = baos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "certificate_" + randomNum + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error generating PDF", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

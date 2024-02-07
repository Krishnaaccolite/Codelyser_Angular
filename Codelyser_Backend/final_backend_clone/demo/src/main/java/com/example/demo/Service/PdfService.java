package com.example.demo.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
@Service
public class PdfService {
    public void generatePdf(String text, String outputPath,String result) {
        String filename = "testcase.pdf";
        String filePath = outputPath + File.separator + filename;  // Using File.separator for platform independence

        System.out.println(filePath);

        Document document = new Document();
        try {
            File file = new File(filePath);

            // Ensure the parent directories exist, create them if not
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Paragraph resultParagraph = new Paragraph(result, boldFont);
            document.add(resultParagraph);
            // Add a line break
            document.add(new Paragraph("\n"));

            // Add a table with borders
            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase(text));
            cell.setBorderWidth(1f); // Set border width
            cell.setPadding(5f); // Set cell padding
            cell.setHorizontalAlignment(Element.ALIGN_LEFT); // Set horizontal alignment
            table.addCell(cell);

            document.add(table);
            // Add text to the PDF

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
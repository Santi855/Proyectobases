package com.hospital.citas.util; // <--- ASEGÚRATE DE QUE ESTE SEA TU PAQUETE

import com.hospital.citas.model.Doctor;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

public class DoctorExportPDF {

    private List<Doctor> listaDoctores;

    public DoctorExportPDF(List<Doctor> listaDoctores) {
        this.listaDoctores = listaDoctores;
    }

    private void escribirCabeceraTabla(PdfPTable tabla) {
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(new Color(245, 158, 11)); // Naranja Pro
        celda.setPadding(8);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.WHITE);

        celda.setPhrase(new Phrase("Nombre del Médico", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Especialidad", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Cédula", fuente));
        tabla.addCell(celda);
        celda.setPhrase(new Phrase("Estado", fuente));
        tabla.addCell(celda);
    }

    public void exportar(HttpServletResponse response) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();
        
        // Estilo del Título
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new Color(30, 41, 59));
        Paragraph titulo = new Paragraph("Reporte de Plantilla Médica", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        documento.add(titulo);

        // Tabla
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10);
        tabla.setWidths(new float[] {3.5f, 3.0f, 2.0f, 1.5f}); // Ancho de columnas
        
        escribirCabeceraTabla(tabla);

        for (Doctor doc : listaDoctores) {
            tabla.addCell(doc.getUsuario().getNombre() + " " + doc.getUsuario().getApellido_paterno());
            tabla.addCell(doc.getEspecialidad().getNombre());
            tabla.addCell(doc.getCedulaProfesional());
            tabla.addCell(doc.getUsuario().isActivo() ? "Activo" : "Inactivo");
        }

        documento.add(tabla);
        documento.close();
    }
}
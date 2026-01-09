package com.hospital.citas.service;

import com.hospital.citas.model.Cita;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

@Service
public class CitaPdfService {

    public void exportar(HttpServletResponse response, List<Cita> citas) throws IOException {
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());

        documento.open();

        // Título del PDF
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
        Paragraph titulo = new Paragraph("Reporte de Citas Médicas", fuenteTitulo);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(new Paragraph(" ")); // Espacio en blanco

        // Creación de la tabla (6 columnas)
        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        
        // Encabezados
        String[] columnas = {"ID", "Fecha", "Hora", "Paciente", "Doctor", "Estado"};
        for (String columna : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(columna, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            celda.setBackgroundColor(Color.LIGHT_GRAY);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda);
        }

        // Datos de la base de datos
        for (Cita cita : citas) {
            tabla.addCell(cita.getIdCita().toString());
            tabla.addCell(cita.getFechaCita().toString());
            tabla.addCell(cita.getHoraCita().toString());
            tabla.addCell(cita.getPaciente().getUsuario().getUsuario());
            tabla.addCell(cita.getDoctor().getUsuario().getUsuario());
            tabla.addCell(cita.getEstatus());
        }

        documento.add(tabla);
        documento.close();
    }
}
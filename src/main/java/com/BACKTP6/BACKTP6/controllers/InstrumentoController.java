package com.BACKTP6.BACKTP6.controllers;

import com.BACKTP6.BACKTP6.entities.Instrumento;
import com.BACKTP6.BACKTP6.services.InstrumentoServiceImplementation;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.layout.element.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/instrumentos")
public class InstrumentoController {

    @Autowired
    private InstrumentoServiceImplementation instrumentoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllInstruments(){
        try{

            return ResponseEntity.status(HttpStatus.OK).body(instrumentoService.findAll());

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getInstrumentById(@PathVariable Long id) {
        try{

            return ResponseEntity.status(HttpStatus.OK).body(instrumentoService.findById(id));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
        }

    }


    @PostMapping("/create")
    public ResponseEntity<?> createInstrument(@RequestBody Instrumento instrumento) {
        try {
            return ResponseEntity.ok(instrumentoService.create(instrumento));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating the instrument");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInstrument(@PathVariable Long id, @RequestBody Instrumento instrumento) {
        try {
            instrumento.setId(id);
            return ResponseEntity.ok(instrumentoService.update(id, instrumento));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instrument not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInstrument(@PathVariable Long id) {
        try {
            instrumentoService.delete(id);
            return ResponseEntity.ok().body("Instrument deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instrument not found");
        }
    }

    //Retornamos una lista de ? (instrumentos)
    @GetMapping("/all/filter")
    public ResponseEntity<?> filterInstrument(@RequestParam Long categoriaId){
        try {
            if (categoriaId != null) {
                return ResponseEntity.status(HttpStatus.OK).body(instrumentoService.findByCategoria(categoriaId));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(instrumentoService.findAll());
            }
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instrument not found");
        }
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<InputStreamResource> generarPDF(@PathVariable Long id) throws Exception {
        Instrumento instrumento = instrumentoService.findById(id);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4, true);
        document.setMargins(30, 30, 30, 30);

        // Crear una tabla
        Table table = new Table(2);
        table.setWidth(UnitValue.createPercentValue(100));
        // Agregar imagen si está disponible
        String imgUrl = instrumento.getImagen();
        if (imgUrl != null && !imgUrl.isEmpty()) {
            ImageData imageData = ImageDataFactory.create(new URL(imgUrl));
            Image image = new Image(imageData);
            image.scaleToFit(200, 200);
            table.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
        } else {
            table.addCell(new Cell().setBorder(Border.NO_BORDER));
        }
        table.addCell(new Cell().add(new Paragraph("Denominación: " + instrumento.getInstrumento())).setBorder(Border.NO_BORDER));
// Agregar precio
        table.addCell(new Cell().add(new Paragraph("Precio: " + String.valueOf(instrumento.getPrecio()))).setBorder(Border.NO_BORDER));
// Agregar marca
        table.addCell(new Cell().add(new Paragraph("Marca: " + instrumento.getMarca())).setBorder(Border.NO_BORDER));
// Agregar modelo
        table.addCell(new Cell().add(new Paragraph("Modelo: " + instrumento.getModelo())).setBorder(Border.NO_BORDER));
        // Agregar costo de envío
        table.addCell(new Cell().add(new Paragraph("Costo Envío: " + instrumento.getCostoEnvio())).setBorder(Border.NO_BORDER));
        // Agregar la tabla al documento
        document.add(table);

        document.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=instrumento.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }

}


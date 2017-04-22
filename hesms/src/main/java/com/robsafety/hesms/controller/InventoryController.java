package com.robsafety.hesms.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfWriter;
import com.robsafety.hesms.domain.Inventory;
import com.robsafety.hesms.domain.InventoryType;
import com.robsafety.hesms.service.InventoryService;
import com.robsafety.hesms.service.QRGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.Writer;
import java.util.List;

/**
 * Created by Jijeesh on 4/9/2017.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    @RequestMapping("")
    public ResponseEntity<List<Inventory>> getInventory(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset){
        return new ResponseEntity<List<Inventory>>(inventoryService.getInventory(limit,offset),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Inventory> saveInventory(@RequestBody Inventory inventory){
        return new ResponseEntity<Inventory>(inventoryService.saveInventory(inventory),HttpStatus.OK);
    }

    @RequestMapping("/type")
    public ResponseEntity<List<InventoryType>> getInventoryType(){
        return new ResponseEntity<List<InventoryType>>(inventoryService.getInventoryTypeList(),HttpStatus.OK);

    }

    @RequestMapping(value = "/type", method = RequestMethod.POST)
    public ResponseEntity<InventoryType> getInventoryType(@RequestBody InventoryType inventoryType){
        return new ResponseEntity<InventoryType>(inventoryService.saveInventoryType(inventoryType),HttpStatus.OK);

    }






    @RequestMapping("/api")
    public List<Inventory> getInventory(){

        return inventoryService.getInventory();
    }

    @RequestMapping(value = "/pdf")
    public ResponseEntity<byte[]> getInventoryPdf(){


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


        try {

            BufferedImage respImage = QRGenerator.generateQR("Hai");
            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            ImageIO.write(respImage, "png", imageStream);

            Image image = Image.getInstance(imageStream.toByteArray());

            PdfImage stream = new PdfImage(image, "", null);

            //Rectangle page = new Rectangle(90, 90);


            //Document document = new Document(page);

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            PdfContentByte canvas = writer.getDirectContentUnder();

            /*Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Chunk chunk = new Chunk("This is the title", chapterFont);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph("This is the paragraph", paragraphFont));
            document.add(chapter);*/

            //canvas.addImage(image, 0, 0, 0, 0, 0, 0);

            document.add(image);
            document.add(image);
            document.add(image);
            document.add(image);
            document.add(image);
            document.close();

        }catch (Exception e){
            System.out.println(e);
        }











        byte[] contents = byteArrayOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);
        return response;
    }

}

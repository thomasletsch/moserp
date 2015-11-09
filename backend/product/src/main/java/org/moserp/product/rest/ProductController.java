package org.moserp.product.rest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import org.moserp.product.domain.Product;
import org.moserp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{productId}/ean")
    public ResponseEntity<?> getProductEAN(@PathVariable String productId) throws WriterException, IOException {
        Product product = repository.findOne(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        EAN13Writer ean13Writer = new EAN13Writer();
        BitMatrix matrix = ean13Writer.encode(product.getEan(), BarcodeFormat.EAN_13, 300, 200);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        byte[] imageData = baos.toByteArray();
        ByteArrayResource byteArrayResource = new ByteArrayResource(imageData);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(byteArrayResource);
    }


}

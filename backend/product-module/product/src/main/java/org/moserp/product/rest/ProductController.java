package org.moserp.product.rest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import org.moserp.common.domain.Quantity;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.product.domain.Product;
import org.moserp.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModuleRegistry moduleRegistry;

    @Autowired
    private RestTemplate restTemplate;

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

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/{productId}/quantityOnHand")
    public Quantity getProductQuantityOnHand(@RequestHeader(value = "Authorization") String authorization, @PathVariable String productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList(authorization));
        HttpEntity request = new HttpEntity(headers);
        String url = "http://" + OtherResources.MODULE_INVENTORY + "/" + OtherResources.PRODUCTS + "/" + productId + "/quantityOnHand";
        ResponseEntity<Quantity> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, Quantity.class);
        return responseEntity.getBody();
    }

}

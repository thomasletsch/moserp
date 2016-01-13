package org.moserp.environment.rest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class TranslationController {

    @RequestMapping(method = RequestMethod.GET, value = "/translations")
    public String getTranslations() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("translations_de.json");
        return IOUtils.toString(stream);
    }
}

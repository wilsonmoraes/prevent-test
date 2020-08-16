package br.com.crdd.config;


import br.com.crdd.common.mapper.ControllerManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@IntegrationTest
@Transactional
public abstract class AbstractControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ControllerManager controllerManager;

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected TestRestTemplate testRestTemplate;


    protected HttpHeaders getHeaderForOpenRequest(Long contaPadrao) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("AccountId", String.valueOf(contaPadrao));
        return requestHeaders;

    }

    protected MockMultipartFile mockFile(String name, String contentType, String path) throws Exception {
        InputStream in = this.getClass().getResourceAsStream(path);

        return new MockMultipartFile(name, null, contentType, in);
    }

    protected MockMultipartFile mockJson(String paramName, String jsonStr) {
        byte[] content = jsonStr.getBytes();
        return new MockMultipartFile(paramName, "", "application/json", content);
    }
}

package br.com.crdd.config;


import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@IntegrationTest
@Transactional
public abstract class AbstractServiceTest {


    protected MockMultipartFile mockFile(String name, String contentType, String path) throws Exception {
        InputStream in = this.getClass().getResourceAsStream(path);

        return new MockMultipartFile(name, null, contentType, in);
    }
}

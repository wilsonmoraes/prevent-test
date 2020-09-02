package br.com.crdd.web;


import br.com.crdd.common.mapper.ControllerManager;
import br.com.crdd.config.IntegrationTest;
import br.com.crdd.dao.AccessLogRepository;
import br.com.crdd.web.open.accesLog.AccessLogDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@Transactional
public class AccessLogControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ControllerManager controllerManager;

    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Test
    public void testFindById() {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<AccessLogDto> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<AccessLogDto> entity = testRestTemplate.exchange(
                "/open/access_log/{id}", HttpMethod.GET, requestEntity,
                AccessLogDto.class, 1);
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);

    }
}

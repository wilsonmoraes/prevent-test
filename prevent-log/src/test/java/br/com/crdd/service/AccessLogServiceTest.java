package br.com.crdd.service;


import br.com.crdd.config.IntegrationTest;
import br.com.crdd.domain.AccessLog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@Transactional
public class AccessLogServiceTest {

    @Autowired
    private AccessLogService accessLogService;


    @Test
    @Rollback
    public void testSaveLog() {
        AccessLog accessLog = AccessLog.builder().clientIP("wilson__..").build();
        accessLog = accessLogService.save(accessLog);
        Assert.assertNotNull(accessLog.getRegistrationDate());

    }


}

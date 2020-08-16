package br.com.crdd.repository;


import br.com.crdd.config.IntegrationTest;
import br.com.crdd.dao.AccessLogRepository;
import br.com.crdd.domain.AccessLog;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
@Transactional
public class AccessLogRepositoryTest {
    @Autowired
    private AccessLogRepository accessLogRepository;


    @Test
    public void testFindAll() {
        List<AccessLog> customers = accessLogRepository.findAll();
        Assert.assertFalse(customers.isEmpty());
    }


}

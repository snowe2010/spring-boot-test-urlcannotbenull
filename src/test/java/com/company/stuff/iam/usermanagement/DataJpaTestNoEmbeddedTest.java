package com.company.stuff.iam.usermanagement;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("embedded") // uncomment to run against embedded?
@DataJpaTest
public class DataJpaTestNoEmbeddedTest {
    @Autowired
    TestEntityManager manager;
    @Autowired
    private DaoRepository daoRepository;

    @Test
    public void inviteAccountExisting() throws URISyntaxException {
        String id = (String) manager.persistAndGetId(new Dao("HI"));
        Dao response = daoRepository.getOne(id);

        assertEquals("HI", response.getStr());
    }
}

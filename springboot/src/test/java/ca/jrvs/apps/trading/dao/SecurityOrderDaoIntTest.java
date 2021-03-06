package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {

  @Autowired
  private SecurityOrderDao securityOrderDao;

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private TraderDao traderDao;

  @Autowired
  private QuoteDao quoteDao;

  private Account account;
  private Trader savedTrader;
  private SecurityOrder securityOrder;
  private Quote savedQuote;

  @Before
  public void insertOne(){
    savedQuote = new Quote();
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("SDG");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
    savedTrader = new Trader();
    savedTrader.setCountry("Canada");
    savedTrader.setDob(new Date(1989,12,31));
    savedTrader.setEmail("Jarvis_2020@gmail.com");
    savedTrader.setFirstName("Zongpeng");
    savedTrader.setLastName("Yang");
    savedTrader.setId(traderDao.save(savedTrader).getId());
    account = new Account();
    account.setTrader_id(savedTrader.getId());
    account.setAmount(1000d);
    account.setId(accountDao.save(account).getId());
    securityOrder = new SecurityOrder();
    securityOrder.setAccountId(account.getId());
    securityOrder.setPrice(100d);
    securityOrder.setSize(10);
    securityOrder.setStatus("FILLED");
    securityOrder.setTicker(savedQuote.getTicker());
    securityOrder.setNotes("N/a");
    securityOrder.setId(securityOrderDao.save(securityOrder).getId());
  }

  @Test
  public void findAllById(){
    List<SecurityOrder> securityOrders = Lists.newArrayList(
        securityOrderDao.findAllById(
            Arrays.asList(securityOrder.getId())
        )
    );
    assertEquals(1, securityOrders.size());
    assertEquals(securityOrder.getAccountId(), securityOrders.get(0).getAccountId());
    assertEquals(securityOrder.getStatus(), securityOrders.get(0).getStatus());
    assertEquals(securityOrder.getTicker(), securityOrders.get(0).getTicker());
    assertEquals(securityOrder.getSize(), securityOrders.get(0).getSize());
    assertEquals(securityOrder.getPrice(), securityOrders.get(0).getPrice());
    assertEquals(securityOrder.getNotes(), securityOrders.get(0).getNotes());
  }

  @Test
  public void update(){
    securityOrder.setStatus("WAITING");
    SecurityOrder securityOrderOut = securityOrderDao.save(securityOrder);
    assertEquals(securityOrder.getStatus(), securityOrderOut.getStatus());
  }

  @After
  public void deleteOne(){
    securityOrderDao.deleteById(securityOrder.getId());
    accountDao.deleteById(account.getId());
    traderDao.deleteById(savedTrader.getId());
  }
}
package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.model.domain.Trader;
import com.google.common.base.FinalizablePhantomReference;
import javax.sql.DataSource;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader> {

  private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

  private final String TABLE_NAME="trader";
  private final String ID_COLUMN="id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public  TraderDao(DataSource dataSource){
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }
  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setSimpleJdbcInsert(SimpleJdbcInsert simpleJdbcInsert) {
    this.simpleJdbcInsert = simpleJdbcInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  @Override
  Class<Trader> getEntityClass() {
    return Trader.class;
  }

  /**
   * update trader information by trader id
   * @param trader
   */
  @Override
  public int updateOne(Trader trader) {
    String updateSQL = "UPDATE "+ TABLE_NAME + " SET first_name=?, last_name=?, dob=?, "
        + "country=?, email=? WHERE id=?";
    return jdbcTemplate.update(updateSQL, makeUpdateValues(trader));
  }

  @Override
  public void delete(Trader entity) {
    throw new  UnsupportedOperationException("not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Trader> entities) {
    throw new  UnsupportedOperationException("not implemented");
  }

  private Object[] makeUpdateValues(Trader trader) {
    return new Object[]{trader.getFirst_name(), trader.getLast_name(), trader.getDob(),
        trader.getCountry(), trader.getEmail(), trader.getId()};
  }

  public static Logger getLogger() {
    return logger;
  }
}

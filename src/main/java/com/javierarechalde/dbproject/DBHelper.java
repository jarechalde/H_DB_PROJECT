package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.flywaydb.core.Flyway;

public class DBHelper {

  public static Connection getConnection() throws SQLException {
    return DBHelper.getInstance().getDataSource().getConnection();
  }

  public static DBHelper getInstance() {
    return DBHelper.INSTANCE;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(DBHelper.class);

  private static final DBHelper INSTANCE = new DBHelper();

  private BasicDataSource ds;

  private DBHelper() {}

  public void close() {
    if (ds != null) {
      DBHelper.LOGGER.debug("Closing the data source");
      try {
        ds.close();
      } catch (final SQLException e) {
        DBHelper.LOGGER.error("Failed to close the data source", e);
      }
    }
  }

  public DataSource getDataSource() {
    return ds;
  }

  public void init() {
    DBHelper.LOGGER.debug("Creating the data source");
    ds = new BasicDataSource();
    ds.setDriverClassName("org.h2.Driver");
    ds.setUrl("jdbc:h2:target/db");
    ds.setUsername("jarechalde");
    ds.setPassword("16387495p");

    DBHelper.LOGGER.debug("Executing Flyway (database migration)");
    final Flyway flyway = new Flyway();
    flyway.setDataSource(ds);
    flyway.migrate();
  }

}
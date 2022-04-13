package com.learning.dao;

import com.learning.model.Circle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class CircleDao {

  public static final String SELECT_FROM_CIRCLE = "SELECT * FROM CIRCLE";
  private JdbcTemplate jdbcTemplate;

  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Autowired
  public void setNamedParameterJdbcTemplate(
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  public int getCircleCount() {
    String sql = "SELECT COUNT(*) FROM CIRCLE";
    return jdbcTemplate.queryForObject(sql, Integer.class);
  }

  public String getCircleName(int id) {
    String sql = "SELECT CIRCLE_NAME FROM CIRCLE WHERE CIRCLE_ID = ?";
    return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
  }

  public String getCircleNameUsingNamedParams(int id) {
    String sql = "SELECT CIRCLE_NAME FROM CIRCLE WHERE CIRCLE_ID = :circle_id";
    SqlParameterSource namedParameters = new MapSqlParameterSource("circle_id", id);
    return namedParameterJdbcTemplate.queryForObject(sql, namedParameters, String.class);
  }

  public Circle getCircleForId(int id) {
    String sql = SELECT_FROM_CIRCLE +  "WHERE CIRCLE_ID = ?";
    return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CircleMapper());
  }

  public List<Circle> getAllCirclesUsingResultSet() {
    String sql = SELECT_FROM_CIRCLE;
    return jdbcTemplate.query(sql, new CircleResultSetExtractor());
  }

  public List<Circle> getAllCirclesUsingRowMapper() {
    String sql = SELECT_FROM_CIRCLE;
    return jdbcTemplate.query(sql, new CircleMapper());
  }

  public void insertCircle(Circle circle) {
    String sql = "INSERT INTO CIRCLE {CIRCLE_ID, CIRCLE_NAME} VALUES(?, ?)";
    jdbcTemplate.update(sql, circle.getId(), circle.getName());
  }

  public void insertCircleUsingNamedParams(Circle circle) {
    String sql = "INSERT INTO CIRCLE {CIRCLE_ID, CIRCLE_NAME} VALUES(:id, :name)";
    SqlParameterSource namedParameters = new MapSqlParameterSource("id", circle.getId())
        .addValue("name", circle.getName());
    namedParameterJdbcTemplate.update(sql, namedParameters);
  }

  private class CircleMapper implements RowMapper<Circle> {

    public Circle mapRow(ResultSet resultSet, int i) throws SQLException {
      List<Circle> circles = new ArrayList<Circle>();
      while (resultSet.next()){
        Circle circle = new Circle();
        circle.setId(resultSet.getInt(1));
        circle.setName(resultSet.getString(2));
        circles.add(circle);
      }
      return circles.get(0);
    }
  }

  private class CircleResultSetExtractor implements ResultSetExtractor<List<Circle>> {

    public List<Circle> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
      List<Circle> circles = new ArrayList<Circle>();
      while(resultSet.next()){
        Circle circle = new Circle();
        circle.setId(resultSet.getInt(1));
        circle.setName(resultSet.getString(2));
        circles.add(circle);
      }
      return circles;
    }
  }
}

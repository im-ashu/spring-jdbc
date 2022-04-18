package spring.jdbc;

import spring.jdbc.config.JdbcConfiguration;
import spring.jdbc.dao.CircleDao;
import spring.jdbc.model.Circle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfiguration.class);
    CircleDao circleDao = context.getBean(CircleDao.class);
    System.out.println(circleDao.getCircleCount());
    System.out.println(circleDao.getCircleName(1));
    System.out.println(circleDao.getCircleForId(1));
    System.out.println(circleDao.getAllCirclesUsingResultSet());
    System.out.println(circleDao.getAllCirclesUsingRowMapper());
    circleDao.insertCircle(new Circle(2, "Second Circle"));

    System.out.println(circleDao.getCircleNameUsingNamedParams(2));
    circleDao.insertCircleUsingNamedParams(new Circle(3, "Third Circle"));
  }
}

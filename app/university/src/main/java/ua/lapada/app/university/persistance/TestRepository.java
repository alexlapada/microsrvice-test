package ua.lapada.app.university.persistance;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
@AllArgsConstructor
@Slf4j
public class TestRepository {
    private final DataSource dataSource;

    public void test() {
        try (Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from test");
            Thread.sleep(500);
            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getString(1));
                System.out.println("Name: " + resultSet.getString(2));
            }
            connection.commit();
        } catch (Exception e) {
            System.out.println("Error in " + Thread.currentThread().getName());
            log.error("Error in {}. {}", Thread.currentThread().getName(), e.getMessage());
        }

    }
}

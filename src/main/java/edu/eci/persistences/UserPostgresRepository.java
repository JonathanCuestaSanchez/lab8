package edu.eci.persistences;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.eci.models.User;
import edu.eci.persistences.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Qualifier("UserPostgresRepository")
public class UserPostgresRepository implements IUserRepository {

    private String dbUrl = "jdbc:postgres://iukiexucwyckzk:2df9a58420c2b7db39c165f33e762d5f8c574c283f2803edd777cdbe569298d0@ec2-174-129-10-235.compute-1.amazonaws.com:5432/d25qeplrnjlljm";

    @Autowired
    private DataSource dataSource;

    @Override
    public User getUserByUserName(String userName) {
        String query = "SELECT * FROM users WHERE name='" + userName + "';";
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            User user = new User();
            user.setName(rs.getString("name"));
            user.setId(UUID.fromString(rs.getString("id")));
            return user;
        } catch (Exception e) {

            System.out.println(e.getMessage());

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setName(rs.getString("name"));
                user.setId(UUID.fromString(rs.getString("id")));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User find(UUID id) {
        String query = "SELECT * FROM users WHERE id="+id.toString()+";";     

        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            User user = new User();
            user.setName(rs.getString("name"));
            user.setId(UUID.fromString(rs.getString("id")));
            return user;
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public UUID save(User entity
    ) {
        String query = "insert into users(name,id) values ('"+entity.getName()+"','"+entity.getId()+"');";
         try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
            
            return entity.getId();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        
    }

    @Override
    public void update(User entity
    ) {
        String query = "UPDATE  users SET name='"+entity.getName()+"' Where id='"+entity.getId()+"';";
         try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(User o
    ) {
        String query = "delete  from users where id='"+o.getId()+"';";
         try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(Long id
    ) {
        String query = "delete  from users where id='"+id.toString()+"';";
         try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeQuery(query);
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }
}

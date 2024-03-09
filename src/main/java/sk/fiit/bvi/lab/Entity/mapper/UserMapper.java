package sk.fiit.bvi.lab.Entity.mapper;


import org.springframework.jdbc.core.RowMapper;
import sk.fiit.bvi.lab.Entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setProfileId(rs.getLong("profile_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}

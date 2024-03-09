package sk.fiit.bvi.lab.Entity.mapper;


import org.springframework.jdbc.core.RowMapper;
import sk.fiit.bvi.lab.Entity.UserLab1;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLab1Mapper implements RowMapper<UserLab1> {
    @Override
    public UserLab1 mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserLab1 user = new UserLab1();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setProfileId(rs.getLong("profile_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}

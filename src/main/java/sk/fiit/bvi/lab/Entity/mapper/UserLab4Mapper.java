package sk.fiit.bvi.lab.Entity.mapper;


import org.springframework.jdbc.core.RowMapper;
import sk.fiit.bvi.lab.Entity.UserLab4;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLab4Mapper implements RowMapper<UserLab4> {
    @Override
    public UserLab4 mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserLab4 user = new UserLab4();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setProfileId(rs.getString("profile_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}

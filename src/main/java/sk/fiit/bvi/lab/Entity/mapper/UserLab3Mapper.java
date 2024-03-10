package sk.fiit.bvi.lab.Entity.mapper;


import org.springframework.jdbc.core.RowMapper;
import sk.fiit.bvi.lab.Entity.UserLab3;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLab3Mapper implements RowMapper<UserLab3> {
    @Override
    public UserLab3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserLab3 user = new UserLab3();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setProfileId(rs.getString("profile_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}

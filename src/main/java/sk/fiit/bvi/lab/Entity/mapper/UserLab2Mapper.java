package sk.fiit.bvi.lab.Entity.mapper;


import org.springframework.jdbc.core.RowMapper;
import sk.fiit.bvi.lab.Entity.UserLab2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLab2Mapper implements RowMapper<UserLab2> {
    @Override
    public UserLab2 mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserLab2 user = new UserLab2();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setProfileId(rs.getString("profile_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}

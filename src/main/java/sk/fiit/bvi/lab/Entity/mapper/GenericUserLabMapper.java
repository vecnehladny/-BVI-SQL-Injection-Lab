package sk.fiit.bvi.lab.Entity.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericUserLabMapper<T extends UserMapperInterface> implements RowMapper<T> {

    private final Class<T> type;

    public GenericUserLabMapper(Class<T> type) {
        this.type = type;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T user = null;
        try {
            user = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new SQLException("Cannot create instance of " + type.getName(), e);
        }

        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setProfileIdFromObject(rs.getObject("profile_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }
}
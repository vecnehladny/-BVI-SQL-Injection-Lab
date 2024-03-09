package sk.fiit.bvi.lab.Entity.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class StringMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder result = new StringBuilder();
            result.append("[");

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                Object columnValue = rs.getObject(i);

                result.append(columnName).append(": ").append(columnValue);

                if (i < columnCount) {
                    result.append(", ");
                }
            }

            result.append("]");
            return result.toString();
        } catch (SQLException e) {
            throw new RuntimeException("Error mapping row", e);
        }
    }
}

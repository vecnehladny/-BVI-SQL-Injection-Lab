package sk.fiit.bvi.lab.Service;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class AbstractLabService {

    private String ctf = "ctf";
    private String query = "empty";

    JdbcTemplate jdbcTemplate;

    public String getCTF() {
        return ctf;
    }

    public void setCTF(String ctf) {
        this.ctf = ctf;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public AbstractLabService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}

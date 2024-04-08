package sk.fiit.bvi.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sk.fiit.bvi.lab.Entity.UserLab1;
import sk.fiit.bvi.lab.Entity.mapper.StringMapper;
import sk.fiit.bvi.lab.Entity.mapper.UserLab1Mapper;
import sk.fiit.bvi.lab.Wrapper.Lab1LoginWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class Lab1Service {

    public static final String CTF = "af6d3d02f07448618a04d4a9223f4f36";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Lab1Service(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Lab1LoginWrapper getUsers(String profileId, String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder()
                               .encodeToString(hash);
        String query = String.format(
                "SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab1 u WHERE u.profile_id=%s AND u.password='%s'",
                profileId,
                encoded);

        List<String> users = jdbcTemplate.query(query, new StringMapper());
        return new Lab1LoginWrapper(query, users);
    }

    public List<UserLab1> getUser(String profileId) {
        String query = String.format("SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab1 u WHERE u.profile_id=%s",
                                     profileId);
        return jdbcTemplate.query(query, new UserLab1Mapper());
    }

    public String getCTF() {
        return CTF;
    }
}

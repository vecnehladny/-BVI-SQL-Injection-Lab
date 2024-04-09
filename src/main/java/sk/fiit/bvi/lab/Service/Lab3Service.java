package sk.fiit.bvi.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sk.fiit.bvi.lab.Entity.UserLab3;
import sk.fiit.bvi.lab.Entity.mapper.StringMapper;
import sk.fiit.bvi.lab.Entity.mapper.UserLab3Mapper;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class Lab3Service {

    public static final String CTF = "ed58ec329fe189ac30abbcc9166d6610";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Lab3Service(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LoginWrapper getUsers(String profileId, String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder()
                               .encodeToString(hash);
        String query = String.format(
                "SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab3 u WHERE u.profile_id='%s' AND u.password='%s'",
                profileId,
                encoded);

        List<String> users = jdbcTemplate.query(query, new StringMapper());
        return new LoginWrapper(query, users);
    }

    public List<UserLab3> getUser(String profileId) {
        String query = String.format(
                "SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab3 u WHERE u.profile_id='%s'",
                profileId);
        return jdbcTemplate.query(query, new UserLab3Mapper());
    }

    public String getCTF() {
        return CTF;
    }
}

package sk.fiit.bvi.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sk.fiit.bvi.lab.Entity.UserLab4;
import sk.fiit.bvi.lab.Entity.mapper.GenericUserLabMapper;
import sk.fiit.bvi.lab.Entity.mapper.StringMapper;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class Lab4Service extends AbstractLabService implements LabServiceInterface<UserLab4> {

    @Autowired
    public Lab4Service(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setCTF("27398e37530a67f859ecf64728262157");
        setQuery("SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab4 u ");
    }

    public LoginWrapper getUsers(String profileId, String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder()
                               .encodeToString(hash);
        String query = String.format(
                getQuery().concat("WHERE u.profile_id='%s' AND u.password='%s'"),
                profileId,
                encoded);

        List<String> users = jdbcTemplate.query(query, new StringMapper());
        return new LoginWrapper(query, users);
    }

    public List<UserLab4> getUser(String profileId) {
        String query = String.format(getQuery().concat("WHERE u.profile_id='%s'"),
                                     profileId);
        return jdbcTemplate.query(query, new GenericUserLabMapper<>(UserLab4.class));
    }
}

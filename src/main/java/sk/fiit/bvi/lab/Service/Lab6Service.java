package sk.fiit.bvi.lab.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sk.fiit.bvi.lab.Entity.UserLab6;
import sk.fiit.bvi.lab.Entity.mapper.GenericUserLabMapper;
import sk.fiit.bvi.lab.Entity.mapper.StringMapper;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class Lab6Service extends AbstractLabService implements LabServiceInterface<UserLab6> {

    @Autowired
    public Lab6Service(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setCTF("7815696ecbf1c96e6894b779456d330e");
        setQuery("SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab6 u ");
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

    public List<UserLab6> getUser(String profileId) {
        String query = String.format(getQuery().concat("WHERE u.profile_id='%s'"),
                                     profileId);
//        try {
            return jdbcTemplate.query(query, new GenericUserLabMapper<>(UserLab6.class));
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
    }
    //a' UNION SELECT NULL, NULL, NULL, NULL, NULL-- -

    //' UNION SELECT 1, password,name,profile_id,email from users_lab6-- -
}

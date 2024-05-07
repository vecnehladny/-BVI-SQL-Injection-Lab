package sk.fiit.bvi.lab.Service;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sk.fiit.bvi.lab.Dto.Lab5EditProfileDto;
import sk.fiit.bvi.lab.Entity.UserLab5;
import sk.fiit.bvi.lab.Entity.mapper.GenericUserLabMapper;
import sk.fiit.bvi.lab.Entity.mapper.StringMapper;
import sk.fiit.bvi.lab.Wrapper.EditWrapper;
import sk.fiit.bvi.lab.Wrapper.LoginWrapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class Lab5Service extends AbstractLabService implements LabServiceInterface<UserLab5> {

    @Autowired
    public Lab5Service(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setCTF("d41d8cd98f00b204e9800998ecf8427e");
        setQuery("SELECT u.id, u.name, u.profile_id, u.email, u.username FROM users_lab5 u ");
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

    public List<UserLab5> getUser(String profileId) {
        String query = String.format(getQuery().concat("WHERE u.profile_id='%s'"),
                                     profileId);
        return jdbcTemplate.query(query, new GenericUserLabMapper<>(UserLab5.class));
    }

    public EditWrapper editUser(String profileId, Lab5EditProfileDto newProfileData) {
        StringBuilder sb = new StringBuilder("UPDATE users_lab5 SET ");
        if (StringUtils.isNotBlank(newProfileData.getEmail())) {
            sb.append("email='")
              .append(newProfileData.getEmail())
              .append("'");}
        if (StringUtils.isNotBlank(newProfileData.getUsername())) {
            if (StringUtils.isNotBlank(newProfileData.getEmail())) {sb.append(", ");}
            sb.append("username='")
              .append(newProfileData.getUsername())
              .append("'");
        }
        sb.append(" WHERE profile_id='")
          .append(profileId)
          .append("'");
        int update = jdbcTemplate.update(sb.toString());
        return new EditWrapper(sb.toString(), update);
    }
}

package sk.fiit.bvi.lab.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.fiit.bvi.lab.Entity.mapper.UserMapperInterface;

@Entity
@Table(name = "users_lab4")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLab4 implements UserMapperInterface {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_id")
    private String profileId;

    @Column(name = "username")
    private String username;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileId() {
        return this.profileId;
    }

    @Override
    public void setProfileIdFromObject(Object o) {
        if (o instanceof String stringProfileId) {
            this.profileId = stringProfileId;
        } else {
            throw new IllegalArgumentException("profileId must be of type String for UserLab4");
        }
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

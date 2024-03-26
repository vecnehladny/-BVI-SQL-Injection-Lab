package sk.fiit.bvi.lab.Wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.fiit.bvi.lab.Entity.UserLab1;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lab1HomeWrapper extends Lab1QueryWrapper {
    List<UserLab1> users;

    public Lab1HomeWrapper(String query, List<UserLab1> users) {
        this.query = query;
        this.users = users;
    }
}

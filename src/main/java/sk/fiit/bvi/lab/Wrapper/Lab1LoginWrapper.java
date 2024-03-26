package sk.fiit.bvi.lab.Wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lab1LoginWrapper extends Lab1QueryWrapper {
    List<String> users;

    public Lab1LoginWrapper(String query, List<String> users) {
        this.query = query;
        this.users = users;
    }
}

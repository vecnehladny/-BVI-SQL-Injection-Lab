package sk.fiit.bvi.lab.Wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditWrapper extends QueryWrapper {
    int updated;

    public EditWrapper(String query, int updated) {
        this.query = query;
        this.updated = updated;
    }
}

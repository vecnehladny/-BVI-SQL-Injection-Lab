package sk.fiit.bvi.lab.Wrapper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    private String type;
    private String message;
    private Exception exception;
}

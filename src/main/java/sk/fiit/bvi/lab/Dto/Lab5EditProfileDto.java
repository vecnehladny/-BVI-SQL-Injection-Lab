package sk.fiit.bvi.lab.Dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Lab5EditProfileDto {
    private String email;
    private String username;
    private String password;
}

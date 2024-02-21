package ru.skillbox.contactapplications.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
@ToString
public class Contact {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}

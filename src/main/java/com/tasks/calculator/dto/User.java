package com.tasks.calculator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.tasks.calculator.dto.enums.Gender;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;


@Entity
@Table(name = "USERS")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName(value = "user")
/*User in the payment system, active transaction's sender*/
public class User extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    public User(@JsonProperty("login") String userName,
                @JsonProperty("password") String password) {
        this.userName = userName;
        this.password = password;
    }


    //@NotNull(message = "Please enter first name")
    @JsonProperty
    @Column(name = "FIRST_NAME")
    //@NotBlank(message = "User first name cannot be blank")
    private String firstName;

    //@NotNull(message = "Please enter last name")
    @JsonProperty
    @Column(name = "LAST_NAME")
    //@NotBlank(message = "User last name cannot be blank")
    private String lastName;

    //@NotNull
    @JsonProperty
    @Column(name = "FULL_NAME")
    //@NotBlank(message = "User full name cannot be blank")
    private String fullName;

    //key

    //@NotNull(message = "Please enter email")
    @JsonProperty
    @Email(message = "Email should be valid")
    @Column(name = "EMAIL")
    private String email;

    //key
    @JsonProperty
    @Column(name = "USER_NAME", nullable = false, unique = true)
    @NotBlank(message = "UserName cannot be blank")
    private String userName;

    //key
    @JsonProperty
    @Length(min = 5, max = 142, message = "Password should be grater than  5 characters")
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @JsonProperty
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "BIRTH_DATE")
    private java.util.Date birthDate;

    @JsonProperty
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number")
    @Size(max = 25)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @JsonProperty
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "GENDER")
    private Gender gender;

    @JsonProperty
    @Column(name = "PICTURE")
    private String picture;

    @JsonProperty
    @Column(name = "NOTES", length = 200)
    private String notes;

    @JsonProperty
    @NotNull(message = "Please enter role name")
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "id")
/*
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
*/
    private Set<Role> roles;
}

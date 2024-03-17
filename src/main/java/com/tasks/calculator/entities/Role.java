package com.tasks.calculator.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tasks.calculator.entities.enums.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "ROLES")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
/*Role which have at the moment user of the payment system*/
public class Role extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull(message = "Please enter role name")
    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName = RoleName.MANAGER.name();

    @Column(name = "ROLE_DESCRIPTION")
    private String roleDescription = this.roleName;

    //ManyToOne=======================================================================================
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id", nullable = false)
    @JsonProperty
    private User user;

}

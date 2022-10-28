package eis.com.alarmservice.modeladmin;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
	
	@Column(name = "user_name")
    @Length(min = 5, message = "*Ваше имя пользователя должно содержать не менее 5 символов.")
    @NotEmpty(message = "*Пожалуйста, укажите имя пользователя")
    private String userName;
	
	@Column(name = "email")
    @Email(message = "*Пожалуйста, укажите действительный адрес электронной почты")
    @NotEmpty(message = "*Пожалуйста, укажите электронную почту")
    private String email;
    
    @Column(name = "password")
    @Length(min = 5, message = "*Ваш пароль должен содержать не менее 5 символов.")
    @NotEmpty(message = "*Пожалуйста, укажите свой пароль")
    private String password;
    
    @Column(name = "name")
    @NotEmpty(message = "*Пожалуйста, укажите ваше имя")
    private String name;
    
    @Column(name = "last_name")
    @NotEmpty(message = "*Пожалуйста, укажите вашу фамилию")
    private String lastName;
    
    @Column(name = "active")
    private Boolean active;
    
    /*******************************/
    @Transient
    private String nameRole;
    /*******************************/
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


}

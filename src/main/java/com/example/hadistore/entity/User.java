package com.example.hadistore.entity;

import com.example.hadistore.enums.Gender;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    @NotBlank(message = "username không được để trống")
    private String username;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "email")
    @NotBlank(message = "email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;
    @Column(name = "password")
    @NotBlank(message = "password không được để trống")
    @Size(min = 6, message = "Password phải có ít nhất 6 ký tự")
    private String password;
    @Column(name = "phone_number", length = 10)
    private String phoneNumber;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Rate> rateList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Order> orderList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Cart> cartList;
}

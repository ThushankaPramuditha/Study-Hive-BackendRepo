//package com.example.Study_Hive_Backend.user;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//
//@Table(name ="_user")
//public class User implements UserDetails {
//    @Id
//
//    @GeneratedValue
//    private Integer id;
//    private String firstname;
//    private String lastname;
//    private String email;
//    private String password;
//
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
////        return true;
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
////        return true;
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
//
//
//}

//package com.example.Study_Hive_Backend.user;
//
//import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.List;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "_user")
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue
//    private Integer id;
//    private String firstname;
//    private String lastname;
//    @Column(nullable = false, unique = true)
//    private String email;
//    private String password;
//
//    private Boolean blocked;
//
//
//    @Column(name = "created_date", updatable = false)
//    private LocalDateTime createdDate;
//
////    @Column(name = "is_blocked")
////    private boolean blocked = false;
//
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @Enumerated(EnumType.STRING)
//    private Status status; // New status field
//
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdDate = LocalDateTime.now();
//        System.out.println("Setting createdDate to: " + createdDate);
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Profile profile;
//
//
//
////    public String firstnameAndlastname() {
////        return firstname +' ' + lastname;
////    }
//}

package com.example.Study_Hive_Backend.user;

import com.example.Study_Hive_Backend.profilesetup.entity.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user") // Avoid using "user" as table name, as it may be a reserved keyword
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private Boolean blocked;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status; // New status field

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        System.out.println("Setting createdDate to: " + createdDate);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust based on your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Boolean.TRUE.equals(blocked); // Blocked users cannot log in
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust based on your logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Adjust based on your logic
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Profile profile; // Establish bidirectional mapping with Profile
}


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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;






    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status; // New status field

    @Column(name = "blocked", nullable = false)
    private Boolean blocked = false;

    @Column(name = "block_count", nullable = false)
    private int blockCount = 0;


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
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
    public String firstname() {
        return firstname;
    }

    public Boolean getBlocked() {
        return blocked;
    }



    public Integer getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(Integer blockCount) {
        this.blockCount = blockCount;
    }

    public void setBlocked(Boolean blocked) {
        if (!this.blocked && blocked) { // Increment only if changing from unblocked to blocked
            this.blockCount++;
        }
        this.blocked = blocked;
    }


   public String firstnameAndlastname() {
       return firstname +' ' + lastname;
   }
  
    public String lastname() {
        return lastname;
    }
}

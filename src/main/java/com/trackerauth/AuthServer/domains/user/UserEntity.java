package com.trackerauth.AuthServer.domains.user;

import com.trackerauth.AuthServer.domains.user.scope.UserScope;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class UserEntity {

    @Id
    private String id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "scope")
    private UserScope scope;

}

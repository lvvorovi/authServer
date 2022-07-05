package com.trackerauth.AuthServer.domains.client;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "clients")
public class ClientEntity {

    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "secret")
    private String secret;

}

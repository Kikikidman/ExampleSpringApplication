package com.example.demo.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "Users")
@ApiModel(description = "Вся информация о пользователе. ")

public class User {
    @ApiModelProperty(notes = "ID пользователя")
    private long id;
    @ApiModelProperty(notes = "Имя пользователя")
    private String first_name;
    @ApiModelProperty(notes = "Фамилия пользователя")
    private String last_name;

    public User() {

    }

    public User(String firstName, String lastName) {
        this.first_name = firstName;
        this.last_name = lastName;
    }

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return first_name;
    }
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return last_name;
    }
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + first_name + ", lastName=" + last_name + "]";
    }
}

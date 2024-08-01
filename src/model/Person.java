package model;

import java.time.LocalDate;

public abstract class Person {
    private int id;
    private String code;
    private String name;
    private LocalDate birthday;
    private String email;

    public Person(int id, String code, String name, LocalDate birthday, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.birthday = birthday;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", code='" + code + '\'' +
                        ", name='" + name + '\'' +
                        ", birthday=" + birthday +
                        ", email='" + email + '\'';
    }
}

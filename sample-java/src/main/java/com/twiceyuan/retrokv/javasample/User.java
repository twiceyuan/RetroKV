package com.twiceyuan.retrokv.javasample;

import java.io.Serializable;

/**
 * Created by twiceYuan on 09/02/2017.
 * <p>
 * <p>
 * 用户对象
 */
class User implements Serializable {

    public User(String username, String password, Float score, Integer age) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.age = age;
    }

    String  username;
    String  password;
    Float   score;
    Integer age;

    public String toString() {
        return String.format("" +
                        "{\n" +
                        "    username: %s,\n" +
                        "    password: %s,\n" +
                        "    score: %s,\n" +
                        "    age: %s\n" +
                        "}",
                username, password, score, age);
    }
}

package com.goodshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;
    private String name;
    private String pass;
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User objUser = (User) o;
        return id == objUser.id && name.equals(objUser.name) && pass.equals(objUser.pass);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash += name.hashCode() * 31;
        hash += pass.hashCode() * 31;
        hash += role.hashCode() * 31;
        return hash;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Role {

        private long id;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Role objRole = (Role) o;
            return id == objRole.id && name.equals(objRole.name);
        }

        @Override
        public int hashCode() {
            int hash = super.hashCode();
            hash += name.hashCode() * 31;
            return hash;
        }
    }
}

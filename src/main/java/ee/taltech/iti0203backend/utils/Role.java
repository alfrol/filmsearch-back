package ee.taltech.iti0203backend.utils;

public enum Role {

    ADMIN,
    GUEST,
    USER;

    public String toSpringRole() {
        return "ROLE_" + this.name();
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}

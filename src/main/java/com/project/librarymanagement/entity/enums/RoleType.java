package com.project.librarymanagement.entity.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN("Admin"),
    EMPLOYEE("Employee"),
    MEMBER("Member");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}

package com.example.school.security;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {
    VIEW_USER("Allows viewing user details"),
    CREATE_USER("Allows creating new users"),
    EDIT_USER("Allows editing existing users"),
    DELETE_USER("Allows deleting users"),
    APPROVE_USER("Allows approving users"),
    REJECT_USER("Allows rejecting users"),
    VIEW_REPORTS("Allows viewing reports"),
    MANAGE_ROLES("Allows managing roles"),
    VIEW_WORKSHOP("Allows viewing workshops"),
    CREATE_WORKSHOP("Allows creating workshops"),
    EDIT_WORKSHOP("Allows editing workshops"),
    DELETE_WORKSHOP("Allows deleting workshops");

    private final String description; // `final` ensures immutability
}


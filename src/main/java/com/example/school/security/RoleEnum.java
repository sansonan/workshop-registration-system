package com.example.school.security;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import static com.example.school.security.PermissionEnum.*;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleEnum {

    ADMIN(Set.of(VIEW_USER, CREATE_USER,EDIT_USER,DELETE_USER, APPROVE_USER, REJECT_USER,VIEW_REPORTS,MANAGE_ROLES,VIEW_WORKSHOP,CREATE_WORKSHOP,EDIT_WORKSHOP,DELETE_WORKSHOP)),
    STUDENT(Set.of(CREATE_USER,EDIT_USER,VIEW_WORKSHOP,EDIT_WORKSHOP));

    private Set<PermissionEnum> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> grantedAuthorities = this.permissions.stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.getDescription()))
                .collect(Collectors.toSet());
        SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_"+this.name());
        grantedAuthorities.add(role);
        return grantedAuthorities;
    }
}

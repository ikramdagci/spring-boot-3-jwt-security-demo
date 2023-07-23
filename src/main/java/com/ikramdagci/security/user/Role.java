package com.ikramdagci.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

import static com.ikramdagci.security.user.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(Set.of(ADMIN_READ,ADMIN_UPDATE,ADMIN_DELETE,ADMIN_CREATE,MANAGER_READ,MANAGER_UPDATE,MANAGER_DELETE,MANAGER_CREATE)),
    MANAGER(Set.of(MANAGER_READ,MANAGER_UPDATE,MANAGER_DELETE,MANAGER_CREATE));

    @Getter
    private final Set<Permission> permisions;
}

package com.example.taskmanager.service;

import com.example.taskmanager.model.Role;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    List<Role> findAll();
}

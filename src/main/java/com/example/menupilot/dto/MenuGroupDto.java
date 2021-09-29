package com.example.menupilot.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuGroupDto {
    private String name;
    private List<MenuDto> menus;
}

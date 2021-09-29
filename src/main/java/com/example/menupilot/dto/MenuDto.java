package com.example.menupilot.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {
    private String menuType;
    private String icon;
    private String name;
    private Integer ordered;
    private String link;
    private String pageLinkType;
    private List<MenuDto> childMenus;
}

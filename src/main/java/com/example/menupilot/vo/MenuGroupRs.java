package com.example.menupilot.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuGroupRs {
    private String name;
    private List<MenuRs> menus;
}

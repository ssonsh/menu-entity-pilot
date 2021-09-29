package com.example.menupilot.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuRs {
    private String menuType;
    private String icon;
    private String name;
    private Integer ordered;
    private String link;
    private String pageLinkType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuRs> childMenus;

}

package com.example.menupilot.entity.menu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("PAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Slf4j
public class PageMenu extends Menu{

    private String link;

    @Enumerated(EnumType.STRING)
    private PageLinkType pageLinkType;

    @Override
    public String getMenuType() {
        return "PAGE";
    }
}

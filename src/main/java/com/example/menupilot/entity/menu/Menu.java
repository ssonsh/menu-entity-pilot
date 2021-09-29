package com.example.menupilot.entity.menu;

import com.example.menupilot.entity.menugroup.MenuGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "menu_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Slf4j
public abstract class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String icon;
    private String name;
    private Integer ordered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "upward_menu_id",
        foreignKey = @ForeignKey(name = "fk_menu_of_menu")
    )
    private Menu upwardMenu;

    @OneToMany(mappedBy = "upwardMenu")
    private List<Menu> childMenus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_group_id", foreignKey = @ForeignKey(name = "fk_menu_group_of_menu"))
    private MenuGroup menuGroup;

    public void addChildMenu(Menu menu){
        this.childMenus.add(menu);
        menu.setUpwardMenu(this);
    }

    public void setUpwardMenu(Menu menu){
        this.upwardMenu = menu;
    }

    public boolean isRootMenu() { return this.upwardMenu == null; }

    public abstract String getMenuType();

    public void setMenuGroup(MenuGroup menuGroup){
        this.menuGroup = menuGroup;
        menuGroup.addMenu(this);
    }
}

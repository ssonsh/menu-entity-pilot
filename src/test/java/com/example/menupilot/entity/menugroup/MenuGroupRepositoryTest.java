package com.example.menupilot.entity.menugroup;

import com.example.menupilot.dto.MenuGroupDto;
import com.example.menupilot.vo.MenuGroupRs;
import com.example.menupilot.entity.menu.*;
import com.example.menupilot.vo.MenuRs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DataJpaTest
class MenuGroupRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void createTest() {
        // given
        TextMenu pmMenu = TextMenu.builder().name("PM").ordered(1).build();
        FolderMenu gmMenu = FolderMenu.builder().name("GM").ordered(1).build();
        FolderMenu wmMenu = FolderMenu.builder().name("WM").ordered(2).build();
        FolderMenu cmMenu = FolderMenu.builder().name("CM").ordered(3).build();
        FolderMenu rvMenu = FolderMenu.builder().name("RV").ordered(4).build();

        PageMenu giMenu = PageMenu.builder().name("GI").link("/performance/home").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu spMenu = PageMenu.builder().name("SP").link("/performance/strategy").pageLinkType(PageLinkType.THIS).ordered(2).build();

        PageMenu tdMenu = PageMenu.builder().name("TD").link("/todo/home").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu lfMenu = PageMenu.builder().name("LF").link("/livefeed/home").pageLinkType(PageLinkType.THIS).ordered(2).build();

        PageMenu htMenu = PageMenu.builder().name("HT").link("/heartTalk/home").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu rpMenu = PageMenu.builder().name("RP").link("/retrospective/home").pageLinkType(PageLinkType.THIS).ordered(2).build();

        menuRepository.save(pmMenu);
        menuRepository.save(gmMenu);
        menuRepository.save(wmMenu);
        menuRepository.save(cmMenu);
        menuRepository.save(rvMenu);
        menuRepository.save(giMenu);
        menuRepository.save(spMenu);
        menuRepository.save(tdMenu);
        menuRepository.save(lfMenu);
        menuRepository.save(htMenu);
        menuRepository.save(rpMenu);

        entityManager.flush();
        entityManager.clear();

        // all menus -> Presistence Context 1st Caching
        List<Menu> menus = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "ordered"));

        Menu pm = findMenuByName(menus, "PM");
        Menu gm = findMenuByName(menus, "GM");
        Menu wm = findMenuByName(menus, "WM");
        Menu cm = findMenuByName(menus, "CM");
        Menu rv = findMenuByName(menus, "RV");

        Menu gi = findMenuByName(menus, "GI");
        Menu sp = findMenuByName(menus, "SP");
        Menu td = findMenuByName(menus, "TD");
        Menu lf = findMenuByName(menus, "LF");
        Menu ht = findMenuByName(menus, "HT");
        Menu rp = findMenuByName(menus, "RP");

        gm.addChildMenu(gi);
        gm.addChildMenu(sp);

        wm.addChildMenu(td);

        cm.addChildMenu(lf);

        rv.addChildMenu(ht);
        rv.addChildMenu(rp);

        pm.addChildMenu(gm);
        pm.addChildMenu(wm);
        pm.addChildMenu(cm);
        pm.addChildMenu(rv);

        entityManager.flush();
        entityManager.clear();

        // all menus -> Presistence Context 1st Caching
        List<Menu> finalMenus = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "ordered"));
        System.out.println("finalMenus" + finalMenus);

        MenuGroup hrpMenuGroup = MenuGroup.builder()
                                          .name("HRP LEFT")
                                          .serviceType(ServiceType.HRP)
                                          .positionType(PositionType.LEFT)
                                          .menus(new ArrayList<>())
                                          .build();

        finalMenus.forEach(v -> v.setMenuGroup(hrpMenuGroup));

        menuGroupRepository.save(hrpMenuGroup);
        entityManager.flush();
        entityManager.clear();

        ModelMapper modelMapper = new ModelMapper();
        MenuGroup finalMenuGroup = menuGroupRepository.findById(hrpMenuGroup.getId()).get();


        List<MenuRs> menuRsList = new ArrayList<>();
        finalMenuGroup.getMenus().stream().filter(Menu::isRootMenu).collect(Collectors.toList()).forEach(v -> {
            menuRsList.add(modelMapper.map(v, MenuRs.class));
        });

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json2 = objectMapper.writeValueAsString(menuRsList);
            System.out.println(json2);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // then
    }


    @Test
    public void v2() {
        // given
        PageMenu gyMenu = PageMenu.builder().name("GY").link("/gy").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu blogMenu = PageMenu.builder().name("BLOG").link("/blog").pageLinkType(PageLinkType.THIS).ordered(2).build();
        PageMenu bsMenu = PageMenu.builder().name("BS").link("/bs").pageLinkType(PageLinkType.THIS).ordered(3).build();
        PageMenu calMenu = PageMenu.builder().name("CAL").link("/cal").pageLinkType(PageLinkType.THIS).ordered(4).build();
        PageMenu spsetMenu = PageMenu.builder().name("SPSET").link("/spset").pageLinkType(PageLinkType.THIS).ordered(5).build();
        FolderMenu pgMenu = FolderMenu.builder().name("PG").ordered(6).build();

        PageMenu p1Menu = PageMenu.builder().name("P1").link("/p1").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu p2Menu = PageMenu.builder().name("P2").link("/p2").pageLinkType(PageLinkType.THIS).ordered(2).build();
        PageMenu p3Menu = PageMenu.builder().name("P3").link("/p3").pageLinkType(PageLinkType.THIS).ordered(3).build();
        PageMenu p4Menu = PageMenu.builder().name("P4").link("/p4").pageLinkType(PageLinkType.THIS).ordered(4).build();
        PageMenu p5Menu = PageMenu.builder().name("P5").link("/p5").pageLinkType(PageLinkType.THIS).ordered(5).build();

        menuRepository.save(gyMenu);
        menuRepository.save(blogMenu);
        menuRepository.save(bsMenu);
        menuRepository.save(calMenu);
        menuRepository.save(spsetMenu);
        menuRepository.save(pgMenu);
        menuRepository.save(p1Menu);
        menuRepository.save(p2Menu);
        menuRepository.save(p3Menu);
        menuRepository.save(p4Menu);
        menuRepository.save(p5Menu);

        entityManager.flush();
        entityManager.clear();

        List<Menu> menus = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "ordered"));

        Menu pg = findMenuByName(menus, "PG");
        Menu p1 = findMenuByName(menus, "P1");
        Menu p2 = findMenuByName(menus, "P2");
        Menu p3 = findMenuByName(menus, "P3");
        Menu p4 = findMenuByName(menus, "P4");
        Menu p5 = findMenuByName(menus, "P5");

        pg.addChildMenu(p1);
        pg.addChildMenu(p2);
        pg.addChildMenu(p3);
        pg.addChildMenu(p4);
        pg.addChildMenu(p5);


        entityManager.flush();
        entityManager.clear();

        // all menus -> Presistence Context 1st Caching
        List<Menu> finalMenus = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "ordered"));
        System.out.println("finalMenus" + finalMenus);
        MenuGroup hrpMenuGroup = MenuGroup.builder()
                                          .name("HRP LEFT")
                                          .serviceType(ServiceType.HRP)
                                          .positionType(PositionType.LEFT)
                                          .menus(new ArrayList<>())
                                          .build();
        finalMenus.forEach(v -> v.setMenuGroup(hrpMenuGroup));

        menuGroupRepository.save(hrpMenuGroup);
        entityManager.flush();
        entityManager.clear();

        ModelMapper modelMapper = new ModelMapper();
        MenuGroup finalMenuGroup = menuGroupRepository.findById(hrpMenuGroup.getId()).get();

        List<MenuRs> menuRsList = new ArrayList<>();
        finalMenuGroup.getMenus().stream().filter(Menu::isRootMenu).collect(Collectors.toList()).forEach(v -> {
            menuRsList.add(modelMapper.map(v, MenuRs.class));
        });

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json2 = objectMapper.writeValueAsString(menuRsList);
            System.out.println(json2);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void v3() {
        // given
        FolderMenu midasianMenu = FolderMenu.builder().name("MIDASIAN").ordered(1).build();
        FolderMenu happinessMenu = FolderMenu.builder().name("HAPPINESS").ordered(2).build();
        FolderMenu rewardsMenu = FolderMenu.builder().name("REWARD").ordered(3).build();
        FolderMenu sharingMenu = FolderMenu.builder().name("SHARING").ordered(4).build();

        PageMenu m1Menu = PageMenu.builder().name("M1").link("/m1").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu m2Menu = PageMenu.builder().name("M2").link("/m2").pageLinkType(PageLinkType.THIS).ordered(2).build();
        PageMenu m3Menu = PageMenu.builder().name("M3").link("/m3").pageLinkType(PageLinkType.THIS).ordered(3).build();
        PageMenu m4Menu = PageMenu.builder().name("M4").link("/m4").pageLinkType(PageLinkType.THIS).ordered(4).build();

        PageMenu h1Menu = PageMenu.builder().name("H1").link("/h1").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu h2Menu = PageMenu.builder().name("H2").link("/h2").pageLinkType(PageLinkType.THIS).ordered(2).build();

        PageMenu r1Menu = PageMenu.builder().name("R1").link("/r1").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu r2Menu = PageMenu.builder().name("R2").link("/r2").pageLinkType(PageLinkType.THIS).ordered(2).build();
        PageMenu r3Menu = PageMenu.builder().name("R3").link("/r3").pageLinkType(PageLinkType.THIS).ordered(3).build();

        PageMenu s1Menu = PageMenu.builder().name("S1").link("/s1").pageLinkType(PageLinkType.THIS).ordered(1).build();

        menuRepository.save(midasianMenu);
        menuRepository.save(happinessMenu);
        menuRepository.save(rewardsMenu);
        menuRepository.save(sharingMenu);

        menuRepository.save(m1Menu);
        menuRepository.save(m2Menu);
        menuRepository.save(m3Menu);
        menuRepository.save(m4Menu);

        menuRepository.save(h1Menu);
        menuRepository.save(h2Menu);

        menuRepository.save(r1Menu);
        menuRepository.save(r2Menu);
        menuRepository.save(r3Menu);

        menuRepository.save(s1Menu);

        entityManager.flush();
        entityManager.clear();


        List<Menu> menus = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "ordered"));
        Menu midasian = findMenuByName(menus, "MIDASIAN");
        Menu happiness = findMenuByName(menus, "HAPPINESS");
        Menu reward = findMenuByName(menus, "REWARD");
        Menu sharing = findMenuByName(menus, "SHARING");

        Menu m1 = findMenuByName(menus, "M1");
        Menu m2 = findMenuByName(menus, "M2");
        Menu m3 = findMenuByName(menus, "M3");
        Menu m4 = findMenuByName(menus, "M4");

        Menu h1 = findMenuByName(menus, "H1");
        Menu h2 = findMenuByName(menus, "H2");

        Menu r1 = findMenuByName(menus, "R1");
        Menu r2 = findMenuByName(menus, "R2");
        Menu r3 = findMenuByName(menus, "R3");

        Menu s1 = findMenuByName(menus, "S1");

        midasian.addChildMenu(m1);
        midasian.addChildMenu(m2);
        midasian.addChildMenu(m3);
        midasian.addChildMenu(m4);

        happiness.addChildMenu(h1);
        happiness.addChildMenu(h2);

        reward.addChildMenu(r1);
        reward.addChildMenu(r2);
        reward.addChildMenu(r3);

        sharing.addChildMenu(s1);

        entityManager.flush();
        entityManager.clear();

        // all menus -> Presistence Context 1st Caching
        List<Menu> finalMenus = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "ordered"));
        System.out.println("finalMenus" + finalMenus);


        MenuGroup hrpMenuGroup = MenuGroup.builder()
                                          .name("MIDASIN MENU")
                                          .serviceType(ServiceType.HRP)
                                          .positionType(PositionType.TOP)
                                          .menus(new ArrayList<>())
                                          .build();
        finalMenus.forEach(v -> v.setMenuGroup(hrpMenuGroup));

        menuGroupRepository.save(hrpMenuGroup);
        entityManager.flush();
        entityManager.clear();

        ModelMapper modelMapper = new ModelMapper();
        MenuGroup finalMenuGroup = menuGroupRepository.findById(hrpMenuGroup.getId()).get();

        List<MenuRs> menuRsList = new ArrayList<>();
        finalMenuGroup.getMenus().stream().filter(Menu::isRootMenu).collect(Collectors.toList()).forEach(v -> {
            menuRsList.add(modelMapper.map(v, MenuRs.class));
        });

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json2 = objectMapper.writeValueAsString(menuRsList);
            System.out.println(json2);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void v4() {

        MenuGroup topMenuGroup = MenuGroup.builder()
                                          .name("TOP_MENU")
                                          .serviceType(ServiceType.HRP)
                                          .positionType(PositionType.TOP)
                                          .menus(new ArrayList<>())
                                          .build();
        MenuGroup leftMenuGroup = MenuGroup.builder()
                                           .name("LEFT_MENU")
                                           .serviceType(ServiceType.HRP)
                                           .positionType(PositionType.LEFT)
                                           .menus(new ArrayList<>())
                                           .build();
        menuGroupRepository.save(topMenuGroup);
        menuGroupRepository.save(leftMenuGroup);
        entityManager.flush();
        entityManager.clear();

        MenuGroup findTopMenuGroup = menuGroupRepository.findById(topMenuGroup.getId()).get();
        MenuGroup findLeftMenuGroup = menuGroupRepository.findById(leftMenuGroup.getId()).get();
        System.out.println("> " + findTopMenuGroup.getMenus());
        System.out.println("> " + findLeftMenuGroup.getMenus());

        FolderMenu midasianMenu = FolderMenu.builder().name("MIDASIAN").ordered(1).build();
        FolderMenu happinessMenu = FolderMenu.builder().name("HAPPINESS").ordered(2).build();
        FolderMenu rewardsMenu = FolderMenu.builder().name("REWARD").ordered(3).build();
        FolderMenu sharingMenu = FolderMenu.builder().name("SHARING").ordered(4).build();

        PageMenu m1Menu = PageMenu.builder().name("M1").link("/m1").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu m2Menu = PageMenu.builder().name("M2").link("/m2").pageLinkType(PageLinkType.THIS).ordered(2).build();
        PageMenu m3Menu = PageMenu.builder().name("M3").link("/m3").pageLinkType(PageLinkType.THIS).ordered(3).build();
        PageMenu m4Menu = PageMenu.builder().name("M4").link("/m4").pageLinkType(PageLinkType.THIS).ordered(4).build();

        PageMenu h1Menu = PageMenu.builder().name("H1").link("/h1").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu h2Menu = PageMenu.builder().name("H2").link("/h2").pageLinkType(PageLinkType.THIS).ordered(2).build();

        PageMenu r1Menu = PageMenu.builder().name("R1").link("/r1").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu r2Menu = PageMenu.builder().name("R2").link("/r2").pageLinkType(PageLinkType.THIS).ordered(2).build();
        PageMenu r3Menu = PageMenu.builder().name("R3").link("/r3").pageLinkType(PageLinkType.THIS).ordered(3).build();

        PageMenu s1Menu = PageMenu.builder().name("S1").link("/s1").pageLinkType(PageLinkType.THIS).ordered(1).build();

        menuRepository.save(midasianMenu);
        menuRepository.save(happinessMenu);
        menuRepository.save(rewardsMenu);
        menuRepository.save(sharingMenu);

        menuRepository.save(m1Menu);
        menuRepository.save(m2Menu);
        menuRepository.save(m3Menu);
        menuRepository.save(m4Menu);

        menuRepository.save(h1Menu);
        menuRepository.save(h2Menu);

        menuRepository.save(r1Menu);
        menuRepository.save(r2Menu);
        menuRepository.save(r3Menu);

        menuRepository.save(s1Menu);

        entityManager.flush();
        entityManager.clear();

        List<Menu> headerMenus = menuRepository.findAllById(Lists.newArrayList(midasianMenu.getId(),
                                                                               happinessMenu.getId(),
                                                                               rewardsMenu.getId(),
                                                                               sharingMenu.getId(),
                                                                               m1Menu.getId(),
                                                                               m2Menu.getId(),
                                                                               m3Menu.getId(),
                                                                               m4Menu.getId(),
                                                                               h1Menu.getId(),
                                                                               h2Menu.getId(),
                                                                               r1Menu.getId(),
                                                                               r2Menu.getId(),
                                                                               r3Menu.getId(),
                                                                               s1Menu.getId()));
        Menu midasian = findMenuByName(headerMenus, "MIDASIAN");
        Menu happiness = findMenuByName(headerMenus, "HAPPINESS");
        Menu reward = findMenuByName(headerMenus, "REWARD");
        Menu sharing = findMenuByName(headerMenus, "SHARING");

        Menu m1 = findMenuByName(headerMenus, "M1");
        Menu m2 = findMenuByName(headerMenus, "M2");
        Menu m3 = findMenuByName(headerMenus, "M3");
        Menu m4 = findMenuByName(headerMenus, "M4");

        Menu h1 = findMenuByName(headerMenus, "H1");
        Menu h2 = findMenuByName(headerMenus, "H2");

        Menu r1 = findMenuByName(headerMenus, "R1");
        Menu r2 = findMenuByName(headerMenus, "R2");
        Menu r3 = findMenuByName(headerMenus, "R3");

        Menu s1 = findMenuByName(headerMenus, "S1");

        midasian.addChildMenu(m1);
        midasian.addChildMenu(m2);
        midasian.addChildMenu(m3);
        midasian.addChildMenu(m4);

        happiness.addChildMenu(h1);
        happiness.addChildMenu(h2);

        reward.addChildMenu(r1);
        reward.addChildMenu(r2);
        reward.addChildMenu(r3);

        sharing.addChildMenu(s1);

        entityManager.flush();
        entityManager.clear();

        headerMenus = menuRepository.findAllById(Lists.newArrayList(midasianMenu.getId(),
                                                                    happinessMenu.getId(),
                                                                    rewardsMenu.getId(),
                                                                    sharingMenu.getId(),
                                                                    m1Menu.getId(),
                                                                    m2Menu.getId(),
                                                                    m3Menu.getId(),
                                                                    m4Menu.getId(),
                                                                    h1Menu.getId(),
                                                                    h2Menu.getId(),
                                                                    r1Menu.getId(),
                                                                    r2Menu.getId(),
                                                                    r3Menu.getId(),
                                                                    s1Menu.getId()));
        headerMenus.forEach(v -> v.setMenuGroup(findTopMenuGroup));

        entityManager.flush();
        entityManager.clear();

        ////////////////////////////////////////////////////////////////////////

        TextMenu pmMenu = TextMenu.builder().name("PM").ordered(1).build();
        FolderMenu gmMenu = FolderMenu.builder().name("GM").ordered(1).build();
        FolderMenu wmMenu = FolderMenu.builder().name("WM").ordered(2).build();
        FolderMenu cmMenu = FolderMenu.builder().name("CM").ordered(3).build();
        FolderMenu rvMenu = FolderMenu.builder().name("RV").ordered(4).build();

        PageMenu giMenu = PageMenu.builder().name("GI").link("/performance/home").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu spMenu = PageMenu.builder().name("SP").link("/performance/strategy").pageLinkType(PageLinkType.THIS).ordered(2).build();

        PageMenu tdMenu = PageMenu.builder().name("TD").link("/todo/home").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu lfMenu = PageMenu.builder().name("LF").link("/livefeed/home").pageLinkType(PageLinkType.THIS).ordered(2).build();

        PageMenu htMenu = PageMenu.builder().name("HT").link("/heartTalk/home").pageLinkType(PageLinkType.THIS).ordered(1).build();
        PageMenu rpMenu = PageMenu.builder().name("RP").link("/retrospective/home").pageLinkType(PageLinkType.THIS).ordered(2).build();

        menuRepository.save(pmMenu);
        menuRepository.save(gmMenu);
        menuRepository.save(wmMenu);
        menuRepository.save(cmMenu);
        menuRepository.save(rvMenu);
        menuRepository.save(giMenu);
        menuRepository.save(spMenu);
        menuRepository.save(tdMenu);
        menuRepository.save(lfMenu);
        menuRepository.save(htMenu);
        menuRepository.save(rpMenu);

        entityManager.flush();
        entityManager.clear();

        List<Menu> leftMenus = menuRepository.findAllById(Lists.newArrayList(pmMenu.getId(),
                                                                             gmMenu.getId(),
                                                                             wmMenu.getId(),
                                                                             cmMenu.getId(),
                                                                             rvMenu.getId(),
                                                                             giMenu.getId(),
                                                                             spMenu.getId(),
                                                                             tdMenu.getId(),
                                                                             lfMenu.getId(),
                                                                             htMenu.getId(),
                                                                             rpMenu.getId()));


        Menu pm = findMenuByName(leftMenus, "PM");
        Menu gm = findMenuByName(leftMenus, "GM");
        Menu wm = findMenuByName(leftMenus, "WM");
        Menu cm = findMenuByName(leftMenus, "CM");
        Menu rv = findMenuByName(leftMenus, "RV");

        Menu gi = findMenuByName(leftMenus, "GI");
        Menu sp = findMenuByName(leftMenus, "SP");
        Menu td = findMenuByName(leftMenus, "TD");
        Menu lf = findMenuByName(leftMenus, "LF");
        Menu ht = findMenuByName(leftMenus, "HT");
        Menu rp = findMenuByName(leftMenus, "RP");

        gm.addChildMenu(gi);
        gm.addChildMenu(sp);

        wm.addChildMenu(td);

        cm.addChildMenu(lf);

        rv.addChildMenu(ht);
        rv.addChildMenu(rp);

        pm.addChildMenu(gm);
        pm.addChildMenu(wm);
        pm.addChildMenu(cm);
        pm.addChildMenu(rv);

        entityManager.flush();
        entityManager.clear();
        leftMenus = menuRepository.findAllById(Lists.newArrayList(pmMenu.getId(),
                                                                  gmMenu.getId(),
                                                                  wmMenu.getId(),
                                                                  cmMenu.getId(),
                                                                  rvMenu.getId(),
                                                                  giMenu.getId(),
                                                                  spMenu.getId(),
                                                                  tdMenu.getId(),
                                                                  lfMenu.getId(),
                                                                  htMenu.getId(),
                                                                  rpMenu.getId()));
        leftMenus.forEach(v -> v.setMenuGroup(findLeftMenuGroup));

        entityManager.flush();
        entityManager.clear();


        MenuGroup finalTopMenuGroup = menuGroupRepository.findById(topMenuGroup.getId()).get();
        System.out.println(finalTopMenuGroup);

        MenuGroup finalLeftMenuGroup = menuGroupRepository.findById(leftMenuGroup.getId()).get();
        System.out.println(finalLeftMenuGroup);


        ModelMapper modelMapper = new ModelMapper();

        List<MenuRs> topMenuRsList = new ArrayList<>();
        finalTopMenuGroup.getMenus()
                         .stream()
                         .filter(Menu::isRootMenu)
                         .collect(Collectors.toList())
                         .forEach(v -> topMenuRsList.add(modelMapper.map(v, MenuRs.class)));

        List<MenuRs> leftMenuRsList = new ArrayList<>();
        finalLeftMenuGroup.getMenus()
                          .stream()
                          .filter(Menu::isRootMenu)
                          .collect(Collectors.toList())
                          .forEach(v -> leftMenuRsList.add(modelMapper.map(v, MenuRs.class)));


        List<MenuGroupRs> groups = Lists.newArrayList();
        groups.add(MenuGroupRs.builder().name(finalTopMenuGroup.getName()).menus(topMenuRsList).build());
        groups.add(MenuGroupRs.builder().name(finalLeftMenuGroup.getName()).menus(leftMenuRsList).build());


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json2 = objectMapper.writeValueAsString(groups);
            System.out.println(json2);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Menu findMenuByName(List<Menu> menus, String name) {
        return menus.stream().filter(x -> x.getName().equals(name)).findAny().get();
    }
}
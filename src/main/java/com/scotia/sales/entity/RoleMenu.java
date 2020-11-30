package com.scotia.sales.entity;

import javax.persistence.*;

/**
 * @author Felix
 */
@Entity
@Table(name = "t_role_menu")
public class RoleMenu {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RoleMenu{" +
                "id=" + id +
                ", menu=" + menu +
                ", role=" + role +
                '}';
    }
}

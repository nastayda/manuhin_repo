package ru.stqa.pft.gge.model;

import com.google.gson.annotations.Expose;

public class GeneratorData {

    @Expose
    private String razdel;

    @Expose
    private String razdelXpath;

    @Expose
    private String menu;

    @Expose
    private String menuXpath;

    @Expose
    private String vitrina;

    @Expose
    private String vitrinaXpath;


    /**
     * Геттеры
     */

    public String getRazdel() {
        return razdel;
    }

    public String getRazdXpath() {
        return razdelXpath;
    }

    public String getMenu(){
        return menu;
    }

    public String getMenuXpath(){
        return menuXpath;
    }

    public String getVitrina() {
        return vitrina;
    }

    public String getVitrinaXpath() {
        return vitrinaXpath;
    }


    /**
     * Сеттеры
     * @param razdList
     */
    public void setRazdName(String razdList) {
        razdel = razdList;
    }

    public void setRazdXpath (String xpathRazd) {
        razdelXpath = xpathRazd;
    }

    public void setMenu(String menuList) {
        menu = menuList;
    }

    public void setMenuXpath(String xpathMenu) {
        menuXpath = xpathMenu;
    }

    public void setVitrina (String vitrinaList) {
        vitrina = vitrinaList;
    }

    public void setVitrinaXpath (String xpathVitrina) {
        vitrinaXpath = xpathVitrina;
    }

    public GeneratorData withRazdelName(String razdel) {
        this.razdel = razdel;
        return this;
    }
    public GeneratorData withRazdelXpath(String razdelXpath) {
        this.razdelXpath = razdelXpath;
        return this;
    }
    public GeneratorData withMenuName(String menu) {
        this.menu = menu;
        return this;
    }
    public GeneratorData withMenuXpath(String menuXpath) {
        this.menuXpath = menuXpath;
        return this;
    }
    public GeneratorData withVitrinaName(String vitrina) {
        this.vitrina = vitrina;
        return this;
    }
    public GeneratorData withVitrinaXpath(String vitrinaXpath) {
        this.vitrinaXpath = vitrinaXpath;
        return this;
    }

    @Override
    public String toString() {
        return "GeneratorData{" +
                "razdel='" + razdel + '\'' +
                ", menu='" + menu + '\'' +
                ", vitrina='" + vitrina + '\'' +
                '}';
    }
}

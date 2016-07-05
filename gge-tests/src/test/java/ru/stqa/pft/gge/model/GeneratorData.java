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
    private String vitrinaID;

    @Expose
    private String vitrinaXpath;

    @Expose
    private String loginUser;

    @Expose
    private String baseUrl;

    @Expose
    private String cardUrl;

    @Expose
    private String listVitrinasUrl;


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

    public String getVitrinaID() {
        return vitrinaID;
    }

    public String getVitrinaXpath() {
        return vitrinaXpath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getCardUrl() {
        return cardUrl;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public String getListVitrinasUrl() {
        return listVitrinasUrl;
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

    public GeneratorData withVitrinaID(String vitrinaID) {
        this.vitrinaID = vitrinaID;
        return this;
    }

    public GeneratorData withVitrinaXpath(String vitrinaXpath) {
        this.vitrinaXpath = vitrinaXpath;
        return this;
    }

    public GeneratorData withBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public GeneratorData withCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
        return this;
    }

    public GeneratorData withLoginUser(String loginUser) {
        this.loginUser = loginUser;
        return this;
    }

    public GeneratorData withListVitrinasUrl(String listVitrinasUrl) {
        this.listVitrinasUrl = listVitrinasUrl;
        return this;
    }

    @Override
    public String toString() {
        return  razdel + " / " +
                menu + " / " +
                vitrina + " / " +
                loginUser + " / " +
                baseUrl;
    }
}

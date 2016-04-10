package ru.stqa.pft.mantis.model;

/**
 * Created by Юрий on 10.04.2016.
 */
public class MailMessage {

    public String to;
    public String text;

    public MailMessage(String to, String text){
        this.to = to;
        this.text = text;
    }
}

package ru.stqa.pft.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Юрий on 10.04.2016.
 */
public class MailHelper {

    private final ApplicationManager app;
    private final Wiser wiser;

    public MailHelper (ApplicationManager app){
        this.app = app;
        wiser = new Wiser();
    }

    public List<MailMessage> waitForMail(int count, long timeout) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + timeout){
            if(wiser.getMessages().size() >= count){
                return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
            }
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
        throw new Error("No mails :(");
    }

    private static MailMessage toModelMail(WiserMessage m)  {
        try {
            MimeMessage mime = m.getMimeMessage();
            return new MailMessage(mime.getAllRecipients()[0].toString(), (String) mime.getContent());
        }
        catch (MessagingException me){
            me.printStackTrace();
            return  null;
        }
        catch (IOException ioe){
            ioe.printStackTrace();
            return  null;
        }
    }
    public void start(){
        wiser.start();
    }

    public void stop(){
        wiser.stop();
    }


}

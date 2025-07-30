package com.groupeisi.ged.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailUtil {
    public static void envoyer(String destinataire, String sujet, String corps) throws MessagingException {
        final String expediteur = "elcheikh771@gmail.com";
        final String motDePasse = "pccm gynu etiy xxfd"; // Utiliser un mot de passe d'application

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(expediteur, motDePasse);
                    }
                });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(expediteur));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
        message.setSubject(sujet);
        message.setText(corps);

        Transport.send(message);
    }
}

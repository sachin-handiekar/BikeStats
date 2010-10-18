package com.appengine.dockstats;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SendEmail
 *  
 * @author Sachin Handiekar
 * @version 1.0
 */
@SuppressWarnings("serial")
public class SendEmail extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Properties  props   = new Properties();
        Session     session = Session.getDefaultInstance(props, null);
        String      name    = req.getParameter("name");
        String      email   = req.getParameter("email");
        String      subject = req.getParameter("subject");
        String      message = req.getParameter("message");
        PrintWriter out     = null;

        try {
            out = resp.getWriter();
        } catch (IOException e1) {

            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        out.println("Name - " + name);
        out.println("Email - " + email);
        out.println("Subject - " + subject);
        out.println("Message - " + message);

        try {
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(email, name));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("sach21@gmail.com", "Sachin Handiekar"));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            out.println("Message Sent!!! ");
        } catch (AddressException e) {

            // ...
        } catch (MessagingException e) {

            // ...
        } catch (UnsupportedEncodingException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

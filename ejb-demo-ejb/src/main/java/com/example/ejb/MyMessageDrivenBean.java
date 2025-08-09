package com.example.ejb;

import com.example.api.MySessionBeanLocal;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myQueue")
})
public class MyMessageDrivenBean implements MessageListener {

    @EJB
    private MySessionBeanLocal sessionBean;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                
                System.out.println("MDB received a message: " + text);
                
                // Forward the message to Session Bean Local for processing
                if (sessionBean != null) {
                    sessionBean.processMessage(text);
                    System.out.println("Message forwarded to Session Bean Local for processing");
                } else {
                    System.err.println("Session Bean Local is not injected!");
                }
                
            } else {
                System.out.println("MDB received a non-text message: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error in MDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

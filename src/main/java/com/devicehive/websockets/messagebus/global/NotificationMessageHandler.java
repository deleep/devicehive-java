package com.devicehive.websockets.messagebus.global;

import com.devicehive.configuration.Constants;
import com.devicehive.model.DeviceNotification;
import com.devicehive.websockets.messagebus.local.LocalMessageBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.io.IOException;

@JMSDestinationDefinition(
        name= Constants.JMS_NOTIFICATION_TOPIC,
        interfaceName = "javax.jms.Topic",
        destinationName = Constants.NOTIFICATION_TOPIC_DESTINATION_NAME
)
@MessageDriven(mappedName= Constants.JMS_NOTIFICATION_TOPIC)
public class NotificationMessageHandler implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationMessageHandler.class);

    @Inject
    private LocalMessageBus localMessageBus;

    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            DeviceNotification notification = (DeviceNotification)objectMessage.getObject();
            if (notification != null) {
                logger.debug("DeviceNotification received: " + notification);
                localMessageBus.submitNotification(notification);
            }
        } catch (JMSException e) {
            logger.error("[onMessage] Error processing notification. ", e);
        } catch (IOException e) {
            logger.error("[onMessage] Error processing notification. ", e);
        }
    }
}

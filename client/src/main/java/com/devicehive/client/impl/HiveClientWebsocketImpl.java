package com.devicehive.client.impl;


import com.devicehive.client.CommandsAPI;
import com.devicehive.client.NotificationsAPI;
import com.devicehive.client.impl.context.HivePrincipal;
import com.devicehive.client.impl.context.WebsocketAgent;
import com.devicehive.client.model.exceptions.HiveException;

/**
 * Specialization of {@link HiveClientRestImpl} that uses WebSocket transport for requests.
 */
public class HiveClientWebsocketImpl extends HiveClientRestImpl {

    private final WebsocketAgent websocketAgent;

    /**
     * Initializes the client with {@link WebsocketAgent} to use for requests.
     *
     * @param websocketAgent a WebsocketAgent to use for requests
     */
    public HiveClientWebsocketImpl(WebsocketAgent websocketAgent) {
        super(websocketAgent);
        this.websocketAgent = websocketAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String login, String password) throws HiveException {
        websocketAgent.authenticate(HivePrincipal.createUser(login, password));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void authenticate(String accessKey) throws HiveException {
        super.authenticate(accessKey);
        websocketAgent.authenticate(HivePrincipal.createAccessKey(accessKey));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandsAPI getCommandsAPI() {
        return new CommandsAPIWebsocketImpl(websocketAgent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotificationsAPI getNotificationsAPI() {
        return new NotificationsAPIWebsocketImpl(websocketAgent);
    }
}

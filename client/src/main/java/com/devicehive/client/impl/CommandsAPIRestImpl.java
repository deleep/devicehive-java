package com.devicehive.client.impl;


import com.devicehive.client.CommandsAPI;
import com.devicehive.client.HiveMessageHandler;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.model.DeviceCommand;
import com.devicehive.client.model.SubscriptionFilter;
import com.devicehive.client.model.exceptions.HiveClientException;
import com.devicehive.client.model.exceptions.HiveException;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.HttpMethod;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Implementation of {@link CommandsAPI} that uses REST transport.
 */
class CommandsAPIRestImpl implements CommandsAPI {
    private static Logger LOGGER = LoggerFactory.getLogger(CommandsAPIRestImpl.class);

    private static final String DEVICE_COMMANDS_COLLECTION_PATH = "/device/%s/command";
    private static final String DEVICE_COMMAND_RESOURCE_PATH = "/device/%s/command/%s";

    private final RestAgent restAgent;

    /**
     * Initializes the API with a {@link RestAgent} to use for requests.
     *
     * @param restAgent an instance of {@link RestAgent}
     */
    CommandsAPIRestImpl(RestAgent restAgent) {
        this.restAgent = restAgent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeviceCommand> queryCommands(String deviceGuid, Date start, Date end, String commandName,
                                             String status, String sortField, String sortOrder, Integer take,
                                             Integer skip, Integer gridInterval) throws HiveException {
        LOGGER.debug("DeviceCommand: query requested for device id {}, start timestamp {}, end timestamp {}, " +
            "commandName {}, status {}, sort field {}, sort order {}, take param {}, skip param {}, grid interval {}",
            deviceGuid, start, end, commandName, status, sortField, sortOrder, take, skip, gridInterval);

        String path = String.format(DEVICE_COMMANDS_COLLECTION_PATH, deviceGuid);

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("start", start);
        queryParams.put("end", end);
        queryParams.put("command", commandName);
        queryParams.put("status", status);
        queryParams.put("sortField", sortField);
        queryParams.put("sortOrder", sortOrder);
        queryParams.put("take", take);
        queryParams.put("skip", skip);
        queryParams.put("gridInterval", gridInterval);

        List<DeviceCommand> result = restAgent.execute(path, HttpMethod.GET, null, queryParams,
            new TypeToken<List<DeviceCommand>>() {}.getType(), COMMAND_LISTED);

        LOGGER.debug("DeviceCommand: query request processed successfully for device id {}, start timestamp {}, " +
            "end timestamp {},commandName {}, status {}, sort field {}, sort order {}, take param {}, " +
            "skip param {}, grid interval {}", deviceGuid, start, end, commandName, status, sortField, sortOrder,
            take, skip, gridInterval);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceCommand getCommand(String guid, long id) throws HiveException {
        LOGGER.debug("DeviceCommand: get requested for device id {} and command id {}", guid, id);

        String path = String.format(DEVICE_COMMAND_RESOURCE_PATH, guid, id);

        DeviceCommand result = restAgent.execute(path, HttpMethod.GET, null, DeviceCommand.class, COMMAND_TO_DEVICE);

        LOGGER.debug("DeviceCommand: get request processed successfully for device id {} and command id {}. Date {}, " +
            "userId {}, command {}, parameters {}, lifetime {}, flags {}, status {}, result {}", guid, id,
            result.getTimestamp(), result.getUserId(), result.getCommand(), result.getParameters(), result.getLifetime(),
            result.getFlags(), result.getStatus(), result.getResult());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeviceCommand insertCommand(String guid, DeviceCommand command,
                                       HiveMessageHandler<DeviceCommand> commandUpdatesHandler) throws HiveException {
        if (command == null) {
            throw new HiveClientException("Command cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceCommand: insert requested for device id {} and command: command {}, parameters {}, " +
            "lifetime {}, flags {}", guid, command.getCommand(), command.getParameters(), command.getLifetime(),
             command.getFlags());

        String path = String.format(DEVICE_COMMANDS_COLLECTION_PATH, guid);
        DeviceCommand result = restAgent.execute(path, HttpMethod.POST, null, null, command, DeviceCommand.class,
            COMMAND_FROM_CLIENT, COMMAND_TO_CLIENT);

        if (commandUpdatesHandler != null) {
            restAgent.subscribeForCommandUpdates(result.getId(), guid, commandUpdatesHandler);
        }

        LOGGER.debug("DeviceCommand: insert request processed successfully for device id {} and command: command {}, " +
            "parameters {}, lifetime {}, flags {}. Result command id {}, timestamp {}, userId {}", guid,
            command.getCommand(), command.getParameters(), command.getLifetime(), command.getFlags(), result.getId(),
            result.getTimestamp(), result.getUserId());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCommand(String deviceId, DeviceCommand command) throws HiveException {
        if (command == null) {
            throw new HiveClientException("Command cannot be null!", BAD_REQUEST.getStatusCode());
        }
        if (command.getId() == null) {
            throw new HiveClientException("Command id cannot be null!", BAD_REQUEST.getStatusCode());
        }

        LOGGER.debug("DeviceCommand: update requested for device id {} and command: id {},  flags {}, status {}, result {}",
            deviceId, command.getId(), command.getFlags(), command.getStatus(), command.getResult());

        String path = String.format(DEVICE_COMMAND_RESOURCE_PATH, deviceId, command.getId());
        restAgent.execute(path, HttpMethod.PUT, null, command, REST_COMMAND_UPDATE_FROM_DEVICE);

        LOGGER.debug("DeviceCommand: update request processed successfully for device id {} and command: id {}, flags {}, " +
            "status {}, result {}", deviceId, command.getId(), command.getFlags(), command.getStatus(), command.getResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String subscribeForCommands(SubscriptionFilter filter,
                                       HiveMessageHandler<DeviceCommand> commandMessageHandler) throws HiveException {
        LOGGER.debug("Device: command/subscribe requested for filter {}", filter);

        return restAgent.subscribeForCommands(filter, commandMessageHandler);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeFromCommands(String subscriptionId) throws HiveException {
        LOGGER.debug("Device: command/unsubscribe requested for subscription {}", subscriptionId);

        restAgent.unsubscribeFromCommands(subscriptionId);
    }
}

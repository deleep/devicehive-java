package com.devicehive.client.model;

import com.devicehive.client.impl.json.strategies.JsonPolicyDef;
import com.google.common.base.Optional;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Represents an OAuth permission grant. See <a href="http://www.devicehive.com/restful#Reference/OAuthGrant">OAuthGrant</a>
 * for more details
 */
public class OAuthGrant implements HiveEntity {

    private static final long serialVersionUID = 6725932065321755993L;
    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_SUBMITTED_TOKEN, OAUTH_GRANT_SUBMITTED_CODE})
    private Long id;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_SUBMITTED_TOKEN, OAUTH_GRANT_SUBMITTED_CODE})
    private Optional<Date> timestamp;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_SUBMITTED_CODE})
    private Optional<String> authCode;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<OAuthClient> client;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_SUBMITTED_TOKEN})
    private Optional<AccessKey> accessKey;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<OAuthType> type;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<AccessType> accessType;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<String> redirectUri;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<String> scope;

    @JsonPolicyDef({OAUTH_GRANT_LISTED, OAUTH_GRANT_PUBLISHED})
    private Optional<String> networkIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return ObjectUtils.cloneIfPossible(timestamp.get());
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = Optional.fromNullable(ObjectUtils.cloneIfPossible(timestamp));
    }

    public String getAuthCode() {
        return NullableWrapper.value(authCode);
    }

    public void setAuthCode(String authCode) {
        this.authCode =Optional.fromNullable(authCode);
    }

    public OAuthClient getClient() {
        return NullableWrapper.value(client);
    }

    public void setClient(OAuthClient client) {
        this.client = Optional.fromNullable(client);
    }

    public AccessKey getAccessKey() {
        return NullableWrapper.value(accessKey);
    }

    public void setAccessKey(AccessKey accessKey) {
        this.accessKey = Optional.fromNullable(accessKey);
    }

    public OAuthType getType() {
        return NullableWrapper.value(type);
    }

    public void setType(OAuthType oauthType) {
        this.type = Optional.fromNullable(oauthType);
    }

    public AccessType getAccessType() {
        return NullableWrapper.value(accessType);
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = Optional.fromNullable(accessType);
    }

    public String getRedirectUri() {
        return NullableWrapper.value(redirectUri);
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = Optional.fromNullable(redirectUri);
    }

    public String getScope() {
        return NullableWrapper.value(scope);
    }

    public void setScope(String scope) {
        this.scope = Optional.fromNullable(scope);
    }

    public String getNetworkIds() {
        return NullableWrapper.value(networkIds);
    }

    public void setNetworkIds(String networkIds) {
        this.networkIds = Optional.fromNullable(networkIds);
    }

    public Set<Long> getNetworkIdsAsSet() {
        if (networkIds == null) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonElement elem = parser.parse(networkIds.get());
        if (elem instanceof JsonNull) {
            return null;
        }
        if (elem instanceof JsonArray) {
            JsonArray json = (JsonArray) elem;
            Set<Long> result = new HashSet<>(json.size());
            for (JsonElement current : json) {
                result.add(current.getAsLong());
            }
            return result;
        }
        throw new IllegalArgumentException("JSON array expected!");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OAuthGrant{");
        sb.append("id=").append(id);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", client=").append(client);
        sb.append(", accessKey=").append(accessKey);
        sb.append(", type=").append(type);
        sb.append(", accessType=").append(accessType);
        sb.append(", redirectUri=").append(redirectUri);
        sb.append(", scope=").append(scope);
        sb.append(", networkIds=").append(networkIds);
        sb.append('}');
        return sb.toString();
    }
}

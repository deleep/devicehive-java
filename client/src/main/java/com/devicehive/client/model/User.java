package com.devicehive.client.model;

import com.devicehive.client.impl.json.strategies.JsonPolicyDef;
import com.google.common.base.Optional;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;
import java.util.Set;

import static com.devicehive.client.impl.json.strategies.JsonPolicyDef.Policy.*;

/**
 * Represents a user to this API. See <a href="http://www.devicehive.com/restful#Reference/User">User</a>
 */
public class User implements HiveEntity {

    private static final long serialVersionUID = -8980491502416082011L;
    @JsonPolicyDef({COMMAND_TO_CLIENT, USER_PUBLISHED, COMMAND_TO_DEVICE, USERS_LISTED, USER_SUBMITTED})
    private Long id;

    @JsonPolicyDef({USER_PUBLISHED, USERS_LISTED, USER_UPDATE})
    private Optional<String> login;

    @JsonPolicyDef({USER_UPDATE})
    private Optional<String> password;

    @JsonPolicyDef({USER_PUBLISHED, USERS_LISTED, USER_UPDATE})
    private Optional<UserRole> role;

    @JsonPolicyDef({USER_PUBLISHED, USERS_LISTED, USER_UPDATE})
    private Optional<UserStatus> status;

    @JsonPolicyDef({USER_PUBLISHED, USERS_LISTED, USER_SUBMITTED})
    private Date lastLogin;

    @JsonPolicyDef({USER_PUBLISHED})
    private Set<UserNetwork> networks;

    public User() {
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return NullableWrapper.value(login);
    }

    public void setLogin(String login) {
        this.login = Optional.fromNullable(login);
    }

    public void removeLogin() {
        this.login = null;
    }

    public String getPassword() {
        return NullableWrapper.value(password);
    }

    public void setPassword(String password) {
        this.password = Optional.fromNullable(password);
    }

    public void removePassword() {
        this.password = null;
    }

    public UserRole getRole() {
        return NullableWrapper.value(role);
    }

    public void setRole(UserRole role) {
        this.role = Optional.fromNullable(role);
    }

    public void removeRole() {
        this.role = null;
    }

    public UserStatus getStatus() {
        return NullableWrapper.value(status);
    }

    public void setStatus(UserStatus status) {
        this.status = Optional.fromNullable(status);
    }

    public void removeStatus() {
        this.status = null;
    }

    public Date getLastLogin() {
        return ObjectUtils.cloneIfPossible(lastLogin);
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = ObjectUtils.cloneIfPossible(lastLogin);
    }

    public Set<UserNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<UserNetwork> networks) {
        this.networks = networks;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login=").append(login);
        sb.append(", role=").append(role);
        sb.append(", status=").append(status);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", networks=").append(networks);
        sb.append('}');
        return sb.toString();
    }
}

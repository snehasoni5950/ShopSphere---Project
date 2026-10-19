package com.shopsphere.network.protocol;

import java.io.Serializable;

/**
 * RTU Syllabus: java.net Networking & Serialization.
 * NetworkCommand defines commands and arguments sent over Socket.
 */
public class NetworkCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String GET_PRODUCT = "GET_PRODUCT";
    public static final String GET_STOCK = "GET_STOCK";
    public static final String SEARCH_PRODUCT = "SEARCH_PRODUCT";
    public static final String UPDATE_STOCK = "UPDATE_STOCK";
    public static final String PING = "PING";
    public static final String BYE = "BYE";

    private String action;
    private String argument;
    private Integer extraInt;

    public NetworkCommand() {}

    public NetworkCommand(String action, String argument) {
        this.action = action;
        this.argument = argument;
    }

    public NetworkCommand(String action, String argument, Integer extraInt) {
        this.action = action;
        this.argument = argument;
        this.extraInt = extraInt;
    }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getArgument() { return argument; }
    public void setArgument(String argument) { this.argument = argument; }

    public Integer getExtraInt() { return extraInt; }
    public void setExtraInt(Integer extraInt) { this.extraInt = extraInt; }

    @Override
    public String toString() {
        return "NetworkCommand{" + "action='" + action + '\'' + ", argument='" + argument + '\'' + '}';
    }
}

package com.app;

public class SessionExpired extends Exception {
    public SessionExpired() {
        super("Session expired");
    }
}

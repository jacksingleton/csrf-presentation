package com.thoughtworks.appsec.csrfDemo.controllers;

public class UserState {
    private final boolean isLoggedIn;

    public UserState(final boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof UserState)) return false;
        final UserState other = (UserState)o;
        if (!other.canEqual((java.lang.Object)this)) return false;
        if (this.isLoggedIn() != other.isLoggedIn()) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof UserState;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isLoggedIn() ? 79 : 97);
        return result;
    }

    public java.lang.String toString() {
        return "UserState(isLoggedIn=" + this.isLoggedIn() + ")";
    }
}
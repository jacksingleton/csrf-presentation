// Generated by delombok at Wed Apr 01 16:27:31 PDT 2015
package com.thoughtworks.appsec.csrfDemo.controllers;

public class UserState {
    private final boolean isLoggedIn;

    @java.beans.ConstructorProperties({"isLoggedIn"})
    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    public UserState(final boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof UserState)) return false;
        final UserState other = (UserState)o;
        if (!other.canEqual((java.lang.Object)this)) return false;
        if (this.isLoggedIn() != other.isLoggedIn()) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof UserState;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isLoggedIn() ? 79 : 97);
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    @javax.annotation.Generated("lombok")
    public java.lang.String toString() {
        return "UserState(isLoggedIn=" + this.isLoggedIn() + ")";
    }
}
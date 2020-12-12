package com.pinzen.journey.entities;

public class Invocation extends Entity {

    protected Entity owner;

    public Invocation(Entity owner) {
        this.owner = owner;
    }

    public boolean isSameTeam(Entity e) {
        if (this == e || this.owner == e)
            return true;
        if (e instanceof Invocation && ((Invocation) e).owner == this.owner)
            return true;
        return false;
    }
}

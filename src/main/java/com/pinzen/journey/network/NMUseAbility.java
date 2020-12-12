package com.pinzen.journey.network;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.pinzen.journey.entities.Entity;
import com.pinzen.journey.entities.TotemDamage;
import com.pinzen.journey.entities.TotemHeal;
import com.pinzen.journey.logic.GameManager;
import com.pinzen.journey.logic.GameMap;
import com.pinzen.journey.logic.User;

public class NMUseAbility extends NetworkMessage {

    public static final int ID = 8;

    public NMUseAbility() {
        super(ID);
    }

    public NMUseAbility(ByteBuffer buffer) {
        super(ID, buffer);
    }

    @Override
    protected void prepareEncode() {

    }

    @Override
    public void decode(UUID senderUuid, GameManager gameManager) {
        User sender = gameManager.getUser(senderUuid);
        int abilityId = this.readInt();
        int mouseX = this.readInt();
        int mouseY = this.readInt();

        GameMap map = gameManager.getMap(sender.currentMapUuid);
        if (abilityId == 1) {
            Entity e = new TotemDamage(sender.player, 3, 30, 1000);
            e.setPosition(mouseX, mouseY);
            map.addEntity(e);
        } else if (abilityId == 2) {
            Entity e = new TotemHeal(sender.player, 2, 30, 1000);
            e.setPosition(mouseX, mouseY);
            map.addEntity(e);
        }
    }

}

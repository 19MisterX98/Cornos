package me.constantindev.ccl.command;

import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.Notification;
import me.constantindev.ccl.etc.base.Command;
import me.constantindev.ccl.etc.helper.ClientHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class RemovePlayer extends Command {
    public RemovePlayer() {
        super("RemovePlayer", "Removes the player you are looking at (CLIENT SIDE!)", new String[]{"rmplayer", "removeplayer"});
    }

    @Override
    public void onExecute(String[] args) {
        HitResult hr = Cornos.minecraft.crosshairTarget;
        if (!(hr instanceof EntityHitResult)) {
            ClientHelper.sendChat("Not sure if you are looking at an entity");
            return;
        }
        EntityHitResult ehr = (EntityHitResult) hr;
        Entity e = ehr.getEntity();
        if (!(e instanceof PlayerEntity)) {
            ClientHelper.sendChat("Not sure if you are looking at a player");
            return;
        }
        e.remove();
        Notification.create("WHERE DID HE GO??!?!?", new String[]{"HOLY SHIT", e.getEntityName().toUpperCase() + " IS GONE!!!"}, 3000);
        super.onExecute(args);
    }
}

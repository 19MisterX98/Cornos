package me.constantindev.ccl.module.ext;

import com.mojang.blaze3d.platform.GlStateManager;
import me.constantindev.ccl.Cornos;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;
import me.constantindev.ccl.etc.ms.MType;
import me.constantindev.ccl.gui.TabGUI;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class NameTags extends Module {

    Toggleable health = new Toggleable("Health", true);
    Toggleable items = new Toggleable("Items", true);
    Toggleable renderSelf = new Toggleable("RenderSelf", false);

    public NameTags() {
        super("NameTags", "Shows bigger better nametags that give you more info about the player", MType.RENDER);
        this.mconf.add(health);
        this.mconf.add(items);
        this.mconf.add(renderSelf);
    }

    public void renderCustomLabel(Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderDispatcher dispatcher) {
        double d = dispatcher.getSquaredDistanceToCamera(entity);
        if (d > 4096) {
            return;
        }
        String tag = entity.getEntityName();
        float f = entity.getHeight() + 0.5F;
        assert Cornos.minecraft.player != null;
        if (entity.getUuid() == Cornos.minecraft.player.getUuid() && !renderSelf.isEnabled()) {
            return;
        }
        if (entity instanceof PlayerEntity) {
            matrices.push();
            float scale = 3F;
            scale /= 50.0f;
            scale *= (float) 0.55;
            assert Cornos.minecraft.player != null;
            if (Cornos.minecraft.player.distanceTo(entity) > 10)
                scale *= Cornos.minecraft.player.distanceTo(entity) / 10;
            matrices.translate(0.0D, f + (scale * 6), 0.0D);
            matrices.multiply(dispatcher.getRotation());
            matrices.scale(-scale, -scale, scale);
            TextRenderer textRenderer = dispatcher.getTextRenderer();
            int health = (int) ((PlayerEntity) entity).getHealth();
            double healthPercentage = (((PlayerEntity) entity).getHealth() / ((PlayerEntity) entity).getMaxHealth());
            double red = Math.abs(healthPercentage - 1.0);
            Color c = new Color((int) (255.0 * red), (int) (255.0 * healthPercentage), 0);
            if (health <= ((PlayerEntity) entity).getMaxHealth() * 0.25) { // If health is below 25%
                tag += "§4";
            } else if (health <= ((PlayerEntity) entity).getMaxHealth() * 0.5) { // If health is below 50%
                tag += "§6";
            } else if (health <= ((PlayerEntity) entity).getMaxHealth() * 0.75) { // If health is below 75%
                tag += "§e";
            } else if (health <= ((PlayerEntity) entity).getMaxHealth()) { // If health is below 100% (aka. everything else)
                tag += "§2";
            }
            tag = this.health.isEnabled() ? tag + " " + health : tag;
            int width = textRenderer.getWidth(tag) / 2;
            GlStateManager.enablePolygonOffset();
            GlStateManager.polygonOffset(1f, -1500000);
            GL11.glDepthFunc(GL11.GL_ALWAYS);
            float size = .5f;
            Matrix4f matrix4f = matrices.peek().getModel();
            TabGUI.drawGuiRect(-width - 4, textRenderer.fontHeight + 2, width + 4, 1, new Color(20, 20, 20, 220).getRGB(), matrix4f);
            TabGUI.drawGuiRect((float) ((-width - 4) * healthPercentage), textRenderer.fontHeight + 2, (float) ((width + 4) * healthPercentage), textRenderer.fontHeight + 2 + size, c.getRGB(), matrix4f);
            Cornos.minecraft.textRenderer.draw(matrices, tag, -Cornos.minecraft.textRenderer.getWidth(tag) / 2F, f, Color.WHITE.getRGB());
            GlStateManager.disablePolygonOffset();
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GlStateManager.polygonOffset(1f, 1500000);
            matrices.pop();
        }
    }
}

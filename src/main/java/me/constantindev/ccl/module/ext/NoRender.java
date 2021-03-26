/*
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
# Project: Cornos
# File: NoRender
# Created by constantin at 19:04, Mär 26 2021
PLEASE READ THE COPYRIGHT NOTICE IN THE PROJECT ROOT, IF EXISTENT
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
package me.constantindev.ccl.module.ext;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.constantindev.ccl.etc.base.Module;
import me.constantindev.ccl.etc.config.Toggleable;

public class NoRender extends Module {
    public static Toggleable fire = new Toggleable("fire", true);
    public static Toggleable pumpkin = new Toggleable("pumpkin", true);
    public static Toggleable sign = new Toggleable("signs", true);
    public static Toggleable armorstand = new Toggleable("armorstands", true);
    public NoRender() {
        super("NoRender", "Does not render some things");
        this.mconf.add(fire);
        this.mconf.add(pumpkin);
        this.mconf.add(sign);
        this.mconf.add(armorstand);
    }
}

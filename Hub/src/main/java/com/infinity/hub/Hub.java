package com.infinity.hub;

import com.infinity.hub.cosmetic.companions.CompanionModule;
import com.infinity.hub.cosmetic.gadget.GadgetManager;
import com.infinity.hub.protection.PlayerModule;
import me.adamtanana.core.Core;

public class Hub extends Core{

    @Override
    protected void enable() {
        enableModules(new PlayerScoreboard(), new PlayerModule(), new GadgetManager(), new CompanionModule());

        getModule(GadgetManager.class).setEnabled(true);
    }

}

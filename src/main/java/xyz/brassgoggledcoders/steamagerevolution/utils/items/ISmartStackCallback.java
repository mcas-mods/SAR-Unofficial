package xyz.brassgoggledcoders.steamagerevolution.utils.items;

import xyz.brassgoggledcoders.steamagerevolution.network.PacketItemUpdate;

public interface ISmartStackCallback {
    void onContentsChanged(int slot);

    void updateStack(PacketItemUpdate message);
}

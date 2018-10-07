package cc.hyperium.cosmetics;

import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.PreCopyPlayerModelAnglesEvent;
import cc.hyperium.event.Priority;
import cc.hyperium.mixinsimp.renderer.model.IMixinModelPlayer;
import cc.hyperium.purchases.EnumPurchaseType;
import cc.hyperium.purchases.HyperiumPurchase;
import cc.hyperium.purchases.PurchaseApi;

public class ButtCosmetic extends AbstractCosmetic {
    public ButtCosmetic() {
        super(false, EnumPurchaseType.BUTT, true);
    }

    @InvokeEvent(priority = Priority.HIGH)
    public void preCopy(PreCopyPlayerModelAnglesEvent event) {

        boolean b = event.getModel() instanceof IMixinModelPlayer;
        if (!b)
            return;
        HyperiumPurchase packageIfReady = PurchaseApi.getInstance().getPackageIfReady(event.getEntity().getUniqueID());
        if (packageIfReady == null) {
            ((IMixinModelPlayer) event.getModel()).getButt().showModel = false;
            return;
        }
        boolean disabled = packageIfReady.getCachedSettings().isButtDisabled();
        boolean purchasedBy = isPurchasedBy(event.getEntity().getUniqueID());
        ((IMixinModelPlayer) event.getModel()).getButt().showModel = purchasedBy && !disabled;
        ((IMixinModelPlayer) event.getModel()).getButt().offsetY = event.getEntity().isSneaking() ? -.45F : -.4F;
        ((IMixinModelPlayer) event.getModel()).getButt().offsetZ = event.getEntity().isSneaking() ? .3F : 0F;

    }
}

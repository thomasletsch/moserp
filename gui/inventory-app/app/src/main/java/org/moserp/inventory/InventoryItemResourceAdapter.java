package org.moserp.inventory;

import org.moserp.common.databinding.BindingAwareViewHolder;
import org.moserp.common.cache.CacheContainer;
import org.moserp.common.cache.ResourceCache;
import org.moserp.common.databinding.RecycleViewResourceAdapter;
import org.moserp.environment.Facility;

import java.util.List;

public class InventoryItemResourceAdapter extends RecycleViewResourceAdapter<InventoryItem> {

    ResourceCache<Facility> facilityCache;

    public InventoryItemResourceAdapter(List<InventoryItem> inventoryItems, int layoutId, CacheContainer cacheContainer) {
        super(inventoryItems, layoutId);
        facilityCache = cacheContainer.get(Facility.class);
    }

    @Override
    public void onBindViewHolder(BindingAwareViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final InventoryItem resource = getResources().get(position);
        holder.getBinding().setVariable(org.moserp.inventory.BR.facility, facilityCache.get(resource.getFacility()));
        holder.getBinding().executePendingBindings();
    }
}

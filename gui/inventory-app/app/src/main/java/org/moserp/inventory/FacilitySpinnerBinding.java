package org.moserp.inventory;

import android.widget.Spinner;

import org.moserp.common.ToolbarActivity;
import org.moserp.common.cache.ResourceCache;
import org.moserp.common.databinding.SpinnerBinding;
import org.moserp.common.databinding.SpinnerValueBinding;
import org.moserp.environment.Facility;

public class FacilitySpinnerBinding extends SpinnerBinding<Facility> {

    public FacilitySpinnerBinding(Spinner spinner, SpinnerValueBinding<Facility> valueBinding, ResourceCache<Facility> resourceCache) {
        super(spinner, R.layout.facility_item, valueBinding, resourceCache);
    }

    public void bind(ToolbarActivity activity, String facilityUri) {
        bindSpinner(resourceCache.getAll());
        retrieveSelectedElementFromUri(activity, facilityUri, Facility.class);
    }

}

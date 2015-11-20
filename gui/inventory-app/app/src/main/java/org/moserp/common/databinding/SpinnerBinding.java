package org.moserp.common.databinding;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.moserp.common.ToolbarActivity;
import org.moserp.common.cache.ResourceCache;
import org.moserp.common.domain.IdentifiableEntity;

import java.util.List;

public class SpinnerBinding<RESOURCE extends IdentifiableEntity> {

    private Spinner spinner;
    private int layoutId;
    private SpinnerValueBinding<RESOURCE> valueBinding;
    protected ResourceCache<RESOURCE> resourceCache;

    public SpinnerBinding(Spinner spinner, int layoutId, final SpinnerValueBinding<RESOURCE> valueBinding, ResourceCache<RESOURCE> resourceCache) {
        this.spinner = spinner;
        this.layoutId = layoutId;
        this.valueBinding = valueBinding;
        this.resourceCache = resourceCache;
    }

    public void bindSpinner(List<RESOURCE> entities) {
        final SpinnerResourceAdapter<RESOURCE> adapter = new SpinnerResourceAdapter<>(entities, layoutId);
        spinner.setAdapter(adapter);
        updateSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("IncomingDelivery", "Item selected: " + adapter.getItem(position).toString());
                valueBinding.setValue(adapter.getResource(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("IncomingDelivery", "Item selected: NONE ");
                valueBinding.setValue(null);
            }
        });
    }

    public void retrieveSelectedElementFromUri(ToolbarActivity activity, String uri, Class<RESOURCE> resourceClass) {
        Log.d("SpinnerBinding", "load from uri: " + uri);
        if (uri == null) {
            return;
        }
        RESOURCE resource = resourceCache.get(uri);
        valueBinding.setValue(resource);
        updateSpinner();
    }



    @SuppressWarnings("unchecked")
    public void updateSpinner() {
        SpinnerResourceAdapter adapter = (SpinnerResourceAdapter) spinner.getAdapter();
        RESOURCE selectedEntity = valueBinding.getValue();
        if (selectedEntity != null && adapter != null) {
            Log.d("IncomingDelivery", "set " + spinner.getContentDescription() + " to: " + selectedEntity);
            spinner.setSelection(adapter.getPosition(selectedEntity));
        }
    }

}

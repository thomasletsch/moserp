package org.moserp.common.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.moserp.common.domain.IdentifiableEntity;
import org.moserp.inventory.BR;

import java.util.List;

public class SpinnerResourceAdapter<RESOURCE extends IdentifiableEntity> extends BaseAdapter {

    private List<RESOURCE> resources;
    private int layoutId;

    public SpinnerResourceAdapter(List<RESOURCE> resources, int layoutId) {
        this.resources = resources;
        this.layoutId = layoutId;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return resources.size();
    }

    public int getPosition(RESOURCE resource) {
        return resources.indexOf(resource);
    }

    @Override
    public Object getItem(int position) {
        return resources.get(position);
    }

    public RESOURCE getResource(int position) {
        return resources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(resources.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(layoutId, parent, false);
        } else {
            view = convertView;
        }
        final ViewDataBinding binding =
                DataBindingUtil.bind(view);
        binding.setVariable(BR.item, getResource(position));
        binding.executePendingBindings();
        return view;
    }
}

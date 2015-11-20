package org.moserp.common.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.moserp.common.domain.IdentifiableEntity;
import org.moserp.inventory.BR;

import java.util.List;

public class RecycleViewResourceAdapter<RESOURCE extends IdentifiableEntity> extends RecyclerView.Adapter<BindingAwareViewHolder> {

    private List<RESOURCE> resources;

    private OnItemClickListener<RESOURCE> onItemClickListener;
    private int layoutId;


    public RecycleViewResourceAdapter(List<RESOURCE> resources, int layoutId) {
        this.resources = resources;
        this.layoutId = layoutId;
        setHasStableIds(true);
    }

    public void setResources(List<RESOURCE> resources) {
        this.resources = resources;
        notifyDataSetChanged();
    }

    @Override
    public BindingAwareViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final ViewDataBinding binding =
                DataBindingUtil.inflate(layoutInflater, layoutId, parent, false);
        return new BindingAwareViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingAwareViewHolder holder, final int position) {
        final RESOURCE resource = resources.get(position);
        holder.getBinding().setVariable(BR.item, resource);
        holder.getBinding().setVariable(BR.clicklistener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, resource);
                }
            }
        });
        holder.getBinding().executePendingBindings();
    }

    public List<RESOURCE> getResources() {
        return resources;
    }

    @Override
    public long getItemId(int position) {
        final RESOURCE resource = resources.get(position);
        if(resource.getId() != null) {
            return Long.parseLong(resource.getId());
        } else {
            return position;
        }
    }

    @Override
    public int getItemCount() {
        return resources.size();
    }

    public void setOnItemClickListener(OnItemClickListener<RESOURCE> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
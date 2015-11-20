package org.moserp.common.databinding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class BindingAwareViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public BindingAwareViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}

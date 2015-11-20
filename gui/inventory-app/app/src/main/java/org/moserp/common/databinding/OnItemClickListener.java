package org.moserp.common.databinding;

public interface OnItemClickListener<ENTITY> {
    void onItemClick(final int position, final ENTITY entity);
}

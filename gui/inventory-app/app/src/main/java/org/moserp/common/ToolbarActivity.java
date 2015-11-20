package org.moserp.common;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.moserp.common.databinding.DividerItemDecoration;
import org.moserp.inventory.R;

public class ToolbarActivity extends AppCompatActivity {

    public ToolbarActivity() {
    }

    protected void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.inflateMenu(R.menu.main_action_bar);
        }
    }

    protected void initRecyclerView(RecyclerView view) {
        view.setHasFixedSize(true);
        view.addItemDecoration(new DividerItemDecoration(getApplicationContext(), null));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        view.setLayoutManager(mLayoutManager);
    }

}

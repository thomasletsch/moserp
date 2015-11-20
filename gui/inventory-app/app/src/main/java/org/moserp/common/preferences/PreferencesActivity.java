package org.moserp.common.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceScreen;
import org.moserp.inventory.R;

@EActivity
@PreferenceScreen(R.xml.pref_general)
public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getPreferenceManager().setSharedPreferencesName(BackendPreferences.class.getSimpleName());
    }


}

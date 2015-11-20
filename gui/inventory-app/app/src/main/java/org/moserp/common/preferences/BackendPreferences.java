package org.moserp.common.preferences;

import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.SharedPref;
import org.moserp.inventory.R;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface BackendPreferences {

    @DefaultRes(R.string.pref_user_name_default)
    String userName();

    @DefaultRes(R.string.pref_password_default)
    String password();

    @DefaultRes(R.string.pref_backend_url_default)
    String backendUrl();

}
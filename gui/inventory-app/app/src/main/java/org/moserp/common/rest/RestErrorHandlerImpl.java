package org.moserp.common.rest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.rest.RestErrorHandler;
import org.moserp.inventory.R;
import org.springframework.core.NestedRuntimeException;

@EBean
public class RestErrorHandlerImpl implements RestErrorHandler {

    @RootContext
    Context context;

    @Override
    @UiThread
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        Log.d("RestErrorHandler", "Error! " + e);
        Toast.makeText(context, R.string.toast_backend_error, Toast.LENGTH_SHORT).show();
    }
}
package ru.olegry.simpleexchanger.data.resources;

import android.content.Context;
import android.support.annotation.StringRes;

import ru.olegry.simpleexchanger.App;

public class ResourceManager {

    private Context appContext;

    public ResourceManager(Context context) {
        appContext = context instanceof App ? context : context.getApplicationContext();
    }

    public String getString(@StringRes int resourceId) {
        return appContext.getString(resourceId);
    }
}

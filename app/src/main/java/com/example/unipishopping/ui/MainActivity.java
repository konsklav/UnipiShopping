package com.example.unipishopping.ui;

import android.util.Log;

import com.example.unipishopping.databinding.ActivityMainBinding;
import com.example.unipishopping.domain.User;
import com.example.unipishopping.ui.constants.IntentExtras;

public class MainActivity extends AppActivityBase<ActivityMainBinding> {
    @Override
    protected void onAfterCreate() {

        User user = getIntent().getParcelableExtra(IntentExtras.USER_PARCELABLE);

        Log.i("MAIN ACTIVITY", "Got user '" + user.getUsername() + "'!");
    }
}
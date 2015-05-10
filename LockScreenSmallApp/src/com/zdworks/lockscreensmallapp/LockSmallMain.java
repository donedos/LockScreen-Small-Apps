package com.zdworks.lockscreensmallapp;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.sony.smallapp.SmallAppWindow;
import com.sony.smallapp.SmallApplication;

public class LockSmallMain extends SmallApplication implements OnClickListener{

	ComponentName mComponentName;
	DevicePolicyManager mDevicePolicyManager;
	
    @Override
    public void onCreate() {
        super.onCreate();

        /* Set the layout of the application */
        setContentView(R.layout.lock_small_main);
        
		mDevicePolicyManager = (DevicePolicyManager)getSystemService(  
				Context.DEVICE_POLICY_SERVICE); 
        mComponentName = new ComponentName(this, AdminReceiver.class);
        
        final ImageView slock = (ImageView) findViewById(R.id.iLock);
        
        slock.setOnClickListener(this);
        

        setMinimizedView(R.layout.lock_main_minimized);

        setTitle(R.string.small_app_name);

        SmallAppWindow.Attributes attr = getWindow().getAttributes();
        attr.flags = (0x75FFFFFD & attr.flags);
        attr.width = 150;
        attr.height = 200;
        getWindow().setAttributes(attr);
        AppRater.app_launched(this);

    }


	@Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iLock:
			boolean isAdmin = mDevicePolicyManager.isAdminActive(mComponentName);  
			if (isAdmin) {  
				mDevicePolicyManager.lockNow();  
				
				//minimizes the small app window when screen is off
		        getWindow().setWindowState(SmallAppWindow.WindowState.MINIMIZED);
				
			}else{
				Toast.makeText(getApplicationContext(), "Please activate in Settings/Security/Device administrator.", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
}

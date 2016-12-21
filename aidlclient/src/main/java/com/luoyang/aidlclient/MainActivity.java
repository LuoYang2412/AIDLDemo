package com.luoyang.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.luoyang.aidlclient.databinding.ActivityMainBinding;
import com.luoyang.aidldemo.IMyAidlInterface;

public class MainActivity extends Activity {

    private IMyAidlInterface iMyAidlInterface;
    private ActivityMainBinding binding;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onClick(View view) throws RemoteException {
        switch (view.getId()) {
            case R.id.textView2:
                if (binding.editText.getText().length() == 0) binding.editText.setText("0");
                if (binding.editText2.getText().length() == 0) binding.editText2.setText("0");
                int i = Integer.parseInt((binding.editText.getText().toString()));
                int i1 = Integer.parseInt((binding.editText2.getText().toString()));
                if (iMyAidlInterface == null) {
                    Toast.makeText(this, "服务未启动", Toast.LENGTH_SHORT).show();
                    return;
                }
                int i2 = iMyAidlInterface.add(i, i1);
                binding.textView3.setText("" + i2);
                break;
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.luoyang.aidldemo", "com.luoyang.aidldemo.IRemoteService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
}

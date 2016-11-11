package com.luoyang.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class IRemoteService extends Service {
    public IRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int mum1, int mum2) throws RemoteException {
            return mum1 + mum2;
        }
    };
}

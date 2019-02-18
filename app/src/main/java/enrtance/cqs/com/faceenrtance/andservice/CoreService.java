package enrtance.cqs.com.faceenrtance.andservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.filter.HttpCacheFilter;

import com.yanzhenjie.andserver.website.AssetsWebsite;

import java.util.concurrent.TimeUnit;

import enrtance.cqs.com.faceenrtance.andservice.handler.CleanAllData;
import enrtance.cqs.com.faceenrtance.andservice.handler.DelectorFace;
import enrtance.cqs.com.faceenrtance.andservice.handler.GetAllRecord;
import enrtance.cqs.com.faceenrtance.andservice.handler.GetImageById;
import enrtance.cqs.com.faceenrtance.andservice.handler.GetRecordById;
import enrtance.cqs.com.faceenrtance.andservice.handler.GetRecordByTime;
import enrtance.cqs.com.faceenrtance.andservice.handler.uploadFace;

public class CoreService extends Service{
    /**
     * AndServer.
     */
    private Server mServer;

    @Override
    public void onCreate() {

        mServer = AndServer.serverBuilder()
                .inetAddress(NetUtils.getLocalIPAddress()) // Bind IP address.
                .port(8080)
                .timeout(10, TimeUnit.SECONDS)
                .website(new AssetsWebsite(getAssets(), "web"))
                .registerHandler("/getAllRecord", new GetAllRecord())
                .registerHandler("/delectFace", new DelectorFace())
                .registerHandler("/uploadFace", new uploadFace())
                .registerHandler("/clean", new CleanAllData())
                .registerHandler("/getRecordByTime",new GetRecordByTime())
                .registerHandler("/getRecordById",new GetRecordById())
                .registerHandler("/getImageById",new GetImageById())
                .filter(new HttpCacheFilter())
                .listener(mListener)
                .build();
    }

    /**
     * Server listener.
     */
    private Server.ServerListener mListener = new Server.ServerListener() {
        @Override
        public void onStarted() {
            String hostAddress = mServer.getInetAddress().getHostAddress();
            ServerManager.serverStart(CoreService.this, hostAddress);
        }

        @Override
        public void onStopped() {
            ServerManager.serverStop(CoreService.this);
        }

        @Override
        public void onError(Exception e) {
            ServerManager.serverError(CoreService.this, e.getMessage());
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopServer(); // Stop server.
    }


    /**
     * Start server.
     */
    private void startServer() {
        if (mServer != null) {
            if (mServer.isRunning()) {
                String hostAddress = mServer.getInetAddress().getHostAddress();
                ServerManager.serverStart(CoreService.this, hostAddress);
            } else {
                mServer.startup();
            }
        }
    }

    /**
     * Stop server.
     */
    private void stopServer() {
        if (mServer != null && mServer.isRunning()) {
            mServer.shutdown();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

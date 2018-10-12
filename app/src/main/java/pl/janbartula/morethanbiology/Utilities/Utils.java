package pl.janbartula.morethanbiology.Utilities;
import android.os.Handler;

public class Utils {

    // Delay mechanism

    public interface DelayCallback {
        void afterDelay();
    }

    public static void delay(long miliseconds, final DelayCallback delayCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, miliseconds); // afterDelay will be executed after milliseconds.
    }
}
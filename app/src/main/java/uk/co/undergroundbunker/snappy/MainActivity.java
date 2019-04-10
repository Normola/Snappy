package uk.co.undergroundbunker.snappy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.format.DateFormat;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Date;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Window window = getWindow();
        View view = window.getDecorView().getRootView();

        view.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            int width = view.getWidth();
            int height = view.getHeight();
            shotEvent(view, window, width, height);
        });

    }
    private void shotEvent(View view, Window window, int width, int height) {

        Date now = new Date();
        String name = DateFormat.format("yyyy-MM-dd_hh:mm:ss", now).toString();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] locationOfViewWindow = new int[2];


        view.getLocationInWindow(locationOfViewWindow);
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        try {
            PixelCopy.request(
                    window,
                    new Rect(
                            locationOfViewWindow[0],
                            locationOfViewWindow[1],
                            locationOfViewWindow[0] + view.getWidth(),
                            locationOfViewWindow[1] + view.getHeight()),
                    bitmap,
                    (copyResult) -> {
                    },
                    new Handler(handlerThread.getLooper()));

        } catch (IllegalArgumentException iaex) {
            iaex.printStackTrace();

            Toast.makeText(this, "**Crunch**", Toast.LENGTH_SHORT).show();

            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] snap = baos.toByteArray();

        DataModel model = new DataModel();
        model.setName(name);
        model.setBitmap(snap);

        SaveDataTask task = new SaveDataTask(this);
        task.execute(model);

        Toast.makeText(this, "Snap", Toast.LENGTH_SHORT).show();

    }
}

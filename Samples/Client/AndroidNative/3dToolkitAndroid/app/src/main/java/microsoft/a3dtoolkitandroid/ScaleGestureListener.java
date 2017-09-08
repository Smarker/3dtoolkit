package microsoft.a3dtoolkitandroid;

import android.util.Log;
import android.view.ScaleGestureDetector;

public class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    private static final String DEBUG_TAG = "Gestures";

    private float mScaleFactor = 1.f;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        mScaleFactor = detector.getScaleFactor() - 1;
        Log.d(DEBUG_TAG,"scale: " + mScaleFactor);
        return true;
    }
}

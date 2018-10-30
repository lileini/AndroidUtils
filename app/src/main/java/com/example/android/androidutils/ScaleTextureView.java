package com.example.android.androidutils;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;

/**
 * 这个类实现了大部分播放器功能
 * 只是提供参考的实例代码
 * <p>
 * 如果有bug请在github留言
 *
 * @author https://github.com/mengzhidaren
 */
public class ScaleTextureView extends TextureView {

    private final String tag = "VlcVideoView";
    private Matrix matrix=new Matrix();
    private Matrix savedMatrix=new Matrix();
    private float scale = 1.0f;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    private int mode = NONE;
    private float scaleRatio = 0.1f;
    // Remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }


    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * 设置缩放比
     * @param scaleRatio
     */
    public void setScaleRatio(float scaleRatio){
        this.scaleRatio = scaleRatio;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked()==MotionEvent.ACTION_POINTER_UP)
            Log.d("Infor", "多点操作");
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                Matrix txform = new Matrix();
                Matrix transform = getTransform(txform);
                /*txform.setScale((float) newWidth / viewWidth, (float) newHeight
                        / viewHeight);
                // txform.postRotate(10); // just for fun
                txform.postTranslate(xoff, yoff);*/

                matrix.set(transform);
                savedMatrix.set(matrix);
                start.set(event.getX(),event.getY());
                Log.d("Infor", "触摸了...");
                mode=DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN: //多点触控
                oldDist=this.spacing(event);
                if(oldDist>10f){
                    Log.d("Infor", "oldDist"+oldDist);
                    savedMatrix.set(matrix);
                    midPoint(mid,event);
                    mode=ZOOM;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode=NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mode==DRAG){     //此实现图片的拖动功能...
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX()-start.x, event.getY()-start.y);
                }
                else if(mode==ZOOM){// 此实现图片的缩放功能...
                    float newDist=spacing(event);
                    if(newDist>10){
                        matrix.set(savedMatrix);
                        scale=newDist/oldDist;
                        LogUtil.i(tag,"scale: "+scale+",mid.x:"+mid.x+",mid.y:"+mid.y);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        setTransform(matrix);
//        setImageMatrix(matrix);
        return true;
    }

    /**
     * 放大
     */
    public void scaleView(){
        scale = scale+0.1f;
        LogUtil.i(tag,"scale: "+scale+",mid.x:"+mid.x+",mid.y:"+mid.y);
        matrix.setScale(scale,scale,mid.x,mid.y);
        setTransform(matrix);
    }
    /**
     * 缩小
     */
    public void shrinkView(){
        scale=scale-0.1f<0.1f?0.1f:scale-0.1f;
        LogUtil.i(tag,"scale: "+scale+",mid.x:"+mid.x+",mid.y:"+mid.y);
        matrix.setScale(scale, scale,mid.x,mid.y);

        setTransform(matrix);
    }

    public ScaleTextureView(Context context) {
        this(context, null);
    }

    public ScaleTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }

        setSurfaceTextureListener(videoSurfaceListener);
    }







//    @Override
//    protected void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        if (isInEditMode()) {
//            return;
//        }
//        setKeepScreenOn(true);// 上层决定开关
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if (isInEditMode()) {
//            return;
//        }
//        setKeepScreenOn(false);
//    }

    /**
     * 这里计算方法是以16:9为基础
     * 在当前view中显示video的最大videoWidth或者video的最大videoHeight
     * 只供参考 跟距产品需求适配
     *
     * @param videoWidth
     * @param videoHeight
     */
    public void adjustAspectRatio(int videoWidth, int videoHeight) {
        if (videoWidth * videoHeight == 0) {
            return;
        }
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        double videoRatio = (double) viewWidth / (double) viewHeight;//显示比例 (小屏16：9 大屏厂商手机比例  真乱)
        double aspectRatio = (double) videoWidth / (double) videoHeight;//视频比例
        int newWidth, newHeight;
        if (videoWidth > videoHeight) {//正常比例16：9
            if (videoRatio > aspectRatio) {//16:9>16:10
                newWidth = (int) (viewHeight * aspectRatio);
                newHeight = viewHeight;
            } else {//16:9<16:8
                newWidth = viewWidth;
                newHeight = (int) (viewWidth / aspectRatio);
            }
        } else {//非正常可能是 90度
            //16:9>1:9
            newWidth = (int) (viewHeight * aspectRatio);
            newHeight = viewHeight;
        }
        float xoff = (viewWidth - newWidth) / 2f;
        float yoff = (viewHeight - newHeight) / 2f;
        Matrix txform = new Matrix();
        getTransform(txform);
        txform.setScale((float) newWidth / viewWidth, (float) newHeight
                / viewHeight);
        // txform.postRotate(10); // just for fun
        txform.postTranslate(xoff, yoff);
        setTransform(txform);
        if (rotation == 180) {
            setRotation(180);
        } else {
            setRotation(0);
        }
        LogUtil.i(tag, "onVideoSizeChanged   newVideo=" + newWidth + "x" + newHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed && mVideoWidth * mVideoHeight > 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    adjustAspectRatio(mVideoWidth, mVideoHeight);
                }
            });
        }
    }


    private int mVideoWidth;
    private int mVideoHeight;
    private int rotation = 0;

//    public int getVideoRotation() {
//        return rotation;
//    }
//
//    public int getVideoWidth() {
//        return mVideoWidth;
//    }
//
//    public int getVideoHeight() {
//        return mVideoHeight;
//    }

//    public Media.VideoTrack getVideoTrack() {
//        return videoMediaLogic.getVideoTrack();
//    }

    /**
     * 如果设备支持opengl这里就不会调用
     *
     * @param width w
     * @param height h
     * @param visibleWidth w
     * @param visibleHeight h
     */
    /*@Override
    public void onVideoSizeChanged(int width, int height, int visibleWidth, int visibleHeight) {
        LogUtil.i(tag, "onVideoSizeChanged   video=" + width + "x" + height + " visible="
                + visibleWidth + "x" + visibleHeight);
        if (width * height == 0) return;
        this.mVideoWidth = visibleWidth;
        this.mVideoHeight = visibleHeight;
        post(new Runnable() {
            @Override
            public void run() {
                adjustAspectRatio(mVideoWidth, mVideoHeight);
            }
        });
    }*/




    private TextureView.SurfaceTextureListener videoSurfaceListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//            videoMediaLogic.setWindowSize(width, height);
//            videoMediaLogic.setSurface(new Surface(surface), null);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//            videoMediaLogic.setWindowSize(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//            videoMediaLogic.onSurfaceTextureDestroyedUI();
            return true;//回收掉Surface
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

}
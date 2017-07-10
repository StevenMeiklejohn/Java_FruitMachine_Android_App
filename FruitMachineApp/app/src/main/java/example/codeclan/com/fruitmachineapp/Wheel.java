package example.codeclan.com.fruitmachineapp;

/**
 * Created by user on 10/07/2017.
 */


//To animate wheels, Wheel class extends Thread from SDK.
//    Each wheel will be animated in an individual thread.
public class Wheel extends Thread {

//    Define wheeListener interface to let the main activity be
//    notified when the image changes for a wheel.
    interface WheelListener {
        void newImage(int img);
    }
//  Images to be drawn by wheels.
    private static int[] imgs = {R.drawable.bar, R.drawable.bell, R.drawable.cherry, R.drawable.grape, R.drawable.orange, R.drawable.seven};
//    Indicates current image.
    public int currentIndex;
    private WheelListener wheelListener;
//    frameduration sets how long the image is shown
    private long frameDuration;
//    startIn will allow delays to triggering the wheels
    private long startIn;
    private boolean isStarted;

    public Wheel(WheelListener wheelListener, long frameDuration, long startIn){
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.startIn = startIn;
        currentIndex = 0;
        isStarted = true;
    }

    public void nextImg(){
        currentIndex++;
        if(currentIndex == imgs.length){
            currentIndex = 0;
        }
    }

    @Override
    public void run(){
        try{
//            pause in milliseconds(defined by startIn)
            Thread.sleep(startIn);
        }catch (InterruptedException e) {
        }
//        spinning loop runs till isStarted is false
//        in loop, change the current image when a frame duration is reached
//        when all images have been displayed, return to the first image.
        while(isStarted){
            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {
            }
            nextImg();
//            notify wheelListener by calling newImage method.
        if(wheelListener != null){
                wheelListener.newImage(imgs[currentIndex]);
            }
        }
    }

    public void stopWheel(){
        isStarted = false;
    }
}

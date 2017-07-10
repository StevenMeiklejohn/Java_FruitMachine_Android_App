package example.codeclan.com.fruitmachineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    private TextView msg;
    private ImageView img1, img2, img3;
    private Wheel wheel1, wheel2, wheel3;
    private Button btn;
    private Boolean isStarted;
    public static final Random RANDOM = new Random();
//    return random long between upper and lower (passed as params)
    public static long randomLong(long lower, long upper){
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      assign Views to variables
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        btn = (Button) findViewById(R.id.btn);
        msg = (TextView) findViewById(R.id.msg);
        isStarted = false;
//        setup onClickListener on button
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isStarted){
                    wheel1.stopWheel();
                    wheel2.stopWheel();
                    wheel3.stopWheel();
//          Check win states.
                    if(wheel1.currentIndex == wheel2.currentIndex && wheel2.currentIndex == wheel3.currentIndex){
                        msg.setText("Jackpot!! Congratulations! Feel free to fire your winnings back in!");
                    }
                    else if(wheel1.currentIndex == wheel2.currentIndex ||
                            wheel2.currentIndex == wheel3.currentIndex ||
                            wheel1.currentIndex == wheel3.currentIndex){
                        msg.setText("You have won the small prize");
                    }
                    else{
                        msg.setText("Unlucky. Please try again");
                    }
                    btn.setText("Start");
                    isStarted = false;
                }
//                if slot machine is not started, create the three wheels
                else
                {
//                    for each wheel define a wheellistener interface implementation
                    wheel1 = new Wheel(new Wheel.WheelListener() {
                        @Override
//                        update the image when the nextImg method is called.
                        public void newImage(final int img) {
//                            each wheel is its own thread, specify it should be run on the UI thread.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img1.setImageResource(img);
                                }
                            });
                        }
//                        start the wheels at random intervals.
                    }, 200, randomLong(0, 200));
                    wheel1.start();

                    wheel2 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img2.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(150, 400));

                    wheel2.start();

                    wheel3 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img3.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(150, 400));

                    wheel3.start();

                    btn.setText("Stop");
                    msg.setText("");
                    isStarted = true;
                }

            }

        });

    }
}

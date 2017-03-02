package theleague.android.com.fitnessguide.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import theleague.android.com.fitnessguide.R;
import theleague.android.com.fitnessguide.app.FitnessGuideApp;
import theleague.android.com.fitnessguide.models.Img;
import theleague.android.com.fitnessguide.models.Instructions;
import theleague.android.com.fitnessguide.models.Workouts;
import theleague.android.com.fitnessguide.utils.SharedData;

import static theleague.android.com.fitnessguide.R.id.btn_timer_ctrl;
import static theleague.android.com.fitnessguide.R.id.progressBar;

public class ExerciseDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ExerciseDetailsActivity.class.getSimpleName();
    private static final String CLICKED_ID = "id";
    private static final String PAUSE_TIMER = "Pause";
    private static final String RESUME_TIMER = "Resume";
    private static final CharSequence START_TIMER = "Start";

    WebView webView;
    ProgressBar progressBar;
    TextView txtProgress;
    private Handler handler;
    int intStatus = 0;
    String position = "";
    private SharedData sharedDataInstance;
    private ImageView exerciseImage;
    private TextView txtExerciseTitle;
    private TextView txtExerciseDesc;
    private TextView txtHold;
    private TextView txtRepeat;
    private TextView txtComplete;
    private TextView txtPerform;
    Thread progressThread = null;
    private CountDownTimer countdownTimer;
    private Button btnTimerCtrl;
    private RelativeLayout rltimer;
    private String hold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);


        Intent intent = getIntent();
        getDataForClick(intent);

        // updateProgress();
        populateDataOnUi();
    }

    private void getDataForClick(Intent intent) {

        Bundle bundle;
        if (intent != null && (bundle = intent.getExtras()) != null) {

            position = String.valueOf(bundle.getInt(CLICKED_ID));
        }
    }

    private void populateDataOnUi() {

        handler = new Handler();
        sharedDataInstance = SharedData.getInstance(FitnessGuideApp.sContext);
        Workouts exercise = sharedDataInstance.getClickedExerciseDetails();

        // set image
        setImageDetails(exercise);
        // set description
        setDescription(exercise);
        // set instructions
        setInstructions(exercise);
        /* webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://player.vimeo.com/video/67705519");*/
    }

    private void setInstructions(Workouts exercise) {

        Instructions instructions = exercise.getInstructions();
        hold = instructions.getHold();
        String complete = instructions.getComplete();
        String perform = instructions.getPerform();
        String repeat = instructions.getRepeat();

        if (instructions != null) {

            txtRepeat = (TextView) findViewById(R.id.tv_repeat);
            txtRepeat.setText(repeat);

            txtHold = (TextView) findViewById(R.id.tv_hold);
            rltimer = (RelativeLayout)findViewById(R.id.rl_timer);

            if(!hold.isEmpty()) {

                rltimer.setVisibility(View.VISIBLE);
                setTimerLayout();
                txtHold.setText(hold);
            }else{

                rltimer.setVisibility(View.GONE);
                txtHold.setText("");

            }
            txtComplete = (TextView) findViewById(R.id.tv_complete);
            txtComplete.setText(complete);

            txtPerform = (TextView) findViewById(R.id.tv_perform);
            txtPerform.setText(perform);
        }
    }

    private void setTimerLayout() {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnTimerCtrl = (Button) findViewById(R.id.btn_timer_ctrl);
        btnTimerCtrl.setOnClickListener(this);
        txtProgress = (TextView) findViewById(R.id.tvProgress);
    }

    private void setDescription(Workouts exercise) {

        String jsonString = exercise.getDescription();
        List descList = sharedDataInstance.getDescription(jsonString);
        txtExerciseTitle = (TextView) findViewById(R.id.tvExerciseDescTitle);
        txtExerciseDesc = (TextView) findViewById(R.id.tvExerciseDesc);
        if (descList != null) {

            txtExerciseTitle.setText(descList.get(0).toString());
            String strExerciseDesc = "";
            for (int i = 1; i < descList.size(); i++) {
                strExerciseDesc += descList.get(i);
            }
            txtExerciseDesc.setText(strExerciseDesc);
        }
    }

    private void setImageDetails(Workouts exercise) {

        String imgPath = sharedDataInstance.getImageURL(exercise);
        exerciseImage = (ImageView) findViewById(R.id.ivExcerciseImg);

        Img imageInfo = exercise.getImageInfo();
        if (imageInfo != null) {

            int imageHeight = Integer.parseInt(imageInfo.getHeight());
            int imageWidth = exerciseImage.getDrawable().getIntrinsicWidth() + 150;
            // exerciseImage.setLayoutParams(RelativeLayout.La);
            if (!imgPath.isEmpty()) {
                Picasso.with(this).load(imgPath).resize(imageWidth, imageHeight).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(exerciseImage);
            } else {
                // viewHolder.imgExercise.setImageResource(R.mipmap.ic_launcher);
                Picasso.with(this).load(R.mipmap.ic_launcher).into(exerciseImage);
            }
        }
    }

    private long milliLeft;
    private long min, sec;

    /**
     * @param total time duration in millis
     */
    private void startCountDownTimer(long total) {
        countdownTimer = new CountDownTimer(total, 1000) {

            public void onTick(long milliTillFinish) {
                //update total with the remaining time left
                milliLeft = milliTillFinish;
                min = (milliTillFinish / (1000 * 60));
                sec = ((milliTillFinish / 1000) - min * 60);
                txtProgress.setText("" + Long.toString(min) + ":" + Long.toString(sec));
                long totalSecLeft =sec*1000;
                progressBar.setProgress((int) totalSecLeft);
            }

            public void onFinish() {
                btnTimerCtrl.setText("Start");
            }
        }.start();
    }

    public void timerPause() {
        countdownTimer.cancel();
    }

    private void timerResume() {

        Log.i(TAG,"min"+Long.toString(min));
        startCountDownTimer(milliLeft);
    }
    
    /*private void updateProgress(){

        if(progressThread==null) {
            progressThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (intStatus <= 100) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(intStatus);
                                txtProgress.setText(intStatus + " %");
                            }
                        });
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        intStatus++;
                    }
                }
            });
            progressThread.start();
        }

    }*/

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_timer_ctrl) {

            if (btnTimerCtrl.getText().equals(START_TIMER)) {

                Log.i(TAG, "Started" + btnTimerCtrl.getText().toString());
                btnTimerCtrl.setText(PAUSE_TIMER);
                if(!hold.isEmpty()) {


                    String holdTimeDigit = hold.replaceAll("\\D+","");
                    Log.i(TAG, ""+holdTimeDigit);
                    startCountDownTimer( Integer.parseInt(holdTimeDigit)* 1000);
                }else{
                    startCountDownTimer(15 * 1000);
                }

            } else if (btnTimerCtrl.getText().equals(PAUSE_TIMER)) {

                Log.i(TAG, "Paused" + btnTimerCtrl.getText().toString());
                btnTimerCtrl.setText(RESUME_TIMER);
                timerPause();

            } else if (btnTimerCtrl.getText().equals(RESUME_TIMER)) {

                btnTimerCtrl.setText(PAUSE_TIMER);
                timerResume();
            }
        }
    }
}

package theleague.android.com.fitnessguide.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import theleague.android.com.fitnessguide.R;
import theleague.android.com.fitnessguide.app.FitnessGuideApp;
import theleague.android.com.fitnessguide.interfaces.IWsResponseListener;
import theleague.android.com.fitnessguide.network.WebServiceComm;
import theleague.android.com.fitnessguide.utils.SharedData;

public class HomeScrActivity extends AppCompatActivity implements View.OnClickListener, IWsResponseListener {

    private static final String DEFAULT_ACCESS_CODE = "FE4528C";
    private Button btnCall;
    private Button btnView;
    private ProgressDialog progressDialog;
    private EditText etAccessCode;
    private String strAccessCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCall = (Button) findViewById(R.id.btnAccessCode);
        etAccessCode = (EditText) findViewById(R.id.etAccessCode);
        btnCall.setOnClickListener(this);
        btnView = (Button) findViewById(R.id.btnView);
        btnView.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnAccessCode) {

            Editable editableCode = etAccessCode.getText();
            strAccessCode = DEFAULT_ACCESS_CODE;
            if(editableCode!=null){
                strAccessCode = editableCode.toString();
            }
            progressDialog.show();
            WebServiceComm.callService(this,strAccessCode);
        }
        else if(view.getId() == R.id.btnView){

            launchScreen();
        }
    }

    // WebService Call Successful
    @Override
    public void onWSSuccess() {

        progressDialog.dismiss();
        // save access code for further use
        SharedData.getInstance(FitnessGuideApp.sContext).saveCode(strAccessCode);
        launchScreen();
    }

    private void launchScreen() {

        Intent intent = new Intent(HomeScrActivity.this,ExerciseListActivity.class);
        startActivity(intent);
    }

    // Failure in WebService Call
    @Override
    public void onWSFailure() {

        progressDialog.dismiss();
    }

    private void showDialog(){

        if(progressDialog!= null && !progressDialog.isShowing()) {

            String message = getResources().getString(R.string.progress_message);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    private void removeDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {

            progressDialog.dismiss();
        }
    }
}

package theleague.android.com.fitnessguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import theleague.android.com.fitnessguide.R;
import theleague.android.com.fitnessguide.adapters.ExercisesAdapter;
import theleague.android.com.fitnessguide.app.FitnessGuideApp;
import theleague.android.com.fitnessguide.models.Workouts;
import theleague.android.com.fitnessguide.utils.SharedData;

import static android.media.CamcorderProfile.get;

public class ExerciseListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = ExerciseListActivity.class.getSimpleName();
    private static final String CLICKED_ID = "id";
    private GridView gridView;
    SharedData sharedDataInstance;
    private ArrayList<Workouts> exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        
        gridView = (GridView) findViewById(R.id.gv_exercise);
        TextView emptyListTextView = (TextView) findViewById(R.id.tvEmptyList);
        sharedDataInstance = SharedData.getInstance(FitnessGuideApp.sContext);
        //  Log.i(TAG,"image ="+ sharedDataInstance.getImage(1).getSrc()+" inst"+ sharedDataInstance.getInstructions(1).getHold());
        // Log.i(TAG,sharedDataInstance.getWorkouts().get(0).getDescription());
        //String jsonStr = sharedDataInstance.getWorkouts().get(0).getDescription();
        //Log.i(TAG,sharedDataInstance.getDescription(jsonStr).toString());

        exercisesList = (ArrayList<Workouts>) sharedDataInstance.getWorkouts();

        if(exercisesList!=null && exercisesList.size()>0) {

            emptyListTextView.setVisibility(View.GONE);
            gridView.setAdapter(new ExercisesAdapter(this, exercisesList));
            gridView.setOnItemClickListener(this);
        }else{
            emptyListTextView.setVisibility(View.VISIBLE);
            emptyListTextView.setText(getResources().getString(R.string.empty_workout_list));
            gridView.setEmptyView(emptyListTextView);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(exercisesList!=null && exercisesList.size()>0) {

            Workouts exercise = exercisesList.get(position);
            if(exercise!=null)
                sharedDataInstance.saveClickedExerciseDetails(exercise);
        }

        Intent intent = new Intent(getApplicationContext(), ExerciseDetailsActivity .class);
        intent.putExtra(CLICKED_ID, position);
        startActivity(intent);
    }
}

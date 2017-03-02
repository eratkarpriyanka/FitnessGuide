package theleague.android.com.fitnessguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import theleague.android.com.fitnessguide.R;
import theleague.android.com.fitnessguide.app.FitnessGuideApp;
import theleague.android.com.fitnessguide.models.Workouts;
import theleague.android.com.fitnessguide.utils.SharedData;


/**
 * Created by Priyanka
 */

public class ExercisesAdapter extends BaseAdapter{

    private static final String TAG = ExercisesAdapter.class.getSimpleName();
    private final SharedData sharedDataInstance;
    private Context mContext;
    private ArrayList<Workouts> exerciseList=null;

    public ExercisesAdapter(Context context, ArrayList<Workouts> exerciseList) {
        this.mContext = context;
        if(exerciseList!=null && exerciseList.size()>0 ) {

            this.exerciseList = exerciseList;
        }
        sharedDataInstance = SharedData.getInstance(FitnessGuideApp.sContext);
    }

    @Override
    public int getCount() {

        if(exerciseList!=null) {
            return exerciseList.size();
        }else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.linearlayout_exercise, null);

            final ImageView imgExercise = (ImageView)convertView.findViewById(R.id.ivExercise);
            final TextView tvExerciseName = (TextView)convertView.findViewById(R.id.tvExerciseName);

            ViewHolder viewHolder = new ViewHolder(imgExercise,tvExerciseName);
            convertView.setTag(viewHolder);
        }
        final ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        Workouts exercise = exerciseList.get(position);
        viewHolder.tvExerciseName.setText(sharedDataInstance.getDescName(exercise));

        String imgPath = sharedDataInstance.getImageURL(exercise);
        if(!imgPath.isEmpty()) {

            Picasso.with(mContext).load(imgPath).placeholder(R.mipmap.ic_launcher).fit().into(viewHolder.imgExercise);
        }else {
           // viewHolder.imgExercise.setImageResource(R.mipmap.ic_launcher);
            Picasso.with(mContext).load(R.mipmap.ic_launcher).into(viewHolder.imgExercise);
        }

        /*  TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(String.valueOf(position));
        return dummyTextView;*/
        return convertView;
    }

    private class ViewHolder{

        private final ImageView imgExercise;
        private final TextView tvExerciseName;

        public ViewHolder(ImageView imgExercise,TextView tvExerciseName){

            this.imgExercise = imgExercise;
            this.tvExerciseName = tvExerciseName;
        }
    }
}

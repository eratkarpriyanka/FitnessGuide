package theleague.android.com.fitnessguide.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import theleague.android.com.fitnessguide.R;
import theleague.android.com.fitnessguide.models.Img;
import theleague.android.com.fitnessguide.models.Instructions;
import theleague.android.com.fitnessguide.models.Workouts;

import static android.media.CamcorderProfile.get;

/**
 * Created by Priyanka
 */
public class SharedData {

    private static final String ACCESS_CODE = "code";
    private static SharedData ourInstance=null;

    private Context context;
    private SharedPreferences sharedPref;
    private Workouts exercise;

    public static SharedData getInstance(Context context) {

        if(ourInstance==null) {
            ourInstance = new SharedData(context);
        }
        return ourInstance;
    }

    private SharedData(Context context) {

        this.context = context;
        sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    }

    /**
     *  save access code
     * @param data
     */
    public void saveCode(String data){

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_CODE, data);
        editor.commit();

    }

    /**
     *
     * @return access code
     */
    public String getCode(){

        String defaultValue = "";
        String strCode = sharedPref.getString(ACCESS_CODE, defaultValue);
        return strCode;
    }

    /**
     *
     * @return list of exercises for a given workout
     */
    public List<Workouts> getWorkouts(){

        List<Workouts> exercisesList = null;
        exercisesList = Workouts.getExercisesList();
        return exercisesList;
    }

    /**
     * get Instructions Info for a given workout
     * @param id
     * @return
     */
    public Instructions getInstructions(long id){

        Instructions instructObj = null;

        List<Instructions> instructList = null;
        instructList = Instructions.getInstructionsList(id);

        if(instructList !=null && instructList.size()>0){

            instructObj = instructList.get(0);
        }
        return instructObj;
    }

    /**
     * get Image Info for a given workout
     * @param id
     * @return
     */
    public Img getImage(long id){

        Img imageObj = null;

        List<Img> imgList = null;
        imgList = Img.getImageList(id);
        if(imgList!=null && imgList.size()>0) {

            imageObj = imgList.get(0);
        }
        return imageObj;
    }

    /**
     *
     * @param jsonString
     * @return
     */
    public List<String> getDescription(String jsonString) {

       /* Gson gson = new GsonBuilder().serializeNulls().create();
        JsonObject obj = gson.fromJson(jsonString, JsonObject.class);
        JsonArray jsonArray = obj.getAsJsonArray();*/
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>(){}.getType();
        List<String> descList = (List<String>) gson.fromJson(jsonString, listType);
        return  descList;
    }

    /**
     *
     * @param exercise
     * @return
     */
    public String getDescName(Workouts exercise){

        String descName = "";

        if(exercise != null){

            String jsonDesc =exercise.getDescription();
            List descList = getDescription(jsonDesc);
            descName = descList.get(0).toString();
        }
        return descName;
    }

    /**
     *
     * @param exercise
     * @return
     */
    public String getImageURL(Workouts exercise) {

        String strImgURL = "";

        if (exercise != null) {

            strImgURL = exercise.getImageInfo().getSrc();
        }

        return strImgURL;
    }



    /**
     *
     * @param exercise
     */
    public void saveClickedExerciseDetails(Workouts exercise){

        this.exercise = exercise;
    }

    public Workouts getClickedExerciseDetails(){

        return exercise;
    }
}

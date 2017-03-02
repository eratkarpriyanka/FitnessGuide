package theleague.android.com.fitnessguide.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import theleague.android.com.fitnessguide.interfaces.IWsResponseListener;
import theleague.android.com.fitnessguide.models.Img;
import theleague.android.com.fitnessguide.models.Instructions;
import theleague.android.com.fitnessguide.models.Workouts;

/**
 * Created by Priyanka
 */

public class ResponseHandler extends JsonHttpResponseHandler{

    public static final String TAG = ResponseHandler.class.getSimpleName();

    public static final String KEY_DESC = "description";
    public static final String KEY_INSTRUCT = "instructions";
    public static final String KEY_REPEAT = "repeat";
    public static final String KEY_COMPLETE = "complete";
    public static final String KEY_HOLD = "hold";
    public static final String KEY_PERFORM = "perform";
    public static final String KEY_IMG = "img";
    public static final String KEY_SRC = "src";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_VIDEO = "video";


    public JSONObject jsonObj;
    String jsonString = "";
    Context context;
    IWsResponseListener interfaceResponse;

    public ResponseHandler(Context context){

        this.context = context;
        interfaceResponse = (IWsResponseListener) context;
    }

    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArr) {

        try {
            Log.i(TAG, "response is "+jsonArr.toString());
            if(jsonArr!=null){

                for(int i=0; i<jsonArr.length(); i++){

                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    jsonString = jsonObj.toString();

                    Gson gson = new GsonBuilder().serializeNulls().create();
                    JsonObject obj = gson.fromJson(jsonString, JsonObject.class);

                    // description array details   Note: store in Json form as
                    // Active Android ORM can't save arrays
                    JsonArray jsonDesc = obj.getAsJsonArray(KEY_DESC);

                    String descString = "";
                    if(jsonDesc!=null){
                        descString = jsonDesc.toString();
                    }

                    // instruction details (repeat,hold,complete,perform)
                    JsonObject jsonInstructions = obj.getAsJsonObject(KEY_INSTRUCT);
                    Instructions instructObj = null;
                    if(jsonInstructions != null) {

                        String repeat = jsonInstructions.get(KEY_REPEAT).getAsString();
                        JsonElement holdElement = jsonInstructions.get(KEY_HOLD);
                        Log.i(TAG, "HOLD ELEMENT "+holdElement);
                        String hold="";
                        if(holdElement != null) {
                            hold = holdElement.getAsString();
                        }
                        String perform = jsonInstructions.get(KEY_PERFORM).getAsString();
                        String complete="";
                        JsonElement completeElement= jsonInstructions.get(KEY_COMPLETE);
                        if(completeElement !=null) {
                            complete = completeElement.getAsString();
                        }
                        instructObj = new Instructions(complete, hold, perform, repeat);
                    }

                    // image details
                    JsonObject jsonImage = obj.getAsJsonObject(KEY_IMG);
                    Img image = null;
                    if(jsonImage !=null) {
                        String src = jsonImage.get(KEY_SRC).getAsString();
                        String height = jsonImage.get(KEY_HEIGHT).getAsString();
                        image = new Img(height, src);
                    }

                    // video details
                    JsonElement jsonVideo= obj.get(KEY_VIDEO);
                    String video="";
                    if(jsonVideo !=null) {

                        video = jsonVideo.getAsString();
                    }
                    // entire workout details
                    Workouts workouts = new Workouts(descString,image,instructObj,video);
                    instructObj.save();
                    image.save();
                    workouts.save();
                    Log.i(TAG, "desc "+jsonDesc);
                    Log.i(TAG, "inst "+jsonInstructions);
                    Log.i(TAG, "img "+jsonImage);
                }
               // Log.i(TAG, "workouts "+Workouts.getAll().get(0));
                interfaceResponse.onWSSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            interfaceResponse.onWSFailure();
        }
    }

    public void onFailure(int statusCode, Header[]headers, Throwable t, JSONObject e)  {
        // Handle the failure and alert the user to retry
        Log.e(TAG, e.toString());
        interfaceResponse.onWSFailure();
    }
}

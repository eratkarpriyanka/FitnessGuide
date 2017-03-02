package theleague.android.com.fitnessguide.network;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * Created by Priyanka
 *
 * This class deals with communication with service APIs
 */

public class WebServiceComm {

    public static final String TAG = WebServiceComm.class.getSimpleName();
    private static final String BASE_URL = "https://arcane-anchorage-34204.herokuapp.com/";
    private static final String GET_WORKOUT_INFO_URL = BASE_URL+"handleCode";

    public static final String KEY_ACCESS_CODE = "code";

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

    public WebServiceComm() {

    }


    public static void callService(final Context context,String strAccessCode){

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;

        try {
            jsonParams.put(KEY_ACCESS_CODE, strAccessCode);
            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(context, GET_WORKOUT_INFO_URL, entity, "application/json",new ResponseHandler(context));
             /*   new JsonHttpResponseHandler() {
                    public JSONObject jsonObj;
                    String jsonString = "";
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
                                    JsonArray jsonDesc = obj.getAsJsonArray("description");

                                    String descString = "";
                                        if(jsonDesc!=null){
                                                descString = jsonDesc.toString();
                                        }

                                    // instruction details (repeat,hold,complete,perform)
                                    JsonObject jsonInstructions = obj.getAsJsonObject("instructions");
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
                                     Log.i(TAG, "workouts "+Workouts.getAll());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {
                        // Handle the failure and alert the user to retry
                        Log.e(TAG, e.toString());

                });
     */
    }


}

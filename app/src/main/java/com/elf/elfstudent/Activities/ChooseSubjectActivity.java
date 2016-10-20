package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.Network.SubListProvider;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.BundleKey;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.model.SubjectModelPrice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 */

public class ChooseSubjectActivity extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, SubListProvider.SubListCallbacks {

    private static final String TAG = "CHOOSE_SUBJECT";
    private static final String GET_SUBJECT_LIST = "";
    String studentId = null;



    //Views
    //NExt Button
    @BindView(R.id.nextbutton) Button mNextbutton;

    private SubListProvider mSubListHandler = null;
    ErrorHandler errorHandler  = null;

    private DataStore mStore = null ;
    private AppRequestQueue mRequestQueue = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_subject_activity);
        ButterKnife.bind(this);

        if (getIntent() != null){
            studentId = getIntent().getStringExtra(BundleKey.ARG_STUDENT_ID);
        }



        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());

        errorHandler  = new ErrorHandler(this);
        mSubListHandler = new SubListProvider(this);
        getSubjectListFor(studentId);

        mNextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextButtonClicked();
            }
        });
    }


    /*find out Which Subject hAs been Cliceked
    * Take Him to Payment Page
    *
    *
    * */
    private void NextButtonClicked() {

       if (allSubjects()){
           //All Subjects
           String allprice = "";
           final  Intent i = new Intent(this,PaymentActivity.class);
           i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           i.putExtra(BundleKey.ALL_PRICE,allprice);
           startActivity(i);
       }
        else{
           //specific subject have been Chosen
           int subjectChosenCOunt = 4;
           String total_price = "";
           //find subject chosen and individual prices
           final  Intent  i = new Intent(this,SpecificSubject.class);
           //i.putExtra()
           startActivity(i);
       }


    }

    private boolean allSubjects() {

        return true;
    }

    private void getSubjectListFor(String studentId) {
        JSONObject mObject = new JSONObject();
        try{
            mObject.put(RequestParameterKey.STUDENT_ID,studentId);
        }
        catch (Exception e ){
            Log.d(TAG, "getSubjectListFor: ");
        }

        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST,GET_SUBJECT_LIST,mObject,mSubListHandler,errorHandler);
        mRequestQueue.addToRequestQue(mRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void TimeoutError() {

    }

    @Override
    public void NetworkError() {

    }

    @Override
    public void ServerError() {

    }

    @Override
    public void SubList(List<SubjectModelPrice> mList) {

    }
}
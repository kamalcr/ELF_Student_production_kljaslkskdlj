package com.elf.elfstudent.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.elf.elfstudent.DataStorage.DataStore;
import com.elf.elfstudent.Network.AppRequestQueue;
import com.elf.elfstudent.Network.EmailHandler;
import com.elf.elfstudent.Network.ErrorHandler;
import com.elf.elfstudent.R;
import com.elf.elfstudent.Utils.RequestParameterKey;
import com.elf.elfstudent.Utils.StringValidator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 20/10/16.
 * the Page which is first shown to user on Register Clicking
 */

public class EmailActivityPage extends AppCompatActivity implements ErrorHandler.ErrorHandlerCallbacks, EmailHandler.EmaiCallbacks {


    private static final String TAG = "ELF";

    private static final String EMAIL_URL = "http://www.hijazboutique.com/elf_ws.svc/CheckStudentEmail";
    @BindView(R.id.email_text)
    TextInputEditText mEmailBox;

    @BindView(R.id.email_next_button)
    Button mNextButton;

    DataStore mStore = null;
    EmailHandler emailHandler = null;
    ErrorHandler errorHandler = null;
    AppRequestQueue mRequestQueue = null;

    String email = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);
        ButterKnife.bind(this);




        mRequestQueue = AppRequestQueue.getInstance(getApplicationContext());

        mStore = DataStore.getStorageInstance(getApplicationContext());

        errorHandler = new ErrorHandler(this);

        emailHandler = new EmailHandler(this);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEmail();
            }
        });
    }

    private void CheckEmail() {
      email  = mEmailBox.getText().toString();
        if (StringValidator.checkeEMail(email)){
                //Correct Email format
            checkifEmailExists(email);
        }
        else{
            //wrong Email Format
            Log.d(TAG, "CheckEmail: wrong email format");
            // TODO: 20/10/16 show Shake Animation
        }
    }

    private void checkifEmailExists(String email) {

        email = email.trim();
        JSONObject mObj = new JSONObject();
        try {
            mObj.put(RequestParameterKey.EMAIL_ID,email);

        }
        catch (Exception e ){
            Log.d(TAG, "checkifEmailExists: ");
        }

        JsonArrayRequest mRequest = new JsonArrayRequest(Request.Method.POST,EMAIL_URL,mObj,emailHandler,errorHandler);
        mRequestQueue.addToRequestQue(mRequest);

    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void TimeoutError() {


        Toast.makeText(getApplicationContext(),"Time Out Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void NetworkError() {
        Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ServerError() {
        Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowPersonalInfoPage() {

        Log.d(TAG, "going to register page");
        mStore.setEmailId(email);
        final Intent i = new Intent(this,RegisterActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @Override
    public void emailAlreadyExists() {
        //// TODO: 20/10/16 email Already Existe show toast

        Toast.makeText(getApplicationContext(),"Email Already Exists",Toast.LENGTH_SHORT).show();
    }
}

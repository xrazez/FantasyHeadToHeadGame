package com.example.michael.fantasyheadtoheadgame.ActivityScreens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.michael.fantasyheadtoheadgame.Classes.MySingleton;
import com.example.michael.fantasyheadtoheadgame.Classes.RequestResponseParser;
import com.example.michael.fantasyheadtoheadgame.Classes.User;
import com.example.michael.fantasyheadtoheadgame.UtilityClasses.CommonUtilityMethods;
import com.example.michael.fantasyheadtoheadgame.UtilityClasses.Constants;
import com.example.michael.fantasyheadtoheadgame.R;
import com.example.michael.fantasyheadtoheadgame.UtilityClasses.SecurityMethods;
import com.example.michael.fantasyheadtoheadgame.Session.SessionManager;

import java.io.Serializable;


/**
 * Created by michaelgeehan on 10/02/2017.
 */

public class Login extends AppCompatActivity{

    //local variables
    private static Context context;
    private SessionManager session;
    private RequestQueue queue;
    private RequestResponseParser responseParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.login);
        
        //set context
        context = Login.this;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        this.finish();
    }
    
    public void logIn(View view){
        //get edit text values and convert to string
        EditText userName = (EditText) findViewById(R.id.userName);
        EditText userPass = (EditText) findViewById(R.id.password);
        String userN = userName.getText().toString();
        String pass = userPass.getText().toString();
        
        //Validity checks on all inputs & security checks SQL injection & password encryption
        if(!userN.isEmpty() && !pass.isEmpty()){
            
            //for sql prevention
            if(SecurityMethods.isCleanInput(userN)){
                String passwordHashed = SecurityMethods.hashPassword(pass);
//                LoginHttpRequest logIn = new LoginHttpRequest(context, userName.getText().toString(), passwordHashed);
//                logIn.delegate = this;
//                logIn.execute();
                
                //creating the RequestQueue
                responseParser = new RequestResponseParser();
                queue = MySingleton.getInstance(this.getApplicationContext()).
                        getRequestQueue();
                sendLoginRequest(userName.getText().toString(),passwordHashed);
                
            }else{
                CommonUtilityMethods.displayToast(context,Constants.INVALID_USERNAME);
            }
        }else{
            if(userN.isEmpty()){
                CommonUtilityMethods.displayToast(context,Constants.USERNAME_EMPTY);
            }else{
                CommonUtilityMethods.displayToast(context,Constants.PASSWORD_EMPTY);
            }
        }
        
    }
    
    public void sendLoginRequest(String userName,String passwordHashed){
        String url ="http://"+ Constants.IP_ADDRESS+":8888/FantasyShowDown/login.php?userN="+userName+"&password="+passwordHashed;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        User u = responseParser.parseLoginResponse(response);
                        
                        if(checkValidUser(u)){
                            session = new SessionManager(getApplicationContext());
                            session.createLoginSession(u.getUsername(),u.getEmail(),u.getId());
                            Intent intent = new Intent(context, MainHub.class);
                            intent.putExtra("UserClass", u);
                            intent.putExtra("parser",responseParser);
                            context.startActivity(intent);
                            finishAffinity();
                        }else{
                            CommonUtilityMethods.displayToast(getApplicationContext(),"I'm sorry your username/password is incorrect!!");
                        }
                        
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CommonUtilityMethods.displayToast(getApplicationContext(),"There seems to be a server issue");
            }
        });
        queue.add(stringRequest);
    }
    
    public boolean checkValidUser(User u){
        if(u != null){
            return true;
        }else{
            return false;
        }
    }
    
    public void registerUser(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
    
    //for admins only for mobile device while server is not up & running online
    public void changeIP(View view){
        final EditText input = new EditText(Login.this);
        input.setHint("Set IP address");
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setView(input);
        builder.setTitle("Change IP")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Constants.IP_ADDRESS = input.getText().toString();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        
                    }

                });
        builder.show();
    }
    
  

    

    
}
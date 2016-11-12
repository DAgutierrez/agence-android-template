package com.gutierrez.diego.android_template.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gutierrez.diego.android_template.R;
import com.gutierrez.diego.android_template.activities.HomeActivity;
import com.gutierrez.diego.android_template.calls.login.MakeLoginCall;
import com.gutierrez.diego.android_template.elements.ProgressDialogElement;
import com.gutierrez.diego.android_template.utils.ErrorHandlerUtil;
import com.gutierrez.diego.android_template.utils.JsonUtil;
import com.gutierrez.diego.android_template.utils.SessionManager;
import com.gutierrez.diego.android_template.utils.ThreadManager;

import org.json.JSONObject;

import java.net.URLEncoder;

/**
 * Created by diego on 20-10-16.
 */
public class LoginFragment extends Fragment {

    Context context;
    Handler handler;
    FragmentManager fm;
    SessionManager session;
    ThreadManager threadManager;
    AppCompatActivity appCompatActivity;
    SessionManager sessionManager;
    ProgressDialogElement progressDialogLogin;
    Typeface ttfRobotoRegular;

    MakeLoginCall makeLoginCall;

    Toast toast;

    TextView txtTitle;
    Button btnLogin;
    EditText inpUsername;
    EditText inpPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        fm = getActivity().getSupportFragmentManager();
        session = new SessionManager(context,fm);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState)
    {

//        appCompatActivity =  ((AppCompatActivity) getActivity());
//        ScreenUtil.HideActionBar(appCompatActivity);

        return inflater.inflate(R.layout.fragment_login, container, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ttfRobotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");

        txtTitle =  (TextView) view.findViewById(R.id.txtLogin);
        txtTitle.setTypeface(ttfRobotoRegular);

        btnLogin =  (Button) view.findViewById(R.id.btnLogin);
        btnLogin.setTypeface(ttfRobotoRegular);

        btnLogin.setOnClickListener(makeLogin);

        inpUsername =  (EditText) view.findViewById(R.id.inpUsername);
        inpUsername.setTypeface(ttfRobotoRegular);

        inpPassword =  (EditText) view.findViewById(R.id.inpPassword);
        inpPassword.setTypeface(ttfRobotoRegular);

    }

    @Override
    public void onResume() {
        super.onResume();

    }



    private  View.OnClickListener makeLogin =  new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            final String username =  inpUsername.getText().toString();
            final String password = inpPassword.getText().toString();

            Boolean theFormIsValid = theFormIsValid(username,password);

            if (theFormIsValid) {

                progressDialogLogin = new ProgressDialogElement(context,"Ingresando");
                progressDialogLogin.showSpinner();


                try {

                    String params =
                            "username=" + URLEncoder.encode(username, "UTF-8") +
                                    "&password=" + URLEncoder.encode(password, "UTF-8") +
                                    "&grant_type=" + URLEncoder.encode("password", "UTF-8");


                    makeLoginCall = new MakeLoginCall(params);

                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            String response = (String) msg.obj;
                            String err = ErrorHandlerUtil.checkError(response.toString());
                            if(err != null) {
                                progressDialogLogin.dismiss();
                                toast = Toast.makeText(context, err , Toast.LENGTH_SHORT);
                                toast.show();
                            } else {

                                JSONObject responseJson = JsonUtil.parseJsonObject(response);

                                try {

                                    String token = responseJson.getString("access_token");

                                    sessionManager =  new SessionManager(context,fm);
                                    sessionManager.createLoginSession(username, password, token);

                                    Intent homeActivity = new Intent(context, HomeActivity.class);
                                    startActivity(homeActivity);

//                                fm.beginTransaction()
//                                        .replace(R.id.home_container, new HomeFragment())
//                                        .commit();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    };

                    threadManager =  new ThreadManager(makeLoginCall,handler);
                    threadManager.start();


                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

        }



    };

    private Boolean theFormIsValid(String username,String password) {

        if (username.isEmpty() || password.isEmpty()) {

            if (username.isEmpty())  inpUsername.getBackground().setColorFilter(getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_ATOP);
            if (password.isEmpty())  inpPassword.getBackground().setColorFilter(getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_ATOP);

            toast = Toast.makeText(context, "campo requerido" , Toast.LENGTH_SHORT);
            toast.show();

            return false;
        } else {

            return true;
        }



    }




}

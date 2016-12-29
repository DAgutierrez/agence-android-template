package com.agence.pharma_investi.fragments.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agence.pharma_investi.R;
import com.agence.pharma_investi.fragments.HomeFragment;
import com.agence.pharma_investi.presenters.login.LoginPresenterImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by diego on 20-10-16.
 */
public class LoginFragment extends Fragment  implements  LoginView{

    Context context;
    FragmentManager fm;

    ProgressDialog progressDialog;
    Typeface ttfRobotoRegular;

    Toast toast;

    LoginPresenterImpl presenter;

    @BindView(R.id.txtLogin) TextView txtTitle;

    @BindView(R.id.btnLogin) Button btnLogin;

    @BindView(R.id.inpUsername)  EditText inpUsername;
    @BindView(R.id.inpPassword)  EditText inpPassword;

    ExecutorService executor = Executors.newFixedThreadPool(1);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        fm = getActivity().getSupportFragmentManager();

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("cargando");

        ttfRobotoRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");

        presenter = new LoginPresenterImpl(this,context);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_login, container, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        txtTitle.setTypeface(ttfRobotoRegular);
        btnLogin.setTypeface(ttfRobotoRegular);
        inpUsername.setTypeface(ttfRobotoRegular);
        inpPassword.setTypeface(ttfRobotoRegular);

        btnLogin.setOnClickListener(loginAction);

    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if(actionBar != null) {
            actionBar.hide();
        }

    }


    private  View.OnClickListener loginAction =  new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            final String username =  inpUsername.getText().toString();
            final String password = inpPassword.getText().toString();

            Boolean theFormIsValid = theFormIsValid(username,password);

            if (theFormIsValid) {

                executor.submit(presenter.login(username,password));


            }
        }
    };

    @Override
    public void onErrorLogin(String error) {

        toast = Toast.makeText(context, error , Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public void onSuccessLogin() {

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.main_container, new HomeFragment())
                .commit();

    }

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

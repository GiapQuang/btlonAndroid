package com.manager.btlonappbanhangonline.login.login.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.manager.btlonappbanhangonline.databinding.FragmentLoginBinding;
import com.manager.btlonappbanhangonline.eventbus.IMoveClickListener;
import com.manager.btlonappbanhangonline.login.forgetpassword.ForgetPasswordActivity;
import com.manager.btlonappbanhangonline.login.login.ResultCallBack;

import java.util.regex.Pattern;

public class LoginFragment extends Fragment{
    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;
    private ProgressDialog progressDialog;
    private IMoveClickListener moveClickListener;

    public LoginFragment(IMoveClickListener moveClickListener) {
        this.moveClickListener = moveClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);

        binding.loginButton.setOnClickListener(v -> login());

        binding.forgetPassWordText.setOnClickListener(v -> {
            moveClickListener.moveTo();
        });

        binding.isShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.passwordText.setInputType(InputType.TYPE_NULL);
            } else {
                binding.passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        binding.forgetPassWordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPassword();
            }
        });
    }

    private void forgetPassword() {
        startActivity(new Intent(requireActivity(), ForgetPasswordActivity.class));
        requireActivity().finish();
    }


    void login(){
        progressDialog.show();
        String email = binding.emailText.getText().toString().trim();
        String password = binding.passwordText.getText().toString();

        if(!validateEmail(email)){
            Toast.makeText(requireActivity(), "Email is not correct.", Toast.LENGTH_SHORT).show();
        }
        loginViewModel.setLoginResultCallback(new ResultCallBack() {
            @Override
            public void onLoginSuccess() {
                requireActivity().finish();
                progressDialog.dismiss();
            }
            @Override
            public void onLoginFailure(String message) {
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        if(email.equalsIgnoreCase("")){
            binding.emailText.setError("");
        }
        if(password.equalsIgnoreCase("")){
            binding.passwordText.setError("");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            loginViewModel.login(email,password);
        }
    }

    Boolean validateEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pattern.matcher(email).matches();
    }
}
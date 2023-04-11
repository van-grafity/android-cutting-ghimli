package com.app.ivans.ghimli.viewmodel;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.repository.UserRepository;


public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private LiveData<APIResponse> responseData;

    public UserViewModel() {
        Context context = new Activity();
        userRepository = new UserRepository(context);
    }

    public LiveData<APIResponse> getUsersLiveData(String auth) {
        responseData = userRepository.getUsersResponse(auth);
        return responseData;
    }

    public LiveData<APIResponse> userSignUpLiveData(String name, String email, String password, String confirmPassword) {
        return responseData = userRepository.userSignUpResponse(name, email, password, confirmPassword);
    }

    public LiveData<APIResponse> getUserLoginResponseLiveData(String email, String password) {
        return responseData = userRepository.getUserLoginResponse(email, password);
    }
}

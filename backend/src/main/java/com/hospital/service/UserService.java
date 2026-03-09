package com.hospital.service;

import com.hospital.dto.ChangePasswordRequest;
import com.hospital.dto.UserResponse;
import com.hospital.dto.UserUpdateRequest;

public interface UserService {

    // GET PROFILE
    UserResponse getCurrentUser();

    // UPDATE PROFILE
    UserResponse updateCurrentUser(UserUpdateRequest request);

    // CHANGE PASSWORD
    void changePassword(ChangePasswordRequest request);
}
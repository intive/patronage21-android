package com.intive.users.edit_user

import androidx.lifecycle.ViewModel

data class UserDataCredentials(
    var gender: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var github: String,
    var bio: String
)

class EditUserViewModel : ViewModel(){



}
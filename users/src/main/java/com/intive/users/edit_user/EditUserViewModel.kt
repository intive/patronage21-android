package com.intive.users.edit_user

import com.intive.users.Gender

data class UserDataCredentials(
    var gender: Gender,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var github: String,
    var bio: String
)

class EditUserViewModel {



}
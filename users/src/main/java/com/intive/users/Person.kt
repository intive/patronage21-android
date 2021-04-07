package com.intive.users

enum class Gender {
    MALE, FEMALE, DIFFERENT
}

data class Person(
    var gender: Gender,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var github: String,
    var bio: String
    )
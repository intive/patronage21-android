package com.intive.repository.network.util

import com.intive.repository.domain.model.User
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.UserDto

class   UserDtoMapper : DomainMapper<UserDto, User> {
    override fun mapToDomainModel(model: UserDto): User {
        return User(
            firstName = model.firstName,
            lastName = model.lastName,
            login = model.userName,
            gender = "Mężczyzna",
            email = model.email,
            phoneNumber = model.phoneNumber,
            github = model.gitHubUrl,
            bio = "User has not set a bio",
            role = model.role
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDto {
        return UserDto(
            firstName = domainModel.firstName,
            lastName = domainModel.lastName,
            userName = domainModel.login,
            email = domainModel.email,
            phoneNumber = domainModel.phoneNumber,
            gitHubUrl = domainModel.github,
            status = "ACTIVE",
            role = domainModel.role
        )
    }

    fun mapToDomainList(model: List<UserDto>): List<User> {
        return model.map { user ->
            mapToDomainModel(user)
        }
    }

    fun mapFromDomainList(model: List<User>): List<UserDto> {
        return model.map { user ->
            mapFromDomainModel(user)
        }
    }
}
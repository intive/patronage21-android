package com.intive.repository.network.util

import com.intive.repository.domain.model.User
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.UserDto

class UserDtoMapper : DomainMapper<UserDto, User> {
    override fun mapToDomainModel(model: UserDto): User {
        return User(
            firstName = model.firstName,
            lastName = model.lastName,
            login = model.login,
            projects = model.projects,
            image = model.image,
            gender = "Mężczyzna",
            email = model.email,
            phoneNumber = model.phoneNumber,
            github = model.gitHubUrl,
            bio = model.bio,
            role = "LEADER"
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDto {
        return UserDto(
            firstName = domainModel.firstName,
            lastName = domainModel.lastName,
            login = domainModel.login,
            email = domainModel.email,
            image = domainModel.image,
            projects = domainModel.projects,
            phoneNumber = domainModel.phoneNumber,
            gitHubUrl = domainModel.github,
            status = "ACTIVE",
            bio = domainModel.bio,
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
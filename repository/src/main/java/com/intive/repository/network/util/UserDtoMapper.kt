package com.intive.repository.network.util

import com.intive.repository.domain.model.User
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.UserDto

class UserDtoMapper : DomainMapper<UserDto, User> {
    override fun mapToDomainModel(model: UserDto): User {
        return User(
            firstName = model.firstName,
            lastName = model.lastName,
            gender = "Helikopter bojowy",
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
            email = domainModel.email,
            phoneNumber = domainModel.phoneNumber,
            gitHubUrl = domainModel.github,
            userName = "User has not set a bio",
            status = "Alive I guess",
            role = domainModel.role
        )
    }
}
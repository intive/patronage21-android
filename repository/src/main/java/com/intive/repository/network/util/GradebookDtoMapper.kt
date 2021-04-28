package com.intive.repository.network.util

import com.intive.repository.domain.model.Gradebook
import com.intive.repository.domain.model.User
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.GradebookDto
import com.intive.repository.network.model.UserDto

class GradebookDtoMapper : DomainMapper<GradebookDto, Gradebook> {

    override fun mapToDomainModel(model: GradebookDto): Gradebook {
        return Gradebook(
                firstName = model.firstName,
                lastName = model.lastName,
                userName = model.userName,
                group = model.group,
                gradeNames = model.gradeNames,
                grades = model.grades,
                gradeReviews = model.gradeReviews,
                averageGrade= model.averageGrade
        )
    }

    override fun mapFromDomainModel(domainModel: Gradebook): GradebookDto {
        return GradebookDto(
            firstName = domainModel.firstName,
            lastName = domainModel.lastName,
            userName = domainModel.userName,
            group = domainModel.group,
            gradeNames = domainModel.gradeNames,
            grades = domainModel.grades,
            gradeReviews = domainModel.gradeReviews,
            averageGrade= domainModel.averageGrade
        )
    }

    fun mapToDomainList(model: List<GradebookDto>): List<Gradebook> {
        return model.map { book ->
            mapToDomainModel(book)
        }
    }

    fun mapFromDomainList(model: List<Gradebook>): List<GradebookDto> {
        return model.map { book ->
            mapFromDomainModel(book)
        }
    }
}
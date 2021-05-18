package com.intive.repository.network.util

import com.intive.repository.domain.model.StageDetails
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.StageDetailsDto

class StageDetailsDtoMapper : DomainMapper<StageDetailsDto, StageDetails> {
    override fun mapToDomainModel(model: StageDetailsDto): StageDetails {
        return StageDetails(
            id = model.id,
            name = model.name,
            description = model.description,
            events = model.events,
            isStageCompleted = model.isStageCompleted,
            completionLevel = model.completionLevel,
            areMaterialsAvailable = model.areMaterialsAvailable
        )
    }

    override fun mapFromDomainModel(domainModel: StageDetails): StageDetailsDto {
        return StageDetailsDto(
            id = domainModel.id!!,
            name = domainModel.name!!,
            description = domainModel.description!!,
            events = domainModel.events,
            isStageCompleted = domainModel.isStageCompleted!!,
            completionLevel = domainModel.completionLevel!!,
            areMaterialsAvailable = domainModel.areMaterialsAvailable!!
        )
    }
}
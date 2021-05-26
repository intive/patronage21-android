package com.intive.repository.network.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.intive.repository.domain.model.Stage
import com.intive.repository.domain.util.DomainMapper
import com.intive.repository.network.model.StageDto

class StageDtoMapper : DomainMapper<StageDto, Stage> {

    override fun mapToDomainModel(model: StageDto): Stage {
        return Stage(
            id = model.id,
            name = model.name,
            dateBegin = model.dateBegin,
            dateEnd = model.dateEnd,
            state = model.state
        )
    }

    override fun mapFromDomainModel(domainModel: Stage): StageDto {
        return StageDto(
            id = domainModel.id,
            name = domainModel.name,
            dateBegin = domainModel.dateBegin,
            dateEnd = domainModel.dateEnd,
            state = domainModel.state
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toDomainList(initial: List<StageDto>): List<Stage>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Stage>): List<StageDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}
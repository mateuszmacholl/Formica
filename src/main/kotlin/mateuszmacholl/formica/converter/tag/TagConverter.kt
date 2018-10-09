package mateuszmacholl.formica.converter.tag

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.tag.CreateTagDto
import mateuszmacholl.formica.model.tag.Tag
import org.springframework.stereotype.Service

@Service
class TagConverter: DtoConverter<CreateTagDto>() {
    override fun toEntity(createTagDto: CreateTagDto) = Tag (
        name = createTagDto.name
    )
}
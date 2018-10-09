package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.tag.TagConverter
import mateuszmacholl.formica.dto.tag.CreateTagDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TagConverterTest {
    private lateinit var tagConverter: TagConverter
    private val name = "c++"
    private val createTagDto = CreateTagDto(name = name)

    @BeforeEach
    fun init() {
        tagConverter = TagConverter()
    }

    @Test
    fun toEntity_returnCorrectPost() {
        //when
        val post = tagConverter.toEntity(createTagDto)
        //then
        Assertions.assertTrue(post.name === name)
    }
}
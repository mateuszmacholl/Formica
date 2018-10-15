package mateuszmacholl.formica.converter.post

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.post.CreatePostDto
import mateuszmacholl.formica.dto.post.UpdateTagsPostDto
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.model.tag.Tag
import mateuszmacholl.formica.service.tag.TagService
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class PostConverter(val userService: UserService, val tagService: TagService) : DtoConverter<CreatePostDto>() {

    override fun toEntity(createPostDto: CreatePostDto): Post {
        val user = userService.findByUsername(createPostDto.author) ?: throw IllegalArgumentException()
        return Post(
                title = createPostDto.title,
                content = createPostDto.content,
                author = user
        )
    }

    fun toEntity(updateTagsPostDto: UpdateTagsPostDto): Set<Tag> {
        val tagsEntities = HashSet<Tag>()
        for (tag in updateTagsPostDto.tags){
            val tagEntity  = tagService.findByName(tag)
            if(tagEntity != null){
                tagsEntities.add(tagEntity)
            }
        }
        return tagsEntities // I don't know how to return set without nullable values with stream
    }
}
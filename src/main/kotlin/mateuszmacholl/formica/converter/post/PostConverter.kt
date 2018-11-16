package mateuszmacholl.formica.converter.post

import mateuszmacholl.formica.converter.DtoConverter
import mateuszmacholl.formica.dto.post.CreatePostDto
import mateuszmacholl.formica.dto.post.UpdateTagsPostDto
import mateuszmacholl.formica.model.post.Post
import mateuszmacholl.formica.model.tag.Tag
import mateuszmacholl.formica.service.channel.ChannelService
import mateuszmacholl.formica.service.tag.TagService
import mateuszmacholl.formica.service.user.UserService
import org.springframework.stereotype.Service

@Service
class PostConverter(val userService: UserService,
                    val tagService: TagService,
                    val channelService: ChannelService) : DtoConverter<CreatePostDto>() {

    override fun toEntity(from: CreatePostDto): Post {
        val user = userService.findByUsername(from.author) ?: throw IllegalArgumentException()
        val post = Post(
                title = from.title,
                content = from.content,
                author = user,
                coordinates = from.coordinates
        )
        if (from.channel != null) {
            post.channel = channelService.findById(from.channel).get()
        } else if (from.coordinates != null) {
            post.coordinates = from.coordinates
        }
        return post
    }

    fun toEntity(updateTagsPostDto: UpdateTagsPostDto): Set<Tag> {
        val tagsEntities = HashSet<Tag>()
        for (tag in updateTagsPostDto.tags) {
            val tagEntity = tagService.findByName(tag)
            if (tagEntity != null) {
                tagsEntities.add(tagEntity)
            }
        }
        return tagsEntities // I don't know how to return set without nullable values using stream
    }
}
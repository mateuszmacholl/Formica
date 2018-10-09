package mateuszmacholl.formica.unitTest.converter

import mateuszmacholl.formica.converter.post.PostConverter
import mateuszmacholl.formica.dto.post.CreatePostDto
import mateuszmacholl.formica.model.user.User
import mateuszmacholl.formica.service.user.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito

class PostConverterTest {
    private lateinit var userService: UserService
    private lateinit var postConverter: PostConverter
    private val title = "title"
    private val content = "content"
    private val author = "author"
    private val createPostDto = CreatePostDto(title = title, content = content, author = author)
    private val user = User(username = author)

    @BeforeEach
    fun init() {
        userService = Mockito.mock(UserService::class.java)
        postConverter = PostConverter()
        postConverter.userService = userService
        Mockito.`when`(postConverter.userService.findByUsername(author)).thenReturn(user)
    }

    @Test
    fun toEntity_returnCorrectPost() {
        //when
        val post = postConverter.toEntity(createPostDto)
        //then
        Assertions.assertTrue(post.author!!.username === user.username)
        Assertions.assertTrue(post.title === title)
        Assertions.assertTrue(post.content === content)
    }

    @Test
    fun toEntity_throwIllegalArgumentException(){
        //given
        val wrongCreatePostDto = createPostDto.copy(author = "wrongUsername")
        //when
        assertThrows<IllegalArgumentException> { postConverter.toEntity(wrongCreatePostDto) }
    }
}
package mateuszmacholl.formica.unitTest.beans

import mateuszmacholl.formica.beans.bCryptPasswordEncoder.MyBCryptPasswordEncoder
import org.junit.Assert.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MyBCryptPasswordEncoderTest {

    private var myBCryptPasswordEncoder: MyBCryptPasswordEncoder? = null

    @BeforeEach
    fun init() {
        myBCryptPasswordEncoder = MyBCryptPasswordEncoder()
    }

    @Test
    fun modelMapper_getModelMapper() {
        val passwordEncoder = myBCryptPasswordEncoder!!.passwordEncoder()
        assertNotNull(passwordEncoder)
    }
}

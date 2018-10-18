package mateuszmacholl.formica.config.exception

import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException




@ControllerAdvice
class CustomRestExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val apiError = createValidationError(ex)
        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleBindException(ex: BindException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val apiError = createValidationError(ex)
        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    private fun createValidationError(ex: Exception): ApiError {
        val exception = ex as MethodArgumentNotValidException

        val errors = ArrayList<String>()
        for (error in exception.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }
        for (error in exception.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }
        return ApiError(HttpStatus.BAD_REQUEST, "", errors)
    }

    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = ex.value.toString() + " value for " + ex.propertyName + " should be of type " + ex.requiredType

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleMissingServletRequestPart(ex: MissingServletRequestPartException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = ex.requestPartName + " part is missing"
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = ex.parameterName + " parameter is missing"
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException, request: WebRequest): ResponseEntity<Any> {
        val error = ex.name + " should be of type " + ex.requiredType!!.name

        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException, request: WebRequest): ResponseEntity<Any> {
        val errors = ArrayList<String>()
        for (violation in ex.constraintViolations) {
            errors.add(violation.message)
        }

        val apiError = ApiError(HttpStatus.BAD_REQUEST, "", errors)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }
    
    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = "No handler found for " + ex.httpMethod + " " + ex.requestURL

        val apiError = ApiError(HttpStatus.NOT_FOUND, ex.localizedMessage, error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleHttpRequestMethodNotSupported(ex: HttpRequestMethodNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val builder = StringBuilder()
        builder.append(ex.method)
        builder.append(" method is not supported for this request. Supported methods are ")
        ex.supportedHttpMethods!!.forEach { t -> builder.append(t.toString() + " ") }

        val apiError = ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.localizedMessage, builder.toString())
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleHttpMediaTypeNotSupported(ex: HttpMediaTypeNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val builder = StringBuilder()
        builder.append(ex.contentType)
        builder.append(" media type is not supported. Supported media types are ")
        ex.supportedMediaTypes.forEach { t -> builder.append(t.toString() + " ") }

        val apiError = ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.localizedMessage, builder.substring(0, builder.length - 2))
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = "Http message is not readable. One of the causes can be empty request body or one of fields"

        val apiError = ApiError(HttpStatus.BAD_REQUEST, "", error)
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @Throws(IOException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException, response: HttpServletResponse): ResponseEntity<Any> {
        return ResponseEntity(HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val message = "Access denied"

        val apiError = ApiError(HttpStatus.UNAUTHORIZED, message, "")
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        logger.info(ex.javaClass.name)
        logger.error("error", ex)

        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.localizedMessage, "error occurred")
        if(ex.cause is  IllegalArgumentException) {
            val badRequestApiError = apiError.copy(status = HttpStatus.BAD_REQUEST)
            return ResponseEntity(badRequestApiError, HttpHeaders(), badRequestApiError.status)
        }
        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

}

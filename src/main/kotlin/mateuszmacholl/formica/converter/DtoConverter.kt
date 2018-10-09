package mateuszmacholl.formica.converter

abstract class DtoConverter<T> {
    abstract fun toEntity(t: T): Any
}
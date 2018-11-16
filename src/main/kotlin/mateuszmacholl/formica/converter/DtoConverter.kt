package mateuszmacholl.formica.converter

abstract class DtoConverter<from> {
    abstract fun toEntity(from: from): Any
}
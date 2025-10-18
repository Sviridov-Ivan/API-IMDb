package com.example.apiimdb.data.converter

import com.example.apiimdb.data.dto.ActorResponse
import com.example.apiimdb.data.dto.CastItemResponse
import com.example.apiimdb.data.dto.DirectorsResponse
import com.example.apiimdb.data.dto.MovieCastResponse
import com.example.apiimdb.data.dto.OtherResponse
import com.example.apiimdb.data.dto.WritersResponse
import com.example.apiimdb.domain.models.MovieCast
import com.example.apiimdb.domain.models.MovieCastPerson

class MovieCastConverter { // для конвертации

    fun convert(response: MovieCastResponse): MovieCast {
        return with(response) {
            MovieCast(
                imdbId = this.imDbId,
                fullTitle = this.fullTitle,
                directors = convertDirectors(this.directors),
                others = convertOthers(this.others),
                writers = convertWriters(this.writers),
                actors = convertActors(this.actors)
            )
        }
    }

    private fun convertDirectors(directorsResponse: DirectorsResponse): List<MovieCastPerson> {
        return directorsResponse.items.map { it.toMovieCastPerson() }
    }

    private fun convertOthers(othersResponses: List<OtherResponse>): List<MovieCastPerson> {
        return othersResponses.flatMap { otherResponse ->
            otherResponse.items.map { it.toMovieCastPerson(jobPrefix = otherResponse.job) }
        }
    }

    private fun convertWriters(writersResponse: WritersResponse): List<MovieCastPerson> {
        return writersResponse.items.map { it.toMovieCastPerson() }
    }

    private fun convertActors(actorsResponses: List<ActorResponse>): List<MovieCastPerson> {
        return actorsResponses.map { actor ->
            MovieCastPerson(
                id = actor.id,
                name = actor.name,
                description = actor.asCharacter,
                image = actor.image,
            )
        }
    }

    private fun CastItemResponse.toMovieCastPerson(jobPrefix: String = ""): MovieCastPerson {
        return MovieCastPerson(
            id = this.id,
            name = this.name,
            description = if (jobPrefix.isEmpty()) this.description else "$jobPrefix -- ${this.description}",
            image = null,
        )
    }
}

// более емкое решение конвертации, поэтому реализовали верхнее решение
/*class MovieCastConverter {

    fun convert(response: MovieCastResponse): MovieCast {
        return with(response) {
            MovieCast(
                imdbId = this.imDbId,
                fullTitle = this.fullTitle,
                directors = this.directors.items.map { director ->
                    MovieCastPerson(
                        id = director.id,
                        name = director.name,
                        description = director.description,
                        image = null,
                    )
                },
                others = this.others.flatMap { othersResponse ->
                    othersResponse.items.map { person ->
                        MovieCastPerson(
                            id = person.id,
                            name = person.name,
                            description = "${othersResponse.job} -- ${person.description}",
                            image = null,
                        )
                    }
                },
                writers = this.writers.items.map { writer ->
                    MovieCastPerson(
                        id = writer.id,
                        name = writer.name,
                        description = writer.description,
                        image = null,
                    )
                },
                actors = this.actors.map { actor ->
                    MovieCastPerson(
                        id = actor.id,
                        name = actor.name,
                        description = actor.asCharacter,
                        image = actor.image,
                    )
                }
            )
        }
    }

}*/

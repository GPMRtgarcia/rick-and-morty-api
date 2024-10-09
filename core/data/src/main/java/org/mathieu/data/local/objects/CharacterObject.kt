package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mathieu.data.remote.responses.CharacterResponse
import org.mathieu.data.repositories.tryOrNull
import org.mathieu.domain.models.character.*
import org.mathieu.domain.models.location.LocationPreview

/**
 * Represents a character entity stored in the SQLite database. This object provides fields
 * necessary to represent all the attributes of a character from the data source.
 * The object is specifically tailored for SQLite storage using Realm.
 *
 * @property id Unique identifier of the character.
 * @property name Name of the character.
 * @property status Current status of the character (e.g. 'Alive', 'Dead').
 * @property species Biological species of the character.
 * @property type The type or subspecies of the character.
 * @property gender Gender of the character (e.g. 'Female', 'Male').
 * @property originName The origin location name.
 * @property originId The origin location id.
 * @property location The current or last known location of the character, represented as a LocationPreview.
 * @property image URL pointing to the character's avatar image.
 * @property created Timestamp indicating when the character entity was created in the database.
 */
internal class CharacterObject: RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var status: String = ""
    var species: String = ""
    var type: String = ""
    var gender: String = ""
    var originName: String = ""
    var originId: Int = -1
    @Ignore
    var location: LocationPreview? = null
    var image: String = ""
    var created: String = ""
}


internal fun CharacterResponse.toRealmObject() = CharacterObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.status = status
    obj.species = species
    obj.type = type
    obj.gender = gender
    obj.originName = origin.name
    obj.originId = tryOrNull { origin.url.split("/").last().toInt() } ?: -1
    obj.location = LocationPreview(
        name = location.name,
        id = tryOrNull { location.url.split("/").last().toInt() } ?: -1,
        type = "",
        dimension = ""
    )
    obj.image = image
    obj.created = created
}

internal fun CharacterObject.toModel() = Character(
    id = id,
    name = name,
    status = tryOrNull { CharacterStatus.valueOf(status) } ?: CharacterStatus.Unknown,
    species = species,
    type = type,
    gender = tryOrNull { CharacterGender.valueOf(gender) } ?: CharacterGender.Unknown,
    origin = originName to originId,
    location = LocationPreview(
        name = location?.name ?: "",
        id = location?.id ?: -1,
        type = location?.type ?: "",
        dimension = location?.dimension ?: ""
    ),
    avatarUrl = image
)

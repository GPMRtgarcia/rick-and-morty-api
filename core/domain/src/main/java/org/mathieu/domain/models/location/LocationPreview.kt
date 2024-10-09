package org.mathieu.domain.models.location

/**
 *
 * DAO of Location entity
 *
 *@property id The unique identifier for the location.
 *@property name The name of the location.
 *@property type The type or category of the location.
 *@property dimension The specific dimension or universe where this location exists.
 */

data class LocationPreview(
    var id: Int,
    var name: String,
    var type: String?,
    var dimension: String?
)
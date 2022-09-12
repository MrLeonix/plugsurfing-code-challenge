package code.challenge.musify.shared.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class Gender(@JsonValue val text: String) {
    MALE("Male"),
    FEMALE("Female")
}

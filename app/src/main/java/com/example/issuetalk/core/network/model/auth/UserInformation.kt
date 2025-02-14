data class UserInformationRequest(
    val name: String,
    val gender: String,
    val birthYear: Int
)

data class UserInformationResponse(
    val name: String = "",
    val gender: String = "",
    val birthYear: Int = 0
)

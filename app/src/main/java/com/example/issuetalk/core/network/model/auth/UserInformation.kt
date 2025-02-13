data class UserInformationRequest(
    val userId: String,
    val name: String,
    val gender: String,
    val birthYear: Int
)

data class UserInformationResponse(
    val userId: String = "",
    val name: String = "",
    val gender: String = "",
    val birthYear: Int = 0
)

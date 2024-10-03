package models


data class OfferResponse(
    val offers: List<Offer>
)
data class Offer(
    val id: String,
    val title: String,
    val buttontext: String,
    val link: String
)

import com.google.gson.annotations.SerializedName

data class CurrenciesAPI (@SerializedName("Date") val date: String? = null,
                          @SerializedName("PreviousDate") val previousDate: String? = null,
                          @SerializedName("PreviousURL") val previousURL: String? = null,
                          @SerializedName("Timestamp") val timestamp: String? = null,
                          @SerializedName("Valute") val currency: Map<String,CurrencyPropertiesAPI>? = null)


data class CurrencyPropertiesAPI (@SerializedName("ID") val id: String? = null,
                                  @SerializedName("NumCode") val numCode: String? = null,
                                  @SerializedName("CharCode") val charCode: String? = null,
                                  @SerializedName("Nominal") val nominal: String? = null,
                                  @SerializedName("Name") val name: String? = null,
                                  @SerializedName("Value") val value: String? = null,
                                  @SerializedName("Previous") val previous: String? = null)

data class CurrenciesHomeWork (@SerializedName("squadName") val squadName: String? = "Exchange rate",
                               @SerializedName("Date") var date: String? = null,
                               @SerializedName("Country") val country: String? = "Russian",
                               @SerializedName("Currency list") val currency_list: MutableList<CurrencyProperties> = mutableListOf(CurrencyProperties()))

data class CurrencyProperties (@SerializedName("CharCode") var charCode: String? = null,
                               @SerializedName("Name") var name: String? = null,
                               @SerializedName("Value") var value: String? = null)

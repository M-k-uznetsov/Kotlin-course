import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import java.io.File
import java.net.URL

fun main () {
    /**
     * Заполение data class значениями из принятого с API центрального банка JSON-файла
     */
    val jsonFromCenterBank = Gson().fromJson(URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(), CurrencyAPI::class.java)
    /**
     * Заполение data class значениями из json-файла DZ1.json
    */
    val jsonFileString = File("DZ1.json").bufferedReader().use {
        it.readText()
    }
    val jsonFromHomeWork = Gson().fromJson(jsonFileString,CurrencyHomeWork::class.java)
    /**
     * Обновение даты и её перевод в формат ГГГГ/ММ/ДД в исходном json-файле
     */
    jsonFromHomeWork.date = jsonFromCenterBank.date?.substringBefore('T')?.replace("-","/")
    /**
     * Заполнение свойств валют
     */
    //Перебор списка валют в файле домашнего задания
     jsonFromHomeWork.currency_list.forEach {itHW->
        //Пребор списка валют в полученном файле
        for ((currencyCB, currencyCBProperties) in jsonFromCenterBank.currency?.entries ?: mapOf(null to null).entries) {
            //Нахождение нужных валют и заполение их свойств
            if (currencyCBProperties?.charCode == itHW.charCode) {
                itHW.name  = currencyCBProperties?.name
                itHW.value = currencyCBProperties?.value
            }
        }
    }
    /**
     * Заполнение файла DZ1.json обновлёнными данными
     */
    val jsonFile = GsonBuilder().setPrettyPrinting().create().toJson(jsonFromHomeWork)
    File("DZ1.json").bufferedWriter().use { out -> out.write(jsonFile) }
}


/**
 * используемые дата классы
 */
data class CurrencyAPI (@SerializedName("Date") val date: String? = null,
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

data class CurrencyHomeWork (@SerializedName("squadName") val squadName: String? = "Exchange rate",
                             @SerializedName("Date") var date: String? = null,
                             @SerializedName("Country") val country: String? = "Russian",
                             @SerializedName("Currency list") val currency_list: MutableList<CurrencyProperties> = mutableListOf(CurrencyProperties()))

data class CurrencyProperties (@SerializedName("CharCode") var charCode: String? = null,
                               @SerializedName("Name") var name: String? = null,
                               @SerializedName("Value") var value: String? = null)
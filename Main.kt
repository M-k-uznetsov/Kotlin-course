import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.File
import java.net.URL

suspend fun main(args: Array<String>)= coroutineScope  {
    val cor = launch(Dispatchers.Default){
        val jsonFromCenterBank = Gson().fromJson(URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(), CurrenciesAPI::class.java)
        val res: List<Map.Entry<String, CurrencyPropertiesAPI>>? = jsonFromCenterBank.currency?.entries
        ?.filter { (key,value)->value.value?.toFloat()!!>value.previous?.toFloat()!! }
        ?.sortedBy{ it.value.value?.toFloat()!!-it.value.previous?.toFloat()!!}


        withContext(Dispatchers.Main) {
            println("валюты, которые повысились в стоимости и их повышения:")
            var averagePercentage = 0F
            res?.forEach() {
                println("${it.key}  ${(it.value.value?.toFloat()!! - it.value?.previous?.toFloat()!!) / 100}")
                averagePercentage += (it.value.value?.toFloat()!! - it.value.previous?.toFloat()!!) / 100
                averagePercentage /= res.size
            }
            println("валюта с наибольшим процентом: ${res?.get(res.size - 1)?.key}")
            println("валюта с наименьшим процентом: ${res?.get(0)?.key}")
            println("средний процент повышения: $averagePercentage")
        }
    }
}


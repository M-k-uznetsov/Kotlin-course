import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File
import java.net.URL


suspend fun main(args: Array<String>)= coroutineScope  {

    val cor = launch(Dispatchers.Default){

        val jsonFromCenterBank = Gson().fromJson(URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(), CurrenciesAPI::class.java)
        if (jsonFromCenterBank.currency == null) return@launch
        val res= jsonFromCenterBank.currency.entries
            .filter { (key,value)->value.value?.toFloat()!!>value.previous?.toFloat()!! }
            .sortedBy{ it.value.value?.toFloat()!!-it.value.previous?.toFloat()!!}
        var averagePercentage =jsonFromCenterBank.currency.entries.sumOf { (it.value.value?.toDouble()!! - it.value.previous?.toDouble()!!) / 100 }
        averagePercentage/=jsonFromCenterBank.currency.entries.size
        val myFlow = flow {
            res.forEach() {
                emit(it.key)
                emit(it.value.value?.toFloat()!! - it.value.previous?.toFloat()!!)
            }
        }
        withContext(Dispatchers.Main) {
            var i=1
            println("валюты, которые повысились в стоимости и их повышения:")
            myFlow.collect {element->
                print("$element     ")
                if(i%2==0)println()
                i+=1
            }
            println("валюта с наибольшим процентом: ${res.last().key}")
            println("валюта с наименьшим процентом: ${res.first().key}")
            println("средний процент повышения: $averagePercentage")
        }
    }
}


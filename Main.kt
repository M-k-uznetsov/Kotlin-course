import com.google.gson.Gson
import java.io.File
import java.net.URL

fun main(args: Array<String>) {
    val jsonFromCenterBank = Gson().fromJson(URL("https://www.cbr-xml-daily.ru/daily_json.js").readText(), CurrenciesAPI::class.java)
    val jsonFileString = File("DZ2.json").bufferedReader().use {
        it.readText()
    }
    val jsonFromHomeWork = Gson().fromJson(jsonFileString,CurrenciesHomeWork::class.java)


   /** обработка данных через цикл
     var result = sortedMapOf<Float,String>()
     jsonFromHomeWork.currency_list.forEach {itHW->
        //Пребор списка валют
        for ((currencyCB, currencyCBProperties) in jsonFromCenterBank.currency?.entries ?: mapOf(null to null).entries) {
            if (currencyCBProperties?.charCode == itHW.charCode && currencyCBProperties?.value?.toFloat()!! > itHW.value?.toFloat()!!) {
                result[(currencyCBProperties?.value?.toFloat()!! - itHW.value?.toFloat()!!)/100] = currencyCBProperties?.charCode
                break
            }
        }
    }
   println("валюты, которые повысились в стоимости:${result.values}")
   println("их процент повышения:${result.keys}")
   println("валюта с наибольшим процентом: ${result.lastKey()}")
   println("валюта с наименьшим процентом: ${result.firstKey()}")
   println("средний процент повышения: ${result.keys.average()}")
    **/


    val res: List<Map.Entry<String, CurrencyPropertiesAPI>>? = jsonFromCenterBank.currency?.entries
       ?.filter { (key,value)->value?.value?.toFloat()!!>value?.previous?.toFloat()!! }
       ?.sortedBy{it.value?.value?.toFloat()!!-it.value?.previous?.toFloat()!!}
    println("валюты, которые повысились в стоимости и их повышения:")
    var averagePercentage= 0F
    res?.forEach{
        println( "${it.key}  ${(it.value?.value?.toFloat()!!-it.value?.previous?.toFloat()!!)/100}")
        averagePercentage+=(it.value?.value?.toFloat()!!-it.value?.previous?.toFloat()!!)/100
    }
    averagePercentage /= res?.size!!
    println("валюта с наибольшим процентом: ${res?.get(res.size-1)?.key}" )
    println("валюта с наименьшим процентом: ${res?.get(0)?.key}")
    println("средний процент повышения: $averagePercentage")
}

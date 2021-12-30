import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CurrenciesAPI (@SerializedName("Date") val date: String? = null,
                          @SerializedName("PreviousDate") val previousDate: String? = null,
                          @SerializedName("PreviousURL") val previousURL: String? = null,
                          @SerializedName("Timestamp") val timestamp: String? = null,
                          @SerializedName("Valute") val currency: Map<String,CurrencyPropertiesAPI>? = null) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeString(previousDate)
        parcel.writeString(previousURL)
        parcel.writeString(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrenciesAPI> {
        override fun createFromParcel(parcel: Parcel): CurrenciesAPI {
            return CurrenciesAPI(parcel)
        }

        override fun newArray(size: Int): Array<CurrenciesAPI?> {
            return arrayOfNulls(size)
        }
    }
}


data class CurrencyPropertiesAPI (@SerializedName("ID") val id: String? = null,
                                  @SerializedName("NumCode") val numCode: String? = null,
                                  @SerializedName("CharCode") val charCode: String? = null,
                                  @SerializedName("Nominal") val nominal: String? = null,
                                  @SerializedName("Name") val name: String? = null,
                                  @SerializedName("Value") val value: String? = null,
                                  @SerializedName("Previous") val previous: String? = null) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(numCode)
        parcel.writeString(charCode)
        parcel.writeString(nominal)
        parcel.writeString(name)
        parcel.writeString(value)
        parcel.writeString(previous)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrencyPropertiesAPI> {
        override fun createFromParcel(parcel: Parcel): CurrencyPropertiesAPI {
            return CurrencyPropertiesAPI(parcel)
        }

        override fun newArray(size: Int): Array<CurrencyPropertiesAPI?> {
            return arrayOfNulls(size)
        }
    }
}

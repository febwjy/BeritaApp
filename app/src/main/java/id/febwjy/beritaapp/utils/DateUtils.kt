package id.febwjy.beritaapp.utils

import android.util.Log
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
object DateUtils {
    var calendar: Calendar? = null

    var monthNameFormat: DateFormat? = null
    var monthShortNameFormat: DateFormat? = null
    var weekdayNameFormat: DateFormat? = null
    var weekdayShortNameFormat: DateFormat? = null

    fun checkDate(date: Date?): Date {
        return date ?: Date()
    }

    fun getStringDate(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        return simpleDateFormat.format(date)
    }

    fun getStringDate(format: String, date: Date): String {
        val simpleDateFormat = SimpleDateFormat(format)

        return simpleDateFormat.format(date)
    }

    fun getStringDate2(format: String, date: Long): String {
        val simpleDateFormat = SimpleDateFormat(format)

        return simpleDateFormat.format(longToDate2(date))
    }

    fun longToDate2(`val`: Long): Date {
        return Date(`val`)
    }

    var months = arrayOf("Januari", "Februari", "March", "April", "Mei", "Juni", "Juli", "Augustus",
        "September", "Oktober", "November", "Desember", "")


    fun getStringDate(strDate: String): String {
        var result = ""

        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            val date = simpleDateFormat.parse(strDate)

            result = weekdayNameFormat()!!.format(date) + ", " + getStringDate("dd", date) + " " +
                    monthNameFormat()!!.format(date)
        } catch (e: Exception) {
            Log.e("catch", e.message!!)
        }

        return result
    }

    fun getStringDate(strDate: String, format: String): String {
        var result = ""

        try {
            val simpleDateFormat = SimpleDateFormat(format)

            val date = simpleDateFormat.parse(strDate)

            result = weekdayNameFormat()!!.format(date) + ", " + getStringDate("dd", date) + " " +
                    monthNameFormat()!!.format(date) + " " + getStringDate("HH:mm:ss", date)
        } catch (e: Exception) {
            Log.e("catch", e.message!!)
        }

        return result
    }

    fun getDate(strDate: String, format: String): Date? {
        var result: Date? = null

        try {
            val simpleDateFormat = SimpleDateFormat(format)

            val date = simpleDateFormat.parse(strDate)

            result = date
        } catch (e: Exception) {
            Log.e("catch", e.message!!)
        }

        return result
    }

    fun getStringDate(strDate: String, format: String, monthly_long: Boolean): String {
        var result = ""

        try {
            val simpleDateFormat = SimpleDateFormat(format)

            val date = simpleDateFormat.parse(strDate)

            if (monthly_long)
                result = getStringDate("dd", date) + " " + monthNameFormat()!!.format(date)
            else
                result = getStringDate("dd", date) + " " + monthShortNameFormat()!!.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun getDateFormatCard(date: Date): String {
        return weekdayNameFormat()!!.format(date) + ", " + getStringDate("dd", date) + " " +
                monthNameFormat()!!.format(date)
    }

    fun stringToDate(strDate: String): Date {
        var date = Date()
        val format = SimpleDateFormat("yyyy-MM-dd")

        try {
            date = format.parse(strDate)
        } catch (e: Exception) {
            Log.e("catch", e.message!!)
        }

        return date
    }

    fun monthNameFormat(): DateFormat? {
        return monthNameFormat
    }

    fun monthShortNameFormat(): DateFormat? {
        return monthShortNameFormat
    }

    fun weekdayNameFormat(): DateFormat? {
        return weekdayNameFormat
    }

    fun weekdayShortNameFormat(): DateFormat? {
        return weekdayShortNameFormat
    }

    @JvmStatic
    fun setMonthsName(newMonths: Array<String>) {
        val symbols = DateFormatSymbols(Locale.getDefault())
        symbols.months = newMonths
        monthNameFormat = SimpleDateFormat("MMMM", symbols)
    }

    @JvmStatic
    fun setShortMonthsName(newShortMonths: Array<String>) {
        val symbols = DateFormatSymbols(Locale.getDefault())
        symbols.shortMonths = newShortMonths
        monthShortNameFormat = SimpleDateFormat("MMM", symbols)
    }

    @JvmStatic
    fun setWeekdaysName(newWeekdays: Array<String>) {
        val symbols = DateFormatSymbols(Locale.getDefault())
        symbols.weekdays = newWeekdays
        weekdayNameFormat = SimpleDateFormat("EEEE", symbols)
    }

    @JvmStatic
    fun setShortWeekdaysName(newShortWeekdays: Array<String>) {
        val symbols = DateFormatSymbols(Locale.getDefault())
        symbols.shortWeekdays = newShortWeekdays
        weekdayShortNameFormat = SimpleDateFormat("EEE", symbols)
    }

    fun longToDate(`val`: Long): Date {
        return Date(`val` * 1000)
    }

    fun dateToLong(`val`: Date): Long {
        return `val`.time / 1000
    }

    fun today(): Date? {
        calendar = Calendar.getInstance()
        return calendar!!.time
    }

    fun tomorrow(): Date? {
        calendar = Calendar.getInstance()
        calendar!!.add(Calendar.DAY_OF_MONTH, 1)
        return calendar!!.time
    }

    fun nextYear(): Date {
        calendar = Calendar.getInstance()
        calendar!!.add(Calendar.YEAR, 1)
        return calendar!!.time
    }

    fun monthLabels(): Array<String> {

        return arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus",
            "September", "Oktober", "November", "Desember")
    }

    fun monthLabelsShort(): Array<String> {

        return arrayOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt",
            "Nov", "Des")
    }

    fun weekDayLabels(): Array<String> {

        return arrayOf("#", "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")
    }

    fun weekDayLabelsShort(): Array<String> {
        val titles = arrayOf("#", "Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")

        return titles
    }

    fun getFormattedDate(date: String): String {
        var format = SimpleDateFormat("yyyy-MM-dd")
        var newDate: Date? = null
        try {
            newDate = format.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        format = SimpleDateFormat("dd MMMM yyyy")

        return format.format(newDate)
    }
}

package be.mariovonbassen.citindi.ui.components

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        return formatter.format(date)
}

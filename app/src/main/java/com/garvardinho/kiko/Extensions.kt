package com.garvardinho.kiko

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView

fun TextView.setTextWithBoldTitle(title: String, text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml("<b>$title</b> $text",
            Html.FROM_HTML_MODE_COMPACT)
    }
}

fun AppCompatImageView.setFavoriteImage(isFavorite: Boolean) {
    if (isFavorite)
        this.background = AppCompatResources.getDrawable(this.context, R.drawable.ic_heart)
    else
        this.background = AppCompatResources.getDrawable(this.context, R.drawable.ic_heart_outline)
}
package com.garvardinho.kiko

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.openFragment(fragment: Fragment) {
    this.beginTransaction()
        .replace(R.id.main_fragment, fragment)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        .addToBackStack(null)
        .commit()
}

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
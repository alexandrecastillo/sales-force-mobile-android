package biz.belcorp.salesforce.base.utils

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.ui.R
import biz.belcorp.salesforce.core.utils.getCompatDrawable
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference


fun BottomNavigationView.removeExtraPadding() {
    val menuView = getChildAt(0) as? BottomNavigationMenuView ?: return
    for (i in 0 until menuView.childCount) {
        val item = menuView.getChildAt(i)
        val activeLabel = item.findViewById<View>(R.id.largeLabel)
        if (activeLabel is TextView) {
            activeLabel.setPadding(0, 0, 0, 0)
        }
    }
}

fun BottomNavigationView.addMenuOption(id: Int, title: String, iconResId: Int) {
    menu.add(0, id, Menu.NONE, title).apply {
        icon = context?.getCompatDrawable(iconResId)
    }
}

fun BottomNavigationView.setupWithNavController(
    navController: NavController,
    matcher: HashMap<Int, Int>,
    onOptionSeleted: ((MenuItem) -> Boolean)? = null
) {
    CustomNavigationUI.setupSelectedListener(this, navController, matcher, onOptionSeleted)
    CustomNavigationUI.setupDestinationListener(this, navController, matcher)
}

fun BottomNavigationView.selectWithoutEvent(
    itemId: Int,
    navController: NavController,
    matcher: HashMap<Int, Int>,
    extraOptions: ((MenuItem) -> Boolean)? = null
) {
    CustomNavigationUI.selectWithoutEvent(this, itemId)
    CustomNavigationUI.setupSelectedListener(this, navController, matcher, extraOptions)
}

object CustomNavigationUI {

    fun selectWithoutEvent(bottomNavigationView: BottomNavigationView, itemId: Int) {
        bottomNavigationView.setOnNavigationItemReselectedListener(null)
        bottomNavigationView.setOnNavigationItemSelectedListener(null)
        bottomNavigationView.selectedItemId = itemId
    }

    fun setupSelectedListener(
        bottomNavigationView: BottomNavigationView,
        navController: NavController,
        matcher: HashMap<Int, Int>,
        onOptionSeleted: ((MenuItem) -> Boolean)? = null
    ) {
        bottomNavigationView.setOnNavigationItemReselectedListener { }
        bottomNavigationView.setOnNavigationItemSelectedListener {
            onNavDestinationSelected(it, navController, matcher, onOptionSeleted)
        }
    }

    fun setupDestinationListener(
        bottomNavigationView: BottomNavigationView,
        navController: NavController,
        matcher: HashMap<Int, Int>
    ) {
        val weakReference = WeakReference(bottomNavigationView)
        navController.addOnDestinationChangedListener(
            object : NavController.OnDestinationChangedListener {
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: Bundle?
                ) {
                    val view = weakReference.get()
                    if (view == null) {
                        navController.removeOnDestinationChangedListener(this)
                        return
                    }
                    val menu = view.menu
                    var h = 0
                    val size = menu.size()
                    while (h < size) {
                        val item = menu.getItem(h)
                        if (matchDestination(destination, matcher[item.itemId] ?: -1)) {
                            item.isChecked = true
                        }
                        h++
                    }
                }
            })
    }

    private fun matchDestination(destination: NavDestination, @IdRes destId: Int): Boolean {
        var currentDestination: NavDestination? = destination
        while (currentDestination?.id != destId && currentDestination?.parent != null) {
            currentDestination = currentDestination.parent
        }
        return currentDestination?.id == destId
    }

    private fun onNavDestinationSelected(
        item: MenuItem,
        navController: NavController,
        matcher: HashMap<Int, Int>,
        onOptionSeleted: ((MenuItem) -> Boolean)? = null
    ): Boolean {
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
        if (item.order and Menu.CATEGORY_SECONDARY == 0) {
            findStartDestination(navController.graph)?.id?.also {
                builder.setPopUpTo(it, false)
            }
        }
        val options = builder.build()
        return try {
            onOptionSeleted?.invoke(item)?.takeIf { it }
                ?: navController.goTo(matcher[item.itemId], options)
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun NavController.goTo(destinationId: Int?, options: NavOptions): Boolean {
        return destinationId?.let { navigate(it, null, options).let { true } } ?: false
    }

    private fun findStartDestination(graph: NavGraph): NavDestination? {
        var startDestination: NavDestination? = graph
        while (startDestination is NavGraph) {
            val parent = startDestination as? NavGraph?
            startDestination = parent?.findNode(parent.startDestination)
        }
        return startDestination
    }

}

package biz.belcorp.salesforce.base.utils.caoc.values

object CaocMessages {

    const val INSTALL_CONTEXT_NULL_ERROR = "Install failed: context is null!"
    const val INSTALL_UNKNOWN_ERROR =
        "An unknown error occurred while installing CustomActivityOnCrash, " +
            "it may not have been properly initialized. Please report this as a bug if needed."
    const val INSTALLED_ALREADY = "CustomActivityOnCrash was already installed, doing nothing!"
    const val INSTALL_DONE = "CustomActivityOnCrash has been installed."
    const val INSTALL_ANOTHER_HANDLER_INSTALLED =
        "IMPORTANT WARNING! You already have an UncaughtExceptionHandler, are you sure this is " +
            "correct? If you use a custom UncaughtExceptionHandler, you must initialize it AFTER" +
            " CustomActivityOnCrash! Installing anyway, but your original handler will not be called."

    const val EXECUTING_CAOC =
        "App has crashed, executing CustomActivityOnCrash's UncaughtExceptionHandler"
    const val CRASH_RECENTLY =
        "App already crashed recently, not starting custom error activity because we could enter a" +
            " restart loop. Are you sure that your app does not crash directly on init?"

    const val APPLICATION_OR_CUSTOM_CRASHED =
        "Your application class or your error activity have crashed, the custom activity will not be launched!"

    const val PREVIOUS_APP_CRASHED =
        "The previous app process crashed. This is the stack trace of the crash:\n"
    const val FRAGMENT_EXCEPTION_PATTERN = "does not have a view"
    const val EXCEPTION_EVENT_LISTENER =
        "The event listener cannot be an inner or anonymous class, because it will need to be " +
            "serialized. Change it to a class of its own, or make it a static inner class."

    const val RESOLVING_ERRROR_ACTIVITY_FAILED =
        "Failed when resolving the error activity class via intent filter, stack trace follows!"
    const val RESOLVING_RESTART_ACTIVITY_FAILED =
        "Failed when resolving the restart activity class via getLaunchIntentForPackage, stack trace follows!"
    const val RESOLVING_RESTART_ACTIVITY_INTENT_FAILED =
        "Failed when resolving the restart activity class via intent filter, stack trace follows!"

    const val STACK_TRACE_DISCLAIMER = " [stack trace too large]"
}



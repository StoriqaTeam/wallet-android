package com.storiqa.storiqawallet.ui.views.countdown

enum class ElapsedTimeUtil {

    MILLISECONDS {
        override fun toSeconds(d: Long): Long {
            return d % C4 % C3 % C2 / C1
        }

        override fun toMinutes(d: Long): Long {
            return d % C4 % C3 / C2
        }

        override fun toHours(d: Long): Long {
            return d % C4 / C3
        }

        override fun toDays(d: Long): Long {
            return d / C4
        }
    };

    open fun toSeconds(duration: Long): Long {
        throw AbstractMethodError()
    }

    open fun toMinutes(duration: Long): Long {
        throw AbstractMethodError()
    }

    open fun toHours(duration: Long): Long {
        throw AbstractMethodError()
    }

    open fun toDays(duration: Long): Long {
        throw AbstractMethodError()
    }

    companion object {

        // Handy constants for conversion methods
        internal val C1 = 1000L
        internal val C2 = C1 * 60L
        internal val C3 = C2 * 60L
        internal val C4 = C3 * 24L
    }

}
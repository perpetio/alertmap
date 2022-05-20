package com.perpetio.alertapp.data

import com.perpetio.alertapp.R

enum class RepeatInterval(
    val btnId: Int, val minutes: Int
) {
    Min(R.id.rb_min_period, 2),
    Middle(R.id.rb_middle_period, 5),
    Max(R.id.rb_max_period, 10)
}
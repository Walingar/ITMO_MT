package ru.walingar

import java.util.*

fun <T> List<T>.random() = get(Random().nextInt(size))
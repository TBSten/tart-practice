package me.tbsten.prac.tart.ui.navigation

sealed interface Route
interface Navigation : Route
interface Screen : Route
interface Dialog : Route

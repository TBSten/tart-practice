package me.tbsten.prac.tart.error

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationErrorStateHolder @Inject constructor() : AbstractErrorStateHolder()

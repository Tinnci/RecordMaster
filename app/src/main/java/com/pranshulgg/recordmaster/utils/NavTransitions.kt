package com.pranshulgg.recordmaster.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MotionScheme

object NavTransitions {

    fun enter(motionScheme: MotionScheme): EnterTransition =
        slideInHorizontally(
            animationSpec = motionScheme.defaultSpatialSpec(),
            initialOffsetX = { it }
        ) + fadeIn()

    fun exit(motionScheme: MotionScheme): ExitTransition =
        slideOutHorizontally(
            animationSpec = motionScheme.defaultSpatialSpec(),
            targetOffsetX = { -it }
        ) + fadeOut()

    fun popEnter(motionScheme: MotionScheme): EnterTransition =
        slideInHorizontally(
            animationSpec = motionScheme.defaultSpatialSpec(),
            initialOffsetX = { -it }
        ) + fadeIn()

    fun popExit(motionScheme: MotionScheme): ExitTransition =
        slideOutHorizontally(
            animationSpec = motionScheme.defaultSpatialSpec(),
            targetOffsetX = { it }
        ) + fadeOut()
}

package com.manmohan.nasaapod.di.scope


import androidx.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

@Documented
@MapKey
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

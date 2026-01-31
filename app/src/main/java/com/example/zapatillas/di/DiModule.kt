package com.example.zapatillas.di

import com.example.zapatillas.data.repositorio.RepositorioImpl
import com.example.zapatillas.domain.repositorio.Repositorio
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    fun provideRepositorio(): Repositorio = RepositorioImpl()
}
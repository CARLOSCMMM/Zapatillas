package com.example.zapatillas.di

import com.example.zapatillas.data.repositorio.RepositorioImpl
import com.example.zapatillas.domain.repositorio.Repositorio
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DiModule {
    @Binds
    @Singleton
    abstract fun provideRepositorio(repo: RepositorioImpl): Repositorio
}
package dk.colle.collememaybe

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.colle.collememaybe.repository.AuthRepository
import dk.colle.collememaybe.repository.BaseAuthRepository
import dk.colle.collememaybe.repository.firebase.BaseAuthenticator
import dk.colle.collememaybe.repository.firebase.FirebaseAuthenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseAuthenticator(): BaseAuthenticator {
        return FirebaseAuthenticator()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): BaseAuthRepository{
        return AuthRepository(provideBaseAuthenticator())
    }

}
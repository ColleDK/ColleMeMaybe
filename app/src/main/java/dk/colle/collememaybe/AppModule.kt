package dk.colle.collememaybe

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.colle.collememaybe.repository.auth.AuthRepository
import dk.colle.collememaybe.repository.auth.BaseAuthRepository
import dk.colle.collememaybe.repository.chat.BaseChatRepository
import dk.colle.collememaybe.repository.chat.ChatRepository
import dk.colle.collememaybe.repository.firebase.auth.BaseAuthenticator
import dk.colle.collememaybe.repository.firebase.auth.FirebaseAuthenticator
import dk.colle.collememaybe.repository.firebase.chat.BaseFirebaseChat
import dk.colle.collememaybe.repository.firebase.chat.FirebaseChat
import dk.colle.collememaybe.repository.firebase.user.BaseFirebaseUser
import dk.colle.collememaybe.repository.firebase.user.FirebaseUser
import dk.colle.collememaybe.repository.user.BaseUserRepository
import dk.colle.collememaybe.repository.user.UserRepository
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
    fun provideAuthRepository(): BaseAuthRepository {
        return AuthRepository(provideBaseAuthenticator())
    }

    @Provides
    @Singleton
    fun provideBaseFirebaseChat(): BaseFirebaseChat {
        return FirebaseChat()
    }

    @Provides
    @Singleton
    fun provideChatRepository(): BaseChatRepository {
        return ChatRepository(
            authenticator = provideBaseAuthenticator(),
            firebaseChat = provideBaseFirebaseChat()
        )
    }

    @Provides
    @Singleton
    fun provideBaseFirebaseUser(): BaseFirebaseUser {
        return FirebaseUser()
    }

    @Provides
    @Singleton
    fun provideUserRepository(): BaseUserRepository {
        return UserRepository(
            firebaseUser = provideBaseFirebaseUser()
        )
    }
}
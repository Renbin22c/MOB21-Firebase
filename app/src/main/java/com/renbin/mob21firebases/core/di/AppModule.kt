package com.renbin.mob21firebases.core.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.renbin.mob21firebases.core.service.StorageService
import com.renbin.mob21firebases.data.db.TodoDatabase
import com.renbin.mob21firebases.data.repo.TodosRepo
import com.renbin.mob21firebases.data.repo.TodosRepoFireStoreImpl
//import com.renbin.mob21firebases.data.repo.TodosRepoRealtimeImpl
//import com.renbin.mob21firebases.data.repo.TodosRepoRoomImpl
import com.renbin.mob21firebases.data.repo.UserRepo
import com.renbin.mob21firebases.data.repo.UserRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.android.gms.auth.api.identity.Identity
import com.renbin.mob21firebases.core.service.AuthService

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuth(): AuthService {
        return AuthService()
    }

    @Provides
    @Singleton
    fun provideStorageService(): StorageService{
        return StorageService()
    }

//    @Provides
//    @Singleton
//    fun provideFirebaseTodosCollection(): CollectionReference {
//        return FirebaseFirestore.getInstance().collection("todos")
//    }

    @Provides
    @Singleton
    fun provideFirebaseRealtimeRef(): DatabaseReference{
        return FirebaseDatabase.getInstance().getReference("todos")
    }

    @Provides
    @Singleton
    fun provideFirebaseTodosCollection(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideTodosRepoFireStore(db: FirebaseFirestore): TodosRepo {
        return TodosRepoFireStoreImpl(db.collection("todos"))
    }

    @Provides
    @Singleton
    fun provideUserRepoFireStore(db: FirebaseFirestore): UserRepo {
        return UserRepoImpl(db.collection("users"))
    }

//    @Provides
//    @Singleton
////    @Named(FIREBASE_REALTIME)
//    fun provideTodosRepoRealTime(db: DatabaseReference): TodosRepo {
//        return TodosRepoRealtimeImpl(db)
//    }





    

//    @Provides
//    @Singleton
//    fun provideRoomDb(@ApplicationContext context: Context): TodoDatabase {
//        return Room.databaseBuilder(
//            context,
//            TodoDatabase::class.java,
//            TodoDatabase.DB_NAME
//        ).fallbackToDestructiveMigration().build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideTodosRepoRoom(db: TodoDatabase): TodosRepo {
//        return TodosRepoRoomImpl(db.todoDao())
//    }

}
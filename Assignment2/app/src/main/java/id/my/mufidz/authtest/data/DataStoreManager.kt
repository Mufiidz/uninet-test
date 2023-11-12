package id.my.mufidz.authtest.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

class DataStoreManager(private val context: Context) {

    companion object {
        const val PREFERENCE_NAME = "USER"
        val TOKEN = stringPreferencesKey("token")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

//    suspend fun val token: String
//        set(value) = {
//            context.dataStore.edit { it[TOKEN] = value }
//        }
//        get() {
//            context.dataStore.data.collect {
//                it[TOKEN]
//            }
//        }
}
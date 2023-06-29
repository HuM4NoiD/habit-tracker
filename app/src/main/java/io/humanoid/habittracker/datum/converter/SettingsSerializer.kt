package io.humanoid.habittracker.datum.converter

import android.os.Parcel
import androidx.datastore.core.Serializer
import io.humanoid.habittracker.datum.model.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.parcelableCreator
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer: Serializer<AppSettings> {
    override val defaultValue: AppSettings = AppSettings()

    override suspend fun readFrom(input: InputStream): AppSettings {
        val parcel = Parcel.obtain()
        return try {
            val bytes = input.readBytes()
            parcel.unmarshall(bytes, 0, bytes.size)
            parcel.setDataPosition(0)
            val creator = parcelableCreator<AppSettings>()
            creator.createFromParcel(parcel)
        } finally {
            parcel.recycle()
        }
    }

    override suspend fun writeTo(t: AppSettings, output: OutputStream) {
        val parcel = Parcel.obtain()
        try {
            withContext(Dispatchers.IO) {
                t.writeToParcel(parcel, 0)
                output.write(parcel.marshall())
            }
        } finally {
            parcel.recycle()
        }
    }
}
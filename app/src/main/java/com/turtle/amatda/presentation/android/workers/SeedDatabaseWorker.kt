/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.turtle.amatda.presentation.android.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.usecases.InsertItemFromAssetsUseCase
import com.turtle.amatda.presentation.utilities.CHECKLIST_DATA_FILENAME


class SeedDatabaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val insertItemFromAssetsUseCase: InsertItemFromAssetsUseCase
) : Worker(appContext, params) {
    override fun doWork(): Result {
        try {
            applicationContext.assets.open(CHECKLIST_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->

                    val itemType = object : TypeToken<List<Item>>() {}.type
                    val itemList: List<Item> = Gson().fromJson(jsonReader, itemType)
                    insertItemFromAssetsUseCase.execute(itemList)
                        .subscribe({
                            Log.i(TAG, "Seeding Complete")
                        },{
                            Log.e(TAG, "Error subscrobe Completable is on Error")
                        })
                    return Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database From Asset", ex)
            return Result.failure()
        }
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory

    companion object {
        const val TAG = "SeedDatabaseWorker"
    }
}
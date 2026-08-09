package com.example.natureexplorer.data

import kotlinx.coroutines.flow.Flow


class TrailRepository(private val trailDao: TrailDao) {

    val allSavedTrails: Flow<List<SavedTrailEntity>> = trailDao.getAllSavedTrails()


    suspend fun addTrailToFavorites(name: String, imageUrl: String) {
        val newEntity = SavedTrailEntity(
            name = name,
            imageUrl = imageUrl,
            addedDate = "Added just now"
        )
        trailDao.insertTrail(newEntity)
    }


    suspend fun removeTrailFromFavorites(name: String) {
        trailDao.deleteTrail(name)
    }
}


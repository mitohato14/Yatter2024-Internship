package com.dmm.bootcamp.yatter2024.usecase.impl.image

import android.util.Log
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.usecase.image.ImageStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.image.ImageStatusUseCaseResult
import java.io.File

class ImageStatusUseCaseImpl(
    private val statusRepository: StatusRepository
) : ImageStatusUseCase{
    override suspend fun execute(
        image: File?,
    ): ImageStatusUseCaseResult{
        return try {
            if(image != null) {
                Log.d("fuga","Reach if block")
                val res = statusRepository.registerImage(image) // Media
                Log.d("fuga","Success register Image")
                ImageStatusUseCaseResult.Success(id = res.id, url = res.url)
            }else{
                Log.d("fuga","Reach else block")
                throw Exception()
            }
        }catch (e: Exception){
            Log.d("fuga","Reach $e")
            ImageStatusUseCaseResult.Failure.OtherError(e)
        }
    }
}
package com.dmm.bootcamp.yatter2024.domain.service

interface CheckRegisterService {
    suspend fun execute(): Boolean
}
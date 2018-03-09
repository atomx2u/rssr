package me.atomx2u.rssr.domain.arch

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

// UseCase 是业务抽象

interface UcRequest {
    object NONE : UcRequest
}

interface ObservableUseCase<in RQ : UcRequest, RP : Any> {
    fun execute(request: RQ): Observable<RP>
}

interface SingleUseCase<in RQ : UcRequest, RP : Any> {
    fun execute(request: RQ): Single<RP>
}

interface CompletableUseCase<in RQ : UcRequest> {
    fun execute(request: RQ): Completable
}
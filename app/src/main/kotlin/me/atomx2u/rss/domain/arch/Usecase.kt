package me.atomx2u.rss.domain.arch

import io.reactivex.CompletableSource
import io.reactivex.ObservableSource
import io.reactivex.Single

interface UseCase<R> {
    fun execute(): ObservableSource<R>
}

interface UseCaseWithRequest<in P : IRequest, R> {
    fun execute(request: P): ObservableSource<R>
}

interface SingleUseCase<R> {
    fun execute(): Single<R>
}

interface SingleUseCaseWithRequest<in P : IRequest, R> {
    fun execute(request: P): Single<R>
}

interface CompletableUseCase {
    fun execute(): CompletableSource
}

interface CompletableUse<in P> {
    fun execute(request: P): CompletableSource
}

interface CompletableUseCaseWithRequest<in P : IRequest> {
    fun execute(request: P): CompletableSource
}

interface SolvedCompletableUseCaseWithRequest<in P : IRequest> {
    fun execute(request: P): CompletableSource
}

interface IRequest
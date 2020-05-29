package s.yzrlykov.circlerecycler.stages.s03_1

import io.reactivex.Observable

interface EventObservable<T : Any> {
    fun connectTo(): Observable<T>
}
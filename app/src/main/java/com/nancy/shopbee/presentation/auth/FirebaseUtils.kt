package com.nancy.shopbee.presentation.auth


import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

//use this instead of callback on canceled complete listener
suspend fun <T> Task<T>.await(): T{

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception !=null){
                cont.resumeWithException(it.exception!!)
            } else{
                cont.resume(it.result, null)

            }

        }


    }

}
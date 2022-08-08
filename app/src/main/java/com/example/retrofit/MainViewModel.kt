package com.example.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

private val _myState= MutableStateFlow<MyResponseState>(MyResponseState.Empty) //mutable state flow
val myState:StateFlow<MyResponseState> = _myState //immutable state flow

fun getInfo(){
    viewModelScope.launch {
        _myState.value = MyResponseState.Loading
        val response: Response<List<MyDataItem>> = repository.getPost()
        val body: List<MyDataItem>? = response.body()
        if (response.isSuccessful && body != null) {
            _myState.emit(MyResponseState.Success(body))
        }
        else {
//            _myState.value = MyResponseState.Error(response.errorBody().toString())
            _myState.emit(MyResponseState.Error(response.errorBody().toString()))
        }
    }
}

sealed class MyResponseState {
    data class Success(val items: List<MyDataItem>) : MyResponseState()
    data class Error(val message: String?) : MyResponseState()
    object Loading : MyResponseState()
    object Empty : MyResponseState()

}
}
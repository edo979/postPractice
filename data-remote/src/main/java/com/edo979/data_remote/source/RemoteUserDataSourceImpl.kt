package com.edo979.data_remote.source

import com.edo979.data_remote.user.UserApiModel
import com.edo979.data_remote.user.UserService
import com.edo979.data_repository.data_source.remote.RemoteUserDataSource
import com.edo979.domain.entity.UseCaseException
import com.edo979.domain.entity.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(private val userService: UserService) :
    RemoteUserDataSource {

    override fun getUsers(): Flow<List<User>> = flow { emit(userService.getUsers()) }
        .map { users -> users.map(::convert) }
        .catch { throw UseCaseException.UserException(it) }

    override fun getUser(id: Long): Flow<User> = flow { emit(userService.getUser(id)) }
        .map(::convert)
        .catch { throw UseCaseException.UserException(it) }

    private fun convert(userApiModel: UserApiModel) = User(
        id = userApiModel.id,
        name = userApiModel.name,
        username = userApiModel.username,
        email = userApiModel.email
    )
}
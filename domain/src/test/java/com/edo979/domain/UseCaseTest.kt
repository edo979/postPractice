package com.edo979.domain

import com.edo979.domain.usecase.UseCase
import com.edo979.domain.entity.UseCaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock

class UseCaseTest {

    private val configuration = UseCase.Configuration(Dispatchers.IO)
    private val request = mock<UseCase.Request>()
    private val response = mock<UseCase.Response>()
    private lateinit var useCase: UseCase<UseCase.Request, UseCase.Response>

    @Test
    fun testExecuteSuccess() = runTest {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                Assert.assertEquals(this@UseCaseTest.request, request)
                return flowOf(response)
            }
        }

        val result = useCase.execute(request).first()
        Assert.assertEquals(Result.success(response), result)
    }

    @Test
    fun testExecuteUserException() = runTest {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                Assert.assertEquals(this@UseCaseTest.request, request)
                return flow {
                    throw UseCaseException.UserException(Throwable())
                }
            }
        }

        val result = useCase.execute(request).first()
        Assert.assertTrue(result.exceptionOrNull() is UseCaseException.UserException)
    }

    @Test
    fun testExecutePostException() = runTest {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                Assert.assertEquals(this@UseCaseTest.request, request)
                return flow {
                    throw UseCaseException.PostException(Throwable())
                }
            }
        }

        val result = useCase.execute(request).first()
        Assert.assertTrue(result.exceptionOrNull() is UseCaseException.PostException)
    }

    @Test
    fun testExecuteUnknownException() = runTest {
        useCase = object : UseCase<UseCase.Request, UseCase.Response>(configuration) {
            override fun process(request: Request): Flow<Response> {
                Assert.assertEquals(this@UseCaseTest.request, request)
                return flow {
                    throw RuntimeException()
                }
            }
        }

        val result = useCase.execute(request).first()
        Assert.assertTrue(result.exceptionOrNull() is UseCaseException.UnknownException)
    }
}
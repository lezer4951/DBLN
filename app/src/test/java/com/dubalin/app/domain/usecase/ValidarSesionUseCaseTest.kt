package com.dubalin.app.domain.usecase

import com.dubalin.app.domain.model.EstadisticasUsuario
import com.dubalin.app.domain.model.Usuario
import com.dubalin.app.domain.repository.SessionRepository
import com.dubalin.app.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidarSesionUseCaseTest {

    @Test
    fun `sin sesion devuelve false`() = runTest {
        val session = FakeSessionRepository(null)
        val useCase = ValidarSesionUseCase(session, FakeUsuarioRepository(null))

        assertFalse(useCase())
    }

    @Test
    fun `usuario existente conserva la sesion`() = runTest {
        val usuario = usuario(id = 7)
        val session = FakeSessionRepository(usuario.id)
        val useCase = ValidarSesionUseCase(session, FakeUsuarioRepository(usuario))

        assertTrue(useCase())
        assertTrue(session.getUsuarioId() == usuario.id)
    }

    @Test
    fun `usuario inexistente elimina la sesion`() = runTest {
        val session = FakeSessionRepository(7)
        val useCase = ValidarSesionUseCase(session, FakeUsuarioRepository(null))

        assertFalse(useCase())
        assertNull(session.getUsuarioId())
    }

    private fun usuario(id: Int) = Usuario(
        id = id,
        nombre = "Ada",
        correo = "ada@example.com",
        fechaRegistro = 1L,
        rachaDias = 0
    )
}

private class FakeSessionRepository(usuarioId: Int?) : SessionRepository {
    private val state = MutableStateFlow(usuarioId)
    override val usuarioId = state

    override fun getUsuarioId(): Int? = state.value

    override fun saveUsuarioId(id: Int) {
        state.value = id
    }

    override fun clearSession() {
        state.value = null
    }
}

private class FakeUsuarioRepository(
    private val usuario: Usuario?
) : UsuarioRepository {
    override suspend fun obtenerUsuario(usuarioId: Int): Usuario? =
        usuario?.takeIf { it.id == usuarioId }

    override fun observarUsuario(usuarioId: Int): Flow<Usuario?> =
        flowOf(usuario?.takeIf { it.id == usuarioId })

    override fun observarEstadisticas(usuarioId: Int): Flow<EstadisticasUsuario?> =
        flowOf(null)
}

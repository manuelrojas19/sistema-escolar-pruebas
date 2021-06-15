package mx.ipn.upiicsa.smbd.sistemaescolar.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

import mx.ipn.upiicsa.smbd.sistemaescolar.dao.ProfesorDao;
import mx.ipn.upiicsa.smbd.sistemaescolar.model.Profesor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private ProfesorDao profesorDao;
    private PasswordEncoder passwordEncoder;
    private ProfesoresService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProfesoresServiceImpl(profesorDao, passwordEncoder) {
        };
    }

    @Test
    void shouldAddProfesor() {
        String email = "manuelarr99@gmail.com";
        Profesor profesor = new Profesor();
        profesor.setPrimerNombre("Manuel");
        profesor.setPrimerApellido("Rojas");
        profesor.setSegundoApellido("Ramos");

        underTest.guardarProfesor(profesor);

        ArgumentCaptor<Profesor> profesorArgumentCaptorArgumentCaptor = ArgumentCaptor.forClass(Profesor.class);
        verify(profesorDao).addProfesor(profesorArgumentCaptorArgumentCaptor.capture());

        Profesor captorProfesor = profesorArgumentCaptorArgumentCaptor.getValue();
        assertThat(captorProfesor).isEqualTo(profesor);
    }
}
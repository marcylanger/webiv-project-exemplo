package com.springwebiv.model.service;

import java.time.OffsetDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import com.springwebiv.model.entity.RoleEnum;
import com.springwebiv.model.entity.Usuario;
import com.springwebiv.model.repository.UsuarioRepository;


public class UsuarioTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/


	/**
	 *
	 */
	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 */
	@Autowired
	private UsuarioService usuarioService;
	
	/*-------------------------------------------------------------------
	 *				 		      TESTS
	 *-------------------------------------------------------------------*/



	/**
	 * Serviço que ativa um usuário do sistema
	 */
	@Test
	@WithUserDetails("marcieli.langer@mailinator.com")
	@Sql( {		    
      "/dataset/truncate.sql",
			"/dataset/usuarios.sql"
	})
	public void ativarUserByIdMustPass(){
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com" );
		this.usuarioService.ativarUser( usuario.getId() );

		Usuario usersaved = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com" );
		Assert.assertEquals( false, usersaved.getDisabled() );
	}

	/**
	 * Serviço que inativa um usuário do sistema
	 */
	@Test
	@WithUserDetails("marcieli.langer@mailinator.com")
	@Sql( {		    
      "/dataset/truncate.sql",
			"/dataset/usuarios.sql"
	})
	public void desativarUserByIdMustPass(){
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com" );
		this.usuarioService.desativarUser( usuario.getId() );
		Usuario usersaved = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com");
		Assert.assertEquals( true, usersaved.getDisabled() );
	}	

	@Test
	@Sql( {		    
      "/dataset/truncate.sql",
			"/dataset/usuarios.sql"
	})
	public void resetPassWordTestMustPass(){
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com" );
		usuario.generateToken();
		usuario.setPasswordResetTokenExpiration( OffsetDateTime.parse("2019-12-03T10:15:30+01:00"));

		usuario = this.usuarioRepository.save( usuario );
		usuario = this.usuarioService.resetPassWord( "123456","123456", usuario.getPasswordResetToken() );
		Assert.assertNotNull( usuario.getPassword());
	}

	/**
	 * Serviço que redefinir senha de um usuário
	 * passando um token com data inválido
	 */
	@Test(expected = IllegalArgumentException.class)
	@Sql( {		    
	      "/dataset/truncate.sql",
				"/dataset/usuarios.sql"
		})
	public void resetPassWordTestInvalidDateMustFail(){
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com" );
		usuario.generateToken();
		usuario.setPasswordResetTokenExpiration( OffsetDateTime.parse("2017-12-03T10:15:30+01:00"));
		usuario = this.usuarioRepository.save( usuario );
		this.usuarioService.resetPassWord( "123456","123456", usuario.getPasswordResetToken() );

		Assert.fail( "Foi possivel redefinir a senha com token vencido" );
	}

	/**
	 * Serviço que redefinir senha de um usuário
	 * passando um token inválido
	 */
	@Test(expected = IllegalArgumentException.class)
	@Sql( {		    
	      "/dataset/truncate.sql",
				"/dataset/usuarios.sql"
		})
	public void resetPasswordTestInvalidTokenMustFail(){
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com" );
		usuario.generateToken();
		usuario.setPasswordResetTokenExpiration( OffsetDateTime.parse("2019-12-03T10:15:30+01:00"));
		usuario = this.usuarioRepository.save( usuario );
		this.usuarioService.resetPassWord( "123456","123456", "123123" );

		Assert.fail( "Foi possivel redefinir a senha com um token inválido" );
	}


	/**
	 * Serviço que redefinir senha de um usuário
	 * passando senhas que não conferem
	 */
	@Test(expected = IllegalArgumentException.class)
	@Sql( {		    
	      "/dataset/truncate.sql",
				"/dataset/usuarios.sql"
		})
	public void resetPassWordTestInvalidPasswordMustFail(){
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase( "marcieli.langer@mailinator.com" );
		usuario.generateToken();
		usuario.setPasswordResetTokenExpiration( OffsetDateTime.parse("2019-12-03T10:15:30+01:00"));
		usuario = this.usuarioRepository.save( usuario );
		this.usuarioService.resetPassWord( "123455","123456", usuario.getPasswordResetToken() );

		Assert.fail( "Foi possivel redefinir a senha de usuário com divergencia entre a senha e confirmação da senha" );
	}

	/**
	 *
	 */
	@Test
	@WithUserDetails("marcieli.langer@mailinator.com")
	@Sql( {		    
	      "/dataset/truncate.sql",
				"/dataset/usuarios.sql"
		})
	public void inativarUserTestMussPass()
	{
		this.usuarioService.inativarUser( 1001L );
		Usuario usuario = this.usuarioRepository.findById( 1001L ).orElse( null );
		Assert.assertEquals( true , usuario.getDisabled() );
	}



	/**
	 *
	 */
	@Test
	@WithUserDetails("marcieli.langer@mailinator.com")
	@Sql( {		    
	      "/dataset/truncate.sql",
				"/dataset/usuarios.sql"
		})
	public void cadastrarUsuarioMustPass()
	{
		Usuario usuario = new Usuario();
		usuario.setSenha( "123456" );
		usuario.setNome("Marcieli");
		usuario.setEmail( "marcieli@mailinator.com" );
		usuario.setPerfil( RoleEnum.ADMINISTRATOR );
		Usuario userSaved = this.usuarioService.cadastrarUsuario(usuario);
		Assert.assertNotNull( userSaved );
		Assert.assertNotNull( userSaved.getId() );

	}

}
package com.springwebiv.model.service;

import java.time.OffsetDateTime;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.springwebiv.configuration.settings.AppSettings;
import com.springwebiv.model.entity.Usuario;
import com.springwebiv.model.repository.IAccountMailRepository;
import com.springwebiv.model.repository.UsuarioRepository;
import com.springwebiv.security.RequestContext;


@Service
@Transactional
public class UsuarioService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * Password encoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	//Repositories
	/**
	 *
	 */
	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 */
	@Autowired
	private IAccountMailRepository accountMailRepository;

	/**
	 *
	 */
	@Autowired
	private AppSettings appSettings;

	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/

	/**
	 * Serviço para inserir um {@link User}
	 *
	 * @param user
	 * @return
	 */
	public Usuario cadastrarUsuario( Usuario usuario )
	{
		usuario.setDisabled( false );
		usuario.setSenha( this.passwordEncoder.encode( usuario.getPassword() ) );
		usuario = this.usuarioRepository.save( usuario );
		try
		{
			this.accountMailRepository.sendNewUserAccount( usuario ).get();
		}
		catch ( MailSendException | InterruptedException | ExecutionException e )
		{
			e.printStackTrace();
		}
		return usuario;
	}

	/**
	 * Serviço privado para buscar um {@link User}
	 *
	 * @param id
	 * @return
	 */
	public Usuario findUserById( long id )
	{
		Usuario usuario = this.usuarioRepository.findById( id )
				.orElseThrow( () -> new IllegalArgumentException( "Nenhum usuário encontrado." ));
		
		return usuario;
	}

	/**
	 * Serviço para ativar o {@link User} para acesso a plataforma
	 * É necessário ter a autorização de Gerenciar Usuarios Ativar Usuarios.
	 *
	 * @param userId
	 */
	public void ativarUser( Long usuarioId )
	{
		Usuario usuario = this.usuarioRepository.findById( usuarioId ).orElse( null );
		Assert.notNull(usuario, "Nenhum usuário encontrado.");
		usuario.setDisabled( false );
		usuario.generateToken();
		usuario.setPasswordResetTokenExpiration( OffsetDateTime.now().plusDays( 1 ) );
		usuario = this.usuarioRepository.save( usuario );
		try
		{
			this.accountMailRepository.sendAccountActivated( usuario, false );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

	}

	/**
	 * Serviço para desativar um {@link User} para acesso a plataforma
	 * É necessário ter a autorização de Gerenciar Usuarios Ativar Usuarios.
	 *
	 * @param userId
	 */
	public void desativarUser( Long usuarioId )
	{
		Usuario usuario = this.usuarioRepository.findById( usuarioId ).orElse( null );
		Assert.notNull(usuario, "Nenhum usuário encontrado.");
		usuario.setDisabled( true );
		this.usuarioRepository.save( usuario );

		try
		{
			this.accountMailRepository.sendAccountInactivated( usuario );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

	}


	/**
	 * Serviço para inativar um {@link User} utilizado ao dar baixa em {@link ApoliceSeguro}.
	 * Desativa seu acesso a plataforma.
	 * Por isso não possui permissão!
	 * Obs: Utilizar o método de desativarUser quando precisar de permissão
	 *
	 * @param userId
	 */
	public Usuario inativarUser( Long usuarioId )
	{
		Usuario usuario = this.usuarioRepository.findById( usuarioId ).orElse( null );
		Assert.notNull(usuario, "Nenhum usuário encontrado.");
		usuario.setDisabled( true );
		return this.usuarioRepository.save( usuario );
	}

	/**
	 * Serviço para redifinir a senha de um {@link User}
	 *
	 * @param senha
	 * @param confirmacaoSenha
	 * @param passwordResetToken
	 * @return
	 */
	public Usuario resetPassWord( String senha, String confirmacaoSenha, String passwordResetToken )
	{
		OffsetDateTime dateTime = OffsetDateTime.now();
		Assert.notNull( passwordResetToken,
				"Token inválido." );
		Assert.isTrue( senha.equals( confirmacaoSenha ),
				"Senha inválida." );
		Usuario usuario = this.usuarioRepository.findByPasswordResetToken( passwordResetToken ).orElse( null );
		Assert.notNull(usuario, "Token inválido");
		Assert.isTrue( usuario.getPasswordResetTokenExpiration().isAfter( dateTime ),
				"Token inválido." );
		usuario.setSenha( this.passwordEncoder.encode( senha ) );
		return this.usuarioRepository.save( usuario );
	}

	/**
	 * Serviço que envia um e-mail para recuperar a senha do {@link User}
	 * Gera um token com um link para acesso a redefinição de senha
	 *
	 * @param email
	 */
	public void enviarEmailRecuperarSenhaUsuario( String email )
	{
		Usuario usuario = this.usuarioRepository.findByEmailIgnoreCase( email );

		Assert.notNull( usuario,
				"E-mail inválido." );

		usuario.generateToken();
		usuario.setPasswordResetTokenExpiration( OffsetDateTime.now().plusDays( 1 ) );
		usuario = this.usuarioRepository.save( usuario );

		try
		{
			this.accountMailRepository.sendAccountActivated( usuario, true );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

	}

	/**
	 * Serviço que trás todas as informações de um {@link User} logado
	 * Precisamos disso pois no método de currentUser() não trás todas as informações
	 * Então utilizamos o findUserById para buscar o {@link User} no banco
	 *
	 * @return
	 */
	public Usuario getAllInformationsAuthenticatedUser()
	{
		return this.findUserById( this.getAuthenticatedUser().getId() );
	}


	/**
	 * Serviço que trás o {@link User} logado
	 * Obs: não tem todos os dados do {@link User},
	 * se precisar de mais dados utilizar o método getAllInformationsAuthenticatedUser()
	 *
	 * @return
	 */
	public Usuario getAuthenticatedUser()
	{
		return RequestContext.currentUser().orElse( null );
	}

	/**
	 * RFU0108
	 * Serviço para "alterar minha conta"
	 *
	 * @param user
	 */
	public Usuario updateMyAccount( Usuario usuario, String password, String confirmationPassword )
	{
		if ( password != null && confirmationPassword != null )
		{
			//verificamos se não é ''
			Assert.hasText( password, "Informe sua senha." );
			Assert.hasText( confirmationPassword, "Informe a configuração de senha." );

			//verificamos a senha se é igual a confirmação
			Assert.isTrue( password.equals( confirmationPassword ),
					"As senhas não conferem." );
			usuario.setSenha( this.passwordEncoder.encode( password ) );
		}
		return this.usuarioRepository.save( usuario );
	}

}
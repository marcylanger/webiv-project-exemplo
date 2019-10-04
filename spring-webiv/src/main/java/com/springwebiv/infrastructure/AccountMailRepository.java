package com.springwebiv.infrastructure;

import java.util.concurrent.Future;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import com.springwebiv.configuration.settings.AppSettings;
import com.springwebiv.model.entity.Usuario;
import com.springwebiv.model.repository.IAccountMailRepository;


/**
 *
 */
@Component
public class AccountMailRepository implements IAccountMailRepository
{
	/*-------------------------------------------------------------------
	 *                          ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 *
	 */
	private final Mailer mailer;
	/**
	 *
	 */
	private final AppSettings appSettings;

	@Autowired
	public AccountMailRepository( Mailer mailer, AppSettings appSettings )
	{
		this.mailer = mailer;
		this.appSettings = appSettings;
	}

	/*-------------------------------------------------------------------
	 *                          BEHAVIORS
	 *-------------------------------------------------------------------*/

	/**
	 * @param usuario
	 * @return
	 */
	@Async
	@Override
	public Future<Void> sendNewUserAccount( Usuario usuario )
	{
		Context context = new Context();
		context.setVariable( "user", usuario );
		Mailer.MessagePreparer preparer = ( messageHelper, mimeMessage ) ->
		{
			messageHelper.setSubject( "Nova conta de usuário" );
			messageHelper.setTo( usuario.getEmail().toLowerCase() );
			messageHelper.setFrom( appSettings.getMailFrom() );
		};
		return mailer.sendMessage(

				"mail/new-account",
				context,
				preparer
		);
	}

	/**
	 *
	 * @param usuario
	 * @return
	 */
	@Override
	public Future<Void> sendPasswordReset( Usuario usuario )
	{
		return new AsyncResult<>( null );
	}

	/**
	 *
	 * @param usuario
	 * @return
	 */
	@Override
	public Future<Void> sendPasswordResetNotice( Usuario usuario )
	{
		return new AsyncResult<>( null );
	}

	/**
	 * @param usuario
	 * @return
	 */
	@Override
	public Future<Void> sendAccountActivated( Usuario usuario, Boolean emailEsqueciSenha )
	{
		Context context = new Context();
		context.setVariable( "user", usuario );
		context.setVariable( "emailEsqueciSenha", emailEsqueciSenha );
		String link = appSettings.getExternalUrl() + "/authentication#/redefinir-senha/";
		context.setVariable( "link", link );

		Mailer.MessagePreparer preparer = ( messageHelper, mimeMessage ) ->
		{
			if ( !emailEsqueciSenha )
			{
				messageHelper.setSubject( "Conta ativada" );
			}
			else
			{
				messageHelper.setSubject( "Recuperar senha" );
			}
			messageHelper.setTo( usuario.getEmail().toLowerCase() );
			messageHelper.setFrom( appSettings.getMailFrom() );
		};

		return mailer.sendMessage(
				"mail/account-activated",
				context,
				preparer
		);
	}

	/**
	 *
	 * @param usuario
	 * @return
	 */
	@Override
	public Future<Void> sendAccountInactivated( Usuario usuario )
	{
		Context context = new Context();
		context.setVariable( "user", usuario );

		Mailer.MessagePreparer preparer = ( messageHelper, mimeMessage ) ->
		{
			messageHelper.setSubject( "Inativação de conta" );
			messageHelper.setTo( usuario.getEmail().toLowerCase() );
			messageHelper.setFrom( appSettings.getMailFrom() );
		};
		return mailer.sendMessage(
				"mail/account-inactivated",
				context,
				preparer
		);
	}

}
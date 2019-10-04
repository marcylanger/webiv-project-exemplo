package com.springwebiv.model.repository;

import java.util.concurrent.Future;

import com.springwebiv.model.entity.Usuario;


/**
 *
 */
public interface IAccountMailRepository
{
	/*-------------------------------------------------------------------
     *                          BEHAVIORS
     *-------------------------------------------------------------------*/

	/**
	 *
	 * @param user
	 * @return
	 */
	Future<Void> sendNewUserAccount( Usuario usuario );

	/**
	 *
	 * @param user
	 * @return
	 */
	Future<Void> sendPasswordReset( Usuario usuario );

	/**
	 *
	 * @param user
	 * @return
	 */
	Future<Void> sendPasswordResetNotice( Usuario usuario );

	/**
	 *
	 * @param user
	 * @return
	 */
	Future<Void> sendAccountActivated( Usuario usuario, Boolean emailEsqueciSenha );

	/**
	 *
	 * @param user
	 * @return
	 */
	Future<Void> sendAccountInactivated( Usuario usuario );
}
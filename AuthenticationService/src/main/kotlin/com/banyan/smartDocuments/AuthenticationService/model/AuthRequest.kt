package com.banyan.smartDocuments.AuthenticationService.model

/** * AuthRequest is a data class that represents the authentication request payload.
 * It contains the username and password fields required for user authentication.
 *
 * @property username The username of the user trying to authenticate.
 * @property password The password of the user trying to authenticate.
 */
data class AuthRequest( var username: String,
                        var password: String)

package com.banyan.smartDocuments.AuthenticationService.model

/**
 * NewUserRegistrationRequest is a data class that represents the request payload for registering a new user.
 * It contains the username, password, and roles of the user being registered.
 *
 * @property username The username of the new user.
 * @property password The password of the new user.
 * @property roles The set of roles assigned to the new user.
 */
data class NewUserRegistrationRequest(var username: String,
                                      var password: String,
                                      var roles: Set<String>
)

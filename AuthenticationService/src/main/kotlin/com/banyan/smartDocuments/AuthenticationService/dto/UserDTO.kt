package com.banyan.smartDocuments.AuthenticationService.dto

/**
 * UserDTO is a data transfer object that represents a user in the authentication service.
 * It contains the user's ID, username, and roles.
 * This DTO is used to transfer user data between the service and the client.
 */
data class UserDTO (  var id: Long?,
                      var username: String,
                      var roles: Set<String>)
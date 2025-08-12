package com.banyan.smartDocuments.AuthenticationService.dto

data class UserDTO (  var id: Long?,
                      var username: String,
                      var roles: Set<String>)
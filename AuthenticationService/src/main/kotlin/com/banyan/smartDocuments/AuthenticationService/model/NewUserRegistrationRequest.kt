package com.banyan.smartDocuments.AuthenticationService.model

data class NewUserRegistrationRequest(var username: String,
                                      var password: String,
                                      var roles: Set<String>
)

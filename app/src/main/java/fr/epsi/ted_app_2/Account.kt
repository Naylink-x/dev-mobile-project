package fr.epsi.ted_app_2

import java.io.Serializable

class Account(val lastName: String, val firstName: String, val email: String,
              val address: String, val zipCode: String, val city: String,
              val cardRef: String): Serializable
{
}
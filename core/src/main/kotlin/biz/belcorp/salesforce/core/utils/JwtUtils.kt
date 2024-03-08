package biz.belcorp.salesforce.core.utils

import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.TextCodec

const val KEY_TYP = "typ"
const val VALUE_JWT = "JWT"
const val KEY_ALG = "alg"
const val VALUE_HS256 = "HS256"

private const val DATA = "kjsfg!)=)4diof25sfdg302dfg57438)!#\$#70dfgf234asdnan"

fun createJwtPath(builder: JwtBuilder.() -> Unit) =
    Jwts.builder()
        .apply { builder.invoke(this) }
        .signWith(
            SignatureAlgorithm.HS256,
            TextCodec.BASE64.encode(DATA)
        )
        .compact()!!

package com.fodapi.services;

import com.fodapi.components.UserComponent;
import com.fodapi.entity.UsersEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Autowired
    Environment env;

    public String createJWT(String issuer, String subject, long currentMilis) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Date now = new Date(currentMilis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(env.getProperty("jwt.secret_key"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = currentMilis + Integer.parseInt(env.getProperty("jwt.expiration_time"));
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }

    public Claims decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(env.getProperty("jwt.secret_key")))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}
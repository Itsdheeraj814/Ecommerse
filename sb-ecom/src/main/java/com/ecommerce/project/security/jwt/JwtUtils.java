package com.ecommerce.project.security.jwt;

import com.ecommerce.project.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    //Getting JWT from header
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.app.jwtCookieName}")
    private String  jwtCookie;
//    public String getJwtFromHeader(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        logger.debug("Authorization Header :{}",bearerToken);
//        if(bearerToken!=null && bearerToken.startsWith("Bearer ")){
//            return bearerToken.substring(7);//removes the bearer token from prefex
//        }
//        return null;
//    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie= WebUtils.getCookie(request,jwtCookie);
        if(cookie!=null){
           return cookie.getValue();
        }
        else{
            return null;
        }
    }
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie,jwt)
                .path("/api")
                .maxAge(24*60*60)
                .httpOnly(false)
                .build();
        return cookie;

    }
    //generate the token form username
    public String generateTokenFromUsername(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(key())
                .compact();
    }
    //getting username form jwt token
    public  String getUserNameFromJETToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    //generate the signed key
    private Key key(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );


    }
    public boolean validateJwtToken(String authToken){
        try{
            System.out.println("Validate");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException exception){
            logger.error("Invalid JWT token : {}", exception.getMessage());
        } catch (ExpiredJwtException exception){
            logger.error("Expired JWT token : {}", exception.getMessage());
        }catch (UnsupportedJwtException exception){
            logger.error("Unsupported JWT token : {}", exception.getMessage());
        }catch (IllegalArgumentException exception){
            logger.error("JWT claims string is empty : {}", exception.getMessage());
        }
        return false;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie,null)
                .path("/api")
                .build();
        return cookie;
    }
}

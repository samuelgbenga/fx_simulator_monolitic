package ng.samuel.fxmono.simulator.auth.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import ng.samuel.fxmono.simulator.exception.exceptions.InvalidTokenException;
import ng.samuel.fxmono.simulator.utils.EnvVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class TokenProviderImpl implements TokenProvider{

    private final Key tokenSecret;

    @Autowired
    public TokenProviderImpl(EnvVariable env) {
        this.tokenSecret = this.convertStringToKey(env.TOKEN_SECRET());
    }

    @Override
    public Token generateToken(String email, int userId, Token.TokenType tokenType) {
        final long accessTokenExpirationMS = 1000 * 60 * 60;  // 1-hour
        final long refreshTokenExpirationMS = 1000 * 60 * 60 * 24 * 14; // 14-days
        final long resetLinkTokenExpirationMS = 1000 * 60 * 10; // 10-min

        long duration = 0;
        switch (tokenType) {
            case ACCESS -> duration = accessTokenExpirationMS;
            case REFRESH -> duration = refreshTokenExpirationMS;
            case PW_RESET -> duration = resetLinkTokenExpirationMS;
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + duration);
        // Since setSubject() only accepts String, I need to concat the email and id "{email}_{id}"
        // then split this string after parsing the JWT in the filter
        String emailAndId = email + "_" + userId;

        String token = Jwts.builder()
                .setSubject(emailAndId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(tokenSecret, SignatureAlgorithm.HS256)
                .compact();

        return new Token(tokenType, token, duration,
                LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    @Override
    public String getUserInfoFromToken(String token) {
        // the "Jwts.parser()" is deprecated, need to use "parserBuilder()"
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).build()
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    @Override
    public LocalDateTime getExpiryDateFromToken(String token) {
        // the "Jwts.parser()" is deprecated, need to use "parserBuilder()"
        Claims claims = Jwts.parser().setSigningKey(tokenSecret).build()
                .parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    @Override
    public boolean validateToken(String token) {
        if(token == null) throw new InvalidTokenException("Invalid token caught in - TokenProvider.validateToken");

        try {
            // the "Jwts.parser()" is deprecated, need to use "parserBuilder()"
            Jwts.parser().setSigningKey(tokenSecret).build().parse(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException
                 | IllegalArgumentException
                 | DecodingException exc) {
            exc.printStackTrace();
        }
        return false;
    }

    private Key convertStringToKey(String tokenSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

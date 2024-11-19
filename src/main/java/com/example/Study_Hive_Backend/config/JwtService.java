// package com.example.Study_Hive_Backend.config;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import java.security.Key;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.function.Function;

// @Service
// public class JwtService {

//     private static final String SECRET_KEY = "d1f4a65c7576bc02d78e962cc694b3c715c076fdd9ac971db4acf337d6ed289e";
//     private static final long JWT_EXPIRATION = 1000 * 60 * 24; // 24 hours

//     public String extractUsername(String token) {
//         return extractClaim(token, Claims::getSubject);
//     }

//     public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//         final Claims claims = extractAllClaims(token);
//         return claimsResolver.apply(claims);
//     }

//     public String generateToken(UserDetails userDetails) {
//         return generateToken(new HashMap<>(), userDetails);
//     }

//     public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//         return Jwts.builder()
//                 .setClaims(extraClaims)
//                 .setSubject(userDetails.getUsername())
//                 .setIssuedAt(new Date(System.currentTimeMillis()))
// <<<<<<< thushanka-dev
//                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 24 hours
// =======
//                 .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
// >>>>>>> main
//                 .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     public boolean isTokenValid(String token, UserDetails userDetails) {
//         try {
//             final String username = extractUsername(token);
//             return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//         } catch (Exception e) {
//             // Log the exception and handle it (e.g., token is invalid or expired)
//             return false;
//         }
//     }

//     public boolean validateToken(String token) {
//         try {
//             extractAllClaims(token); // Just trying to parse the token
//             return true;
//         } catch (Exception e) {
//             // Log the exception and handle it (e.g., token is invalid or expired)
//             return false;
//         }
//     }

//     public boolean isTokenExpired(String token) {
//         return extractAllClaims(token).getExpiration().before(new Date());
//     }


//     private Date extractExpiration(String token) {
//         return extractClaim(token, Claims::getExpiration);
//     }

// <<<<<<< thushanka-dev

//     private Claims extractAllClaims(String token) {
//         return Jwts
//                 .parserBuilder()
//                 .setSigningKey(getSignInKey())
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
// =======
//     // Example code for verifying a token
//     public Claims extractAllClaims(String token) {
//         try {
//             return Jwts.parser()
//                     .setSigningKey(SECRET_KEY) // Ensure SECRET_KEY is correct
//                     .parseClaimsJws(token)
//                     .getBody();
//         } catch (Exception e) {
//             throw new RuntimeException("Invalid token");
//         }
// >>>>>>> main
//     }


//     private Key getSignInKey() {
//         byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//         return Keys.hmacShaKeyFor(keyBytes);
//     }
// }

package com.example.Study_Hive_Backend.config;

import com.example.Study_Hive_Backend.user.Role;
import com.example.Study_Hive_Backend.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "d1f4a65c7576bc02d78e962cc694b3c715c076fdd9ac971db4acf337d6ed289e";
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }



    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }


    public String generateToken(UserDetails userDetails) {
        if (userDetails instanceof User user) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("firstname", user.getFirstname());
            claims.put("lastname", user.getLastname());
            return generateToken(claims, userDetails);
        }
        return generateToken(new HashMap<>(), userDetails);
    }

    public String extractFullName(String token) {
        String firstname = extractClaim(token, claims -> claims.get("firstname", String.class));
        String lastname = extractClaim(token, claims -> claims.get("lastname", String.class));
        return firstname + " " + lastname;
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        } catch (Exception e) {
            // Log the exception and handle it (e.g., token is invalid or expired)
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // Just trying to parse the token
            return true;
        } catch (Exception e) {
            // Log the exception and handle it (e.g., token is invalid or expired)
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


package com.example.steamflix_be.aspects;

import com.example.steamflix_be.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Autowired private JwtService jwtService;

    @Autowired private HttpServletRequest request;

    private static final String USER_ID_ATTRIBUTE = "userId";

    @Around("@annotation(com.example.steamflix_be.annotations.SecureRoute)")
    public Object validateJwtToken(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Extract userId from JWT in Authorization header
            String userId = jwtService.extractUserIdFromRequest(request);
            request.setAttribute(USER_ID_ATTRIBUTE, userId);
            return joinPoint.proceed();

        } catch (io.jsonwebtoken.JwtException e ) {
            // JWT library error
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body( "Unauthorized: " + e.getMessage());

        } catch (IllegalArgumentException e) {
            // Token missing or invalid
            return ResponseEntity
                    .badRequest()
                    .body("Invalid token: " + e.getMessage());

        } catch (Exception e) {
            // Other
            return ResponseEntity
                    .internalServerError()
                    .body("Internal Error: " + e.getMessage());
        }
    }
}

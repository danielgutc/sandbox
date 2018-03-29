package org.dgc.sandbox.jwt.client.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
public class JwtSecurityContextRepository implements ServerSecurityContextRepository
{
    @Value("${application.properties.security.secret}")
    private String secret;

    @Override
    public Mono<Void> save(ServerWebExchange serverWebExchange, SecurityContext securityContext)
    {
        return null;
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange serverWebExchange)
    {
        Authentication authentication = null;
        List<String> tokenHeaders = serverWebExchange.getRequest().getHeaders().get("Authorization");

        if (tokenHeaders != null)
        {
            try
            {
                String user = Jwts.parser()
                        .setSigningKey(this.secret)
                        .parseClaimsJws(tokenHeaders.get(0).split(" ")[1])
                        .getBody()
                        .getSubject();

                authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

                return Mono.just(new SecurityContextImpl(authentication));
            }
            catch (SignatureException ex)
            {
                ex.printStackTrace();
            }
            catch (ExpiredJwtException ex)
            {
                ex.printStackTrace();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        authentication = new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
        authentication.setAuthenticated(false);

        return Mono.just(new SecurityContextImpl(authentication));
    }
}

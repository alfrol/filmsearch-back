package ee.taltech.iti0203backend.config;

import ee.taltech.iti0203backend.security.CustomAuthenticationEntryPoint;
import ee.taltech.iti0203backend.security.JwtRequestFilter;
import ee.taltech.iti0203backend.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomUserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Configure AuthenticationManager.
     *
     * It must know from where to load users for matching credentials.
     * For password matching use BCryptPasswordEncoder.
     *
     * @param auth - AuthenticationManager instance to configure.
     * @throws Exception if some error occurs.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()

                // make sure we use stateless session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().headers().httpStrictTransportSecurity().disable()

                // dont authenticate these particular requests
                .and().authorizeRequests()
                .antMatchers("/films").permitAll()
                .antMatchers("/films/detail/*").permitAll()
                .antMatchers("/films/filter/*").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/users/register").permitAll()

                // These requests allow access only for users with certain roles
                .antMatchers(HttpMethod.POST,"/users/add_film").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/users?username=*").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.PUT, "/users/update_user").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/users/wishlist/*").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/users/delete/*/*").hasAnyRole("ADMIN", "USER")

                // all other requests need to be authenticated
                .anyRequest().authenticated()

                // Add a filter to validate the tokens with every request
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                // Logout handling
                .logout().logoutSuccessUrl("/login");
    }
}

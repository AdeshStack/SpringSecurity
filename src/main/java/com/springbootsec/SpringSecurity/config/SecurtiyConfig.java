package com.springbootsec.SpringSecurity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // this is config class
@EnableWebSecurity  //-> don't use the default configuration use this one
public class SecurtiyConfig {


    @Autowired
    private UserDetailsService userDetailsService;


    //this filter is created by ourself
    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//          http.csrf(customizer->customizer.disable());
//          http.authorizeHttpRequests(request-> request.anyRequest().authenticated());// make any request acceptable
//          http.formLogin(Customizer.withDefaults()); // giving bydefault login form for browser
//          http.httpBasic(Customizer.withDefaults()); //for enable the basic auth in postman
//         http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // session keep changing in each request

               return   http
                          .csrf(customizer->customizer.disable())
                          .authorizeHttpRequests(request-> request
                                  .requestMatchers("register","login")
                                  .permitAll()
                                  .anyRequest().authenticated())// make other to be authenticated
//                            .formLogin(Customizer.withDefaults()) // giving bydefault login form for browser
                            .httpBasic(Customizer.withDefaults()) //for enable the basic auth in postman
                           .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                       .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)// it say dont go for taking username and pass , go for this filterchain
                       .build(); // session keep changing in each request


    }

//    @Bean// all these things is built in spring // this username and password is used to authenticate.
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1=User.withDefaultPasswordEncoder().username("adesh").password("a@123").roles("USER").build();
//        UserDetails user12=User.withDefaultPasswordEncoder().username("Mahesh").password("M@123").roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user1,user12);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // used to verifying the bcryptpassword from db
        provider.setUserDetailsService(userDetailsService); //setting user details coming from db
        return  provider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

       return config.getAuthenticationManager();
    }


}

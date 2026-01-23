package com.kh.reactrip.configuration;

import java.util.Arrays;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.reactrip.configuration.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigure {

   private final JwtFilter jwtFilter;
   
   @Value("${instance.url}")
   private String instance;

   // 우리의 문제점 : 시큐리티의 formLogin필터가 자꾸만 인증이 안됐다고 회원가입도 못하게함
   // 해결방법 : form로그인 안쓸래 하고 fillterChain을 빈으로 등록

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
      // return httpSecurity.formLogin().disable().build();
      /*
       * return httpSecurity.formLogin(new
       * Customizer<FormLoginConfigurer<HttpSecurity>>() {
       * 
       * @Override public void customize(FormLoginConfigurer<HttpSecurity> t) {
       * t.disable(); } }).build();
       */

      // formLogin필터를 사용안함으로써 401은 지나갔는데 ==> 403이 뜸
      // CSRF(Cross-Site Request Forgery)필터가 튀어나옴
      // <img src="http://www.naver.com" />

      // Example ) 회원가입, 로그인 => 누구나 다 할 수 있어야함

      // 회원정보수정, 회원탈퇴 => 로그인 된 사용자만 할 수 있어야 함

      return httpSecurity
              .formLogin(AbstractHttpConfigurer::disable)
              .csrf(AbstractHttpConfigurer::disable)
              .cors(Customizer.withDefaults())
              .authorizeHttpRequests(requests -> {

                  // 1. POST - 비로그인 허용 (회원가입/로그인, 차량/예약 등)
                  requests.requestMatchers(HttpMethod.POST,
                          "/api/members/login",
                          "/api/members",
                          "/api/members/**",
                          "/api/auth/refresh",
                          "/api/cars/**",
                          "/api/station/**",
                          "/api/reserve/**",
                          "/api/**"
                          "/api/admin/members",
                          "/api/admin/**"
                  ).permitAll();

                  // 2. GET - 비로그인 허용 (목록/조회용)
                  requests.requestMatchers(HttpMethod.GET,
                          "/uploads/**",
                          "/api/members/**",
                          "/api/cars/**",
                          "/api/station/**",
                          "/api/station/search",
                          "/api/boards",
                          "/api/boards/search",
                          "/api/imgBoards",
                          "/api/imgBoards/search",
                          "/api/notices",
                          "/api/notices/search",
                          "/api/comments/**",
                          "/api/imgComments/**",
                          "/api/reserve/**",
                          "/api/reviews/**",
                          "/api/main",
                          "/api/places/**",
                		  "/api/admin/members",
                		  "/api/admin/members/search",
                		  "/api/admin/**"
                
                  ).permitAll();
                  
                  requests.requestMatchers(HttpMethod.PUT,
                		  "/api/admin/members",
                		  "/api/admin/members/search",
                		  "/api/admin/members/**"
                  ).permitAll();
                  
                  requests.requestMatchers(HttpMethod.DELETE,
                		  "/api/admin/members",
                		  "/api/admin/members/search",
                		  "/api/**"
                  ).permitAll();

                  
                  // 3. GET - 로그인 필요 (상세 페이지들)
                  requests.requestMatchers(HttpMethod.GET,
                          "/api/boards/*",
                          "/api/imgBoards/*",
                          "/api/notices/*"
                  ).authenticated();
                  

                  // 4. PUT - 로그인 필요
                  requests.requestMatchers(HttpMethod.PUT,
                          "/api/**"
                  ).authenticated();

                  // 5. DELETE - 로그인 필요
                  requests.requestMatchers(HttpMethod.DELETE,
                          "/api/members",
                          "/api/boards/**",
                          "/api/imgBoards/**", 
                          "/api/comments/**",
                          "/api/imgComments/**",
                          "/api/reserve/**", 
                          "/api/reviews/**",
                          "/api/station/**"
                  ).authenticated();


                  // 6. POST - 게시글/댓글/공지 작성 (로그인 필요)
                  requests.requestMatchers(HttpMethod.POST,
                          "/api/boards/**",
                          "/api/imgBoards/**",
                          "/api/comments/**",
                          "/api/imgComments/**",
                          "/api/notices/**",
                          "/api/reviews/**"
                  ).authenticated();

                  /*
                  // 7. 관리자 전용
                  requests.requestMatchers(HttpMethod.GET,
                          "/api/admin/ranking/users",
                          "/api/admin/**",
                          "/api/admin/settings/**",
                          "/api/admin/notice/list",
                          "/api//admin/community/**",
                          "/api/uploads/**",
                          "/api/admin/**"
                  ).hasAuthority("ROLE_ADMIN");

                  requests.requestMatchers(HttpMethod.POST,
                          "/api/admin/**",
                          "/api/admin/settings/**",
                          "/api/admin/notice/**",
                          "/api/admin/**"
                  ).hasAuthority("ROLE_ADMIN");

                  requests.requestMatchers(HttpMethod.PUT,
                          "/api/admin/**",
                          "/api/admin/settings/**",
                          "/api/admin/notice/**",
                          "/api/admin/**"
                  ).hasAuthority("ROLE_ADMIN");

                  requests.requestMatchers(HttpMethod.DELETE,
                          "/api/admin/**",
                          "/api/api/admin/**",
                          "/api/admin/**",
                          "/api/admin/notice/**",
                          "/api/admin/community/**",
                          "/api/admin/**"
                  ).hasAuthority("ROLE_ADMIN");
                  */
              })
              .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
              .build();


   }

   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(Arrays.asList(instance));
      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
      configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-type"));
      configuration.setAllowCredentials(true);
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }
   
   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
      return authConfig.getAuthenticationManager();
   }

}


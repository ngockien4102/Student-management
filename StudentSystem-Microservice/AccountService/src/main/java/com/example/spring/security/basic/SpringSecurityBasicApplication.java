package com.example.spring.security.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityBasicApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityBasicApplication.class, args);
    }

//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
////         Khi chương trình chạy
////         Insert vào csdl một user.
//        User user = new User();
//        user.setUsername("kiennn");
//        user.setPassword(passwordEncoder.encode("123"));
//        userRepository.save(user);
//        System.out.println(user);
//    }

}
